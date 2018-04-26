package strategy1;
// 2. Bury implementation
abstract class StrategySolution implements Strategy {
    // 3. Template Method
	@Override
    public void solve() {
        start();
        while (nextTry() && !isSolution()) {}
        stop();
    }

    abstract void start();
    abstract boolean nextTry();
    abstract boolean isSolution();
    abstract void stop();
}