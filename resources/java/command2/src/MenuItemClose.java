//MenuItemClosed acts as a "ConcreteCommand" (Command Pattern)
public class MenuItemClose implements ICommand {
	private IDocumentOperations documentOperations;
	private String fileName;
	
	public MenuItemClose(IDocumentOperations documentOperations, String fileName) {
		this.documentOperations = documentOperations;
		this.fileName = fileName;
	}

	@Override
	public void execute() {
		this.documentOperations.close(this.fileName);
	}

	@Override
	public void undo() {} //NOP

}