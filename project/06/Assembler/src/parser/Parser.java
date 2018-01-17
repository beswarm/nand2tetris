package parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {
	public static final int A_COMMAND = 0;
	public static final int C_COMMAND = 1;
	public static final int L_COMMAND = 2;

	private List<String> lines = new ArrayList<String>();
	private int index = 0;

	public Parser() {
	}

	public Parser(String filePath) {
		try {
			InputStream in = new FileInputStream(filePath);
			Scanner sc = new Scanner(in);

			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				System.out.println(line);
				String preparedLine = line.replaceAll("//.+", "");
				if (!preparedLine.isEmpty()) {
					lines.add(preparedLine.replaceAll("\\s+", ""));
				}
			}
			System.out
					.println("File readed, comments and space are removed------");

			for (int i = 0; i < lines.size(); i++) {
				System.out.println(lines.get(i));
			}

			System.out.println("lines is " + lines.size());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean hasMoreCommands() {
		if (index < lines.size()) {
			return true;
		}
		return false;
	}
	
	public void reset(){
		index=0;
	}

	public void advance() {
		++index;
	}

	public int commandType() {
		String command = lines.get(index);
		if (command.contains("@")) {
			return A_COMMAND;
		}

		if (command.contains("(")) {
			return L_COMMAND;
		}

		return C_COMMAND;
	}

	public String commandTypeString() {
		String command = lines.get(index);
		if (command.contains("@")) {
			return "A_COMMAND";
		}

		if (command.contains("(")) {
			return "L_COMMAND";
		}

		return "C_COMMAND";
	}

	public String symbol() {
		String command = lines.get(index);

		if (this.commandType() == C_COMMAND) {
			throw new RuntimeException("A/L_COMMAND needed");
		}

		if (this.commandType() == A_COMMAND) {
			return command.substring(command.indexOf("@") + 1);
		}

		if (this.commandType() == L_COMMAND) {
			return command.substring(1, command.length() - 1);
		}
		return null;// Should not happen;
	}

	public String dest() {
		if (this.commandType() != C_COMMAND) {
			throw new RuntimeException("C_COMMAND needed");
		}
		String command = lines.get(index);
		int equl = command.indexOf("=");

		if (equl < 0) {
			return "null";
		} else {
			return command.substring(0, equl);
		}
	}

	public String comp() {
		if (this.commandType() != C_COMMAND) {
			throw new RuntimeException("C_COMMAND needed");
		}
		String command = lines.get(index);
		int equl = command.indexOf("=");
		int semi = command.indexOf(";");

		int begin = equl < 0 ? 0 : equl + 1;
		int end = semi < 0 ? command.length() : semi;
		return command.substring(begin, end);
	}

	public String jump() {
		if (this.commandType() != C_COMMAND) {
			throw new RuntimeException("C_COMMAND needed");
		}

		String command = lines.get(index);
		int semi = command.indexOf(";");
		if (semi < 0) {
			return "null";
		} else {
			return command.substring(semi + 1, command.length());
		}

	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("the current index is " + index)
				.append(": " + lines.get(index))
				.append("  " + this.commandTypeString())
				.append("  "
						+ (this.commandType() == C_COMMAND ? "dest:"
								+ this.dest() + ",comp:" + this.comp()
								+ ",jump:" + this.jump() : "symbol:"
								+ this.symbol()));
		return sb.toString();
	}

}
