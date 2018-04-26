package observer.jdk.observer;

import org.junit.Test;

public class TestJdkObserver {
	
	@Test
	public void test() {
		WeatherDataObservable subject = new WeatherDataObservable();
		new WeatherDataDisplayObserver(subject);
		
		// StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);
		// ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData);
		subject.setMeasurements(80, 65, 30.4f);
		subject.setMeasurements(82, 70, 29.2f);
		subject.setMeasurements(78, 90, 29.2f);
	}
}
