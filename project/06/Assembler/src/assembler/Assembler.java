package assembler;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import parser.Parser;
import symbol.SymbolTable;
import code.Code;

public class Assembler {
	private SymbolTable symbolTable=new SymbolTable();
	
	private String currentFile;
	private Parser parser;
	private Code code;
	private List<String> lines = new ArrayList<String>();
	
	
	public Assembler(String fileName) {
		currentFile=fileName;
		parser = new Parser(fileName);
		code = new Code();
	}

	public void doWork() {
		collectSymbols();
		
		while (parser.hasMoreCommands()) {
			switch (parser.commandType()) {
			case Parser.A_COMMAND:
				
				System.out.println("A_COMMAND------------BEGIN------");
				String symbol=parser.symbol();
				if(symbol.matches("[0-9]+")){
					String bits=Integer.toBinaryString(Integer.parseInt(symbol));
					String fullBits="0000000000000000".substring(0,16-bits.length())+bits;
					System.out.println(fullBits);

					lines.add(fullBits);
				}else{// symbolic A-Instruction
					if(symbolTable.getAddress(symbol)==null){// no symbol
						symbolTable.addAutoEntry(symbol);
						
						String bits=Integer.toBinaryString(symbolTable.getAddress(symbol));
						String fullBits="0000000000000000".substring(0,16-bits.length())+bits;
						System.out.println(fullBits);
						
						lines.add(fullBits);
					}else{// symbols defined
						String bits=Integer.toBinaryString(symbolTable.getAddress(symbol));
						String fullBits="0000000000000000".substring(0,16-bits.length())+bits;
						System.out.println(fullBits);
						
						lines.add(fullBits);
					}
				}
				System.out.println("A_COMMAND------------END------\n");

				break;
			case Parser.C_COMMAND:
				
				System.out.println("C_COMMAND--------BEGIN----------");
				StringBuilder sb=new StringBuilder();
				String comp=parser.comp();
				String dest=parser.dest();
				String jump=parser.jump();
				sb.append("111").append(code.comp(comp)).append(code.dest(dest)).append(code.jump(jump));
				lines.add(sb.toString());
				System.out.println(sb.toString());
				System.out.println("C_COMMAND--------- END---------\n");
				break;
			case Parser.L_COMMAND:
				System.out.println("L_COMMAND------------BEGIN------");

				System.out.println("L_COMMAND------------END------\n");

				break;
			default:
				throw new RuntimeException("error");
			}

			parser.advance();
		}
		
	}
	
	private void collectSymbols(){
		Integer romAddress=0;
		System.out.println("Begin collecting symbols======================");
		while (parser.hasMoreCommands()) {
			switch (parser.commandType()) {
			case Parser.L_COMMAND:
				symbolTable.addEntry(parser.symbol(), romAddress);
				break;
			default:
				romAddress=romAddress+1;
				break;
			}
			parser.advance();
		}
		parser.reset();
	}
	
	public void save(){
		String hackFile=currentFile.replaceAll(".asm", ".hack");
		try {
			OutputStream os=new FileOutputStream(hackFile);
			OutputStreamWriter writer=new OutputStreamWriter(os);
			for(int i=0;i<lines.size();i++){
				writer.write(lines.get(i)+"\n");
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Override
	public String toString(){
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<lines.size();i++){
			sb.append(lines.get(i)).append("\n");
		}
		return sb.toString();
	}
}
