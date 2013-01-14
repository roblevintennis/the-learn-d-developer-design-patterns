<?php

class Car {
	protected $engine = null;
	protected $passengerCompartment = null;

	public function __construct(CarPartsFactory $partsFactory) {
		$this->engine = $partsFactory->createEngine();	
		$this->passengerCompartment = 
			$partsFactory->createPassengerCompartment();
	}
	public function start() {
		$this->engine->start();
	}
	public function stop() {
		$this->engine->stop();
	}
	public function accelerate() {
		$this->engine->accelerate();
	}
	protected function __ensureBuilt() {
		if ($this->engine == null) {
			$this->build();
		}
	}
	public function getEngine() {
		return $this->engine;
	}
	public function getPassengerCompartment() {
		return $this->passengerCompartment;
	}
}

abstract class CarPartsFactory {
	abstract public function createEngine();
	abstract public function createPassengerCompartment();
	// ... Doors, Wheels, etc., etc.
}

class StandardCarPartsFactory extends CarPartsFactory {
	public function createEngine() {
		return new CombustionEngine();
	}
	public function createPassengerCompartment() {
		return new StandardPassengerCompartment();
	}
}

class MuscleCarPartsFactory extends CarPartsFactory {
	public function createEngine() {
		return new V8Engine();
	}
	public function createPassengerCompartment() {
		return new MusclePassengerCompartment();
	}
}

class HybridCarPartsFactory extends CarPartsFactory {
	public function createEngine() {
		return new HybridEngine();
	}
	public function createPassengerCompartment() {
		return new HybridPassengerCompartment();
	}
}

interface Engine {
	public function start();
	public function stop();
	public function accelerate();
}

class HybridEngine implements Engine {
	public function start() {
		echo "Starting hybrid engine\n";
	}
	public function stop() {
		echo "Stopping hybrid engine\n";
	}
	public function accelerate() {
		echo "Accelerating hybrid engine\n";
	}
} 

class V8Engine implements Engine {
	public function start() {
		echo "Starting V8 engine\n";
	}
	public function stop() {
		echo "Stopping V8 engine\n";
	}
	public function accelerate() {
		echo "Accelerating V8 engine\n";
	}
} 

class CombustionEngine implements Engine {
	public function start() {
		echo "Starting Combustion engine\n";
	}
	public function stop() {
		echo "Stopping Combustion engine\n";
	}
	public function accelerate() {
		echo "Accelerating Combustion engine\n";
	}
} 

interface PassengerCompartment {}
class StandardPassengerCompartment implements PassengerCompartment {}
class HybridPassengerCompartment implements PassengerCompartment {}
class MusclePassengerCompartment implements PassengerCompartment {}

?>