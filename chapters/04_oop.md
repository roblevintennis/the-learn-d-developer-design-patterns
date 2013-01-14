

# Object Oriented Principles

This book assumes fundamental knowledge of basic object oriented concepts—a prerequisite to understanding the patterns we'll be covering in this book—and so the discussion here will be purposely short for for the sake of brevity. If you're already an "object oriented guru", feel free to skip this chapter (although may want to peruse the section on S.O.L.I.D. principles for review).

## Abstraction

Dictionary.com provides the following definition for abstraction:
> Considering something as a general quality or characteristic, apart from concrete realities, specific objects, or actual instances.

From a programming perspective, an abstraction is a focused representation of the essential properties of a particular thing. Further, an _abstract date type_ (ADT), will, typically, provide self-evident and immediate understanding of what that thing is. For example, when we mention the word "Car", it is immediately understood that we are talking about a _specific type of thing_ that can be driven, has a steering wheel, breaks, etc. So, although a car mechanic may additionally think of internal combustion systems, crankshafts, cylinders, pistons, etc.—your crazy Aunt Helen surely does not! It's the first set of car qualities described that are at a _higher level of abstraction_, and therefore, easier to understand. When we make good use of abstraction while designing our interfaces, we keep the inner workings and gory details of our implementations hidden; they become "black boxes", that, "just work".

## Class

A class can be thought of as a blueprint that defines an abstract data type (or ADT; see above). Thinking in terms of the nouns in a problem statement (e.g. an employee, customer, bank account, etc.), one can derive classes from real world things. 

_For brevity's sake, we assume you have some knowledge in this area. If not, see [Wikipedia's page on Classes][classes]._

## Interface

>The set of all signatures defined by an object's operations is called the _interface_ to the object. An object's interface characterizes the complete set of requests that can be sent to the object ... _type_ is a name used to denote a particular interface ... a type is a _subtype_ of another if its interface contains the interface of its _supertype_—GoF 

The word type supports the notion that something is a _particular type of thing_, and so it may brings some semantic benefits in that it helps us to visualize that thing in our minds. We will, therefore, use it interchangably with interface
.

## Dynamic Binding

Through the use of interfaces we can achieve _dynamic binding_—the run-time association of a method call on an object and one of its methods. Since an interface provides a guarantee that certain operations will be available, we can substitute objects that have identical interfaces for each other at run-time. This substitutability is, in fact, known as _polymorphism_, and will be discussed in more detail below.

## Inheritance

A _superclass_ (also known as a base, or parent class) contains the core properties of a type, and may be "inherited from" by a _subclass_ (also known as a derived, extended, or child class). The ability to inherit provides a way to reuse fields and methods since sub-types are able to _derive_ properties from their ancestors.

## Encapsulation

[Grady Booch][booch_ooad_book] defines encapsulation as:
> "the process of compartmentalizing the elements of an abstraction that constitute its structure and behavior". 

Encapsulation is a means of achieving _information hiding_ such that the internal properties of an object are only allowed to be accessed or modified in a centralized and controlled manner. This is typically done via accessors and mutators (also known as getters and setters). The encapsulated property can then be changed (or completely replaced, reimplemented, etc.), and—provided the public interface remains the same—external components will not be impacted. 

Encapsulation provides another "protective benefit"—the prevention of internal data from being changed throughout a system—and, thus, helps us to maintain proper data integrity. Further, encapsulation reduces system complexity by limiting interdependencies between software components. 

To recap, the following are benefits of encapsulation:
* data management is centralized
* data integrity is maintained
* modules remain decoupled since each manage their own data 

These concepts are explored in much greater detail in the seminal work by [Grady Booch, Object-Oriented Analysis and Design with Applications][booch_ooad_book]. The following two pages are also useful: [Encapsulation][encapsulation], and [Information Hiding][info_hiding] Wikipedia pages for more details.


## Polymorphism

[Wikipedia][polymorphism_wikip]:
>Polymorphism is a programming language feature that allows values of different data types to be handled using a uniform interface.

The word _polymorphism_ refers to the ability for one thing to take on many different forms. Similarly, in programming, polymorphism is the ability for one interface to represent many implementations. This is generally achieved by having an _interface_ that is implemented by one or more _sub-types_ (concrete implementations of that interface). While the sub-types may contain varying implementation details, the fact that they all support the same interface allows them to be used interchangeably.

Computer programs can utilize polymorphism and achieve runtime flexibility. If we take our Car example from earlier, and assume the Car interface has a _drive_ method, we can pass instances that implement this interface dynamically. The users of these instances will be able to "blindly" call _drive_:

```java
Car honda = new HondaAccord();
Car toyota = new ToyotaCamry();
someObject.doSomethingUsefulWithCars(honda);
someObject.doSomethingUsefulWithCars(toyota);
...
public void doSomethingUsefulWithCars(Car car) {
    // We don't have to care what kind of car it is, we just call drive:
	car.drive();
}
```

The most interesting part here is that—while we have instantiated two completely different car implementations (a Honda and a Toyota)—we have set them both to the type Car (an interface). In doing so, we guarantee that they have both have the _drive_ method available. This is known as "programming to an interface not an implementation", and becomes quite important as we strive for more flexibile systems. _See the chapter on Strategy pattern for a more detailed example._

[GoF][gof] succinctly states some of polymorphism's key benefits:

> Polymorphism simplifies the definitions of clients, decouples objects from each other, and lets them vary their relationships to each other at run-time.

## S.O.L.I.D. Principles 

The [mnemonic acronym SOLID was][solid_wikip]:
> introduced by Michael Feathers for the "first five principles" identified by Robert C. Martin in the early 2000s that stands for five basic principles of object-oriented programming and design. The principles when applied together intend to make it more likely that a programmer will create a system that is easy to maintain and extend over time.

## SRP: The Single Responsibility Principle

The SRP principle is related to a broader concept called _cohesion_—the degree to which elements of a class or module are related. If you have a Customer class that deals with saving this customer to a file or database, generating reports for the customer, etc., it would be said to have _low cohesion_ since these are not the responsibilities of a customer.

The SRP provides that: [A class should have one, and only one, reason to change][martin_ood]. Inverting this we could say that: a class with only one responsibility only has one reason to change. For an interesting discussion on the SOLID principle see the [Hanselminutes Podcast #145][martin_hansel] where Robert Martin interviewed on the topic.

## Open/Closed Principle

The open/closed principle provides that: classes may be open for extension but closed for change. So, if we want augment a classes behavior, we must do so without actually modifying the class itself! Strategies for accomplishing this will be discussed in more detail in later chapters, but we may: 

* Use _compositional delegation_ to compose various abstractions at runtime, all acting in accordance with a common interface. _We will see this technique in action in the chapter on the Strategy pattern._

* Use an asynchronous messaging scheme, where objects are notified on an "as-needed basis", via a common defined interface._This technique will be seen in the chapter on the Observer pattern._

* Use a plugin based archetecture that instantiates dynamic plugins, perhaps, searching a common directory and/or using common naming conventions, etc.

The take away, is that we should strive to design our modules, such that, once they've been fully tested and deployed in to production, we don't have to make any more _direct changes_ to them. While this may seem overly ambitious, it turns out to be accomplishable.

## Liskov Substitution Principle

In his interview with Scott Hansen ([show #145][martin_hansel]), Robert C. Martin describes how the substitution principle may be understood in terms of how inherited instances may be used within a program:
> "If you have an expectation of some object or some entity and there are several possible sub-entities, we'll call them sub-types that could implement that original entity. The caller of the original entity should not be surprised by anything that happens if one of the sub-entities is substituted. So, the simple way to think about this is, if you're used to driving a Chevrolet you shouldn't be too surprised when you got into a Volkswagen, you'd still be able to drive it. That's the basic idea, there's an interface, you can use that interface, lots of things implement that interface one way or another and none of them should surprise you."

He has also covered this in his writings by succinctly stating: ["Derived classes must be substitutable for their base classes."][martin_ood] 

Another example might be if we were to create a Stack class by inheriting from a LinkedList super class. Although we'd be able to conveniently use the list's functionality to _push_ and _pop_ elements from off the front of the list, we could not pass around the _stack instance_ without surprising users with inappropriate methods (those inherited from the linked list like: _contains_, _indexOf_, etc.) These operations are not what one might expect from a stack, and therefore, we'd be violating the substitution principle.

In this case, we should, instead, compose the Stack with an internal property that references an instance of linked list. With that in place, the Stack can then choose to expose only the appropriate _push_ and _pop_ operations—these would, in turn, delegate to the list "under the hood". The concept of _delegation_ can be distilled as:

* a class property holds a reference to another _module_ (the delegate)
* that property is used to call one of the delegate's instance methods 

By using a list instance (rather than inheriting from list) and then delegating to it, we are _favoring composition over inheritance_. Here's naive but easy to understand example of how that might work (please humor our assumptions):

```java
class Stack {
	// Here we are "composing" our stack with a list
	// Assume we later initialized with: list=new LinkedList() 
	private List list;

	public void push(Object someObject) {
		// Assume the linked list inserts at index 0
	    list.insert(someObject);
	}

	public Item pop() {
		// Assume the linked list returns the first item on delete
	    return list.delete();
	}
}
```

If we were to now create an instance of this version of Stack class, it would no longer expose inappropriate list methods as it did before. Only _push_ and _pop_ are exposed (which is what one would expect of a simple Stack), and thus, it does not _surprise_ it's users. Incomplete as this implementation might be, it would not be in violation of the Liskov Substitution Principle.

**When ISA "falls down"**
A heuristic that's popular for determining class relationships is to check if the "ISA" statement holds true. For example, we could classify an Espresso by saying that it _isa_ (yes, that's one word!) type of beverage. It holds that if something is a type of something else, it must share some properties. For example, both a Beverage and an Espresso have a _density_. Since density is fairly _low level_ thing, it's nice to have that defined in Beverage, so Esspresso doesn't have to deal with such details. We can then focus on more interesting features of Esspresso like _caffeine_ and perhaps define an operation like: _getAmountOfCaffeine_. Therefore, we are able to contemplate Esspresso from a higher level of abstraction. Unfortunately, though, performing such classifications using the isa technique has some issues...

[Robert Martin][martin_ood] provides that the above heuristic can break down with the canonical example: a square _is a_ rectangle. While this holds (from a geometry perspective), if you derive a Square class from a Rectangle class, you will get inappropriate behavior. For example, _setHeight_ from Rectangle, will not need to do anything to it's width, whereas the Square must be sure to keep its width and height the same. In fact, the square might not even want to separate width and height, and prefer to have a "side" property instead (since a square's sides will always have the same length!). Being lazy, you may decide that, "well, I can override setHeight, and put in a simple conditional check"). As you do so, you realize that you will also need the exact same conditional check in setWidth. Obviously, this scenario will result in an abysmal state of affairs for the poor maintainer of the inconsistent Square.

## Interface Segregation Principle

ISP, essentially, provides that one large interface must be "segregated" into (separated), more specific, individual interfaces. This is really a corollary to _single responsibility_ and _cohesion_ principles which provide that a particular module should have one, and only one, focused responsibility. 

["Make fine grained interfaces that are client specific."][martin_ood]—Robert Martin

## Dependency Inversion Principle 

The dependency inversion explicity states that we should: ["depend on abstractions, not on concretions."][martin_ood]—Robert Martin. So, if you're calling a function, it should be an abstract function; if you're holding a reference to an object, that reference must point to an abstraction, etc. In practice, this is impossible to achieve throughout an entire system, since, at some point, somewhere, we'll need to instantiate an object instance. We can, however, be mindful to encapsulate these object creations using creational patterns we'll discuss later. By doing so, we compartmentalize areas that are likely to change. 

