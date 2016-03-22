package syntaxAnalysis;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;

public class Scanner {
	
	private final StreamTokenizer input;
	
	public enum Token {
		INVALID_CHAR,
		VARIABLE,
		PLUS,
		MULTIPLY,
		ASSIGN,
		LEFT_PAREN,
		RIGHT_PAREN,
		SEMICOLUMN,
		COMMA,
		FALSE,
		TRUE,
		EOF,
		CALL,
		CONSTANT
	}
	
	public Scanner(Reader r) { this.input = getInputAnalyzer(r); }
	
	protected StreamTokenizer getInputAnalyzer(Reader reader) {
		
		StreamTokenizer streamTokenizer = new StreamTokenizer(reader);
		
		streamTokenizer.resetSyntax();
		streamTokenizer.eolIsSignificant(false);
		streamTokenizer.wordChars('a', 'z');
		streamTokenizer.wordChars('A', 'Z');
		streamTokenizer.ordinaryChar('+');
		streamTokenizer.ordinaryChar('*');
		streamTokenizer.ordinaryChar('=');
		streamTokenizer.ordinaryChar('(');
		streamTokenizer.ordinaryChar(')');
		streamTokenizer.ordinaryChar(';');
		streamTokenizer.parseNumbers();
		streamTokenizer.whitespaceChars(' ', ' ');
		
		return streamTokenizer;
	}
	
	public Token nextToken() {
		
		try {
			
			switch(input.nextToken()) {
			
				case StreamTokenizer.TT_EOF: return Token.EOF;
					
				case StreamTokenizer.TT_WORD: return wordSelection(input.sval);
					
				case StreamTokenizer.TT_NUMBER: return Token.CONSTANT;
					
				case '+': return Token.PLUS;
					
				case '*': return Token.MULTIPLY;
					
				case '=': return Token.ASSIGN;
					
				case '(': return Token.LEFT_PAREN;
					
				case ')': return Token.RIGHT_PAREN;
					
				case ';': return Token.SEMICOLUMN;
					
				case ',': return Token.COMMA;
					
				default: return Token.INVALID_CHAR;
			}
			
		} catch (IOException e) { return Token.EOF; }
	}

	private Token wordSelection(String tokenValue) {
		
		if (tokenValue == null) return Token.INVALID_CHAR;
		
		if (tokenValue.equals("false")) return Token.FALSE;
		
		if (tokenValue.equals("true")) return Token.TRUE;
		
		if (tokenValue.equals("call")) return Token.CALL;
		
		return Token.VARIABLE;
	}

	public String getString() { return input.sval; }
	
	public double getNumber() { return input.nval; }
}
