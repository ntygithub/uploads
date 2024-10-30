from pypdf import PdfReader 
import re
from pathlib import Path
import threading
from flask import Flask, render_template

app = Flask(__name__)

@app.route('/')
def index():
    return render_template('index.html', output_data=output)




def iterate():
    list = Path('PhQue').rglob('*.pdf')
    for _ in list:
        getqs(str(_))


definitioncases = [
    "define",
    "state what is meant by",
    "explain what is meant by",
    # "calculate"
    ]

alph = ['a', 'b', 'c', 'd', 'e', 'f', 'g']
roman = ['i', 'ii', 'iii', 'iv', 'v', 'vi', 'vii', 'viii']
arab = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 
        17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 
        31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 
        45, 46, 47, 48, 49, 50]

output=[]


def findhaha(mat,w):
    pattern = rf'\(.*?\).+?{re.escape(mat)}.*$'
    return re.search(pattern,w,re.IGNORECASE | re.MULTILINE)

def findhaha3(mat,w):
    stupid = "..........................................................................................................................................."
    pattern = rf'\n.+(\(.*\).+?{re.escape(mat)}.+?){re.escape(stupid)}' # this is starts with the thingt, because spaces
    return re.findall(pattern,w,re.IGNORECASE | re.MULTILINE | re.DOTALL) # this way is bad because it believes context to be the question
   #fixed, goo job me
   #check that first braket has no CAPS or numb3rs
def getpart(w):
    extraction_pattern = rf'\((.*?)\)'
    return re.findall(extraction_pattern,w,re.IGNORECASE | re.MULTILINE)

def getquest(hello):
    pattern1 = rf'^\d+.*?\[Total: \d+\]*$'# is causing problems #a question with no parts has no no total, so finding question will be off by 1 sometimes
    return re.findall(pattern1,hello,re.IGNORECASE | re.MULTILINE | re.DOTALL)

def getsubpart(j,deep,wholething):
    myguy = str(getquest(wholething)[j])
    # print(myguy.find('('+deep+')'))
    arkansas = myguy.find("("+deep+")")
    while True:
        arkansas-=1
        if myguy[arkansas] == '(' and myguy[arkansas+2] == ')' and myguy[arkansas+1] in alph:
            return myguy[arkansas+1]
        
def getansa(byebye,haha):
    pattern = rf'{re.escape(haha)}(.*?)\s*;\s*(\d+)\s*(?:\n|(?=9700))' #still a case, just do braindead
    return re.search(pattern, byebye, re.DOTALL |re.IGNORECASE | re.MULTILINE)

def getansa2(byebye,haha):
    pattern = rf'({re.escape(haha)})(.*?)(?=\n\d+(\([a-z]\))?(\([ivxlc]+\))?)'
    return re.search(pattern, byebye, re.DOTALL |re.IGNORECASE | re.MULTILINE)

def getraw(textmepleaseibegyouimissyou):
    patter = rf'\(.*\)\s*(.+)$'
    return re.search(patter, textmepleaseibegyouimissyou, re.DOTALL |re.IGNORECASE | re.MULTILINE)


def getqs(paper):
    reader = PdfReader(paper) 
    modifications = [(2, 'A'), (3, 'n'), (4, 's'), (15, 'm'), (16, 's')]

    anspaper = ''.join(
        (char if i not in dict(modifications) else dict(modifications)[i])
        for i, char in enumerate(paper))

    wholething = ""
    for _ in reader.pages:
        wholething +=(_.extract_text() + "\n")

    for i in range(len(reader.pages)):
        page = reader.pages[i]
        text=page.extract_text()
        for _ in definitioncases:
            if findhaha(_,text):
                for j in range(len(getquest(wholething))):
                    if findhaha(_,text).group(0) in getquest(wholething)[j]:
                        questno = str(j+1)
                        subpartno = ""
                        partno = ""
                        if getpart(findhaha(_,text).group(0))[-1] in roman:
                            partno = "("+getsubpart(j,getpart(findhaha(_,text).group(0))[-1],wholething)+")"
                            subpartno = "("+getpart(findhaha(_,text).group(0))[-1]+")"
                        else:
                            partno = "("+getpart(findhaha(_,text).group(0))[-1]+")"
                        
                        # print(findhaha3(_,text)[0])
                        try:
                            temp=str("Q: "+getraw(findhaha3(_,text)[0]).group(1))
                            temp2=str(getas(anspaper,questno + partno + subpartno))
                            # print("-"*100+"\nQ: "+getraw(findhaha3(_,text)[0]).group(1))
                            # # print("FOUND ON: pg."+ str(i +1) + ", q."+ questno + partno + subpartno)
                            # print(getas(anspaper,questno + partno + subpartno))
                            # print("-"*100+"\n")
                            output.append([temp,temp2,anspaper[5:]])
                            
                        except:
                            print("FAILURE")
                            pass
                            # print("EPIC FAIL ON: " + questno + partno + subpartno)

#matches this ...........................................................................................................................................

def getas(paper,id):
    reader1 = PdfReader(paper) 

    wholething1 = ""
    for _ in reader1.pages:
        wholething1 +=(_.extract_text() )
    try:
        return str("A: (" +paper+") " +getansa2(wholething1,id).group(1)+"\n"+getansa2(wholething1,id).group(2))
    except:
        return str("A: FAILED ON: (" +paper+") " )
    # print(wholething1)


async def asleep():
    pass

data_thread = threading.Thread(target=iterate)
data_thread.daemon = True
data_thread.start()
                

if __name__=="__main__":
    # iterate()
    # getqs('ChQue/9700_s18_qp_41.pdf')
    app.run(debug=True)
    