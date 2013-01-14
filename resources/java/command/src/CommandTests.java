import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// http://docs.mockito.googlecode.com/hg/org/mockito/Mockito.html
import static org.mockito.Mockito.*;

public class CommandTests {

	private Invoker invoker;
	private Receiver mockReceiver;
	private Command command;

	@Before
	public void setUp() throws Exception {
		this.mockReceiver = mock(Receiver.class);
		this.command = new ConcreteCommand(this.mockReceiver);
		this.invoker = new Invoker();
		this.invoker.setCommand(this.command);
	}

	@After
	public void tearDown() throws Exception {
		this.command = null;
		this.invoker = null;
		this.mockReceiver = null;
	}
	
	@Test
	public void testCommandCallsReceiver() {
		// Test acting as "client" for now
		this.invoker.action();
		verify(this.mockReceiver, times(1)).doSomething();
	}
	
	@Test
	public void testUndo() {
		this.invoker.undo();
		verify(this.mockReceiver, times(1)).undoSomething();
	}
	
}
