
# Composite Pattern

## Overview

The Composite pattern allows us to:

> Compose objects into tree structures to represent part-whole hierarchies. Composite lets clients treat individual objects and compositions of objects uniformly.—[Gang of Four][gof]

Before we can understand how Composite pattern works, we need to understand some terms: 

* _Leaf_: A leaf is an individual component that has no children
* _Composite_ A composite (also known as a _node_ in tree terminology) is a component which may, in turn, contain other components (either leaf or composite).

The structure we're describing is, essentially, an inverted tree.

Using the Composite pattern, our leaf and composite components share one or more common operations—removing the need for client code to have to distinguish between the two—thus reducing the complexity of our code.

For example, imagine an online store that sells individual computer components, but also lets its customers assemble custom computers by arranging combinations of these components (they can choose their motherboard, cpu, power supply, etc.). When a customer proceeds to checkout, they might have any combination of individual parts, assembled computers, or both! How does the system get the price of each item given that the computers are really _composites_ of individual components?

Using the composite pattern, the shopping cart system would be able to call _getPrice_ on either an invidual component or an assembled computer. This is because the pattern defines a shared _Component_ interface that both the leaf and composite implement (we'll see later that this does come at a price).

## Implementation 

At the core of this pattern is the ability for clients to treat individual items and aggregates the same. In our example, we want to be able to get the prices for individual computer parts, or, fully assembled computers. In the diagram below, you'll notice that both the Leaf and the Composite components share the _operation_ method—in our shopping cart example, this would map to the _getPrice_ method:

![](img/composite-class.png) 

The following is an implementation solving the issue of totalling the prices for both computer parts and assembled computers as described earlier. This code is in JavaScript and uses the mocha.js testing library. If you'd like to run this code, you will need to install [node.js][node], [npm][npm], and [mocha.js][mocha]. First let's look at the tests:

```javascript
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

		it("should get price of a computer part",
			function() {
			var computerPart, 
				expected = 99;
			computerPart = new ComputerPart(expected);
			assert.equal(expected, computerPart.getPrice());
		});
		it("should get price of a part", function() {
			var part = new ComputerPart(10);
			assert.equal(10, part.getPrice());
		});
		it("should be able to get price of a computer",
			function() {
			var computer, expected;
			expected = 1234;
			computer = new Computer(expected);
			assert.equal(expected, computer.getPrice());
		});
		it("should add parts and get total price",
			function() {
			var computer, computerPart, computerPart2;
			computerPart = new ComputerPart(10);
			computerPart2 = new ComputerPart(10);
			computer = new Computer(10);
			computer.add(computerPart);
			computer.add(computerPart2);
			assert.equal(30, computer.getPrice());
		});
		it("should nest composite and leafs",
			function() {
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
		it("should remove parts from computers", 
			function() {
			var computer, computerPart, computerPart2;
			computerPart = new ComputerPart(10);
			computerPart2 = new ComputerPart(10);
			computer = new Computer(10);
			computer.add(computerPart);
			computer.add(computerPart2);
			computer.remove(computerPart);
			assert.equal(20, computer.getPrice());
		});
		it("should not throw Error for no op methods",
			function() {
			var part = new ComputerPart(10);
			assert.equal(part.add(null), undefined);
			assert.equal(part.remove(null), undefined);
		});
	});
});
```

These tests show that we can, in fact, get the total price of recursively nested components (both leafs and composites). The very last test shows that we've opted to "no op" for the methods that aren't appropriate for the Leaf component. These are _add_ and _remove_, operations really meant for dealing with the nested composites (which doesn't make sense for Leaf). This is an unfortunate aspect of Composite pattern—the Leaf component dredges up inappropriate methods from the Component interface it implements (we'll discuss this more later in the chapter). 

Let's look at the implementation. Note that since JavaScript doesn't offer interfaces (at the language level), we define "mandatory methods" on the prototype as a way to enfore they will be overriden.

```javascript
var IComponent = function() {
};
IComponent.prototype.getPrice = function() {
	throw new Error("Method must be implemented."); 
};
IComponent.prototype.add = function(component) {
	throw new Error("Method must be implemented."); 
};
IComponent.prototype.remove = function(component) {
	throw new Error("Method must be implemented."); 
};

var Computer = function(price) {
	this.price = price;
	this.components = [];
};
Computer.prototype = new IComponent();
Computer.prototype.getPrice = function() {
	var i,
		len=this.components.length,
		total = this.price;

	if (len) {
		for(i=0; i<len; i++) {
			total += this.components[i].getPrice();
		}
	}
	return total;
};
Computer.prototype.add = function(component) {
	this.components.push(component);
};
Computer.prototype.remove = function(componentToRemove) {
	var i,
		len = this.components.length,
		updatedArray = [];

	for (i = 0; i < len; i++) {
		// Pushes all but the one being removed 
		if(componentToRemove !== this.components[i]) {
			updatedArray.push(this.components[i]);
		}
	}
	this.components = updatedArray;
};

var ComputerPart = function(price) {
	this.price = price;
};
ComputerPart.prototype = new IComponent();
ComputerPart.prototype.getPrice = function() {
	return this.price;
};
// No ops
ComputerPart.prototype.add = function(component) {};
ComputerPart.prototype.remove = function(component) {};


module.exports.IComponent = IComponent;
module.exports.Computer = Computer;
module.exports.ComputerPart = ComputerPart;
```

## Issues

Two S.O.L.I.D. principles are violated by this pattern: 

* The interface has two responsibilities, those related to leafs, and those related to composites. Thus we can say that it violates the Single Responsibility Principle.
* Since Leaf components will need to implement _add_ and _remove_, they will, in doing so, break the Liskov Substitution Principle (LSP) (one is quite _surprised_ to see an _add_ or _remove_ method in a Leaf!).

As you recall, we simply chose to "no op" in the Leaf implementation for these methods. The drawback to this approach is that there's a chance we're masking buggy client code.

There are some other alternatives (all with their own tradeoffs):

* Only define the composite related methods in the Composite class

This has the drawback that client code now has to do run-time checks to determine if it's dealing with a Leaf or a Composite component. Thus we can no longer treat all components uniformly (one of the main supposed benefits!) 

* Throw exceptions in composite methods if called on the Leaf

This trades robustness (the ability of a system to cope with errors, or, not crash), for type safety, ensuring that buggy client code isn't masked.

You may also need to deal with some of the following challenges:

* Enforcing the restriction of certain components from composites
* Ordering nested components
* Performance issues due to deeply nested composites 

## Summary 

As we have seen, there are some design tradeoffs to take in to account when deciding whether or not to implement the Composite pattern. It favors _transparency_ (being able to treat all components uniformly) over _safety_; and so you will be able to treat leaf and composite components operations the same. However, you will be violating SRP and LSP to some degree. As tradeoffs are a reality, you will have to use your judgement to decide if the benefits outweigh the costs.

