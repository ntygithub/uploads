import 'dart:async';
import 'dart:math';
import 'package:flutter/material.dart';

class CoinToss extends StatefulWidget {
  const CoinToss({super.key});

  @override
  _CoinTossState createState() => _CoinTossState();
}

class _CoinTossState extends State<CoinToss> {
  int sides = 2; 
  int result = 0;
  Timer? _timer;
  bool isTossing = false; 

  void increasesides() {
    setState(() {
      sides += 1; 
    });
  }

  void decreasesides() {
    setState(() {
      sides -= 1; 
    });
  }

  void toss() {
    _timer?.cancel(); 
    int iterations = 0;
    Random random = Random();

    setState(() {
      isTossing = true; 
    });

    _timer = Timer.periodic(Duration(milliseconds: 50), (timer) {
      setState(() {
        result = random.nextInt(sides) + 1;
      });
      iterations++;

      if (iterations >= 40) { 
        timer.cancel();
        setState(() {
          result = random.nextInt(sides) + 1; 
          isTossing = false; 
        });
      }
    });
  }

  @override
  void dispose() {
    _timer?.cancel(); 
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Color.fromARGB(255, 109, 101, 143),
        title: Text("Coin Toss",
                          style: TextStyle(
                            color: Color.fromARGB(255, 255, 255, 255),
                            fontWeight: FontWeight.bold,
                          ),),
        leading: Builder(
          builder: (BuildContext context) {
            return IconButton(
              icon: Icon(Icons.menu),
              color: Colors.white, 
              onPressed: () {
                Scaffold.of(context).openDrawer();
              },
            );
          },
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
                size: 48,
              ),
            ),
            ListTile(
              leading: Icon(Icons.home),
              title: Text("Home"),
              onTap: () {
                Navigator.pop(context);
                Navigator.pushNamed(context, '/homepage');
              },
            ),
            ListTile(
              leading: Icon(Icons.emoji_food_beverage),
              title: Text("Notes"),
              onTap: () {
                Navigator.pop(context);
                Navigator.pushNamed(context, '/notes');
              },
            ),
            ListTile(
              leading: Icon(Icons.money),
              title: Text("Coin Toss"),
              onTap: () {
                Navigator.pop(context);
                Navigator.pushNamed(context, '/cointoss');
              },
            ),
            ListTile(
              leading: Icon(Icons.settings),
              title: Text("Settings"),
              onTap: () {
                Navigator.pop(context);
                Navigator.pushNamed(context, '/settings');
              },
            ),
          ],
        ),
      ),
      body: Center(
        child: Column(
          children: [
            Expanded(
              flex: 7,
              child: Column(
                children: [
                  Expanded(flex: 1, child: Container()),
                  Container(
                    child: Text(
                      sides.toString(),
                      style: TextStyle(
                        color: Colors.black,
                        fontWeight: FontWeight.bold,
                        fontSize: 50,
                      ),
                    ),
                  ),
                  Expanded(flex: 1, child: Container()),
                ],
              ),
            ),
            Expanded(
              flex: 20,
              child: Column(
                children: [
                  Expanded(flex: 1, child: Container()),
                  Container(
                    child: Text(
                      result.toString(),
                      style: TextStyle(
                        color: isTossing ? Color.fromARGB(255, 109, 101, 143) : Colors.black,
                        fontWeight: FontWeight.bold,
                        fontSize: 150,
                      ),
                    ),
                  ),
                  Expanded(flex: 1, child: Container()),
                ],
              ),
            ),
            Expanded(
              flex: 10,
              child: Row(
                children: [
                  Expanded(
                    child: Column(
                      children: [
                        Expanded(flex: 10, child: Container()),
                        FloatingActionButton(
                          onPressed: increasesides,
                          backgroundColor: Color.fromARGB(255, 109, 101, 143),
                          elevation: 0.6,
                          shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(5)),
                          child: Icon(
                            Icons.add,
                            color: Colors.white,
                          ),
                        ),
                        Expanded(flex: 1, child: Container()),
                        FloatingActionButton(
                          onPressed: () {
                            if (sides > 1) {
                              decreasesides();
                            }
                          },
                          backgroundColor: Color.fromARGB(255, 109, 101, 143),
                          elevation: 0.6,
                          shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(5)),
                          child: Icon(
                            Icons.remove,
                            color: Colors.white,
                          ),
                        ),
                        Expanded(flex: 10, child: Container()),
                      ],
                    ),
                  ),
                  Expanded(
                    flex: 2,
                    child: Container(
                      child: FloatingActionButton(
                        onPressed: toss,
                        backgroundColor: Color.fromARGB(255, 154, 139, 223),
                        elevation: 0.6,
                        shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(19)),
                        child: Text(
                          "toss",
                          style: TextStyle(
                            color: Color.fromARGB(255, 255, 255, 255),
                            fontWeight: FontWeight.bold,
                            fontSize: 25,
                          ),
                        ),
                      ),
                    ),
                  ),
                  Expanded(flex: 1, child: Container()),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
