package strategy1;
// 4. Clients couple strictly to the interface
public class TestStrategyDemo {
    // client code here
    private static void execute(Strategy strategy) {
    	strategy.solve();
    }

    public static void main( String[] args ) {
//        Strategy[] algorithms = {new StrategySolutionImpl(), new StrategySearchImpl()};
//        for (Strategy algorithm : algorithms) {
//            execute(algorithm);
//        }
    	
    	if(Math.random() > 0.6) {
    		execute(StrategyType.STRATEGY_SEARCH.getStrategy());
    	} else {
    		execute(StrategyType.STRATEGY_SOLUTION.getStrategy());
    	}
    	
    }
}