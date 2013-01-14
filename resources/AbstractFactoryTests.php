<?php

require_once('AbstractFactory.php');

class AbstractFactoryTests extends PHPUnit_Framework_TestCase {

	public function testFactoriesGetCorrectStandardEnginePart() {
		$standardCarFactory = new StandardCarPartsFactory();
		$this->assertInstanceOf('CombustionEngine',
				$standardCarFactory->createEngine(),
				"Standard gets correct CombustionEngine");
	}
	public function testFactoriesGetCorrectMuscleEnginePart() {
		$muscleCarFactory = new MuscleCarPartsFactory();
		$this->assertInstanceOf('V8Engine',
				$muscleCarFactory->createEngine(),
				"Muscle gets correct V8Engine");
	}
	public function testFactoriesGetCorrectHybridEnginePart() {
		$hybridCarFactory = new HybridCarPartsFactory();
		$this->assertInstanceOf('HybridEngine',
				$hybridCarFactory->createEngine(),
				"Hybrid car gets correct HybridEngine");
	}
	public function testGetCorrectStandardPassengerCompartment() {
		$standardCarFactory = new StandardCarPartsFactory();
		$this->assertInstanceOf('StandardPassengerCompartment',
				$standardCarFactory->createPassengerCompartment(),
				"Standard gets correct StandardPassengerCompartment");
	}
	public function testGetCorrectMusclePassengerCompartment() {
		$muscleCarFactory = new MuscleCarPartsFactory();
		$this->assertInstanceOf('MusclePassengerCompartment',
				$muscleCarFactory->createPassengerCompartment(),
				"Muscle car gets correct MusclePassengerCompartment");
	}
	public function testGetCorrectHybridPassengerCompartment() {
		$hybridCarFactory = new HybridCarPartsFactory();
		$this->assertInstanceOf('HybridPassengerCompartment',
				$hybridCarFactory->createPassengerCompartment(),
				"Hybrid car gets correct HybridPassengerCompartment");
	}

	public function testAllPartsBuiltWhenCarStarted() {
		$muscleCarFactory = new MuscleCarPartsFactory();
		$car = new Car($muscleCarFactory);
		$car->start();
		$this->assertInstanceOf('V8Engine', $car->getEngine(),
				"MuscleCar gets correct V8Engine");
		$this->assertInstanceOf('MusclePassengerCompartment',
				$car->getPassengerCompartment(),
				"MuscleCar gets correct MusclePassengerCompartment");
	}
	public function testCarPartsFactoryCreateEngineCalled() {
		$hybridEngine = new HybridEngine();
        $factoryStub = $this->getMock('HybridCarPartsFactory');
        $factoryStub->expects($this->once())
        	 ->method('createEngine')
        	 ->will($this->returnValue($hybridEngine));
		$car = new Car($factoryStub);
		$car->start();
    }
}
?>
