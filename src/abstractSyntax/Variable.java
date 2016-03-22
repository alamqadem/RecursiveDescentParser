package abstractSyntax;

public class Variable extends Factor {
	
	private final String name;

	public Variable(String name) { this.name = name; }
	
	@Override
	public double eval() { return 0; }

	@Override
	public String print() { return name; }

}
