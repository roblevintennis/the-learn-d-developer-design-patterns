import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//http://docs.mockito.googlecode.com/hg/org/mockito/Mockito.html
import static org.mockito.Mockito.*;

public class GUITests {
	
	private ICommand mockMenuOpenCommand;
	private ICommand mockMenuCloseCommand;
	private ICommand mockMenuCut;
	private ICommand mockMenuPaste;
	
	private ToolBarItemOpen mockToolbarOpenCommand;
	private ToolBarItemClose mockToolbarCloseCommand;
	private ToolBarItemCut mockToolbarCut;
	private ToolBarItemPaste mockToolbarPaste;
	
	private UIEventsManager eventManager;
	private DocumentOperations mockDocumentOperations;

	@Before
	public void setUp() {
		mockMenuOpenCommand = mock(MenuItemOpen.class);
		mockMenuCloseCommand = mock(MenuItemClose.class);
		mockMenuCut = mock(MenuItemCut.class);
		mockMenuPaste = mock(MenuItemPaste.class);
		mockToolbarOpenCommand = mock(ToolBarItemOpen.class);
		mockToolbarCloseCommand = mock(ToolBarItemClose.class);
		mockToolbarCut = mock(ToolBarItemCut.class);
		mockToolbarPaste = mock(ToolBarItemPaste.class);
		mockDocumentOperations = mock(DocumentOperations.class);
		eventManager = new UIEventsManager();
	}
	
	@After
	public void tearDown() {
		mockMenuOpenCommand = null;
		mockMenuCloseCommand = null;
		mockMenuCut = null;
		mockMenuPaste = null;
		mockToolbarOpenCommand = null;
		mockToolbarCloseCommand = null;
		mockToolbarCut = null;
		mockToolbarPaste = null;
		mockDocumentOperations = null;
		eventManager = null;
	}
	
	@Test
	public void testDocumentOperationsReceiverCalledForMenuOpen() {
		ICommand menuOpenCommand = new MenuItemOpen(mockDocumentOperations, "foofile.txt");
		menuOpenCommand.execute();
		verify(mockDocumentOperations, times(1)).open("foofile.txt");
	}
		
	@Test
	public void testDocumentOperationsReceiverCalledForToolbarOpen() {
		ICommand toolbarOpenCommand = new ToolBarItemOpen(mockDocumentOperations, "foo2file.txt");
		toolbarOpenCommand.execute();
		verify(mockDocumentOperations, times(1)).open("foo2file.txt");
	}
	
	@Test
	public void testDocumentOperationsReceiverCalledForMenuClose() {
		ICommand menuCloseCommand = new MenuItemClose(mockDocumentOperations, "foofile.txt");
		menuCloseCommand.execute();
		verify(mockDocumentOperations, times(1)).close("foofile.txt");
	}
	
	@Test
	public void testDocumentOperationsReceiverCalledForToolbarClose() {
		ICommand toolbarCloseCommand = new ToolBarItemClose(mockDocumentOperations, "foo2file.txt");
		toolbarCloseCommand.execute();
		verify(mockDocumentOperations, times(1)).close("foo2file.txt");
	}
	
	@Test
	public void testDocumentOperationsReceiverCalledForMenuCut() {
		ICommand menuCutCommand = new MenuItemCut(mockDocumentOperations);
		menuCutCommand.execute();
		verify(mockDocumentOperations, times(1)).cut();
	}
	
	@Test
	public void testDocumentOperationsReceiverCalledForToolbarCut() {
		ICommand toolbarCutCommand = new ToolBarItemCut(mockDocumentOperations);
		toolbarCutCommand.execute();
		verify(mockDocumentOperations, times(1)).cut();
	}
	
	@Test
	public void testDocumentOperationsReceiverCalledForMenuPaste() {
		ICommand menuPasteCommand = new MenuItemPaste(mockDocumentOperations);
		menuPasteCommand.execute();
		verify(mockDocumentOperations, times(1)).paste();
		menuPasteCommand.undo();
		verify(mockDocumentOperations, times(1)).undoPaste();
	}
	
	@Test
	public void testDocumentOperationsReceiverCalledForToolbarPaste() {
		ICommand toolbarPasteCommand = new ToolBarItemPaste(mockDocumentOperations);
		toolbarPasteCommand.execute();
		verify(mockDocumentOperations, times(1)).paste();
		toolbarPasteCommand.undo();
		verify(mockDocumentOperations, times(1)).undoPaste();
	}
	
	@Test
	public void testInvokerInvokesMenuOpenConcreteCommand() {
		eventManager.addMenuCommand("open", mockMenuOpenCommand);
		eventManager.handleMenuPressEvent("open");
		verify(mockMenuOpenCommand, times(1)).execute();
	}
	
	@Test
	public void testInvokerInvokesToolbarOpenConcreteCommand() {
		eventManager.addMenuCommand("open", mockToolbarOpenCommand);
		eventManager.handleMenuPressEvent("open");
		verify(mockToolbarOpenCommand, times(1)).execute();
	}
	
	@Test
	public void testInvokerInvokesMenuClosedConcreteCommand() {
		eventManager.addMenuCommand("close", mockMenuCloseCommand);
		eventManager.handleMenuPressEvent("close");
		verify(mockMenuCloseCommand, times(1)).execute();
	}
	
	@Test
	public void testInvokerInvokesToolbarClosedConcreteCommand() {
		eventManager.addMenuCommand("close", mockToolbarCloseCommand);
		eventManager.handleMenuPressEvent("close");
		verify(mockToolbarCloseCommand, times(1)).execute();
	}
	
	@Test
	public void testInvokerInvokesMenuCut() {
		eventManager.addMenuCommand("cut", mockMenuCut);
		eventManager.handleMenuPressEvent("cut");
		verify(mockMenuCut, times(1)).execute();
	}
	
	@Test
	public void testInvokerInvokesToolbarCut() {
		eventManager.addMenuCommand("cut", mockToolbarCut);
		eventManager.handleMenuPressEvent("cut");
		verify(mockToolbarCut, times(1)).execute();
	}
	
	@Test
	public void testInvokerInvokesMenuPaste() {
		eventManager.addMenuCommand("paste", mockMenuPaste);
		eventManager.handleMenuPressEvent("paste");
		verify(mockMenuPaste, times(1)).execute();
	}
	
	@Test
	public void testInvokerInvokesToolbarPaste() {
		eventManager.addMenuCommand("paste", mockToolbarPaste);
		eventManager.handleMenuPressEvent("paste");
		verify(mockToolbarPaste, times(1)).execute();
	}
}

