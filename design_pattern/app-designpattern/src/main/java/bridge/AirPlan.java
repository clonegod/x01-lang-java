package bridge;

import bridge.planmaker.AirplanMaker;

public abstract class AirPlan {
	
	public abstract void fly();
	
	protected AirplanMaker airplanMaker; // 桥梁---将另一个变化的因素使用组合模式引入到系统中

	public AirPlan(AirplanMaker airplanMaker) {
		this.airplanMaker = airplanMaker;
	}

	public AirplanMaker getAirplanMaker() {
		return airplanMaker;
	}

	public void setAirplanMaker(AirplanMaker airplanMaker) {
		this.airplanMaker = airplanMaker;
	}
	
	
}
