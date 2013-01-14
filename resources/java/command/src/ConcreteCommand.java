
public class ConcreteCommand implements Command {

	private Receiver receiver;
	
	public ConcreteCommand(Receiver receiver) {
		this.receiver = receiver;
	}
	@Override
	public void execute() {
		this.receiver.doSomething();
	}

	@Override
	public void undo() {
		this.receiver.undoSomething();
	}

}
