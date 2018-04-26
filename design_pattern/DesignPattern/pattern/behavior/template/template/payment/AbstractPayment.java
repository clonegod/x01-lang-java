package template.payment;

public abstract class AbstractPayment implements Payment {
	
	/**
	 * 定义算法的骨架
	 */
	@Override
	public PayResponse pay(PayRequest payRequest) {
		beforePay();
		PayResponse payResponse = doPay(payRequest);
		postPay();
		return payResponse;
	}
	
	// 不同子类对同一个操作提供不同的实现
	protected abstract void beforePay();

	protected abstract PayResponse doPay(PayRequest payRequest);

	protected abstract void postPay();

}

