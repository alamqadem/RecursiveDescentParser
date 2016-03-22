package syntaxAnalysis;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import abstractSyntax.Constant;
import abstractSyntax.EnclosedBracketsExpression;
import abstractSyntax.Expression;
import abstractSyntax.Factor;
import abstractSyntax.FunctionCall;
import abstractSyntax.Statement;
import abstractSyntax.Term;
import abstractSyntax.Variable;
import syntaxAnalysis.Scanner.Token;

public class Parser {
	
	private Scanner scanner;
	private Token token;
	
	public Statement parse(Reader r) throws SyntaxException {
		
		scanner = new Scanner(r);
		nextToken();
		Statement stat = statement();
		expect(Token.EOF);
		
		return stat;
	}

	// stat ::= variable = expr;
	private Statement statement() throws SyntaxException {
		
		Variable var = variable();
		
		expect(Token.ASSIGN);
		
		Expression exp = expr();
		
		Statement stat = new Statement(var, exp);
		
		expect(Token.SEMICOLUMN);
		
		return stat;
	}

	// expr ::= term [ + term ]
	private Expression expr() throws SyntaxException {
		
		Term term = term();
		
		if (token == Token.PLUS) {	
			nextToken();
			return new Expression(term, expr());	
		}
		
		return new Expression(term, null);
	}

	// term ::= factor [ * factor ]
	private Term term() throws SyntaxException {
		
		Factor fact = factor();
		
		if (token == Token.MULTIPLY) {
			nextToken();
			return new Term(fact, term());
		}
		
		return new Term(fact, null);
	}

	// factor ::= ( expr ) | var | constant | call( fun, expr )
	private Factor factor() throws SyntaxException {
		
		if (token == Token.LEFT_PAREN) {
			
			nextToken();
			Expression exp = expr();
			expect(Token.RIGHT_PAREN);
			
			return new EnclosedBracketsExpression(exp);
		}  
		
		if (token == Token.VARIABLE) 
			return variable();
		
		if (token == Token.CONSTANT) 
			return constant();
		
		if (token == Token.CALL) {
			
			nextToken();
			return functionCall();
		}
		
		throw new SyntaxException("Unrecognized factor production, founded: " + token);
	}

	// var ::= identifier
	private Variable variable() {
		
		Variable exp = new Variable(scanner.getString());
		nextToken();
		
		return exp;
	}
	
	// constant
	private Constant constant() {
		
		Constant constant = new Constant(scanner.getNumber());
		nextToken();
		
		return constant;
	}
	
	// call(function, exp)
	private Factor functionCall() throws SyntaxException {
			
		expect(Token.LEFT_PAREN);
		
		String functionName = null;
		
		if (token == Token.VARIABLE) {

			functionName = scanner.getString();
			nextToken();			
		} else 
			throw new SyntaxException("Expected identifier as first argument " 
									+ "of function call, founded: " + token);
		
		expect(Token.COMMA);
		
		Expression exp = expr();
		
		expect(Token.RIGHT_PAREN);
		
		return new FunctionCall(functionName, exp);
	}
	
	/** expect
	 *  check if the current token is of type otherwise a SyntaxException exception is raised 
	**/
	private void expect(Token expectedToken) throws SyntaxException {
		
		if (token != expectedToken)
			throw new SyntaxException(expectedToken, token);
		
		nextToken();
	}

	/** nextToken
	 *  read the next token from the scanner 
	**/
	private void nextToken() { this.token = scanner.nextToken(); }
	
	
	public static void main(String[] args) throws IOException, SyntaxException  {
		
		FileReader fileReader = new FileReader("test_files/program1.txt");
		
		Parser parser = new Parser();
		
		Statement stat = parser.parse(fileReader);
		
		System.out.println("File parsed successfully :)");
		
		System.out.println(stat);
		
		fileReader.close();
	}
}
