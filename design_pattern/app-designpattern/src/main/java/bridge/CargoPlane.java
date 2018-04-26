package bridge;

import bridge.planmaker.AirplanMaker;

public class CargoPlane extends AirPlan {

	public CargoPlane(AirplanMaker airplanMaker) {
		super(airplanMaker);
	}

	@Override
	public void fly() {
		System.out.println(airplanMaker.produce() + "\t生产的货运机正fly in the sky...");
	}

}
