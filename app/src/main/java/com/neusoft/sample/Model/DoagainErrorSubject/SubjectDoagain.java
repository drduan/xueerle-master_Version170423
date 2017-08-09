package com.neusoft.sample.Model.DoagainErrorSubject;

import java.util.ArrayList;


public abstract class SubjectDoagain {


	protected ArrayList<ObserverDoagain> observers = new ArrayList<ObserverDoagain>();

//	Obser
	 
	public void addObserver(ObserverDoagain o) {
		observers.add(o);
	}

	public void removeObserver(ObserverDoagain o) {
		observers.remove(o);
	}
	 
	public abstract void notifyObservers();
}
 
