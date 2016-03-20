package abstractSyntax;

public abstract class Factor implements ExpressionInterface{
	
	@Override
	public abstract double eval();

	@Override
	public abstract String print();

}
