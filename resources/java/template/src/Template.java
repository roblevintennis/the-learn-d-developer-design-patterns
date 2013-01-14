
abstract class AbstractClass {
	public abstract void operation1();
	public abstract void operation2();
	public void hook1() {}
	public void hook2() {}
	public final void templateMethod() {
		hook1();
		operation1();
		operation2();
		hook2();
	}
}
class ConcreteClass extends AbstractClass {
	private String className = this.getClass().getSimpleName();
	public void operation1() {
		System.out.println(className + ": operation1 called...");
	}
	public void operation2() {
		System.out.println(className + ": operation2 called...");
	}
	public void hook1() {
		System.out.println(className + ": hook1 called..."); 
	}
}
class ConcreteClass2 extends AbstractClass {
	private String className = this.getClass().getSimpleName();
	public void operation1() {
		System.out.println(className + ": operation1 called...");
	}
	public void operation2() {
		System.out.println(className + ": operation2 called...");
	}
	public void hook2() {
		System.out.println(className + ": hook2 called..."); 
	}
}

public class Template {
	public static void main(String[] args) {
		ConcreteClass cc1 = new ConcreteClass();
		cc1.templateMethod();
		ConcreteClass2 cc2 = new ConcreteClass2();
		cc2.templateMethod();
	}
}