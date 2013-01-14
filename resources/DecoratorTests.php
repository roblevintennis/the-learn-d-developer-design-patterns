<?php
require_once('Decorator.php');

class DecoratorTests extends PHPUnit_Framework_TestCase 
{
    private $racket;
    public function setUp()
    {
        $this->racket = new Racket();
    }
    public function testShouldDecoratesComponentWithSynthGut()
    {
        $decorator = new PrinceSyntheticGutStringDecorator($this->racket);
        $this->assertEquals(105, $decorator->getPrice(),
            "Should properly wrap with synthetic gut decorator and add it's price to racket");
    }
    public function testShouldDecoratesComponentWithVSGut()
    {
        $decorator = new VSGutStringDecorator($this->racket);
        $this->assertEquals(140, $decorator->getPrice(),
            "Should properly wrap with gut string decorator and add it's price to racket");
    }
    public function testShouldAddMultipleDecoratorTypes()
    {
        $trickedOutRacket = new WilsonProOvergripDecorator(new VSGutStringDecorator($this->racket));
        $this->assertEquals(143, $trickedOutRacket->getPrice(),
            "Should properly wrap with multiple decorator types");
    }
    public function testShouldAddSameDecoratorsMultipleTimes()
    {
        $trickedOutRacket = new WilsonProOvergripDecorator(new VSGutStringDecorator($this->racket));
        $wrappedAgain = new WilsonProOvergripDecorator($trickedOutRacket);
        $this->assertEquals(146, $wrappedAgain->getPrice(),
            "Should properly wrap with same decorator type");
    }
}
?>