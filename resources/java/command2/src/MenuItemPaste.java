
public class MenuItemPaste implements ICommand {

	private IDocumentOperations documentOperations;
	
	public MenuItemPaste(IDocumentOperations documentOperations) {
		this.documentOperations = documentOperations;
	}

	@Override
	public void execute() {
		this.documentOperations.paste();
	}

	@Override
	public void undo() {
		this.documentOperations.undoPaste();
	}

}
