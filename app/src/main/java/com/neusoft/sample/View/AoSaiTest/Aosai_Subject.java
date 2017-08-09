package com.neusoft.sample.View.AoSaiTest;

import java.util.ArrayList;


public abstract class Aosai_Subject {


	protected ArrayList<Aosai_Observer> observers = new ArrayList<Aosai_Observer>();

//	Obser
	 
	public void addObserver(Aosai_Observer o) {
		observers.add(o);
	}

	public void removeObserver(Aosai_Observer o) {
		observers.remove(o);
	}
	 
	public abstract void notifyObservers();
}
 
