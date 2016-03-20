package abstractSyntax;

public class Expression implements ExpressionInterface {
	
	private final Term first;
	private final Expression rest;
	
	public Expression(Term first, Expression rest) {
		
		this.first = first;
		this.rest = rest;
	}
	
	public double eval(){
		
		double res = first.eval();
		
		if (rest != null)
			res += rest.eval();
		
		return res;
	}
	
	public String print(){
		
		String res = first.print();
		
		if (rest != null)
			res += " + " + rest.print();
		
		return res;
	}

}
