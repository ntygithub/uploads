from turtle import width
import cv2
from win32gui import FindWindow, GetWindowRect
import numpy as np
import PySimpleGUI as sg
class MousePts:
    def __init__(self,windowname,img1,heightImg,widthImg):
        self.windowname = windowname
        self.img11 = img1.copy()
        self.img1 = self.img11.copy()
        cv2.namedWindow(windowname,cv2.WINDOW_NORMAL)
        cv2.resizeWindow(windowname, (widthImg, heightImg))
        cv2.imshow(windowname,img1)
        self.curr_pt = []
        self.point   = []
    def select_point(self,event,x,y,flags,param):
        if event == cv2.EVENT_LBUTTONDOWN:
            self.point.append([x,y])
            cv2.circle(self.img1,(x,y),10,(0,0,255),-1)
        elif event == cv2.EVENT_MOUSEMOVE:
            self.curr_pt = [x,y]
    def getpt(self,count=1,img1=None):
        if img1 is not None:
            self.img1 = img1
        else:
            self.img1 = self.img11.copy()
        cv2.namedWindow(self.windowname,cv2.WINDOW_NORMAL)
        cv2.imshow(self.windowname,self.img1)
        cv2.setMouseCallback(self.windowname,self.select_point)
        self.point = []
        while(1):
            cv2.imshow(self.windowname,self.img1)
            k = cv2.waitKey(20) & 0xFF
            if k == 27 or len(self.point)>=count:
                break
        cv2.setMouseCallback(self.windowname, lambda *args : None)
        cv2.destroyAllWindows()
        return self.point, self.img1
def reorder(myPoints):
    myPoints = myPoints.reshape((4, 2))
    myPointsNew = np.zeros((4, 1, 2), dtype=np.int32)
    add = myPoints.sum(1)
    myPointsNew[0] = myPoints[np.argmin(add)]
    myPointsNew[3] = myPoints[np.argmax(add)]
    diff = np.diff(myPoints, axis=1)
    myPointsNew[1] = myPoints[np.argmin(diff)]
    myPointsNew[2] = myPoints[np.argmax(diff)]
    return myPointsNew
def nothing(x):
    pass      
def biggestContour(contours):
    biggest = np.array([])
    max_area = 0
    for i in contours:
        area = cv2.contourArea(i)
        if area > 5000:
            peri = cv2.arcLength(i, True)
            approx = cv2.approxPolyDP(i, 0.02 * peri, True)
            if area > max_area and len(approx) == 4:
                biggest = approx
                max_area = area
    return biggest, max_area
def drawRectangle(img, biggest, thickness):
    cv2.line(img, (biggest[0][0][0], biggest[0][0][1]), \
        (biggest[1][0][0], biggest[1][0][1]), (0, 255, 0), thickness)
    cv2.line(img, (biggest[0][0][0], biggest[0][0][1]), \
        (biggest[2][0][0], biggest[2][0][1]), (0, 255, 0), thickness)
    cv2.line(img, (biggest[3][0][0], biggest[3][0][1]), \
        (biggest[2][0][0], biggest[2][0][1]), (0, 255, 0), thickness)
    cv2.line(img, (biggest[3][0][0], biggest[3][0][1]), \
        (biggest[1][0][0], biggest[1][0][1]), (0, 255, 0), thickness)
    return img
def use_selectedvals(selectedpts, borderpts, imgBigContour, img, portrait, coords_portrait, \
    coords_landscape, widthImg, heightImg):
    biggest = reorder(selectedpts) 
    pts1 = np.float32(biggest) 
    if not portrait: borderpts = coords_portrait ###something doesn't make sense here but just ignore
    if portrait: borderpts = coords_landscape
    matrix = cv2.getPerspectiveTransform(pts1,borderpts)
    result = cv2.warpPerspective(img, matrix, (widthImg, heightImg))
    storedpts = biggest.copy()
    return borderpts, storedpts, result, pts1, imgBigContour, matrix, biggest
def pop_up(imgContours, _, window, dummycounter, auto_adjustment, selectedpts,heightImg,widthImg):
    result = imgContours
    _, result
    img1 = result.copy()
    windowname = 'Manual Adjustment'
    coordinateStore = MousePts(windowname,img1,heightImg,widthImg)
    pts,img1 = coordinateStore.getpt(4)
    combined = cv2.addWeighted(result, .1, img1, 0.9, 0)
    imgbytes=cv2.imencode('.png',combined)[1].tobytes()
    window['image'].update(data=imgbytes)
    if pts is not None: selectedpts = np.float32(pts)
    else: selectedpts = np.float32([[0, 0], [widthImg, 0], [0, heightImg], [widthImg, heightImg]])
    selectedpts = reorder(selectedpts)
    dummycounter = dummycounter + 1
    auto_adjustment = not auto_adjustment
    return dummycounter, auto_adjustment, selectedpts
def main():
    thres = (150,150)
    video_capture = cv2.VideoCapture(0)
    if not video_capture.isOpened(): raise IOError("Cannot open webcam")
    heightImg = 610
    widthImg = 867
    
    borderpts = storedpts =coords_portrait =selectedpts=  np.float32([[0, 0], [widthImg, 0], [0, heightImg], [widthImg, heightImg]])
    coords_landscape = selectedpts =\
        np.float32([[0, 0], [heightImg, 0], [0, widthImg], [heightImg, widthImg]])
    dummycounter = int(0)
    factor_smallest = 1

    portrait = False
    parity = int(0)
    recording = True
    unlocked_camera = False
    auto_adjustment = True
    vertical_inversion = int(-1)
    
    myImg = sg.Image(filename = '',key= 'image')
    menu_def = ['Settings', ['Toggle Auto adjust', 'Change camera'\
        ,['One','Two','Three'],'Rotate','Manually Adjust', 'Flip Vertical']],
    sg.theme('LightGray')
    layout=[
            [sg.Text('DeskCam',size=(40,1), pad = ((0,0),(0,10)),font='Arial 20')],
            [sg.Column([[myImg]], justification='center')],
            [sg.Button('Freeze',size = (10,1), pad= ((10,0),(10,0)),font='Arial 14'),
            sg.Push(),
            sg.Button('Rotate',size = (10,1),pad = ((0,10),(10,0)),font='Arial 14')],
            [sg.Menu(menu_def)]
            ]

    window = sg.Window('DeskCam',layout, location=(350,10), finalize=True )
    window.maximize()

    while True: 

        event, values= window.read(timeout=20)
        if event == 'One': video_capture = cv2.VideoCapture(0)
        elif event == 'Two': video_capture = cv2.VideoCapture(1)
        elif event == 'Three': video_capture = cv2.VideoCapture(2)
        elif event == 'Flip Vertical': vertical_inversion = vertical_inversion*-1
        elif event =='Exit' or event ==sg.WIN_CLOSED: return
        elif event == 'Rotate':
            if not portrait: 
                widthImg = 650
                heightImg = 920
            if portrait: 
                widthImg = 1300
                heightImg = 920
            portrait = not portrait
        elif event == 'Freeze': recording = False
        elif event == 'Toggle Auto adjust': unlocked_camera = not unlocked_camera
        elif event == 'Manually Adjust' and parity == 0: auto_adjustment = not auto_adjustment
        elif event == 'Manually Adjust' and parity == 1: dummycounter = dummycounter +1

        _, vid = video_capture.read() 
        parity = dummycounter % 2
        img = cv2.flip(vid, vertical_inversion)
        img = cv2.resize(img, (widthImg, heightImg))

        imgGray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)  
        imgBlur = cv2.GaussianBlur(imgGray, (5, 5), 1)
        imgThreshold = cv2.Canny(imgBlur, thres[0], thres[1])  
        kernel = np.ones((5, 5))
        imgDial = cv2.dilate(imgThreshold, kernel, iterations=2)  
        imgThreshold = cv2.erode(imgDial, kernel, iterations=1)  
        imgContours = img.copy()  
        imgBigContour = img.copy()  
        contours, hierarchy = cv2.findContours(imgThreshold, cv2.RETR_EXTERNAL,
                                            cv2.CHAIN_APPROX_SIMPLE)  
        cv2.drawContours(imgContours, contours, -1, (0, 255, 0), 2)  
        biggest, maxArea = biggestContour(contours)
        
        if biggest.size != 0:
            if unlocked_camera:
                if auto_adjustment:
                    parity = dummycounter % 2
                    if parity == 0: borderpts, storedpts, result, pts1, imgBigContour, matrix, biggest=\
                        use_selectedvals(biggest, borderpts, imgBigContour, img, portrait, coords_portrait,coords_landscape, widthImg, heightImg)
                    if parity == 1: borderpts, storedpts, result, pts1, imgBigContour, matrix, biggest=\
                        use_selectedvals(selectedpts, borderpts, imgBigContour, img, portrait, coords_portrait,coords_landscape, widthImg, heightImg)
                if not auto_adjustment: dummycounter, auto_adjustment, selectedpts=\
                    pop_up(imgContours, _, window, dummycounter, auto_adjustment, selectedpts,heightImg,widthImg)
            if not unlocked_camera:
                parity = dummycounter % 2
                if not auto_adjustment: dummycounter, auto_adjustment, selectedpts=\
                    pop_up(imgContours, _, window, dummycounter, auto_adjustment, selectedpts,heightImg,widthImg)
                if parity == 1: borderpts, storedpts, result, pts1, imgBigContour, matrix, biggest=\
                    use_selectedvals(selectedpts, borderpts, imgBigContour, img, portrait, coords_portrait,coords_landscape, widthImg, heightImg)
                else: result = img
        else:
            if unlocked_camera:
                if auto_adjustment:
                    pts1 = np.float32(storedpts) 
                    if portrait: borderpts = coords_portrait
                    if not portrait: borderpts = coords_landscape
                    matrix = cv2.getPerspectiveTransform(pts1,borderpts)
                    result = cv2.warpPerspective(img, matrix, (widthImg, heightImg))
                if not auto_adjustment: dummycounter, auto_adjustment, selectedpts=\
                    pop_up(imgContours, _, window, dummycounter, auto_adjustment, selectedpts,heightImg,widthImg)     
            if not unlocked_camera:
                parity = dummycounter % 2
                if not auto_adjustment: dummycounter, auto_adjustment, selectedpts=\
                    pop_up(imgContours, _, window, dummycounter, auto_adjustment, selectedpts,heightImg,widthImg)  
                if parity == 1: borderpts, storedpts, result, pts1, imgBigContour, matrix, biggest=\
                    use_selectedvals(selectedpts, borderpts, imgBigContour, img, portrait, coords_portrait,coords_landscape, widthImg, heightImg)
                else: result = img
        if recording:
            _, result
            imgbytes=cv2.imencode('.png',result)[1].tobytes()
            window['image'].update(data=imgbytes) 

if __name__ =='__main__':
    main()
