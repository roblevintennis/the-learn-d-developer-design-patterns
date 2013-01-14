// DocumentOperations acts as a "Receiver" (Command Pattern)
public class DocumentOperations implements IDocumentOperations {

	public void open(String fileName) {
		System.out.println("Opening " + fileName + "...");
	}

	public void close(String fileName) {
		System.out.println("Closing " + fileName + "...");
	}

	@Override
	public void cut() {
		System.out.println("Cutting some text...");
	}

	@Override
	public void paste() {
		System.out.println("Pasting some text...");
	}
	
	@Override
	public void undoPaste() {
		System.out.println("Undoing last paste operation...");
	}
}
