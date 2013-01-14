public class GUIManualTest {

	public static void main(String[] args) {
		UIEventsManager eventManager = new UIEventsManager();
		DocumentOperations docOperations = new DocumentOperations();
		ICommand menuOpenCommand = new MenuItemOpen(docOperations, "myfile.txt");
		ICommand menuCloseCommand = new MenuItemClose(docOperations, "myfile.txt");
		ICommand menuCutCommand = new MenuItemCut(docOperations);
		ICommand menuPasteCommand = new MenuItemPaste(docOperations);
		
		ICommand toolbarOpenCommand = new ToolBarItemOpen(docOperations, "myfile2.txt");
		ICommand toolbarCloseCommand = new ToolBarItemClose(docOperations, "myfile2.txt");
		ICommand toolbarCutCommand = new ToolBarItemCut(docOperations);
		ICommand toolbarPasteCommand = new ToolBarItemPaste(docOperations);
		
		eventManager.addMenuCommand("open", menuOpenCommand);
		eventManager.addMenuCommand("close", menuCloseCommand);
		eventManager.addMenuCommand("cut", menuCutCommand);
		eventManager.addMenuCommand("paste", menuPasteCommand);
		eventManager.addToolBarCommand("open", toolbarOpenCommand);
		eventManager.addToolBarCommand("close", toolbarCloseCommand);
		eventManager.addToolBarCommand("cut", toolbarCutCommand);
		eventManager.addToolBarCommand("paste", toolbarPasteCommand);
		
		
		// Here we "trigger" some pertinent events
		System.out.println("Manually testing the menu-based commands...");
		eventManager.handleMenuPressEvent("open");
		eventManager.handleMenuPressEvent("cut");
		eventManager.handleMenuPressEvent("paste");
		eventManager.handleUndoMenuPressEvent("paste");
		eventManager.handleMenuPressEvent("close");
		
		System.out.println("Manually testing the toolbar-based commands...");
		eventManager.handleToolBarPressEvent("open");
		eventManager.handleToolBarPressEvent("cut");
		eventManager.handleToolBarPressEvent("paste");
		eventManager.handleUndoToolBarPressEvent("paste");
		eventManager.handleToolBarPressEvent("close");
	}

}
