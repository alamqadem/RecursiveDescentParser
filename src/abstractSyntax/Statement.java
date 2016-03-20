package abstractSyntax;

public class Statement {

	private final Variable var;
	private final Expression expr;

	public Statement(Variable var, Expression exp) {
		this.var = var;
		this.expr = exp;
	}
	
	public Variable getVariable() {
		return var;
	}
	
	public Expression getExpression() {
		return expr;
	}
	
	@Override
	public String toString() {
		return var.print() + " = " + expr.print() + " [" + expr.eval() + "] ;";
	}
}
