package observer.jdk.observer;

import java.util.Observable;
import java.util.Observer;

public class WeatherDataDisplayObserver implements Observer, DisplayElement {
	Observable observable;
	private float temperature;
	private float humidity;

	public WeatherDataDisplayObserver(Observable observable) {
		this.observable = observable;
		observable.addObserver(this);
	}

	/**
     * @param   obs   主题对象（观察者获取主题的引用后，不使用主题推的数据，而是通过主题“拉pull”数据）
     * @param   arg   主题对象主动传递给观察者的数据（各个观察者关心的数据可能不同，此为主题主动“推push”数据的方式的应用）
	 */
	@Override
	public void update(Observable obs, Object arg) {
		if (obs instanceof WeatherDataObservable) {
			WeatherDataObservable weatherData = (WeatherDataObservable) obs;
			this.temperature = weatherData.getTemperature();
			this.humidity = weatherData.getHumidity();
			display();
		}
	}
	
	@Override
	public void display() {
		System.out.println("Current conditions: " + temperature + "F degrees and " + humidity + "% humidity");
	}
}