import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class RacketTests {

	private ConcreteRacket racket;
	
	@Before
	public void setUp() throws Exception {
		racket = new ConcreteRacket();
	}

	@After
	public void tearDown() throws Exception {
		racket = null;
	}

	@Test
	public void testShouldDecorateComponentWithSynthGut() {
		RacketDecorator decorator = new PrinceSyntheticGutStringDecorator(racket);
		assertEquals("Should wrap with Synthgut decorator and add it's price", 
				105.00, decorator.getPrice(), 0.01);
	}
	@Test
	public void testShouldDecorateComponentWithVSGut() {
		RacketDecorator decorator = new VSGutStringDecorator(racket);
		assertEquals("Should wrap with Synthgut decorator and add it's price", 
				140.00, decorator.getPrice(), 0.01);
	}
	@Test
	public void testShouldAddMultipleDecoratorTypes() {
		RacketDecorator decorator = new WilsonProOvergripDecorator( new VSGutStringDecorator(racket) );
		assertEquals("Should wrap with VSGut decorator and add it's price", 
				143.00, decorator.getPrice(), 0.01);
	}
	@Test
	public void testShouldAddSameDecoratorsMultipleTimes() {
		RacketDecorator decorator = new WilsonProOvergripDecorator( new VSGutStringDecorator(racket) );
		RacketDecorator wrappedAgain = new WilsonProOvergripDecorator( decorator );
		assertEquals("Should wrap with multiple decorators and adding all to price", 
				146.00, wrappedAgain.getPrice(), 0.01);
	}
}
