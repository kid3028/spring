package org.springframework.learn.event;

import java.util.EventObject;
import java.util.Observable;
import java.util.Observer;

public class ObserverDemo {

	public static void main(String[] args) {
		EventObservable observable = new EventObservable();
		observable.addObserver(new EventObserver());
		observable.setChange("Hello Spring");
	}

	static class EventObservable extends Observable {

		public void setChange(Object msg) {
			setChanged();
			notifyObservers(new EventObject(msg));
			clearChanged();
		}
	}

	static class EventObserver implements Observer {

		@Override
		public void update(Observable o, Object msg) {
			System.out.println("收到事件 : " + msg);
		}
	}
}
