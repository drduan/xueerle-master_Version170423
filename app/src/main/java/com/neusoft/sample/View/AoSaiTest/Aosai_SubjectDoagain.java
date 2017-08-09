package com.neusoft.sample.View.AoSaiTest;

import java.util.ArrayList;


public abstract class Aosai_SubjectDoagain {


	protected ArrayList<Aosai_ObserverDoagain> observers = new ArrayList<Aosai_ObserverDoagain>();

//	Obser
	 
	public void addObserver(Aosai_ObserverDoagain o) {
		observers.add(o);
	}

	public void removeObserver(Aosai_ObserverDoagain o) {
		observers.remove(o);
	}
	 
	public abstract void notifyObservers();
}
 
