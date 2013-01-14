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