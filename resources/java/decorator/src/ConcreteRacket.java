
public class ConcreteRacket implements Racket {
	
	// In a "real program", we'd create a "value object"
	private double price;

	public ConcreteRacket() {
		price = 100;
	}
	
	@Override
	public double getPrice() {
		return price;
	}	
}
