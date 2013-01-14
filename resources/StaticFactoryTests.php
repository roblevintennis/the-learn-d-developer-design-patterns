<?php
require_once('StaticFactory.php');

class StaticFactoryTests extends PHPUnit_Framework_TestCase {
	
	public function testGetDefinedInstance() {
		$returnedInstance = ProviderFramework::getInstance("moduleA");
		$this->assertInstanceOf('ModuleA', $returnedInstance,
			"Should get the correct instance");
	}

	public function testGetUndefinedInstanceReturnsDefault() {
		$returnedInstance = ProviderFramework::getInstance("bogus");
		$this->assertInstanceOf('DefaultModule', $returnedInstance,
			"Should fall back to a default instance if undefined key");
	}
}

?>