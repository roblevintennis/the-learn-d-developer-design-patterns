# Observer Pattern

**Tip:** _This is a long chapter that goes through the TDD process with a fine tooth comb. If you're already comfortable with TDD, feel free to [skip to the bottom](#final-implementation) to see the implementation._

## Overview

Wikipedia describes the [Observer Pattern][observer_wiki] as follows:

> The observer pattern is a software design pattern in which an object, called the subject, maintains a list of its dependents, called observers, and notifies them automatically of any state changes, usually by calling one of their methods...

There are many event based systems that are quite [similar][alternative_observers] in spirit to the Observer pattern such as: [Pub/Sub][pubsub], [EventEmitter][event_emitter] ([node.js][node]), [NSNotification][ios] ([Cocoa][cocoa]), etc. These all have in common the ability to dynamically notify one to many interested parties via some sort of broadcast mechanism. In the case of the Observer, a _subject_ calls the <code>update</code> method of one to many _boserver_ instances (it does this without needing to know anything specific about these parties other than that they implement a common interface to receive said notifications). In object oriented circles, this decoupled use of composition and interfaces is called [_programming to an interface not an implementation_][program_to_interface]. This is a key object oriented principle that allows us to maintain orthogonality in our systems. 

Another feature of this pattern is that observers can be added or removed at runtime—thus providing a means to "hot swap" components easily when needed.

At its core the pattern involves the following steps:

1. Allow _observers_ to register (and also unregister) to receive notifications.

2. Keep these observer instances in a suitable data structure such as a _List_ or _Array_.

3. When state changes (e.g. a new article is published, a user logs in, a track is played, etc.) these observers are notified by the _subject_ who calls an interface defined <code>update</code> method on each of the observers.

## Observer Pattern Class Diagram 

The following is a class diagram that represents the Observer Pattern we'll be discussing in this chapter:

![Observer Pattern Class Diagram](img/observer.png "Observer Pattern UML Class Diagram")

_Note that there can be 1..* concrete subjects or observers._









## Implementing Observer Pattern using TDD

Let's use TDD to implement this pattern. Specifically, we'll be writing [unit tests][unit_testing] to test our Observer Pattern implementation as an isolated unit. Please be forewarned that I'll start out going through each and every "Red, Green, Refactor" cycle (so you can get a feel for this process), but will eventually start to condense things as we move forward.  We'll be using the [phpunit framework][phpunit] for this chapter so make sure you have that installed if you'd like to follow along.

```php
<?php
require_once('Observer.php');

class ObserverTests extends PHPUnit_Framework_TestCase
{
    public function testSubscribing()
    {
        $publisher = new Subject();
        
    }
}
?>
```

And when we run phpunit we get:

```bash
$ phpunit ObserverTest.php # outputs:
Fatal error: Class 'Subject' not found in
/Users/rlevin/programming/labs/BOOK/research/observer/ObserverTests.php on line 8
```

So now we add the appropriate minimal code to get it green:

```php
<?php
abstract class Subject
{
}
class ConcreteSubject extends Subject
{
}
```

And then we move on to finishing up our spec:

```php
<?php
class ObserverTests extends PHPUnit_Framework_TestCase
{
    public function testSubscribing()
    {
    	$observer = array();
        $subject = new ConcreteSubject();
        $subject->register($observer);
        $this->assertEquals(1, $subject->getNumberOfObservers(),
        	"Subscriber should have 1 observer when 1 registered.");
    }
}
?>
```

Running phpunit again:

```bash
$ phpunit ObserverTest.php # outputs:
Fatal error: Call to undefined method ConcreteSubject::register() in 
/Users/rlevin/programming/labs/BOOK/research/observer/ObserverTests.php on line 10
```

So we add the two methods:

```php
<?php
public function register($observer)
{
}
public function getNumberOfObservers()
{
}
?>
```

And get following:

```bash
$ phpunit ObserverTests.php
Should be 1 observer when 1 added.
Failed asserting that null matches expected 1.
```

So we need to create the data structure to hold our list of observers:

```php
<?php
abstract class Subject
{
}
class ConcreteSubject extends Subject
{
	private $observers = array();
	public function register($observer)
	{
		array_push($this->observers, $observer);
	}
	public function getNumberOfObservers()
	{
		return count($this->observers);
	}
}
?>
```

### Refactoring
So now that we have our first test spec defined (and we're all green), we can take a step back and think about what in this code could be better. This is called __refactoring__ (or [Code Refactoring][code_refactoring]). Note that we only start refactoring when our tests are green.

So, is there something about this code that strikes you as odd? It should! We have an abstract class but we're not defining any abstract methods. In this case, the _register_ method is really part of our Subject interface so we should add that abstract method to Subject:

```php
<?php
abstract class Subject
{
	abstract public function register($observer);
	// abstract public function unregister($observer);
}
?>
```

Notice that we take the liberty of also adding the <code>unregister</code> abstract method, but we comment it out since we want to maintain a fairly fast "iterative rhythm".

You may have also noticed that we made a pretend observer object (we could have used a [fake object][mock_objects], but in this case we knew we'd soon be implementing real observers). Let's get rid of that and create a very simple but real observer now:

```php
<?php
public function testSubscribing()
{
	$observer = new ConcreteObserver();
	...
?>
```

And we get a failure saying it's not defined so...

```php
<?php
interface iObserver
{
}
class ConcreteObserver implements iObserver
{
} 
?>
```

Ok, so we've proven that we can push an object on to a PHP array. "Big deal!", you say. Sure, but we now have the benifit of having some test coverage to build on top of as we introduce more "exciting" features. 

**"Hot swapping" components**

One of the features of Observer pattern is the ability for observers to attach and detach themselves at runtime. There are many uses for this, but let's say you wanted to temporarily re-route a minimal production logging system with a more verbose logging system to track down an issue. Rather than taking down the whole system, you could simply swap out the ProductionLogger observer and swap in a VerboseLogger simply calling _unregister_ and _register_ respectively. Then, upon fixing the issue, you could do the reverse, and thus, swap the components back as they were.

So let's get to work on that _unregister_ method...

```php
<?php
public function testUnsubscribing()
{
	$observer1 = new ConcreteObserver();
	$observer2 = new ConcreteObserver();
    $subject = new ConcreteSubject();
    $subject->register($observer1);
    $subject->register($observer2);
    $this->assertEquals(2, $subject->getNumberOfObservers(), 
    	"Should be 2 observers when 2 added.");
    $subject->unregister($observer1);
    $this->assertEquals(1, $subject->getNumberOfObservers(),
    	"Should be 1 observer when 1 when one removed.");
}
?>
```

Of course we get:

```bash
**Fatal error: Call to undefined method ConcreteSubject::unregister()
```

_At this point I'm not going to be listing every step (like when we define a class or method when we have an undefined test error just to get the test to pass) but, if you're following along, remember to take small steps between coding and running your tests._

We need to define an _unregister_ method which we do as follows:

```php
<?php
public function unregister($observer)
{
	foreach ($this->observers as $k => $v)
	{
		if ($observer == $v)
		{
			unset ($this->observers[$k]);
			return true;
		}
	}
	return false;
}
?>
```

And we're all green again. Our implementation so far is:

```php
<?php
abstract class Subject
{
	abstract public function register($observer);
	abstract public function unregister($observer);
}

class ConcreteSubject extends Subject
{
	private $observers = array();

	public function register($observer)
	{
		array_push($this->observers, $observer);
	}

	public function unregister($observer)
	{
		foreach ($this->observers as $k => $v)
		{
			if ($observer == $v)
			{
				unset ($this->observers[$k]);
				return true;
			}
		}
		return false;
	}
	
	public function getNumberOfObservers()
	{
		return count($this->observers);
	}
}

interface iObserver
{
}
class ConcreteObserver implements iObserver
{
} 
?>
```

**Refactoring Pull Up**

At this point, it's debatable whether Subject should be abstract or an interface since we aren't supplying any default behavior. Having a quick look, it seems we can ["pull up"][pull_up] the getNumberOfObservers method. We pull it up into our abstract Subject class as follows:

```php
<?php
abstract class Subject
{
	protected $observers = array();
	abstract public function register($observer);
	abstract public function unregister($observer);
	public function getNumberOfObservers()
	{
		return count($this->observers);
	}
}
?>
```

_Note that getNumberOfObservers is a method that I took the liberty of creating to make the Subject more testable. It's not actually part of the Observer pattern._

**DRY (don't repeat yourself)**

Ok, that looks better but I don't like our <code>testUnsubscribing</code> spec. It's too long for such a simple test and also, if you look carefully at <code>testSubscribing</code>, you'll see that we have duplicate code breaking the [DRY Principle][dry]. Essentially, the DRY principle states that we want to avoid duplication since that requires us to maintain duplicate code bases in parallel.

**Setup and Teardown**

We can pull that duplicate code up in to a setUp method (setUp is used to set up state before each test case run; as expected, tearDown executes just after each test case run). Doing so will mean we have to go through and change our references from the local to class level like so:

```php
<?php
require_once('Observer.php');

class ObserverTests extends PHPUnit_Framework_TestCase
{
	private $observer = null;
	private $observer2 = null;
	private $subject = null;

	public function setUp()
	{
    	$this->observer = new ConcreteObserver();
    	$this->observer2 = new ConcreteObserver();
        $this->subject = new ConcreteSubject();
	}
	public function tearDown()
	{
    	$this->observer = null;
    	$this->observer2 = null;
        $this->subject = null;
	}
	
    public function testSubscribing()
    {
        $observers = $this->registerMany(1);
        $this->assertEquals(1, $this->subject->getNumberOfObservers(),
        	"Subscriber should have 1 observer when 1 observer registered.");
    }
    
    public function testUnsubscribing()
    {
        $this->subject->register($this->observer);
        $this->subject->register($this->observer2);
        $this->assertEquals(2, $this->subject->getNumberOfObservers(),
        	"Should be 2 observers when 2 added.");
        $this->subject->unregister($this->observer);
        $this->assertEquals(1, $this->subject->getNumberOfObservers(),
        	"Should be 1 observer when 1 when one removed.");
    }
}
?>
```

You should now notice that—since our last refactoring—the test case has become much more readable and easy to understand. Thus we didn't just reduce duplication (but more importantly) made our tests [self documenting][self_documenting].

For this exercise, we're using phpunit's setUp/tearDown combination (similar to JUnit if you're a Java developer), but you'll see other similar hooks in other frameworks like beforeEach/afterEach ([Jasmine][jasmine]), begin/done ([QUnit][qunit]), etc. These methods are called just before and after each test case allowing you to set up (and tear down) state. This ensures that each test is ran in isolation not depending on any leftover state from previous test case runs.

**Helpers**

I'd like to do one more refactoring before moving on—I'm not happy with the way we're registering our observers within each test case. Let us try moving that code in to a helper function:

```php
<?php
    public function testUnsubscribing()
    {
        $observers = $this->registerMany(5);
...
    private function registerMany($n)
    {
        $observers = array();
        foreach (range(0, $n-1) as $key) {
            $observers[$key] = new ConcreteObserver();
            $this->subject->register($observers[$key]);
        }
        return $observers;
    }
}
?>
```

_The above helper method is a direct lift from the **rollMany** helper function in the famous bowling game kata. If you haven't heard of it you should take a quick look at [Uncle Bob's Bowling Game Kata][bowling_game]. Read the short article and perhaps schedule time in your calendar to go through the power point presentation step by step. Better yet, play with the kata using your favorite language(s) in your "spare time"...it's quite instructive. Here's an example of using [phpunit with BDD to solve Bowling Game][bowling_game_phpunit]._

### A Slight Detour: What should I test?

Determining just what to test is a difficult task but here are some general guidelines to help with that process.

#### Structured Basis Testing
The main idea of [Structured Basis Testing][structured_basis_testing] is that your test cases, for a particular piece of code, are derived from the code's logic and you try to have tests in place for every possible branch. So at it's simplest:

```php
<?php
function foo() {  // count 1 for the function itself
    if (something) { // count 2 for this condition
        // ...
    } else if (somethingElse) { // count 3 for this condition
        // ...
    } 
    // We count one for function itself because this might be
    // all that's executed if neither above are truthy!
    return true;
}
?>
```

This is also sometimes referred to as _logic coverage testing_. The programmer's tome [Code Complete 2][code_complete2] discusses this in depth in it's _Chapter 22: Developer Testing_.

#### Boundary Conditions Testing

[The Practice of Programming][practice_of_programming] tells us that we should:

>Test code at its boundaries...The idea is that most bugs occur at boundaries.

When you test production code that can take a range of valid values, use just below minimum, minimum, maximum, and just below and exceeding maximum values. This takes discipline and time (but given that boundaries are likely places where errors creep up) it's well worth the effort. 

_Also keep in mind that there might be compound boundaries when 2 or more variables interact. An obvious example is when you multiply two numbers. In this case, you have the potential to inadvertently exceed the maximum value._

#### Known Error Testing
Is there an area that is likely to be problematic ? If so, make sure to test for that area.

An example might be if you were to implement a "time picker". Setting aside time zone and daylight savings concerns, we'd want heavy coverage on times known to be error prone such as: 00:00, 12:00am, 12:00pm (keep in mind the user's time format might be 12 or 24 hour format!) In addition, you may also want to use: 23:59, 11:59pm, 11:59am, 00:01, 12:01am, 12:01pm, etc.

#### Data-Flow Testing
This is also discussed in the [Code Complete 2][code_complete2] book, and you should turn to that resource for more in depth coverage. However, essentially, data-flow testing provides that _data usage_ is just as problematic as control flow, and that you therefore need to take into account of the various possible states your data structures might be in (e.g. Defined, Used, Killed, Entered, Exited, etc.) Data flow testing is beyond the scope of this book but you can read more in [this paper][data_flow_testing] if you're interested.

#### Cross Checking
If you have to implement something that's been reliably implemented elsewhere, you may be able to _cross check_ your results against the existing implementation:

```java
actual = MyMath.squareRoot(n);
expected = Math.sqrt(n); 
assertEquals(actual, expected,
"My implementation of square root should be same as Java's");
```

_In this chapter, we are focusing on using TDD to unit test our Observer implementation thus leaving out many other important types of testing: [Integration Testing][integration_testing], [Systems Testing][system_testing], [Stress Testing][stress_testing], etc. In a real system, you will ideally have a suite of complimentary tests that include all of the aforementioned within an [Continuous Integration System][ci]. You should also consider using a tool to measure your [test code coverage][code_cov]._

### Testing both the "happy" and "sad" paths

Gary Bernhardt, of [Destroy All Software][das_gary], uses "happy" and "sad" to describe the normal and abnormal code paths. At minimum, both of these paths—sometimes also called the nominal and non-nominal paths (but I like Gary's happy/sad better!)—should get proper test coverage. Perhaps so far we've been a bit too joyful!

```php
<?php
    public function testSubscribingWithFalsy()
    {
        $observers = $this->subject->register(null);
        $this->assertEquals(0, $this->subject->getNumberOfObservers(),
        	"Should be 0 observers if register called with a falsy.");
    }
?>
```

Which results in:

```bash
Failed asserting that 1 matches expected 0.
```

So we change our system under test to be:

```php
<?php
	public function register($observer)
	{
		if (!empty(\$observer))
		{
			$this->observers[] = $observer;
		}
	}
?>
```

And we're back to passing. However, not only could we pass in falsy arguments, but we might also pass in a non-observer as well. So we need to defend against that as well:

```php
<?php
    public function testSubscribingWithNonObserver()
    {
        $nonObserver = "I'm not a legal observer!";
        $observers = $this->subject->register($nonObserver);
        $this->assertEquals(0, $this->subject->getNumberOfObservers(), 
        	"Should be 0 observers if register called with a falsy.");
    }
?>
```

Obviously, we need some type hinting help here and so we try to change our abstract Subject's interface to be like:

```php
<?php
    abstract public function register(iObserver $observer);
    abstract public function unregister(iObserver $observer);
?>
```

But doing this results in the following goliath error output:

```bash
1) ObserverTests::testSubscribingWithFalsy
Argument 1 passed to ConcreteSubject::register()
must implement interface iObserver, null given...

2) ObserverTests::testSubscribingWithNonObserver
Argument 1 passed to ConcreteSubject::register()
must implement interface iObserver, string given...
...
```

But if we look carefully at these errors, we see that the type hinting is actually doing it's job thus disallowing us to pass both NULL and String to register! Let's remove the following two test cases:

```php
    // public function testSubscribingWithFalsy()
    // {
    //     $observers = $this->subject->register(null);
    //     $this->assertEquals(0, $this->subject->getNumberOfObservers(),
    //         "Should be 0 observers when register called with a falsy.");
    // }
    // public function testSubscribingWithNonObserver()
    // {
    //     $nonObserver = "I'm not a legal observer";
    //     $observers = $this->subject->register($nonObserver);
    //     $this->assertEquals(0, $this->subject->getNumberOfObservers(),
    //         "Should be 0 observers when register called with a falsy.");
    // }
```

After said removal, we're back to passing again, and we've made our production code easier to read:

```php
<?php
    public function register(iObserver $observer)
    {
        $this->observers[] = $observer;
    }
?>
```

Obviously, in a real system we'd need to think of other "sad paths", but it's interesting to note that the mere process of testing has helped us to remove needless code. For example, we don't have to add code like:

```php
    if (is_object($newSubject) &&
    	$newSubject instanceof Subject) {
    ...
```

Let's continue to look for other possible "sad paths":

```php
<?php
    public function testUnsubscribingWhenNone()
    {
        $actual = $this->subject->unregister(new ConcreteObserver());
        $this->assertEquals(false, $actual, 
        	"Unregister returned status should be false if no observers");
    }
    public function testGetNumberObserversWhenNone()
    {
        $this->assertEquals(0, $this->subject->getNumberOfObservers(),
        	"Should be 0 observers when none.");
    }
?>
```

We try the above and they both pass without any changes. Since the getNumberOfObservers is really just a helper to facilitate testing (and is quite simple), let's remove that test. We only want "useful" tests.

Before we can implement our observers we need to add a capability to the subject. In order to be useful, we need to be able to set and get some sort of meaningful data from the Subject. Let's do that now:

```php
<?php
    public function testSubjectShouldHoldState()
    {
        $stateAsString = "some state";
        $this->subject->setState($stateAsString);
        $actual = $this->subject->getState();
        $this->assertEquals($stateAsString, $actual,
        	"Should get/set state as string");

        $stateAsArray = array('foo' => 'foo val');
        $this->subject->setState($stateAsArray);
        $actual = $this->subject->getState();
        $this->assertSame($stateAsArray, $actual,
        	"Should get/set state as array");
    }
?>
```

And the obvious implementation:

```php
<?php
    public function setState($state)
    {
        $this->state = $state;
    }
    public function getState()
    {
        return $this->state;
    }
?>
```

I'm not pleased with the length of the test case and don't think it's too useful to test both for types String and Array. It was the first time through but not for repeatable test suite runs. So I'll remove the String part leaving just:

```php
<?php
    public function testSubjectShouldHoldState()
    {
        $stateAsArray = array('foo' => 'foo val');
        $this->subject->setState($stateAsArray);
        $actual = $this->subject->getState();
        $this->assertSame($stateAsArray, $actual,
        	"Should get/set state as array");
    }
?>
```

Again, we want readable and meaningful tests so we sometimes have to make these sorts of judgement calls, and I'm going to favor brevity in this case.

Now that we have a way to hold state, we can start implementing our observer interface:

```php
<?php
    public function testObserverGetsNotified()
    {
        $subject = new ConcreteSubject();
        $observer = new ConcreteObserver($subject);
        $state = 'yogabbagabba';
        $subject->setState($state);
        $this->assertEquals($state, $observer->getState(),
        	"Setting state in subject notifies observer");
    }
?>
```

And then:

```php 
<?php
interface iObserver
{
    public function update (iSubject $subject);
}
class ConcreteObserver implements iObserver
{
    private $state;
    public function update (iSubject $subject)
    {
        $this->state = $subject->getState();
    }
    public function getState()
    {
        return $this->state;
    }
} 
?>
```

Resulting in:

```bash
1) ObserverTests::testObserverGetsNotified
Setting state in subject notifies observer
Failed asserting that null matches expected 'yogabbagabba'.
```

Our observer looks ok, but it's likely not getting notified (duh, we didn't implement that yet!)...

### Implementing Observers

So we're at a sort of critical point here. We've implemented quite a bit of code, and we still have more (the notify observers routine for example). Moreover, we can feel our blood pressure rise and our confidence level dropping. What to do? Simply back up a step. Get those tests green again!

```php
    // public function testObserverGetsNotified()
    // {
    //     $subject = new ConcreteSubject();
    //     $observer = new ConcreteObserver($subject);
    //     $state = 'yogabbagabba';
    //     $subject->setState($state);
    //     $this->assertEquals($state, $observer->getState(),
    //         "Setting state in subject notifies observer");
    // }
```

We've simply commented out our last test case, re-ran our tests and we're green:

```bash
OK, but incomplete or skipped tests!
Tests: 6, Assertions: 6, Incomplete: 1.
```

We can take a deep breath feeling reassured that we haven't created a 30 minute debugging session for ourselves. Also note that we've left the "partially implemented" changes in our production code. So we've actually made some progress given that, so far, we haven't caused a regression. We'll take a bit of a risk, and leave those change in while we implement notifications:

```php
<?php
    public function testObserverGetsNotified()
    {
        $this->subject->register($this->observer);
        $state = 'yogabbagabba';
        $this->subject->setState($state);
        $this->subject->notify();
        $this->assertEquals($state, $this->observer->getState(),
        	"Setting state in subject notifies observer");
    }
?>
```

This was derived from the test case we commented out earlier. If it's not obvious, we've defined $this->observer in setUp. Here's our full implementation so far:

```php
<?php
abstract class Subject
{
    protected $observers;
    protected $state;

    abstract public function register(iObserver $observer);
    abstract public function unregister(iObserver $observer);
    
    public function getNumberOfObservers()
    {
        return count($this->observers);
    }
}

class ConcreteSubject extends Subject
{
    public function __construct()
    {
        $this->observers = array();
    }
    public function register(iObserver $observer)
    {
        $this->observers[] = $observer;
    }

    public function unregister(iObserver $observer)
    {
        foreach ($this->observers as $k => $v)
        {
            if ($observer == $v)
            {
                unset ($this->observers[$k]);
                return true;
            }
        }
        return false;
    }

    public function notify()
    {
        foreach ($this->observers as $observer) {
            $observer->update($this);
        }
    }
    public function setState($state)
    {
        $this->state = $state;
    }

    public function getState()
    {
        return $this->state;
    }
}

interface iObserver
{
    public function update (Subject $subject);
}
class ConcreteObserver implements iObserver
{
    private $state = null;
    public function update (Subject $subject)
    {
        $this->state = $subject->getState();
    }
    public function getState()
    {
        return $this->state;
    }
}
?> 
```

Notice that by using a sprinkle of [type hinting][type_hinting], we don't have to resort to using things [method_exists][method_exists] checks to determine if the subject passed into _update_ actually has a _getState_ method...it can be safely assumed since _Subject_ defines that as an abstract (thus required) method.

**Unique observers**
We'd like to prevent an exact observer instance getting included in our subject's observer _List_ twice. There are several approaches we could take to solve this, but let's go with adding an id property to each observer instance and then utilizing <code>array_unique</code>.

First the ID part:

```php
<?php
// we add this test
    public function testObserversHaveIds()
    {
        $this->assertNotNull($this->observer->getID());
    }

// and we add this to observer:
    private $state = null;
    private $id;
    public function __construct()
    {
        $this->state = null;
        $this->id = uniqid (rand(), true);
    }
    public function getID()
    {
        return $this->id;
    }
?>
```

Now that our observers have IDs, we can have our subject enforce uniqueness in _register_. First let's see this fail:

```php
<?php
    public function testAddingDuplicates()
    {
        $this->subject->register($this->observer);
        $this->subject->register($this->observer);
        $this->assertEquals(1, $this->subject->getNumberOfObservers(),
        	"Should only add a particular observer instance once");
    }
?>
```

This results in:

```bash
1) ObserverTests::testAddingDuplicates
Should only add observer once
Failed asserting that 2 matches expected 1.
```

In order to use <code>array_unique</code>we need to first implement a <code>__toString</code> in our observer. 

We first comment out <code>testAddingDuplicates</code>, verify our tests are again passing, add the <code>__toString</code> method, and once more verify our tests are still passing. After safely adding <code>__toString</code>, we uncomment testAddingDuplicates and continue onwards on our mission to disallow duplicates. All we have to at this point is to call <code>array_unique</code> when we register our observers like so:

```php
<?php
    public function register(iObserver $observer)
    {
        $this->observers[] = $observer;
        $this->observers = array_unique($this->observers);  
    }
?>
```

Our test is now passing and we've ensured all our observers are, in fact, unique instances. 

## Final Implementation
At this point, we have a fairly complete Observer implementation that could be further extended easily should we so desire.

#### ObserverTests.php

```php
<?php
require_once('Observer.php');

class ObserverTests extends PHPUnit_Framework_TestCase
{
    private $subject = null;
    private $observer = null;

    public function setUp()
    {
        $this->subject = new ConcreteSubject();
        $this->observer = new ConcreteObserver($this->subject);
    }
    public function tearDown()
    {
        $this->subject = null;
        $this->observer = null;
    }

    public function testSubscribing()
    {
        $observers = $this->registerMany(1);
        $this->assertEquals(1, $this->subject->getNumberOfObservers(),
        	"Subscriber should have 1 observer when 1 observer registered.");
    }

    public function testUnsubscribing()
    {
        $observers = $this->registerMany(5);
        $this->assertEquals(5, $this->subject->getNumberOfObservers(),
        	"Should be 5 observers when 5 added.");
        $this->subject->unregister($observers[0]);
        $this->assertEquals(4, $this->subject->getNumberOfObservers(),
        	"Should be 4 observers when 1 when one removed.");
    }
    public function testUnsubscribingWhenNone()
    {
        $actual = $this->subject->unregister(new ConcreteObserver());
        $this->assertEquals(false, $actual,
        	"Unregister returned status should be false if no observers");
    }
    public function testSubjectShouldHoldState()
    {
        $stateAsArray = array('foo' => 'foo val');
        $this->subject->setState($stateAsArray);
        $actual = $this->subject->getState();
        $this->assertSame($stateAsArray, $actual,
        	"Should get/set state as array");
    }
    public function testCreateObserver()
    {
        $actual = new ConcreteObserver(new ConcreteSubject());
        $this->assertNotNull($actual, "Should create observer");
    }
    public function testObserverGetsNotified()
    {
        $this->subject->register($this->observer);
        $state = 'yogabbagabba';
        $this->subject->setState($state);
        $this->subject->notify();
        $this->assertEquals($state, $this->observer->getState(),
        	"Setting state in subject notifies observer");
    }
    public function testObserversHaveIds()
    {
        $this->assertNotNull($this->observer->getID());
    }
    public function testAddingDuplicates()
    {
        $this->subject->register($this->observer);
        $this->subject->register($this->observer);
        $this->assertEquals(1, $this->subject->getNumberOfObservers(),
        	"Should only add a particular observer instance once");
    }
    private function registerMany($n)
    {
        $observers = array();
        foreach (range(0, $n-1) as $key) {
            $observers[$key] = new ConcreteObserver();
            $this->subject->register($observers[$key]);
        }
        return $observers;
    }
}
?>
```

#### Observer.php

```php
<?php
abstract class Subject
{
    protected $observers;
    protected $state;

    abstract public function register(iObserver $observer);
    abstract public function unregister(iObserver $observer);
    abstract public function notify();
 
    public function getNumberOfObservers()
    {
        return count($this->observers);
    }
}

class ConcreteSubject extends Subject
{
    public function __construct()
    {
        $this->observers = array();
    }
    public function register(iObserver $observer)
    {
        $this->observers[] = $observer;
        $this->observers = array_unique($this->observers);  
    }

    public function unregister(iObserver $observer)
    {
        foreach ($this->observers as $k => $v)
        {
            if ($observer == $v)
            {
                unset ($this->observers[$k]);
                return true;
            }
        }
        return false;
    }

    public function notify()
    {
        foreach ($this->observers as $observer) {
            $observer->update($this);
        }
    }
    public function setState($state)
    {
        $this->state = $state;
    }

    public function getState()
    {
        return $this->state;
    }
}

interface iObserver
{
    public function update (Subject $subject);
}
class ConcreteObserver implements iObserver
{
    private $state = null;
    private $id;
    public function __construct()
    {
        $this->state = null;
        $this->id = uniqid (rand(), true);
    }
    public function getID()
    {
        return $this->id;
    }
    public function __toString()
    {
        return "id: " . $this->getID();
    }
    public function update (Subject $subject)
    {
        $this->state = $subject->getState();
    }
    public function getState()
    {
        return $this->state;
    }
} 
?>
```

## Considerations
Here are some further considerations when implementing Observer pattern that we haven't discussed...

### SPL 
It should be noted that we could have used the PHP 5 Standard Library's SplObserver and SplSubject instead of rolling our own interfaces. However, doing so was helpful in getting a clear understanding of exactly how the Observer Pattern works. If you're interested learning more about SPL, you may want to have a look at the following resources: [SPL][spl], [SplSubject][spl_subject], and [SplObserver][spl_observer]. _Note that instead of register/unregister they use the names attach/detach._

### Push vs. Pull
It should also be noted there are different strategies for how the state is obtained by the observers. In this chapter's example, we used the _pull method_ since our observers called:

```php
$subject->getState()
```

Thus, upon being notified, we "pulled in" the changes from the subject. However, the inverse is also possible—where the subject _pushes_ changes down to the observers—and, appropriately enough, is called the _push method_. An example of how _push method_ might work is the following:

```php
$observer->update($this, $user, $trackPlayed, 
	$action, [..more state info..]);
```

_Here, the subject has explicitly included the pertinent state information in the call to update._

The up side here is that the observers need not call:

```php
subject->getState()
```

However, there's bigger downside—loss of reuse. When we keep the state object in the subject generic enough, it's easy to reuse the Observer code (since the state object abstracts itself in a model, value object, etc.) However, if we (in contrast to using a generic state object) use a specific method signature to push state, we tightly couple our subject to the observers. In general, favor the _pull method_ over the _push method_.

### Pitfalls
_Warning: This section contains some opinionated suggestions. Use your own judgement and form your own opinions!_

The following are some potential issues to look out for when implementing Observer pattern:

* **Cyclic dependencies**: Allowing observers to set state reduces orthogonality. It is our opinion that another "impartial module" should be responsible for providing state updates to the subject. If you do need this coupling consider another design approach or implementing a Mediator to manage these interactions.

* **Relationship hierarchies**: Insidious coupling can occur if you allow observers to interact with each other. Strive to design observers to be completely unaware of each other.

* **Wasteful updates**: If an observer only has a particular _interest_, receiving all updates is wasteful. This can be remedied by adding an _interest_ input to the _register_ signature and checking on a per observer basis before broadcasting to that observer.

* **Spurious updates**: In a high throughput system with many observers, there are potential temporal issues. For example, a call to <code>setState()</code> before a previous state has been fully pushed out could result in notifications being sent to observers that have yet to receive the previous state. The obvious workaround is to queue these states and only start notifications for the "newest state" once "previous state" notification are complete. This of course adds additional complexity.

* **Unpredictable ordering**: This pattern does not provide predictable ordering of observer updates and doing so increases coupling.

* **Inconsistent state**: If setting state is more than a simple assignment, you must ensure _consistent state_ before broadcasting updates.

* **Duplicate observers**: It's easy to inadvertently create a system where an observer is receiving "double updates" as a result of duplicates observers having been registered.

_We have other decisions to make when we design our implementation (who should call notify? what happens when a subject is deleted? will there be orphaned observers?). These require further research and are beyond the scope of this chapter._

### Example Uses

**What are some example applications that might benefit from the Observer Pattern?**

Here are a couple ideas:

* **Mailing List**:
Let's say you have a system that periodically adds new articles to be published. Upon a new article becoming available, the system could make a call to _setState_ and thereafter _notify_ allowing observers: EmailObserver, RSSFeedObserver, GooglePlusObserver, FacebookObserver, etc., to deal with the particulars of broadcasting said article as appropriate for that broadcast method.

* **Login system**:
Say you want provide notifications to various sub-systems when a user logs in. For example you want a "Security Observer" and perhaps a "User Stats Observer" (for the marketing department), Logger (for troubleshooting), etc. You could do so easily with the Observer pattern. _This article has a nice example [Login system using Observer][login_example]._

* **Social Music**:
A social music application could choose to broadcast notifications when a user plays, pauses, stops, fast forwards, seeks, etc. Perhaps your company is fortunate enough to be partnered with Facebook and needs to broadcast to the Music Dashboard via the [Open Graph API][opengraph]. But then another partner comes along and offers a similar deal to broadcast to the [tunein][tunein_site] online radio site. And then another...

In the above example, each partner's API is likely to be quite different. You'd want your user tracking system to be [completely orthogonal][orthogonal] from the various broadcasting modules that will integrate with the above APIs. _[The Pragmatic Programmer][tpp] book has a wonderful chapter on the advantages of writing orthogonal software if you're interested in further investigating the concept._

* **GUI Notifications**:
You have some "live stats" that need to be reflected in real time by various components. For example, when the price of Apple stock changes, you need to update corresponding charts, graphs, text in callout boxes, etc.

## Exercises
Do one of the following:

* Implement one of these systems using simple print statements. For example, when the RSSFeedObserver's _update_ is called you can just do something like:

```php
<?php
public function update($subject)
{
    $article = $subject->getState(); 
    print "RSSFeedObserver::update called: " .
    	"About to syndicate article " . $article->getTitle();
}
?>
```

* Come up with your own problem that lends itself to the Observer pattern and implement it.

## Summary

In this chapter we've taken a look at the _Observer Pattern_ and learned the following:

* What the _Observer Pattern_ is and how to implement it

* Observer allows us to _program to an interface, not an implementation_—subjects need not know about what the observers will do with state...they just call the _update_ method—thus decoupling modules that interact

* Observers can be "hot swapped" via the register and unregister routines

* We discussed the differences and trade offs between the _pull method_ and the _push method_ strategies

* Programming to an interface can result in less boiler-plate "guard checks" if type hinting used within the _update_ routine 

* We also learned about some of the subtleties of TDD as we iterated on our Observer implementation 


**Other references not linked above**

[Design Patterns in Ruby][dp_ruby_observer],
[Gang of Four][gof],
[Easles on Observer][eales_observer],
[IBM Article][ibm],
[GOF Patterns][gof_patterns],
[Pitfalls of Observer Pattern][pitfalls2],
[Derkeiler on Observer][derkeiler],
[Rice - CPP Resources][rice],
[Addy Osmani Patterns Book][addy]
[PHP Design Patterns][php_ex1],
[Andy Pangus Article][php_ex2]

[eales_observer]: http://www.citrenz.ac.nz/conferences/2005/concise/eales_observer.pdf
[ibm]: http://www.research.ibm.com/designpatterns/example.htm
[gof_patterns]: http://www.gofpatterns.com/design-patterns/module6/tradeoffs-implementing-observerPattern.php
[pitfalls2]: http://askldjd.wordpress.com/2010/03/18/pitfalls-of-observer-pattern/
[derkeiler]: http://coding.derkeiler.com/Archive/General/comp.object/2006-07/msg00234.html
[rice]: http://www.bandgap.cs.rice.edu/classes/comp410/resources/CppResources/DesignPatterns/observer.html
[addy]: http://addyosmani.com/resources/essentialjsdesignpatterns/book/#observerpatternjavascript
