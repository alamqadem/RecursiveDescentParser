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

public class Parser {
	
	private Scanner scanner;
	private Scanner.Type token;
	
	public Statement parse(Reader r) throws SyntaxException {
		
		scanner = new Scanner(r);
		nextToken();
		Statement stat = statement();
		expect(Scanner.Type.EOF);
		
		return stat;
	}

	// stat ::= variable = expr;
	private Statement statement() throws SyntaxException {
		
		Variable var = variable();
		
		expect(Scanner.Type.ASSIGN);
		
		Expression exp = expr();
		
		Statement stat = new Statement(var, exp);
		
		expect(Scanner.Type.SEMICOLUMN);
		
		return stat;
	}

	// expr ::= term [ + term ]
	private Expression expr() throws SyntaxException {
		
		Term term = term();
		
		if (token == Scanner.Type.PLUS) {	
			nextToken();
			return new Expression(term, expr());	
		}
		
		return new Expression(term, null);
	}

	// term ::= factor [ * factor ]
	private Term term() throws SyntaxException {
		
		Factor fact = factor();
		
		if (token == Scanner.Type.MULTIPLY) {
			nextToken();
			return new Term(fact, term());
		}
		
		return new Term(fact, null);
	}

	// factor ::= ( expr ) | var | constant | call( fun, expr )
	private Factor factor() throws SyntaxException {
		
		if (token == Scanner.Type.LEFT_PAREN) {
			
			nextToken();
			Expression exp = expr();
			expect(Scanner.Type.RIGHT_PAREN);
			
			return new EnclosedBracketsExpression(exp);
		}  
		
		if (token == Scanner.Type.VARIABLE) 
			return variable();
		
		if (token == Scanner.Type.CONSTANT) 
			return constant();
		
		if (token == Scanner.Type.CALL) 			
			return functionCall();
		
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
			
		nextToken();
		expect(Scanner.Type.LEFT_PAREN);
		
		String functionName = null;
		if (token == Scanner.Type.VARIABLE)
			functionName = scanner.getString();
		else 
			expect(Scanner.Type.VARIABLE);
		
		nextToken();
		expect(Scanner.Type.COMMA);
		
		Expression exp = expr();
		
		expect(Scanner.Type.RIGHT_PAREN);
		
		return new FunctionCall(functionName, exp);
	}
	
	// expect: check if the current token is of type otherwise a SyntaxException exception is raised
	private void expect(Scanner.Type t) throws SyntaxException {
		if (token != t)
			throw new SyntaxException(t, token);
		
		nextToken();
	}

	// nextToken: read the next token from the scanner
	private void nextToken() {
		this.token = scanner.nextToken();
	}
	
	public static void main(String[] args) throws IOException, SyntaxException  {
		
		FileReader fileReader = new FileReader("test_files/program1.txt");
		
		Parser parser = new Parser();
		
		Statement stat = parser.parse(fileReader);
		
		System.out.println("File parsed successfully :)");
		
		System.out.println(stat);
		
		fileReader.close();
	}
}
