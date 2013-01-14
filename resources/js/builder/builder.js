var DrinkMenu = {
	beer: "prepareBeer",
	mojito: "prepareMojito",
	kamikaze: "prepareKamikaze",
	margarita: "prepareMargarita",
	mai_tai: "prepareMaiTai",
	pina_colada: "preparePinaColada"
};

var IBartender = function() {};
IBartender.prototype[DrinkMenu.mojito] = function() {
	throw new Error("Method must be implemented."); 
};
IBartender.prototype[DrinkMenu.kamikaze] = function() {
	throw new Error("Method must be implemented."); 
};
IBartender.prototype[DrinkMenu.mai_tai] = function() {
	throw new Error("Method must be implemented."); 
};
IBartender.prototype[DrinkMenu.margarita] = function() {
	throw new Error("Method must be implemented."); 
};
IBartender.prototype[DrinkMenu.beer] = function() {
	throw new Error("Method must be implemented."); 
};
IBartender.prototype[DrinkMenu.pina_colada] = function() {
	throw new Error("Method must be implemented."); 
};


var Bartender = function() {
	this.drinks = [];	
};
Bartender.prototype = new IBartender(); 
Bartender.prototype[DrinkMenu.beer] = function() {
	this.drinks.push("Beer");
};
Bartender.prototype[DrinkMenu.mojito] = function() {
	this.drinks.push("Mojito");
};
Bartender.prototype[DrinkMenu.kamikaze] = function() {
	this.drinks.push("Kamikaze");
};
Bartender.prototype[DrinkMenu.mai_tai] = function() {
	this.drinks.push("Mai Tai");
};
Bartender.prototype[DrinkMenu.margarita] = function() {
	this.drinks.push("Margarita");
};
Bartender.prototype[DrinkMenu.pina_colada] = function() {
	this.drinks.push("Pina Colada");
};
Bartender.prototype.ordersUp = function() {
	return this.drinks;
};


var Waitress = function(bartender, menu) {
	this.bartender = bartender;
	this.menu = menu;
};

// Precondition: drinks elements must be valid DrinkMenu items 
Waitress.prototype.takeOrder = function(drinks) {
	var i, order = [], self = this;

	function isBartenderFunction(key) {
		return typeof self.bartender[key]==="function";
	}

	if(Array.isArray(drinks)) {
		for (i = 0; i < drinks.length; i++) {
			if (isBartenderFunction(drinks[i])) {
				order.push(self.bartender[drinks[i]]());
			} else {
				return null;
			}
		}
		return (order.length) ? self.bartender.ordersUp() : null;
	}
	return null;
};

module.exports.IBartender = IBartender;
module.exports.Bartender = Bartender;
module.exports.Waitress = Waitress;
module.exports.DrinkMenu = DrinkMenu;
