package abstractSyntax;

public class FunctionCall extends Factor {

	private final String functionName;
	private final Expression argument;

	public FunctionCall(String functionName, Expression argument) {
		
		this.functionName = functionName;
		this.argument = argument;
	}
	
	@Override
	public double eval() { return 0; }

	@Override
	public String print() { return functionName + "(" + argument.print() + ")"; }

}
