var IStrategy = function() {};
IStrategy.prototype = {
	play: function() {
		throw new Error("Method must be implemented."); 
	},
	pause: function() {
		throw new Error("Method must be implemented."); 
	}
};

var HTML5AudioPlayer = function() {};
HTML5AudioPlayer.prototype = new IStrategy(); 
HTML5AudioPlayer.prototype = {
	play: function() {
		console.log("Playing HTML5 audio...");
	},
	pause: function() {
		console.log("Pausing HTML5 audio...");
	}
};

var SWFAudioPlayer = function() {};
SWFAudioPlayer.prototype = new IStrategy(); 
SWFAudioPlayer.prototype = {
	play: function() {
		console.log("Playing SWF audio...");
	},
	pause: function() {
		console.log("Pausing SWF audio...");
	}
};

var AudioPlayer = function(playerStrategy) {
	this.player = playerStrategy;
};
AudioPlayer.prototype = {
	playAudio: function() {
		this.player.play();
	},
	pauseAudio: function() {
		this.player.pause();
	}
};

module.exports.IStrategy = IStrategy;
module.exports.HTML5AudioPlayer = HTML5AudioPlayer;
module.exports.SWFAudioPlayer = SWFAudioPlayer;
module.exports.AudioPlayer = AudioPlayer;
