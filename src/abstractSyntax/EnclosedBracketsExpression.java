package abstractSyntax;

public class EnclosedBracketsExpression extends Factor {
	
	private final Expression expression;

	public EnclosedBracketsExpression(Expression expression) { this.expression = expression; }

	@Override
	public double eval() { return expression.eval(); }

	@Override
	public String print() { return "( " + expression.print() + " )" ; }

}
