package observer.mine.observable;

import org.junit.Test;

import observer.mine.observer.CurrentConditionsDisplay;
import observer.mine.observer.ForecastDisplay;

public class TestObserverExample {

	@Test
	public void test() {
		WeatherData subject = new WeatherData();
		
		new CurrentConditionsDisplay(subject);
		// StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);
		new ForecastDisplay(subject);
		
		subject.setMeasurements(80, 65, 30.4f);
		subject.setMeasurements(82, 70, 29.2f);
		subject.setMeasurements(78, 90, 29.2f);
	}
}
