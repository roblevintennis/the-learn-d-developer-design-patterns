
# Decorator Pattern

## Overview

[Design Patterns: Elements of Reusable Object-Oriented Software][gof] provide the following intent for decorators:

> Attach additional responsibilities to an object dynamically. Decorators provide a flexible alternative to subclassing for extending functionality.

They further state that decorators may be used to:

> add responsibilities to individual objects dynamically ... for responsibilities that can be withdrawn ... when extension by subclassing is impractical ...

Decorators, also known as _wrappers_, conform to the interface of the component they decorate. When the decorator is called, it in turn, calls the same method for the component it decorates (it can do so since they share a common interface). This arrangement allows us to nest decorators recursively, and add or remove them as needed.



## Decorator Pattern Class Diagram 

The following is a class diagram that represents the Decorator Pattern we'll be discussing in this chapter:

![Decorator Pattern Class Diagram](img/decorator.png "Decorator Pattern UML Class Diagram")



## Decorator Pattern Implementation 

Here are the steps to implementing the decorator pattern:

* Define your main component's interface

* Define your concrete implementations of that interface

* Define a Decorator interface or abstract class. It must also implement the main component's interface!

* Define concrete decorators that conform to above interface. _You may be able to "pull up" the method in to the above abstract Decorator if the decorated operation is simple and/or there is duplication (we did so in our examples below)._

* In the client code (code that uses the decorator(s) defined above):

	* Instantiate the main component
	* Instatiate one or many decorators being careful to "wrap" each one around the main component
	* Call decorated operations as needed 

### Java Implementation

Let us imagine that we have a store that sells tennis rackets that are set at various prices as expected. However, a customer may also choice to customize the string, grip, etc., when making their purchase. Thus we need a way to account for the change in price depending on these customizations. We can do so easily by simply wrapping each customization in respective decorators. Let's look at how this might be implemented using Java...

_Disclaimers: for the sake of brevity and understandability, we have not been "thorough" in either our tests or implementation. We may continue to take such liberties throughout the rest of the book. Also note that we have combined the tests and implementation below (whereas the project in the book's repo has separate files)_

```java

/**
 * Tests 
 */

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
		RacketDecorator decorator = 
			new WilsonProOvergripDecorator( new VSGutStringDecorator(racket) );
		assertEquals("Should wrap with VSGut decorator and add it's price", 
				143.00, decorator.getPrice(), 0.01);
	}
	@Test
	public void testShouldAddSameDecoratorsMultipleTimes() {
		RacketDecorator decorator =
			new WilsonProOvergripDecorator( new VSGutStringDecorator(racket) );
		RacketDecorator wrappedAgain = new WilsonProOvergripDecorator( decorator );
		assertEquals("Should wrap with multiple decorators and adding all to price", 
				146.00, wrappedAgain.getPrice(), 0.01);
	}
}


/**
 * Implementation 
 */

public interface Racket {
	public double getPrice();
}

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

public class PrinceSyntheticGutStringDecorator extends RacketDecorator {
	
	public PrinceSyntheticGutStringDecorator(Racket component) {
		super(component);
		this.price = 5;
	}
}

public class VSGutStringDecorator extends RacketDecorator {
	public VSGutStringDecorator(Racket component) {
		super(component);
		this.price = 40;
	}
}

public class WilsonProOvergripDecorator extends RacketDecorator {

	public WilsonProOvergripDecorator(Racket component) {
		super(component);
		this.price = 3;
	}

}

```


### PHP Implementation

Let us now look at the PHP version of this same implementation. It's virtually identical!

```php

<?php

interface iRacket 
{
	public function getPrice();
}

class Racket implements iRacket 
{
	private $price;

	public function __construct()
	{
		$this->price = 100;
	}

	public function getPrice()
	{
		return $this->price;
	}
}

// We use abstract instead of interface here so we can define 
// the properties $component and $price properties just once
// Also note that by implementing iRacket, it "gets" getPrice.
abstract class RacketDecorator implements iRacket
{
	protected $component;
	protected $price;
	public function __construct($racket)
	{
		$this->component = $racket;
	}
	public function getPrice()
	{
		return $this->component->getPrice() + $this->price;
	}
}

class PrinceSyntheticGutStringDecorator extends RacketDecorator
{
	public function __construct($racket)
	{
		parent::__construct($racket);
		$this->price = 5;
	}
}
class VSGutStringDecorator extends RacketDecorator
{
	public function __construct($racket)
	{
		parent::__construct($racket);
		$this->price = 40;
	}
}
class WilsonProOvergripDecorator extends RacketDecorator
{
	public function __construct($racket)
	{
		parent::__construct($racket);
		$this->price = 3;
	}
}

?>
```

_We have omitted the PHP tests to save space, but, if you'd like to see them, they are in the book's repo._

We have coded the above implementation quite close to the racket _domain_, but we could have chosen to make a more general Product module, in place of what we called Racket, and thereafter embody various different types of products within Product. This might be useful in a general shopping cart scenario.


## Considerations 

Let us now examine the benefits and drawbacks of this pattern, and also, look at why it might be a better choice than inheritance.

### Decorator vs. Inheritance

Decorators are often contrasted to using static inheritance to achieve the same thing—after all, we do have the option to simply create new sub-classes for each new responsibility discovered. In doing so, however, we increase the complexity of our system and pollute our base class with unrelated responsibilities. 

Let's take an example that leads to having child classes that need to share properties with each other. Many derived _Beverage_ types might need to have a Milk property, for example: Egg Nog, Shake, Mocha, while others do not (Juice should **not** have a Milk property). This leads to the following quandary: if we add milk to each derived class we will end up with _duplication_, but, if we instead add milk to the base class we violate the _substitution principle_ (as one will undoubtedly be _surprised_ to find a Milk property in their _orange juice_ instance!).

We can avoid these inheritance problems altogether as the decorator pattern allows us to: _change the skin of an object versus changing its guts_—[GoF][gof]. We simply add or remove decorators as needed. Doing so will simply _extend_ the functionality of our classes, while acting in accordance with the open/closed principle—which, as you recall, states that we can extend a class, but must not not change it directly.

### Transparency 

One key benefit of the decorator pattern is that, by using the component’s original interface, we can transparently add capabilities. This unburdens clients from having to keep track of things like: how many times has the component been decorated, in what way, etc. We simply add responsibilities, as needed, by merely wrapping the component with new decorators.

## Pitfalls

**Explosion of decorator objects**

Use of this pattern will often lead to a proliferation of small, similar classes, decreasing both understandability and maintainability. One can imagine a whole product catalog implemented this way (and get an immediate migrane headache as a result!)

**Identity tests**

Decorators are not identical to the components they wrap, and thus, tests for object types will not work. 

**Removal/reordering of decorators**

As you recall, [GoF][gof] provides that you might use decorators:

> for responsibilities that can be withdrawn

This would seem to imply the ability to withdraw decorators (perhaps at run-time). If we consider the underlying mechanics of how decorators reference one another—similar to a linked list, where one link has a direct relationship to the next or previous—one might simply manipulate references to achieve this. However, this would result in objects that _need to know too much_, adding complexity and coupling to a system. In practice, removal won't be required, but, if you absolutely must have this functionality, you might want to consider another approach. 

**It not be idiomatic for the language you're using**

The decorator pattern is more pervasive in certain (usually statically typed) languages than others. Java and .NET, both use the decorator pattern extensively in their own I/O Stream implementations. Also, most GUI programming done in Java will involve the wrapping of nested UI components. Thus, if you're on a team that's doing Java, C#, etc., the decorator pattern might be perfectly appropriate. However, if you're using a more dynamic language (like Ruby), it may not be an idiomatic choice. Since such languages allow you to add methods to objects at runtime—known as _duck typing_ ("if it walks like a duck, and quacks like a duck, treat it as a duck")—you might entirely avoid the decorator pattern.

## Exercises

We mentioned that while our implementation is quite specific to tennis rackets, one could implement a shopping cart system utilizing the decorator pattern to represent the products in that system. Do so. You don't need to implement a "full blown" shopping cart system, simply use print statements to stub out details that are not pertinent to the exercise.

Once you've done the above, add more and more products to your store's catalog. What do you notice?

