// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[3], respectively.)


//i count from RAM[0]-->0
@0
D=M
@i
M=D

// RAM[2] initanized to 0
@2
M=0


// begin the loop
(LOOP)
// if i < 0, end the loop
@i
D=M
@END
D;JLE

// and RAM[1] once more to RAM[2]
@1
D=M
@2
M=D+M

// update the i = i-1
@i
M=M-1
@LOOP
0;JMP
(END)


@END
0;JMP




