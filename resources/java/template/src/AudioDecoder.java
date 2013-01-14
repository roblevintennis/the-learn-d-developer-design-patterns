
class AudioInputStream {}

public abstract class AudioDecoder {
	protected String filePath;
	protected INativeDecoder decoder;
	
	public AudioDecoder(INativeDecoder decoder, String pathToAudioFile) {
		this.filePath = pathToAudioFile;
		this.decoder = decoder;
	}
	public abstract AudioInputStream loadStream();
	public abstract void decode(AudioInputStream ais);
	
	// Hooks
	public void beforeDecode(){}
	public void afterDecode(){}
	
	public void play() {
		AudioInputStream ais = loadStream();
		beforeDecode();
		decode(ais);
		afterDecode();
	}
}

interface INativeDecoder {
	public void decode(AudioInputStream ais);
}
class NativeAACDecoder implements INativeDecoder {
	public void decode(AudioInputStream ais) {
		// ... complex decode implementation omitted
		System.out.println("NativeAACDecoder decoding audio stream...");
	}
}

class NativeMP3Decoder implements INativeDecoder {
	public void decode(AudioInputStream ais) {
		// ... complex decode implementation omitted
		System.out.println("NativeMP3Decoder decoding audio stream...");
	}
}

class AACDecoder extends AudioDecoder {
	
	public AACDecoder(INativeDecoder decoder, String pathToAudioFile) {
		super(decoder, pathToAudioFile);
	}

	@Override
	public AudioInputStream loadStream() {
		// ... complex code to load an AAC audio stream
		return null;
	}

	@Override
	public void decode(AudioInputStream ais) {
		this.decoder.decode(ais);
	}
	
	public void beforeDecode(){
		System.out.println("AAC staring...");
	}
	public void afterDecode(){
		System.out.println("AAC stopped...");
	}
}

class MP3Decoder extends AudioDecoder {

	public MP3Decoder(INativeDecoder decoder, String pathToAudioFile) {
		super(decoder, pathToAudioFile);
	}

	@Override
	public void decode(AudioInputStream ais) {
		this.decoder.decode(ais);
	}
	
	@Override
	public AudioInputStream loadStream() {
		// ... complex code to load an MP3 audio stream
		return null;
	}
	
	public void beforeDecode(){
		System.out.println("MP3 staring...");
	}
	public void afterDecode(){
		System.out.println("MP3 stopped...");
	}
}