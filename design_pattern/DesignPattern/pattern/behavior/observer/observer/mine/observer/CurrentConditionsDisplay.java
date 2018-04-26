package observer.mine.observer;

import observer.mine.observable.Subject;

public class CurrentConditionsDisplay implements Observer, DisplayItem {

	private float temperature;
	private float humidity;
	Subject weatherData; // 取消注册时，如果有Subject引用则会方便很多

	public CurrentConditionsDisplay(Subject weatherData) {
		this.weatherData = weatherData;
		weatherData.registerObserver(this);
	}

	/**
	 * 主题发生更新时，update方法被调用
	 */
	@Override
	public void update(float temperature, float humidity, float pressure) {
		this.temperature = temperature;
		this.humidity = humidity;
		display();
	}

	@Override
	public void display() {
		System.out.println("Current conditions: " + temperature + "F degrees and " + humidity + "% humidity");
	}

}
