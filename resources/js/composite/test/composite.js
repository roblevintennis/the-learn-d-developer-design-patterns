// Install node.js, npm, and then mocha:
// $ npm install -g mocha
// $ mocha --ignore-leaks
var assert, Component, Computer, ComputerPart;

assert = require('assert');
Component = require("../composite.js").IComponent;
Computer = require("../composite.js").Computer;
ComputerPart = require("../composite.js").ComputerPart;

describe("Composite tests", function() {

	describe("Shared methods", function() {

		it("should be able to get price of a computer part", function() {
			var computerPart, 
				expected = 99;
			computerPart = new ComputerPart(expected);
			assert.equal(expected, computerPart.getPrice());
		});
		it("should get price of a part", function() {
			var part = new ComputerPart(10);
			assert.equal(10, part.getPrice());
		});
		it("should be able to get price of a computer", function() {
			var computer, expected;
			expected = 1234;
			computer = new Computer(expected);
			assert.equal(expected, computer.getPrice());
		});
		it("should add a parts to computers and get total price", function() {
			var computer, computerPart, computerPart2;
			computerPart = new ComputerPart(10);
			computerPart2 = new ComputerPart(10);
			computer = new Computer(10);
			computer.add(computerPart);
			computer.add(computerPart2);
			assert.equal(30, computer.getPrice());
		});
		it("should nest composite and leafs", function() {
			var c1, c2, c3, p1, p2, p3;
			p1 = new ComputerPart(1);
			p2 = new ComputerPart(1);
			p3 = new ComputerPart(1);
			c1 = new Computer(1);
			c2 = new Computer(1);
			c3 = new Computer(1);
			c3.add(p3);
			c2.add(p2);
			c2.add(c3);
			c1.add(p1);
			c1.add(c2);
			assert.equal(6, c1.getPrice());
		});
		it("should remove parts from computers", function() {
			var computer, computerPart, computerPart2;
			computerPart = new ComputerPart(10);
			computerPart2 = new ComputerPart(10);
			computer = new Computer(10);
			computer.add(computerPart);
			computer.add(computerPart2);
			computer.remove(computerPart);
			assert.equal(20, computer.getPrice());
		});
		it("should not throw Error for IComponent unimplemented (no op) methods in ComputerPart", function() {
			var part = new ComputerPart(10);
			assert.equal(part.add(null), undefined);
			assert.equal(part.remove(null), undefined);
		});
	});
});

