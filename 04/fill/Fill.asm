// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input. 
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel. When no key is pressed, the
// program clears the screen, i.e. writes "white" in every pixel.


// black the Screen 
// if 24576>0 fill , otherwise unfill

// define max screen 
@24576
D=A
@MXSCREEN
M=D

// define screen pointer (16384)
@SCREEN
D=A
@POINTER
M=D


(LOOP)
@KBD
D=M
//if kbd value > 0, go the fill loop 
@FILL
D;JGT
//else go to unfill loop
@UNFILL
0;JMP


(FILL)
// if the pointer point to MXSCREEN , go to loop
@MXSCREEN
D=A
@POINTER
D=D-A
@LOOP
D;JEQ

//black the pixel the pointer point to
D=-1
@POINTER
A=M
M=D

// iterate pointer by 1
D=1
@POINTER
D=D+M
@POINTER
M=D

@LOOP
0;JMP

(UNFILL)
// the index i goes from 0 to 8192, and set the screen memory to 0 
@SCREEN
D=A
@POINTER
D=D-M
@LOOP
D;JEQ

// unfill the screen
D=0
@POINTER
A=M
M=D

// decrement Pointer
D=1
@POINTER
D=M-D
@POINTER
M=D

@LOOP
0;JMP


