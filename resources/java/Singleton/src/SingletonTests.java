import static org.junit.Assert.*;

import org.junit.Test;


public class SingletonTests {

	@Test
	public void testCreateSingleton() {		  
		assertSame(Singleton.getInstance(), Singleton.getInstance());
	}

}
