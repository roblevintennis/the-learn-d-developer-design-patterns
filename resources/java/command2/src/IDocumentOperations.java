// IDocumentOperations acts as a "Receiver" (Command Pattern)
public interface IDocumentOperations {
	public void open(String fileName);
	public void close(String fileName);
	public void cut();
	public void undoPaste();
	public void paste();
}
