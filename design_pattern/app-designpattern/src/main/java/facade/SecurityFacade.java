package facade;

import facade.subsystem.Alarm;
import facade.subsystem.Camera;
import facade.subsystem.Light;
import facade.subsystem.Sensor;

public class SecurityFacade {
	
	private Camera camera1,camera2;
	
	private Light light1, light2, light3;
	
	private Sensor sensor;
	
	private Alarm alarm;
	
	
	public SecurityFacade() {
		this.camera1 = new Camera();
		this.camera2 = new Camera();
		this.light1 = new Light();
		this.light2 = new Light();
		this.light3 = new Light();
		this.sensor = new Sensor();
		this.alarm = new Alarm();
	}

	public void active() {
		camera1.turnOn();
		camera2.turnOn();
		
		light1.turnOn();
		light2.turnOn();
		light3.turnOn();
		
		sensor.turnOn();
		
		alarm.turnOn();
	}
	
	public void deActive() {
		camera1.turnOff();
		camera2.turnOff();
		
		light1.turnOff();
		light2.turnOff();
		light3.turnOff();
		
		sensor.turnOff();
		
		alarm.turnOff();
	}
	
}
