// Install node.js, npm, mocha.js, and sinon.js:
// $ npm install -g mocha
// $ npm install sinon
// $ mocha --ignore-leaks
var sinon, assert, IState, AudioLib,
	AudioPlayer, Playing, Paused, Stopped;

assert = require('assert');
sinon = require('sinon');
IState = require("../state.js").IState;
AudioPlayer = require("../state.js").AudioPlayer;
AudioLib = require("../state.js").AudioLib;
Playing = require("../state.js").Playing;
Paused = require("../state.js").Paused;
Stopped = require("../state.js").Stopped;

describe("state tests", function() {
	var audioPlayer, stopped, playing, paused, spyPlayingPlay, lib;

	beforeEach(function() {
		lib = new AudioLib();
		playing = new Playing('Playing', lib);
		paused = new Paused('Paused', lib);
		stopped = new Stopped('Stopped', lib);
	});
	
	afterEach (function() {
		playing = null;
		paused = null;
		stopped = null;
	});

	describe("Paused", function() {
		it("should transition from paused to playing state", function() {
			var spyPausedPlay, spyLibPlay, player;

			spyPausedPlay = sinon.spy(paused, "play");
			spyLibPlay = sinon.spy(lib, "play");
			player = new AudioPlayer({	'playing': playing, 
										'paused': paused},
										'paused');
			player.playAudio();
			assert(spyPausedPlay.called);
			assert(spyLibPlay.called);
			assert(player.getState()['name'] === 'Playing');
			spyPausedPlay.restore();
		});
		it("should transition from paused to stopped state", function() {
			var spyPausedStop, spyLibStop, player;

			spyPausedStop = sinon.spy(paused, "stop");
			spyLibStop = sinon.spy(lib, "stop");
			player = new AudioPlayer({	'playing': playing, 
										'stopped': stopped,
										'paused': paused},
										'paused');
			player.stopAudio();
			assert(spyPausedStop.called);
			assert(spyLibStop.called);
			assert(player.getState()['name'] === 'Stopped');
			spyPausedStop.restore();
		});
		it("should not pause if already paused", function() {
			var spyPausePause, spyLibPause, player;

			spyPausePause = sinon.spy(paused, "pause");
			spyLibPause = sinon.spy(lib, "pause");
			player = new AudioPlayer({	'playing': playing, 
										'stopped': stopped,
										'paused': paused},
										'paused');
			player.pauseAudio();
			assert(!spyLibPause.called);
			assert(player.getState()['name'] === 'Paused');
			spyPausePause.restore();
		});	
	});

	describe("Playing", function() {
		it("should transition from playing to paused state", function() {
			var spyPlayingPause, spyLibPause, player;

			spyPlayingPause = sinon.spy(playing, "pause");
			spyLibPause = sinon.spy(lib, "pause");
			player = new AudioPlayer({	'playing': playing, 
										'stopped': stopped,
										'paused': paused},
										'playing');
			player.pauseAudio();
			assert(spyPlayingPause.called);
			assert(spyLibPause.called);
			assert(player.getState()['name'] === 'Paused');
			spyPlayingPause.restore();
		});
		it("should transition from playing to stopped state", function() {
			var spyPlayingStop, spyLibStop, player;

			spyPlayingStop = sinon.spy(playing, "stop");
			spyLibStop = sinon.spy(lib, "stop");
			player = new AudioPlayer({	'playing': playing, 
										'stopped': stopped,
										'paused': paused},
										'playing');
			player.stopAudio();
			assert(spyPlayingStop.called);
			assert(spyLibStop.called);
			assert(player.getState()['name'] === 'Stopped');
			spyPlayingStop.restore();
		});
		it("should not play if already playing", function() {
			var spyPlayingPlay, spyLibPlay, player;

			spyPlayingPlay = sinon.spy(playing, "play");
			spyLibPlay = sinon.spy(lib, "play");
			player = new AudioPlayer({	'playing': playing, 
										'stopped': stopped,
										'paused': paused},
										'playing');
			player.playAudio();
			assert(!spyLibPlay.called);
			assert(player.getState()['name'] === 'Playing');
			spyPlayingPlay.restore();
		});	
	});

	describe("Stopped", function() {
		it("should transition from stopped to playing state", function() {
			var spyStoppedPlay, spyLibPlay, player;

			spyStoppedPlay = sinon.spy(stopped, "play");
			spyLibPlay = sinon.spy(lib, "play");
			player = new AudioPlayer({	'playing': playing, 
										'stopped': stopped,
										'paused': paused},
										'stopped');
			player.playAudio();
			assert(spyStoppedPlay.called);
			assert(spyLibPlay.called);
			assert(player.getState()['name'] === 'Playing');
			spyStoppedPlay.restore();
		});
		it("should not pause if stopped", function() {
			var spyStoppedPause, spyLibPause, player;

			spyStoppedPause = sinon.spy(stopped, "pause");
			spyLibPause = sinon.spy(lib, "pause");
			player = new AudioPlayer({	'playing': playing, 
										'stopped': stopped,
										'paused': paused},
										'stopped');
			player.pauseAudio();
			assert(!spyLibPause.called);
			assert(player.getState()['name'] === 'Stopped');
			spyStoppedPause.restore();
		});
		it("should not stop if already stopped", function() {
			var spyStoppedStop, spyLibStop, player;

			spyStoppedStop = sinon.spy(stopped, "stop");
			spyLibStop = sinon.spy(lib, "stop");
			player = new AudioPlayer({	'playing': playing, 
										'stopped': stopped,
										'paused': paused},
										'stopped');
			player.stopAudio();
			assert(!spyLibStop.called);
			assert(player.getState()['name'] === 'Stopped');
			spyStoppedStop.restore();
		});
	});
});

