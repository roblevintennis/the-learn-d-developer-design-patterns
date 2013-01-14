// Install node.js, npm, mocha.js, and sinon.js:
// $ npm install -g mocha
// $ npm install sinon
// $ mocha --ignore-leaks
var sinon, assert, IStrategy, 
	HTML5AudioPlayer, SWFAudioPlayer, AudioPlayer;

assert = require('assert');
sinon = require('sinon');
IStrategy = require("../strategy.js").IStrategy;
HTML5AudioPlayer = require("../strategy.js").HTML5AudioPlayer;
SWFAudioPlayer = require("../strategy.js").SWFAudioPlayer;
AudioPlayer = require("../strategy.js").AudioPlayer;

describe("strategy tests", function() {
	var html5AudioPlayer, swfAudioPlayer;

	beforeEach(function() {
		html5AudioPlayer = new HTML5AudioPlayer();
		swfAudioPlayer = new SWFAudioPlayer();
	});
	afterEach (function() {
		html5AudioPlayer = null;
		swfAudioPlayer = null;
	});

	it("should play html5 audio", function() {
		var playSpy, player;

		playSpy = sinon.spy(html5AudioPlayer, "play");
		player = new AudioPlayer(html5AudioPlayer);

		player.playAudio();
		assert(playSpy.called);
		html5AudioPlayer.play.restore();
	});

	it("should pause html5 audio", function() {
		var pauseSpy, player;

		pauseSpy = sinon.spy(html5AudioPlayer, "pause");
		player = new AudioPlayer(html5AudioPlayer);

		player.playAudio();
		player.pauseAudio();
		assert(pauseSpy.called);
		html5AudioPlayer.pause.restore();
	});

	it("should play Flash audio", function() {
		var playSpy, player;

		playSpy = sinon.spy(swfAudioPlayer, "play");
		player = new AudioPlayer(swfAudioPlayer);

		player.playAudio();
		assert(playSpy.called);
		swfAudioPlayer.play.restore();
	});

	it("should pause Flash audio", function() {
		var pauseSpy, player;

		pauseSpy = sinon.spy(swfAudioPlayer, "pause");
		player = new AudioPlayer(swfAudioPlayer);

		player.playAudio();
		player.pauseAudio();
		assert(pauseSpy.called);
		swfAudioPlayer.pause.restore();
	});
});

