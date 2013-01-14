
public class ToolBarItemPaste implements ICommand {

	private IDocumentOperations documentOperations;
	
	public ToolBarItemPaste(IDocumentOperations documentOperations) {
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

