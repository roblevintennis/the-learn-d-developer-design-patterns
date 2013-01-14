
public class MenuItemCut implements ICommand {
	private IDocumentOperations documentOperations;
	
	public MenuItemCut(IDocumentOperations documentOperations) {
		this.documentOperations = documentOperations;
	}

	@Override
	public void execute() {
		this.documentOperations.cut();
	}

	@Override
	public void undo() {} //NOP
}
