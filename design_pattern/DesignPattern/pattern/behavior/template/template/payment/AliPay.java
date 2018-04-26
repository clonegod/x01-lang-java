package template.payment;

public class AliPay extends AbstractPayment {

	@Override
	public PayResponse doPay(PayRequest payRequest) {
		return new PayResponse(PaymentChannel.ALIPAY.name(), "success");
	}

	@Override
	protected void beforePay() {
		System.out.println("检查支付环境，支付渠道：" + PaymentChannel.ALIPAY);
	}

	@Override
	protected void postPay() {
		System.out.println("支付成功，结束");
	}

	
}
