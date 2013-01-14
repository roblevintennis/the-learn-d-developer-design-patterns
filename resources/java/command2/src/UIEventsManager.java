import java.util.HashMap;
import java.util.Map;

public class UIEventsManager {
    private Map<String, ICommand> menuCommandsMap = new HashMap<String, ICommand>();
    private Map<String, ICommand> toolBarCommandsMap = new HashMap<String, ICommand>();
    
	public void addMenuCommand(String key, ICommand menuCommand) {
		this.menuCommandsMap.put(key, menuCommand);
	}
	
	public void addToolBarCommand(String key, ICommand toolbarCommand) {
		this.toolBarCommandsMap.put(key, toolbarCommand);
	}
	
	public void handleMenuPressEvent(String key) {
		ICommand menuCommand = this.menuCommandsMap.get(key);
		if (menuCommand != null) {
			menuCommand.execute();
		}
	}
	
	public void handleUndoMenuPressEvent(String key) {
		ICommand menuCommand = this.menuCommandsMap.get(key);
		if (menuCommand != null) {
			menuCommand.undo();
		}
	}
	
	public void handleToolBarPressEvent(String key) {
		ICommand toolbarCommand = this.toolBarCommandsMap.get(key);
		if (toolbarCommand != null) {
			toolbarCommand.execute();
		}
	}
	
	public void handleUndoToolBarPressEvent(String key) {
		ICommand toolbarCommand = this.toolBarCommandsMap.get(key);
		if (toolbarCommand != null) {
			toolbarCommand.undo();
		}
	}
}
