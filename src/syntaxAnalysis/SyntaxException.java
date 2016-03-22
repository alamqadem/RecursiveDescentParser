package syntaxAnalysis;

import syntaxAnalysis.Scanner.Token;

@SuppressWarnings("serial")
public class SyntaxException extends Exception {
	
	private final String message;

	public SyntaxException(String message) {
		this.message = message;
	}
	
	public SyntaxException(Token expected, Token founded) {		
		this.message = "expected " + expected + " instead of " + founded; 
	}
	
	@Override
	public String getMessage() {
		return message; 
	}

}
