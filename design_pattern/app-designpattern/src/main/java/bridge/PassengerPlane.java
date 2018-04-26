package bridge;

import bridge.planmaker.AirplanMaker;

public class PassengerPlane extends AirPlan {

	public PassengerPlane(AirplanMaker airplanMaker) {
		super(airplanMaker);
	}

	@Override
	public void fly() {
		System.out.println(airplanMaker.produce() + "\t生产的客运机正fly in the sky...");
	}

}
