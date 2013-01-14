<?php
// Factories
interface Factory {
	public function createSender();
}
class FedexFactory implements Factory {
	public function createSender() {
		return new FedexSender(); 
	}
}
class SnailMailFactory implements Factory {
	public function createSender() {
		return new SnailMailSender(); 
	}
}
class AramexFactory implements Factory {
	public function createSender() {
		return new AramexSender(); 
	}
}

// Products 
class SenderTypes {
    const SnailMail = 0;
    const Fedex = 1;
    const Aramex = 2;
}
interface Sender {
	public function send($destination, $package);
	public function getType();
}
class FedexSender implements Sender {
	public function send($destination, $package) {
		echo ("Fedex sending...\n");
	}
	public function getType() {
		return SenderTypes::Fedex;
	}
}
class SnailMailSender implements Sender {
	public function send($destination, $package) {
		echo ("Snail mail sending...\n");
	}
	public function getType() {
		return SenderTypes::SnailMail;
	}
}
class AramexSender implements Sender {
	public function send($destination, $package) {
		echo ("Aramex sending international...\n");
	}
	public function getType() {
		return SenderTypes::Aramex;
	}
}
?>
