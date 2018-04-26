package strategy1;

enum StrategyType {
	
	STRATEGY_SEARCH(new StrategySearchImpl()), 
	STRATEGY_SOLUTION(new StrategySolutionImpl());
	
	private Strategy strategy;
	
	StrategyType(Strategy strategy) {
		this.strategy = strategy;
	}
	
	public Strategy getStrategy() {
		return this.strategy;
	}
}
