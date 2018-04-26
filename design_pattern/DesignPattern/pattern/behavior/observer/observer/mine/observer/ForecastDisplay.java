package observer.mine.observer;

import observer.mine.observable.Subject;

public class ForecastDisplay implements Observer, DisplayItem {
	
	private float currentPressure = 29.92f;
	private float lastPressure;
	
	public ForecastDisplay(Subject subject) {
		subject.registerObserver(this);
	}
	
	@Override
	public void display() {
		System.out.println("lastPressure: " + lastPressure + ", currentPressure " + currentPressure);
	}

	@Override
	public void update(float temp, float humidity, float pressure) {
		lastPressure = currentPressure;
		this.currentPressure = pressure;
		display();
	}

}
