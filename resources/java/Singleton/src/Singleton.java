
public class Singleton {

	// Here we "eager-load" our Singleton instance. Other options
	// might be using a synchronized getInstance or double locking (phew!)
    private final static Singleton instance = new Singleton();

	private Singleton () {}
	
	public static Singleton getInstance() {
		return instance;
	}
}
