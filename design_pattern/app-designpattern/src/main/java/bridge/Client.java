package bridge;

import bridge.planmaker.AirBus;
import bridge.planmaker.AirplanMaker;
import bridge.planmaker.Boeing;

public class Client {
	
	public static void main(String[] args) {
		
		AirplanMaker airBus = new AirBus();
		AirplanMaker boeing = new Boeing();
		
		System.out.println("=====================客运机=====================");

		AirPlan passengerPlane = new PassengerPlane(airBus);
		passengerPlane.fly();

		passengerPlane.setAirplanMaker(boeing);
		passengerPlane.fly();

		System.out.println("\n=====================货运机=====================");
		
		AirPlan cargoPlan = new CargoPlane(airBus);
		cargoPlan.fly();
		cargoPlan.setAirplanMaker(boeing);
		cargoPlan.fly();
		
	}
}
