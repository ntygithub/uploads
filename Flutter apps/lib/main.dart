// ignore_for_file: prefer_const_constructors, depend_on_referenced_packages, prefer_const_literals_to_create_immutables, unnecessary_to_list_in_spreads

import 'package:first/pages/cointoss.dart';
import 'package:first/pages/home.dart';
import 'package:first/pages/notes.dart';
import 'package:first/pages/settings.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget{
  const MyApp({super.key});

  @override 
  Widget build(BuildContext context){
    return MaterialApp( 
      debugShowCheckedModeBanner: false,
      home: HomePage(),
      routes: {
        '/cointoss': (context) => CoinToss(),
        '/notes': (context) => MyNotes(),
        '/homepage': (context) => HomePage(),
        '/settings': (context) => SettingsPage(),
      },
    );
  }
}

