package com.neusoft.sample.Model;

import java.util.ArrayList;



public abstract class Subject {


	protected ArrayList<Observer> observers = new ArrayList<Observer>();

//	Obser
	 
	public void addObserver(Observer o) {
		observers.add(o);
	}

	public void removeObserver(Observer o) {
		observers.remove(o);
	}
	 
	public abstract void notifyObservers();
}
 
