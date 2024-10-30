
import 'dart:ui';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_colorpicker/flutter_colorpicker.dart';

class SettingsPage extends StatefulWidget {
  const SettingsPage({super.key});

  @override
  _SettingsPageState createState() => _SettingsPageState();
}

class _SettingsPageState extends State<SettingsPage> {
  bool isDrawingMode = false;
  List<Path> drawings = []; 
  int currentPage = 0;
  final double pageHeight = 800; 
  final ScrollController _scrollController = ScrollController();

  double lineWidth = 2.0; 
  Color lineColor = const Color.fromARGB(255, 13, 71, 161); 

  @override
  void initState() {
    super.initState();
    drawings = List.generate(5, (_) => Path()); 
    _scrollController.addListener(() {
      setState(() {
        currentPage = (_scrollController.offset ~/ pageHeight).clamp(0, drawings.length - 1);
      });
    });
  }

  void toggleDrawingMode() {
    setState(() {
      isDrawingMode = !isDrawingMode;
    });
  }

  void onDrawStart(Offset point) {
    if (isDrawingMode) {
      setState(() {
        drawings[currentPage].moveTo(point.dx, point.dy);
      });
    }
  }

  void onDrawUpdate(Offset point) {
    if (isDrawingMode) {
      setState(() {
        drawings[currentPage].lineTo(point.dx, point.dy+ _scrollController.offset- kToolbarHeight );
      });
    }
  }

  void onPointerEvent(PointerEvent event) {
    setState(() {
      if (event is PointerDownEvent && event.kind == PointerDeviceKind.stylus) {
        isDrawingMode = true;
      } 
      if (event is PointerUpEvent && event.kind == PointerDeviceKind.stylus) {
        isDrawingMode = false;
      } 
      if (event is PointerDownEvent && event.kind != PointerDeviceKind.stylus) {
        isDrawingMode = false;
      }


    });
  }

    void onPointerhova(PointerEvent event) {
    setState(() {
       if (event is PointerHoverEvent && event.kind == PointerDeviceKind.stylus) {
        isDrawingMode = true;
      } 

    });
  }

  void _changeLineWidth(double? value) {
    if (value != null) {
      setState(() {
        lineWidth = value;
      });
    }
  }

  void _pickColor() async {
    Color? pickedColor = await showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text('Select Line Color'),
        content: SingleChildScrollView(
          child: BlockPicker(
            pickerColor: lineColor,
            onColorChanged: (color) {
              setState(() {
                lineColor = color;
              });
              Navigator.of(context).pop(color);
            },
          ),
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Settings"),
        actions: [
          IconButton(
            icon: Icon(isDrawingMode ? Icons.edit_off : Icons.edit),
            onPressed: toggleDrawingMode,
          ),
          DropdownButton<double>(
            value: lineWidth,
            items: [1.0, 2.0, 3.0, 4.0, 5.0, 10.0, 15.0]
                .map((width) => DropdownMenuItem<double>(
                      value: width,
                      child: Text("$width px"),
                    ))
                .toList(),
            onChanged: _changeLineWidth,
          ),
          IconButton(
            icon: Icon(Icons.color_lens),
            onPressed: _pickColor,
          ),
        ],
      ),
      body: Listener(
        onPointerDown: onPointerEvent,
        onPointerHover: onPointerhova,
        onPointerUp: onPointerEvent,
        

        child: GestureDetector(
          onPanStart: (details) {
            RenderBox renderBox = context.findRenderObject() as RenderBox;
            Offset localPosition = renderBox.globalToLocal(details.globalPosition);
            onDrawStart(Offset(localPosition.dx, localPosition.dy - kToolbarHeight  + _scrollController.offset ));
          },
          onPanUpdate: (details) {
            RenderBox renderBox = context.findRenderObject() as RenderBox;
            Offset localPosition = renderBox.globalToLocal(details.globalPosition);
            onDrawUpdate(Offset(localPosition.dx, localPosition.dy  ));
          },
          child: SingleChildScrollView(
            physics: isDrawingMode ? NeverScrollableScrollPhysics() : AlwaysScrollableScrollPhysics(),
            controller: _scrollController,
            child: Stack(
              children: [
                Container(
                  height: pageHeight * drawings.length,
                  child: CustomPaint(
                    painter: MultiPageDrawingPainter(drawings, pageHeight, lineColor, lineWidth),
                    size: Size.infinite,
                  ),
                ),
                Positioned(
                  bottom: 16,
                  left: 16,
                  child: Text(
                    'Page ${currentPage + 1}',
                    style: TextStyle(color: Colors.black, fontSize: 20),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}

class MultiPageDrawingPainter extends CustomPainter {
  final List<Path> drawings;
  final double pageHeight;
  final Color lineColor;
  final double lineWidth;

  MultiPageDrawingPainter(this.drawings, this.pageHeight, this.lineColor, this.lineWidth);

  @override
  void paint(Canvas canvas, Size size) {
    final paint = Paint()
      ..color = lineColor
      ..strokeCap = StrokeCap.round
      ..strokeWidth = lineWidth
      ..style = PaintingStyle.stroke;

    for (int page = 0; page < drawings.length; page++) {
      canvas.drawLine(Offset(0, page * pageHeight), Offset(size.width, page * pageHeight), paint..color = Color.fromARGB(255, 202, 202, 202));
      canvas.drawPath(drawings[page], paint..color = lineColor,);
    }
  }

  @override
  bool shouldRepaint(covariant CustomPainter oldDelegate) {
    return true;
  }
}
