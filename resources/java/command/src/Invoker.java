
public class Invoker {
	private Command command = null;
	
	public void setCommand(Command command) {
		this.command = command;
	}
	public void action() {
		if (this.command != null) {
			command.execute();
		}
	}
	public void undo() {
		if (this.command != null) {
			command.undo();
		}
	}
}
