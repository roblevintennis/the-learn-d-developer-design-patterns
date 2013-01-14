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
