// Install node.js, npm, mocha.js, and sinon.js:
// $ npm install -g mocha
// $ npm install sinon
// $ mocha --ignore-leaks
var sinon, assert, 
	IBartender, Bartender, Waitress, DrinkMenu;

assert = require('assert');
sinon = require('sinon');
IBartender = require("../builder.js").IBartender;
Bartender = require("../builder.js").Bartender;
Waitress = require("../builder.js").Waitress;
DrinkMenu = require("../builder.js").DrinkMenu;

describe("Builder tests", function() {
	var bartender, waitress;
	beforeEach(function() {
		bartender = new Bartender();
		waitress = new Waitress(bartender, DrinkMenu);
	});
	afterEach (function() {
		bartender = null;
		waitress = null;
	});

	it("should take an order for mojito", function() {
		var spy = sinon.spy(bartender, "prepareMojito");
		waitress.takeOrder([DrinkMenu.mojito]);
		assert(spy.called);
		bartender.prepareMojito.restore();
	});

	it("should take order for all drinks on menu", function() {
		var allDrinks=[], 
			key, 
			allSpies={};

		// Spy on each of the Bartender's prepareXXX methods
		for(key in DrinkMenu) {
			allSpies[key] = sinon.spy(bartender, DrinkMenu[key]);
			allDrinks.push(DrinkMenu[key]);
		}

		waitress.takeOrder(allDrinks);

		// Assert Bartender's prepareXXX methods called; restore
		for(key in allSpies) {
			assert(allSpies[key].called);
		}
		for(key in DrinkMenu) {
			bartender[DrinkMenu[key]].restore();
		}
	});

	it("should only take Array of valid drink types", function() {
		var result;
		result = waitress.takeOrder("invalid type");
		assert.equal(null, result);
		result = waitress.takeOrder(['not_a_drink!']);
		assert.equal(null, result);
		result = waitress.takeOrder([DrinkMenu.mai_tai,
									'not_a_drink!',
									DrinkMenu.pina_colada]);
		assert.equal(null, result);
		result = waitress.takeOrder(undefined);
		assert.equal(null, result);
		result = waitress.takeOrder(null);
		assert.equal(null, result);
	});
	it("should return correct number of drinks", function() {
		var drinksServed =
			waitress.takeOrder([DrinkMenu.mojito,
								DrinkMenu.mai_tai]);
		assert.equal(2, drinksServed.length);
	});		
	it("should allow duplicate drinks to be ordered", function() {
		var drinksServed =
			waitress.takeOrder([DrinkMenu.mojito,
								DrinkMenu.mai_tai,
								DrinkMenu.mai_tai,
								DrinkMenu.margarita,
								DrinkMenu.mai_tai]);
		assert.equal(5, drinksServed.length);
	});
});

