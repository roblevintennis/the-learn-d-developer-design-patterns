<?php

require_once('FactoryMethod.php');

class FactoryMethodTests extends PHPUnit_Framework_TestCase {

	public function testFactoryMethod() {
		$snailMailFactory = new SnailMailFactory();
		$fedexFactory = new FedexFactory();
		$aramexFactory= new AramexFactory();

		$this->assertInstanceOf('FedexFactory', $fedexFactory,
			"Should get the fedex factory");
		$this->assertInstanceOf('SnailMailFactory', $snailMailFactory,
			"Should get the snail mail factory");
		$this->assertInstanceOf('AramexFactory', $aramexFactory,
			"Should get the aramex factory");
	}

	public function testFactoryBegetsProduct() {
		$fedexFactory = new FedexFactory();
		$fedexProduct = $fedexFactory->createSender();
		$this->assertInstanceOf('FedexSender', $fedexProduct,
			"Should get correct sender from factory");
		$this->assertEquals(SenderTypes::Fedex, $fedexProduct->getType(),
			"Should be able to call sender's operations");
	}
}
?>
