
# Singleton Pattern

## Overview

The Singleton pattern provides a means of ensuring that only one instance of a particular class is created. This is generally achieved by marking the constructor of that class as private, and then providing a public method that returns the one shared instance (e.g. 'getInstance').

Singletons may be applicable when:

> "there must be exactly one instance of a class, and it must be accessible to clients from a well-known access point"—[Gang of Four][gof]

Singletons are probably the most controversial of the design patterns, bringing the following concerns:

* Meaningful unit testing becomes far more difficult

If the system under test uses one or many singleton objects, it becomes hard to test that system in an isolated manner. Generally, it is advised to use [dependency injection][di_demystified] instead. 

* Global state 

While the issue of multi-threaded applications creating duplicate singletons can be circumnavigated by using _synchronized methods_ or "eager instantiation", the global state introduced by this pattern greatly ["reduces the chance of parallelism within a program"][wiki_singleton].

Despite this controversy, we've decided to write this short chapter on Singletons, since you'll likely encounter them in many projects and need to, at least, be familiar with how they work. That said, definitely think thrice before adding Singeltons to your project, and, perhaps read these posts first:

* [Guide: Writing Testable Code](http://misko.hevery.com/code-reviewers-guide/ "Guide: Writing Testable Code")
* [Performant Singletons](http://scientificninja.com/blog/performant-singletons "Performant Singletons")


## Implementation

So you've read the "warning labels" and still want to see how this is implemented; well, with much trepidation, we present the Singleton pattern:

```java

public class Singleton {

	// Here we've "eager-initialized" our Singleton instance.
	// Using a synchronized getInstance or double locking are
	// other options.
    private final static Singleton instance = new Singleton();

	private Singleton () {}
	
	public static Singleton getInstance() {
		return instance;
	}
}

// ... code intentionally omitted

	@Test
	public void testCreateSingleton() {		  
		assertSame(Singleton.getInstance(), Singleton.getInstance());
	}

```


