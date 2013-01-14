<?php

class Metadata {
	public function getModules() {
		// Let's imagine that in a "real system" we'd be getting
		// these from a known metadata file.
		return array( 
			"moduleA" => "ModuleA",
			"moduleB" => "ModuleB",
		);
	}	
}

class DefaultModule {
}
class ModuleA {
}
class ModuleB {
}

class ProviderFramework {
	private static $modules = null;

	private static function initMetadata() {
		if (self::$modules == null) {
			$meta = new Metadata();
			self::$modules = $meta->getModules();
		}
	}
	
	public static function getInstance($key) {
		self::initMetadata();
		if (!isset(self::$modules[$key])) {
			return new DefaultModule();
		}
		$klass = self::$modules[$key];
		return new $klass();
	}
}

?>