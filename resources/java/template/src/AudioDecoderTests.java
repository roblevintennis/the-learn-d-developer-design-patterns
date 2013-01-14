import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//http://docs.mockito.googlecode.com/hg/org/mockito/Mockito.html
import static org.mockito.Mockito.*;

public class AudioDecoderTests {
	private INativeDecoder mockNativeMP3Decoder;
	private INativeDecoder mockNativeAACDecoder;
	
	@Before
	public void setUp() throws Exception {
		mockNativeMP3Decoder = mock(NativeMP3Decoder.class);
		mockNativeAACDecoder = mock(NativeAACDecoder.class);
	}

	@After
	public void tearDown() throws Exception {
		mockNativeMP3Decoder = null;
		mockNativeAACDecoder = null;
	}

	@Test
	public void testPlaysMP3s() {
		MP3Decoder mp3Decoder = new MP3Decoder(mockNativeMP3Decoder, "my_song.mp3");
		mp3Decoder.play();
		verify(mockNativeMP3Decoder, times(1)).decode(null);
	}

	@Test
	public void testPlaysAACs() {
		AACDecoder aacDecoder = new AACDecoder(mockNativeAACDecoder, "my_song.aac");
		aacDecoder.play();
		verify(mockNativeAACDecoder, times(1)).decode(null);
	}
}
