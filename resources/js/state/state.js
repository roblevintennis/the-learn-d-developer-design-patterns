// 3rd Party Lib
var AudioLib = function() {};
AudioLib.prototype = {
	play: function() {
		console.log('Playing audio...');
	},
	pause: function() {
		console.log('Pausing audio...');
	},
	stop: function() {
		console.log('Stopping audio...');
	}
};

// Context
var AudioPlayer = function(states, initialState) {
	this.states = states;
	this.currentState = states[initialState];
};
AudioPlayer.prototype = {
	playAudio: function() {
		this.currentState.play(this);
	},
	pauseAudio: function() {
		this.currentState.pause(this);
	},
	stopAudio: function() {
		this.currentState.stop(this);
	},
	getState: function() {
		return this.currentState;
	},
	setState: function(newState) {
		this.currentState = this.states[newState];
	}
};

// State
var IState = function(name) {
	this.name = name;
};
IState.prototype = {
	play: function(context) {
		throw new Error("Method must be implemented."); 
	},
	pause: function(context) {
		throw new Error("Method must be implemented."); 
	},
	stop: function(context) {
		throw new Error("Method must be implemented."); 
	}
};

// Concrete States
var Playing = function(name, audioLib) {
	this.name = name;
	this.audioLib = audioLib;
};
Playing.prototype = new IState(); 
Playing.prototype = {
	play: function(context) {
		console.log("Already playing ... nothing to do...");
	},
	pause: function(context) {
		this.audioLib.pause();
		context.setState('paused');
	},
	stop: function(context) {
		this.audioLib.stop();
		context.setState('stopped');
	}
};

var Paused = function(name, audioLib) {
	this.name = name;
	this.audioLib = audioLib;
};
Paused.prototype = new IState(); 
Paused.prototype = {
	play: function(context) {
		this.audioLib.play();
		context.setState('playing');
	},
	pause: function(context) {
		console.log("Already paused ... nothing to do");
	},
	stop: function(context) {
		this.audioLib.stop();
		context.setState('stopped');
	}
};

var Stopped = function(name, audioLib) {
	this.name = name;
	this.audioLib = audioLib;
};
Stopped.prototype = new IState(); 
Stopped.prototype = {
	play: function(context) {
		this.audioLib.play();
		context.setState('playing');
	},
	pause: function(context) {
		console.log("Can't pause when stopped...");
	},
	stop: function(context) {
		console.log("Already stopped... nothing to do");
	}
};

module.exports.IState = IState;
module.exports.AudioPlayer = AudioPlayer;
module.exports.AudioLib = AudioLib;
module.exports.Playing = Playing;
module.exports.Paused = Paused;
module.exports.Stopped = Stopped;
