# MELTr
For manual visualization of live music
This program allows the user to create trippy/psychedlic visuals that go along with music either playing out of a speaker, or being performed live. 
![IMG_20230930_150244_186](https://github.com/b-s-l-s-k/MELTr/assets/146375342/8f90c0f1-5150-4b72-bedf-247b637a506e)

This was created out of the need for an open-source VJ (Visual Jockey) program as the current market only contains niche products, or hyper expensive multi-faceted ones.

## GOALS:
To create a VJ program the is efficient, easy to use, and super customizable. 

## Getting Started 
The current configuration is as follows ny default:

Once the program starts, a little splashs reen will appear. After its done loading, a white window will appear. Omce kt does, Press the SPACE bar and it will enable the thread. 

It is recommended that the user presses /, l, ;, ', O, P, -, =, 2, D
In that order, to get started. 
[Key] [Mapping]

SPACE - Start/Stop

A - Add Line 

S - Add Clear boxes

D - Add Colored Squares

F - Add Spikes

G - Add Text (funtionality limited)

H - (inactive)

J - Add Circles

Backspace - Clear last added Context

Q - Toggle Glitch Effect

W - Toggle Image (unfinished)

1 - Normal Mode

2 - Reflect Mode

3 - Deep Reflect Mode

4 - Quad Reflect Mode

5 - Sideways Splay Mode


O - Toggle Rotation Shifter

P - Toggle Scale Shifter

L - Enable Angle

; - Enable Scale

' - Enable Translation

/ - Enable Drawing Mode

Esc - Close Program 

Up/Down -manually change Angle
Left/Right - manually change Scale
PgUp/PgDown - X translation 
Home/End - Y translation 


## CONTROLS:
Controls are mapped via the file "keys.txt" in the following format

### Regular Key:
**[KEYCODE] [TYPE] [SUB_TYPE] [DIRECTION_TYPE]**

  ex) 65 0 0 -1             ------> Key: "a" -> ADD CONTEXT -> LINE -> N/A
  
### Multi-Key:

[KEYCODE] [# OF SETTINGS] [TYPE] [SUB_TYPE] [DIRECTION_TYPE] ...

  ex)-61440 2 2 -1 0 2 -2 0 ------> Key: "F13" -> 2 SETTINGS -> 
  
  1st: SET SETTING -> CHANGE ANGLE -> SET @0
    
  2nd: SET SETTING -> CHANGE SCALE -> SET @0
    

    
Keycode corresponds to the output of KeyEvent.getKeyCode();

======================TYPE===========================

> public static final int ADD_CONTEXT = 0, REMOVE_CONTEXT = 1, SET_SETTING = 2, SET_DRAW = 3, SET_EFFECT = 4, SET_SHIFTER = 5;


 
======================SUB-TYPE========================

----------------------CONTEXT-------------------------

> public static final int CONTEXT_LINE = 0,  CONTEXT_CLEAR = 1,  CONTEXT_COLOR = 2,  CONTEXT_GLITCH = 3, CONTEXT_WORD = 4;

----------------------SETTING---------------------------

> public static final int SETTING_ANGLE = 0, SETTING_SCALE = 1, SETTING_TRANX = 2, SETTING_TRANY = 3, SETTING_DRAWTOGGLE = 4;

> public static final int CHANGE_ANGLE = -1, CHANGE_SCALE = -2, CHANGE_TRANX = -3, CHANGE_TRANY = -4,CHANGE_DRAWTOGGLE = -5;

----------------------DRAW-MODE-------------------------

> public static final int DRAW_NORMAL = 0, DRAW_DOUBLE = 1, DRAW_2XDOUBLE = 2, DRAW_QUAD = 3, DRAW_GLITCH = 4, DRAW_LIFE = 5, DRAW_BURST = 6;

----------------------SHIFTER---------------------------

> public static final int TOGGLE_ANGLE = 0, TOGGLE_SCALE = 1, TOGGLE_TRANX = 2, TOGGLE_TRANY = 3;


 
======================DIRECTION-TYPE====================

----------------------SHIFT-----------------------------

> public static final int SHIFT_UP = 0, SHIFT_DOWN = 1 ,SHIFT_SET = 2      ,      SETTING_NA = -1;



## LINKS:
Links are how drawing modes are connected together. 
Drawing Modes (any class the extends the abstract class **PaintMode**) is stored into an array of <PaintMode>
The index of the Mode within the array corresponds to an integer that represents the linked Mode
Negative Link numbers tell MELTr to use the linked Mode _BEFORE_ the selected
Positive Numbers tell MELTr to use the linked mode _AFTER_ the selected
If the number of the link is the same as the index within the array, only the selected is used
