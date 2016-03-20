package abstractSyntax;

public class Term implements ExpressionInterface{
	
	private final Factor first;
	private final Term rest;

	public Term(Factor first, Term rest) {
		
		this.first = first;
		this.rest = rest;
	}

	@Override
	public double eval() {
		
		double res = first.eval();
		
		if (rest != null)
			res *= rest.eval();
		
		return res;
	}

	@Override
	public String print() {
		
		String res = first.print();
		
		if (rest != null)
			res += " * " + rest.print();
		
		return res;
	}

}
