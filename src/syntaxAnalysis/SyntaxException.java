package syntaxAnalysis;

import syntaxAnalysis.Scanner.Type;

public class SyntaxException extends Exception {
	
	private final String message;

	public SyntaxException(String message) {
		this.message = message;
	}
	
	public SyntaxException(Type expected, Type founded) {		
		this.message = "expected " + expected + " instead of " + founded; 
	}
	
	@Override
	public String getMessage() {
		return message; 
	}

}
