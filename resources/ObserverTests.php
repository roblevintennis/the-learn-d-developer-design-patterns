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
        $this->assertEquals(1, $this->subject->getNumberOfObservers(), "Subscriber should have 1 observer when 1 observer has been registered.");
    }

    public function testUnsubscribing()
    {
        $observers = $this->registerMany(5);
        $this->assertEquals(5, $this->subject->getNumberOfObservers(), "Should be 5 observers when 5 added.");
        $this->subject->unregister($observers[0]);
        $this->assertEquals(4, $this->subject->getNumberOfObservers(), "Should be 4 observers when 1 when one removed.");
    }
    public function testUnsubscribingWhenNone()
    {
        $actual = $this->subject->unregister(new ConcreteObserver());
        $this->assertEquals(false, $actual, "Unregister returned status should be false if no observers");
    }
    public function testSubjectShouldHoldState()
    {
        $stateAsArray = array('foo' => 'foo val');
        $this->subject->setState($stateAsArray);
        $actual = $this->subject->getState();
        $this->assertSame($stateAsArray, $actual, "Should get/set state as array");
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
        $this->assertEquals($state, $this->observer->getState(), "Setting state in subject notifies observer");
    }
    public function testObserversHaveIds()
    {
        $this->assertNotNull($this->observer->getID());
    }
    public function testAddingDuplicates()
    {
        $this->subject->register($this->observer);
        $this->subject->register($this->observer);
        $this->assertEquals(1, $this->subject->getNumberOfObservers(), "Should only add a particular observer instance once");
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