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
