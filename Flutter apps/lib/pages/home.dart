import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class HomePage extends StatelessWidget{
  const HomePage({super.key});
  

  int nthperiod(int time, bool tuesday){
    int period =-1;
    if(!tuesday){
      if(time<830){
      return -1;
      }
      else if(time<935){
        return 0;
      }
      else if(time<1045){
        return 1;
      }
      else if(time<1115){
        return 2;
      }
      else if(time<1220){
        return 3;
      }
      else if(time<1330){
        return 4;
      }
      else if(time<1410){
        return 5;
      }
      else if(time<1515){
        return 6;
      }
      else if(time<1625){
        return 7;
      }
      else{return 8;}
    }
    if(tuesday){
      if(time<830){
      return -1;
      }
      else if(time<925){
        return 0;
      }
      else if(time<1025){
        return 1;
      }
      else if(time<1055){
        return 2;
      }
      else if(time<1150){
        return 3;
      }
      else if(time<1250){
        return 4;
      }
      else if(time<1320){
        return 5;
      }
      else if(time<1415){
        return 6;
      }
      else if(time<1515){
        return 7;
      }
      else if(time<1625){
        return 8;
      }
      else{return 9;}
    }
    
    return period;
  }

  String startsorendsat(int period, bool tuesday, bool starty){
    var starts = [830,940,1045,1115,1225,1330,1410,1520,1630];
    var ends = [935,1045,1115,1220,1330,1410,1515,1625,-100];

    var startsT = [830,930,1025,1055,1155,1250,1320,1420,1515,1630];
    var endsT = [925,1025,1055,1150,1250,1320,1415,1515,1625];
    if(period<0){
      return "90000";
    }
    if(!tuesday){
      if(starty){
        return goodtime(starts[period]);
      }
      else{return goodtime(ends[period]);}
    }
    if(tuesday){
      if(starty){
        return goodtime(startsT[period]);
      }
      else{return goodtime(endsT[period]);}
    }
    return "0";
  }

  String goodtime(int time){
    if(((time-time%100)/100)%12==0){
      return "${((time-time%100)/100).toInt()%12+12}:${time%100}";
    }
    return "${((time-time%100)/100).toInt()%12}:${time%100}";
  }

  

  @override
  Widget build(BuildContext context){
    SystemChrome.setEnabledSystemUIMode(
    SystemUiMode.manual, overlays: [SystemUiOverlay.bottom]);
    var periods = [
    ["Biology","Free","1st Break","Free","English","2nd Break","Physics","Chemistry","Nothing","Nothing"],
    ["English","Tutor","1st Break","Physics","Biology","2nd Break","Free","Free","Chemistry","Nothing"],
    ["Free","English","1st Break","Physics","Free","2nd Break","Biology","Chemistry","Nothing","Nothing"],
    ["Physics","English","1st Break","Free","Biology","2nd Break","Free","Chemistry","Nothing","Nothing"],
    ["Free","Physics","1st Break","English","Biology","2nd Break","Nothing","Nothing","Nothing","Nothing"],
    ["Nothing","Nothing","Nothing","Nothing","Nothing","Nothing","Nothing","Nothing"],
    ["Nothing","Nothing","Nothing","Nothing","Nothing","Nothing","Nothing","Nothing"]
    ];
    var starts = [830,940,1115,1225,1410,1520,-100];
    var ends = [935,1045,1220,1410,1515,1625,-100];
    bool isWeekend= false;
    bool isTueday = false;
    bool isBeforeschool = false;
    bool isAfterschool = false;
    bool isInschool = false;
    bool isInclass = false;

    // DateTime now = DateTime.now().toLocal().subtract(const Duration(hours:12));
    DateTime now = DateTime.now().toLocal();
    int bettertime = now.hour*100+now.minute;
    if(now.weekday==DateTime.tuesday){
      isTueday = true;
    }
    else{isTueday=false;}

    if(now.weekday==DateTime.saturday || now.weekday==DateTime.sunday){
      isWeekend = true;
    }

    if(now.hour*100+now.minute<830){
      isBeforeschool = true;
    }
    if(now.hour*100+now.minute>1630){
      isAfterschool = true;
    }

    if(!isAfterschool&&!isBeforeschool&&!isWeekend){
      isInschool=true;
    }
    else{isInschool=false;}

    if(isInschool&& nthperiod(bettertime, isTueday)==0 || nthperiod(bettertime, isTueday)==1 || nthperiod(bettertime, isTueday)==3|| nthperiod(bettertime, isTueday)==4 || nthperiod(bettertime, isTueday)==6|| nthperiod(bettertime, isTueday)==7|| nthperiod(bettertime, isTueday)==8){
      isInclass = true;
    }
    else{isInclass=false;}

    TextStyle classicstyle = TextStyle(
                        color: Colors.black,
                        fontWeight: FontWeight.bold,
                        fontSize: 55,
                      );

    TextStyle classicstyle1 = TextStyle(
      color: Colors.black,
      fontWeight: FontWeight.w300,
      fontSize: 35,
    );

    TextStyle classicstyle2 = TextStyle(
                        color: Colors.black,
                        fontWeight: FontWeight.bold,
                        fontSize: 35 ,
                      );

    TextStyle classicstyle3 = TextStyle(
      color: Colors.black,
      fontWeight: FontWeight.w300,
      fontSize: 27,
    );
    TextStyle classicstyle4 = TextStyle(
                        color: Colors.black,
                        fontWeight: FontWeight.bold,
                        fontSize: 30 ,
                      );

    TextStyle classicstyle5 = TextStyle(
      color: Colors.black,
      fontWeight: FontWeight.w300,
      fontSize: 17,
    );

    Expanded theone(int before, int b){
      return Expanded(flex: 45, child: Container(width: 10000,decoration: BoxDecoration(
            color: Colors.white, borderRadius: BorderRadius.circular(20),), child: Row(children: [
              Expanded(flex: 1, child: Container()),
              Expanded(flex:15,child:Column(children: [
                Expanded(flex: 9, child: Container()),
                Expanded(flex: 15, child: Text(
                  (periods[now.weekday-before][b]).toString() ,style: classicstyle4),),
                Expanded(flex: 5, child: Container()),
            
                ]),),
                
              Expanded(flex: 1, child: Container()),
            ])));
    }


    Color bgcol =  Color.fromARGB(255, 203, 207, 216);
     Widget dynamicbody;
     if(!isWeekend && isBeforeschool &&!isTueday){//reverse this, also means that index cannot be negative
      dynamicbody = Row(children: [
        Expanded(flex: 10, child: Container(color: bgcol)),
        Expanded(flex: 45, child: Column(children:[

          Expanded(flex: 15, child: Container()),
          theone(1, 0),
          Expanded(flex: 5, child: Container()),
          theone(1,1),
          Expanded(flex: 5, child: Container()),
          theone(1,3),
          Expanded(flex: 5, child: Container()),
          theone(1,4),
          Expanded(flex: 5, child: Container()),
          theone(1,6),
          Expanded(flex: 5, child: Container()),
          theone(1,7),
            
          Expanded(flex: 10, child: Container(color: bgcol)),
          Expanded(flex: 15, child: Container(color: bgcol)),
          ]),),
        Expanded(flex: 10, child: Container(color: bgcol)),
      ],);
    }
    else if(!isWeekend && isBeforeschool &&isTueday){//reverse this, also means that index cannot be negative
      dynamicbody = Row(children: [
        Expanded(flex: 10, child: Container(color: bgcol)),
        Expanded(flex: 45, child: Column(children:[
          
          Expanded(flex: 15, child: Container()),
          theone(1, 0),
          Expanded(flex: 5, child: Container()),
          theone(1,1),
          Expanded(flex: 5, child: Container()),
          theone(1,3),
          Expanded(flex: 5, child: Container()),
          theone(1,4),
          Expanded(flex: 5, child: Container()),
          theone(1,6),
          Expanded(flex: 5, child: Container()),
          theone(1,7),
          Expanded(flex: 5, child: Container()),
          theone(1,8),
            
          Expanded(flex: 10, child: Container(color: bgcol)),
          Expanded(flex: 15, child: Container(color: bgcol)),
          ]),),
        Expanded(flex: 10, child: Container(color: bgcol)),
      ],);
    }
    else if(!isWeekend && isAfterschool &&!isTueday){//reverse this, also means that index cannot be negative
      dynamicbody = Row(children: [
        Expanded(flex: 10, child: Container(color: bgcol)),
        Expanded(flex: 45, child: Column(children:[
          
          Expanded(flex: 15, child: Container()),
          theone(0, 0),
          Expanded(flex: 5, child: Container()),
          theone(0,1),
          Expanded(flex: 5, child: Container()),
          theone(0,3),
          Expanded(flex: 5, child: Container()),
          theone(0,4),
          Expanded(flex: 5, child: Container()),
          theone(0,6),
          Expanded(flex: 5, child: Container()),
          theone(0,7),
            
          Expanded(flex: 10, child: Container(color: bgcol)),
          Expanded(flex: 15, child: Container(color: bgcol)),
          ]),),
        Expanded(flex: 10, child: Container(color: bgcol)),
      ],);
    }
    else if(!isWeekend && isAfterschool &&isTueday){//reverse this, also means that index cannot be negative
      dynamicbody = Row(children: [
        Expanded(flex: 10, child: Container(color: bgcol)),
        Expanded(flex: 45, child: Column(children:[
          
          Expanded(flex: 15, child: Container()),
          theone(0, 0),
          Expanded(flex: 5, child: Container()),
          theone(0,1),
          Expanded(flex: 5, child: Container()),
          theone(0,3),
          Expanded(flex: 5, child: Container()),
          theone(0,4),
          Expanded(flex: 5, child: Container()),
          theone(0,6),
          Expanded(flex: 5, child: Container()),
          theone(0,7),
          Expanded(flex: 5, child: Container()),
          theone(0,8),
            
          Expanded(flex: 10, child: Container(color: bgcol)),
          Expanded(flex: 15, child: Container(color: bgcol)),
          ]),),
        Expanded(flex: 10, child: Container(color: bgcol)),
      ],);
    }


    else if(isInschool&&isInclass){//reverse this, also means that index cannot be negative
      dynamicbody = Row(children: [
        Expanded(flex: 10, child: Container(color: bgcol)),
        Expanded(flex: 45, child: Column(children:[
          Expanded(flex: 15, child: Container(color: bgcol)),
          Expanded(flex: 45, child: Container(width: 10000,decoration: BoxDecoration(
            color: Colors.white, borderRadius: BorderRadius.circular(20),), child: Row(children: [
              Expanded(flex: 1, child: Container()),
              Expanded(flex:15,child:Column(children: [
                Expanded(flex: 8, child: Container()),
                Expanded(flex: 15, child: Text(
                  (periods[now.weekday-1][nthperiod(bettertime, isTueday)]).toString() ,style: classicstyle),),
                Expanded(flex: 9, child: Container()),
                Expanded(flex: 15, child: Container(color: Colors.white, child: Text(
                  "Ends at: ${startsorendsat(nthperiod(bettertime, isTueday), isTueday, false)}" ,style: classicstyle1),)),
                Expanded(flex: 5, child: Container()),
            
                ]),),
                
              Expanded(flex: 1, child: Container()),
            ]))),

          Expanded(flex: 10, child: Container(color: bgcol)),
          Expanded(flex: 30, child: Row(children:[
            Expanded(flex: 10, child: Container(color: bgcol)),
            Expanded(flex: 55, child: Container(width: 1000,decoration: BoxDecoration(
            color: Color.fromARGB(132, 255, 255, 255), borderRadius: BorderRadius.circular(20),), child: Row(children: [
              Expanded(flex: 1, child: Container()),
              Expanded(flex:15,child:Column(children: [
                Expanded(flex: 8, child: Container()),
                Expanded(flex: 15, child: Text(
                  (periods[now.weekday-1][nthperiod(bettertime, isTueday)+1]).toString() ,style: classicstyle2),),
                Expanded(flex: 9, child: Container()),
                Expanded(flex: 15, child: Text(
                  "Starts at: ${startsorendsat(nthperiod(bettertime, isTueday)+1, isTueday, true)}" ,style: classicstyle3)),
                Expanded(flex: 5, child: Container()),
            
                ]),),
                
              Expanded(flex: 1, child: Container()),
            ]))),
            Expanded(flex: 10, child: Container(color: bgcol)),
            ]),),
          Expanded(flex: 15, child: Container(color: bgcol)),
          ]),),
        Expanded(flex: 10, child: Container(color: bgcol)),
      ],);
    }
    else if(isInschool&&!isInclass){//reverse this, also means that index cannot be negative
      dynamicbody = Row(children: [
        Expanded(flex: 10, child: Container(color: bgcol)),
        Expanded(flex: 45, child: Column(children:[
          Expanded(flex: 15, child: Container(color: bgcol)),
          Expanded(flex: 30, child: Row(children:[
            Expanded(flex: 10, child: Container(color: bgcol)),
            Expanded(flex: 55, child: Container(width: 1000,decoration: BoxDecoration(
            color: Color.fromARGB(132, 255, 255, 255), borderRadius: BorderRadius.circular(20),), child: Row(children: [
              Expanded(flex: 1, child: Container()),
              Expanded(flex:15,child:Column(children: [
                Expanded(flex: 8, child: Container()),
                Expanded(flex: 15, child: Text(
                  (periods[now.weekday-1][nthperiod(bettertime, isTueday)-1]).toString() ,style: classicstyle2),),
                Expanded(flex: 9, child: Container()),
                Expanded(flex: 15, child: Text(
                  "Ended at: ${startsorendsat(nthperiod(bettertime, isTueday)-1, isTueday, false)}" ,style: classicstyle3)),
                Expanded(flex: 5, child: Container()),
            
                ]),),
                
              Expanded(flex: 1, child: Container()),
            ]))),
            Expanded(flex: 10, child: Container(color: bgcol)),
            ]),),
          Expanded(flex: 10, child: Container(color: bgcol)),
          Expanded(flex: 55, child: Container(width: 10000,decoration: BoxDecoration(
            color: Colors.white, borderRadius: BorderRadius.circular(20),), child: Row(children: [
              Expanded(flex: 1, child: Container()),
              Expanded(flex:15,child:Column(children: [
                Expanded(flex: 8, child: Container()),
                Expanded(flex: 15, child: Text(
                  (periods[now.weekday-1][nthperiod(bettertime, isTueday)]).toString() ,style: classicstyle),),
                Expanded(flex: 9, child: Container()),
                Expanded(flex: 15, child: Container(color: Colors.white, child: Text(
                  "Ends at: ${startsorendsat(nthperiod(bettertime, isTueday), isTueday, false)}" ,style: classicstyle1),)),
                Expanded(flex: 5, child: Container()),
            
                ]),),
                
              Expanded(flex: 1, child: Container()),
            ]))),
          Expanded(flex: 10, child: Container(color: bgcol)),
          Expanded(flex: 30, child: Row(children:[
            Expanded(flex: 10, child: Container(color: bgcol)),
            Expanded(flex: 55, child: Container(width: 1000,decoration: BoxDecoration(
            color: Color.fromARGB(132, 255, 255, 255), borderRadius: BorderRadius.circular(20),), child: Row(children: [
              Expanded(flex: 1, child: Container()),
              Expanded(flex:15,child:Column(children: [
                Expanded(flex: 8, child: Container()),
                Expanded(flex: 15, child: Text(
                  (periods[now.weekday-1][nthperiod(bettertime, isTueday)+1]).toString() ,style: classicstyle2),),
                Expanded(flex: 9, child: Container()),
                Expanded(flex: 15, child: Text(
                  "Starts at: ${startsorendsat(nthperiod(bettertime, isTueday)+1, isTueday, true)}" ,style: classicstyle3)),
                Expanded(flex: 5, child: Container()),
            
                ]),),
                
              Expanded(flex: 1, child: Container()),
            ]))),
            Expanded(flex: 10, child: Container(color: bgcol)),
            ]),),
          Expanded(flex: 15, child: Container(color: bgcol)),
          ]),),
        Expanded(flex: 10, child: Container(color: bgcol)),
      ],);
    }
    else{dynamicbody = Row(children: [
        Expanded(flex: 10, child: Container(color: const Color.fromARGB(255, 41, 48, 54))),
        Expanded(flex: 45, child: Column(children:[
          Expanded(flex: 15, child: Container(color: Colors.orange)),
          Expanded(flex: 45, child: Container(color: const Color.fromARGB(255, 183, 250, 243), child: Text(
            "ASJFhASLdlkjasd",style: classicstyle),)),
          Expanded(flex: 10, child: Container(color: Colors.orange)),
          Expanded(flex: 30, child: Row(children:[
            Expanded(flex: 10, child: Container(color: Colors.orange)),
            Expanded(flex: 40, child: Container(color: const Color.fromARGB(255, 214, 53, 107),child: Text(
              "AJFDHAKJHSD",style: classicstyle),)),
            Expanded(flex: 10, child: Container(color: Colors.orange)),
            ]),),
          Expanded(flex: 15, child: Container(color: Colors.orange)),
          ]),),
        Expanded(flex: 10, child: Container(color: Color.fromARGB(255, 27, 224, 132))),
      ],);}
    

    return Scaffold(
      backgroundColor: bgcol,
      appBar: AppBar(title:Text("Home")),
      drawer: Drawer(
          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(5)),
          backgroundColor: Colors.white,
          child: Column(
            children: [
              DrawerHeader(
                child: Icon(
                  Icons.emoji_emotions,
                  size:48,
                  ),
                ),
              ListTile(leading:Icon(Icons.home),title:Text("Home"),onTap:(){Navigator.pop(context); Navigator.pushNamed(context,'/homepage');}),
              ListTile(leading:Icon(Icons.emoji_food_beverage),title:Text("Notes"),onTap:(){Navigator.pop(context); Navigator.pushNamed(context,'/notes');}),
              ListTile(leading:Icon(Icons.money),title:Text("Coin Toss"),onTap:(){Navigator.pop(context); Navigator.pushNamed(context,'/cointoss');}),
              ListTile(leading:Icon(Icons.settings),title:Text("Settings"),onTap:(){Navigator.pop(context); Navigator.pushNamed(context,'/settings');}),
            ],
          ),
        ),
      body: dynamicbody
    );
  }
}