public class ToolBarItemClose implements ICommand {
	private IDocumentOperations documentOperations;
	private String fileName;
	
	public ToolBarItemClose(IDocumentOperations documentOperations, String fileName) {
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