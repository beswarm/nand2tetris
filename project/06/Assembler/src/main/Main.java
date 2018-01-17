package main;

import assembler.Assembler;

public class Main {
	
	public static void main(String[] args) {
		String commonPrefix="/home/oneswarm1988/Dropbox/Always/我的课件/nand2Tetris/nand2tetris/projects/06";
		String file1=commonPrefix+"/max/MaxL.asm";
		String file2=commonPrefix+"/max/Max.asm";
		
		String file3=commonPrefix+"/pong/PongL.asm";
		String file4=commonPrefix+"/pong/Pong.asm";
		
		String file5=commonPrefix+"/rect/RectL.asm";
		String file6=commonPrefix+"/rect/Rect.asm";
		
		
		String file0=commonPrefix+"/add/Add.asm";
		
		// symbol-less files
		assemble(file1);
		assemble(file3);
		assemble(file5);
		
		
//		 files with symbol
		assemble(file0);
		assemble(file2);
		assemble(file4);
		assemble(file6);
		
	}
	
	public static void assemble(String fileName){
		Assembler assembler = new Assembler(fileName);
		assembler.doWork();
		assembler.save();
		System.out.println(assembler);
	}
}
