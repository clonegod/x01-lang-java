package observer.jdk.observer;

import java.util.Observable;

public class WeatherDataObservable extends Observable {
	private float temperature;
	private float humidity;
	private float pressure;

	public WeatherDataObservable() {
	}

	public void measurementsChanged() {
		 /* 
		  * 如果没有setChanged()方法，我们的气象站测量是如此敏锐,于温度计读数每十分之一度就会更新，
		  * 这会造成WeatherData对象持续不断地通知观察者，我们并不希望看到这样的事情发生。
		  * 如果我们希望半度以上才更新，就可以在温度差距到达半度时，调用setChanged()，进行有效的更新*/
		setChanged(); // 是否通知观察者，可通过控制是否调用该方法来实现！
		
		notifyObservers(); // notifyObservers(null);
		// notifyObservers(extra data to observer); // 可以传递额外参数，主动推数据给观察者
	}

	public void setMeasurements(float temperature, float humidity, float pressure) {
		this.temperature = temperature;
		this.humidity = humidity;
		this.pressure = pressure;
		measurementsChanged();
	}

	public float getTemperature() {
		return temperature;
	}

	public float getHumidity() {
		return humidity;
	}

	public float getPressure() {
		return pressure;
	}
}