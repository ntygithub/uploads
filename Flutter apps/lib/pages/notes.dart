// ignore_for_file: prefer_const_constructors, depend_on_referenced_packages, prefer_const_literals_to_create_immutables, unnecessary_to_list_in_spreads

import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'dart:convert';
import 'dart:math';

class Tile extends StatelessWidget {
  final String name;
  final double height1;
  final double width1;
  final double left1;
  final double top1;
  final VoidCallback onTap;
  final ValueChanged<String> onTextChanged;
  final bool isEditing;
  final Function(double, double) onDragUpdate;

  const Tile({
    super.key,
    required this.name,
    required this.height1,
    required this.width1,
    required this.left1,
    required this.top1,
    required this.onTap,
    required this.onTextChanged,
    required this.isEditing,
    required this.onDragUpdate,
  });

  @override
  Widget build(BuildContext context) {
    return Positioned(
      left: left1,
      top: top1,
      child: Draggable(
        feedback: Material(
          child: Container(
            height: height1,
            width: width1,
            padding: EdgeInsets.all(12),
            decoration: BoxDecoration(
              color: Colors.yellow,
              boxShadow: [
                BoxShadow(
                  color: Colors.black.withOpacity(0.2),
                  spreadRadius: 1,
                  blurRadius: 1,
                  offset: Offset(2, 2),
                ),
              ],
            ),
            child: Text(name),
          ),
        ),
        childWhenDragging: Container(), 
        onDragUpdate: (details) {
          onDragUpdate(details.delta.dx, details.delta.dy);
        },
        child: GestureDetector(
          onTap: onTap,
          child: Container(
            height: height1,
            width: width1,
            padding: EdgeInsets.all(12),
            decoration: BoxDecoration(
              color: Colors.yellow,
              boxShadow: [
                BoxShadow(
                  color: Colors.black.withOpacity(0.2),
                  spreadRadius: 1,
                  blurRadius: 1,
                  offset: Offset(2, 2),
                ),
              ],
            ),
            child: isEditing
                ? TextField(
                    onSubmitted: onTextChanged,
                    autofocus: true,
                    decoration: InputDecoration(
                      border: InputBorder.none,
                    ),
                  )
                : Text(name),
          ),
        ),
      ),
    );
  }
}

class MyNotes extends StatefulWidget {
  const MyNotes({super.key});

  @override
  _MyNotesState createState() => _MyNotesState();
}

class _MyNotesState extends State<MyNotes> {
  List<List<dynamic>> something = [];
  int? editingIndex;
  bool isPressed = false;

  @override
  void initState() {
    super.initState();
    loadTiles(); 
  }

  void saveTiles() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String jsonString = jsonEncode(something);
    await prefs.setString('tiles', jsonString);
  }

  void loadTiles() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String? jsonString = prefs.getString('tiles');
    if (jsonString != null) {
      List<dynamic> jsonData = jsonDecode(jsonString);
      setState(() {
        something = List<List<dynamic>>.from(
            jsonData.map((item) => List<dynamic>.from(item)));
      });
    }
  }

  void moveToTop(int index) {
    setState(() {
      final item = something.removeAt(index);
      something.add(item);
      saveTiles(); 
    });
  }

  void deletethis(int index) {
    setState(() {
      something.removeAt(index);
      saveTiles(); 
    });
  }

  void addnewtile(double widthmax, double heightmax) {
    Random random = new Random();
    int randomwidth = random.nextInt(50) + 150;
    int randomheight = random.nextInt(50) + 150;
    int randomx = random.nextInt(widthmax.toInt() - 10 - randomwidth);
    int randomy = random.nextInt(heightmax.toInt() - 2 * kToolbarHeight.toInt() - randomheight);
    setState(() {
      something.add([
        "New Note",
        randomheight.toDouble(),
        randomwidth.toDouble(),
        randomx.toDouble(),
        randomy.toDouble()
      ]);
      saveTiles(); 
    });
  }

  void updateText(int index, String newText) {
    setState(() {
      something[index][0] = newText;
      editingIndex = null; 
      saveTiles(); 
    });
  }

  void onTileDragUpdate(double dx, double dy) {
    setState(() {
    });
  }

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;
    double widthmax = size.width;
    double heightmax = size.height;
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: Scaffold(
        backgroundColor: Colors.white,
        appBar: AppBar(
          backgroundColor: Colors.yellow,
          title: Text(
            "Notes",
            style: TextStyle(
              color: Colors.black,
              fontWeight: FontWeight.bold,
            ),
          ),
        ),
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
        floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
        floatingActionButton: FloatingActionButton(
          onPressed: () {
            addnewtile(widthmax, heightmax);
          },
          backgroundColor: Colors.yellow,
          elevation: 0.6,
          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(5)),
          child: Icon(Icons.add),
        ),
        body: Stack(
          children: [
            ...something.asMap().entries.map((entry) {
              int index = entry.key;
              List<dynamic> value = entry.value;
              return Tile(
                name: value[0],
                height1: value[1],
                width1: value[2],
                left1: value[3],
                top1: value[4],
                onTap: () {
                  if (index == something.length - 1) {
                    setState(() {
                      editingIndex = index; 
                    });
                  } else {
                    editingIndex = null;
                  }
                  if (isPressed) {
                    deletethis(index);
                  }
                  moveToTop(index);
                },
                onTextChanged: (newText) {
                  updateText(something.length - 1, newText);
                },
                isEditing: editingIndex == index,
                onDragUpdate: (dx, dy) {
                  onTileDragUpdate(dx, dy);               
                  something[index][3] += dx; 
                  something[index][4] += dy; 
                  saveTiles();
                },
                

              );
            }).toList(),
            Positioned(
              bottom: 16,
              left: 16,
              child: FloatingActionButton(
                onPressed: () {
                  setState(() {
                    isPressed = !isPressed;
                  });
                },
                backgroundColor: isPressed ? Color.fromARGB(255, 212, 212, 212) : Color.fromARGB(255, 255, 255, 255),
                elevation: 0,
                shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(5)),
                child: Icon(
                  Icons.delete,
                  color: isPressed ? Color.fromARGB(255, 0, 0, 0) : const Color.fromARGB(255, 151, 151, 151),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
