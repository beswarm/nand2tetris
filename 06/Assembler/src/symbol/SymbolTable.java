package symbol;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
	private Map<String,Integer> symbols=new HashMap<String,Integer>();
	private Integer userDefinedVariable=16;
	
	public SymbolTable(){
		symbols.put("SP",0);
		symbols.put("LCL",1);
		symbols.put("ARG", 2);
		symbols.put("THIS", 3);
		symbols.put("THAT", 4);
		symbols.put("SCREEN", 16384);
		symbols.put("KBD", 24576);
		
		for(int i=0;i<=15;i++){
			symbols.put("R"+i, i);
		}
		
	}
	
	public void addEntry(String symbol,int address){
		symbols.put(symbol, address);
	}

	public void addAutoEntry(String symbol){
		symbols.put(symbol, userDefinedVariable++);
	}
	
	public boolean contains(String symbol){
		return symbols.containsKey(symbol);
	}
	
	public Integer getAddress(String symbol){
		return symbols.get(symbol);
	}
	

	

}
