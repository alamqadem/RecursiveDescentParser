package abstractSyntax;

public class Constant extends Factor {

	private final double number;

	public Constant(double number) {
		this.number = number;
	}

	@Override
	public double eval() {
		return number;
	}

	@Override
	public String print() {
		return String.valueOf(number);
	}

}
