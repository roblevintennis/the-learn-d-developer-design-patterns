
public class ToolBarItemOpen implements ICommand {

	private IDocumentOperations documentOperations;
	private String fileName;
	
	public ToolBarItemOpen(IDocumentOperations documentOperations, String fileName) {
		this.documentOperations = documentOperations;
		this.fileName = fileName;
	}

	@Override
	public void execute() {
		this.documentOperations.open(this.fileName);
	}

	@Override
	public void undo() {} // NOP

}

