package syntaxAnalysis;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;

public class Scanner {
	
	private StreamTokenizer input;
	private Type lastToken;
	
	public enum Type {
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
	
	public Scanner(Reader r){
	
		this.input = new StreamTokenizer(r);
		
		input.resetSyntax();
		input.eolIsSignificant(false);
		input.wordChars('a', 'z');
		input.wordChars('A', 'Z');
		input.ordinaryChar('+');
		input.ordinaryChar('*');
		input.ordinaryChar('=');
		input.ordinaryChar('(');
		input.ordinaryChar(')');
		input.ordinaryChar(';');
		input.parseNumbers();
		input.whitespaceChars(' ', ' ');
	}
	
	public Type nextToken() {
		
		try {
			
			switch(input.nextToken()) {
			
			case StreamTokenizer.TT_EOF:
				
				return Type.EOF;
				
			case StreamTokenizer.TT_WORD:
				
				if (input.sval.equals("false"))
					return Type.FALSE;
				else if (input.sval.equals("true"))
					return Type.TRUE;
				else if (input.sval.equals("call"))
					return Type.CALL;
				else
					return Type.VARIABLE;
				
			case StreamTokenizer.TT_NUMBER:
				return Type.CONSTANT;
				
			case '+':
				return Type.PLUS;
				
			case '*':
				return Type.MULTIPLY;
				
			case '=':
				return Type.ASSIGN;
				
			case '(':
				return Type.LEFT_PAREN;
				
			case ')':
				return Type.RIGHT_PAREN;
				
			case ';':
				return Type.SEMICOLUMN;
				
			case ',':
				return Type.COMMA;
				
			default:
				return Type.INVALID_CHAR;
			}
			
		} catch (IOException e) {
			return Type.EOF;
		}
	}

	public String getString() {
		return input.sval;
	}
	
	public double getNumber() {
		return input.nval;
	}
}
