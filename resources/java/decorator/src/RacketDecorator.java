
public abstract class RacketDecorator implements Racket {

	protected Racket racket;
	protected double price;
	
	public RacketDecorator(Racket component) {
		racket = component;
	}
	
	@Override
	public double getPrice() {
		return racket.getPrice() + price;
	}

}
