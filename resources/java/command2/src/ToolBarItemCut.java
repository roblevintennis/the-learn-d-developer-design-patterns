
public class ToolBarItemCut implements ICommand {
	private IDocumentOperations documentOperations;
	
	public ToolBarItemCut(IDocumentOperations documentOperations) {
		this.documentOperations = documentOperations;
	}

	@Override
	public void execute() {
		this.documentOperations.cut();
	}

	@Override
	public void undo() {} //NOP
}

