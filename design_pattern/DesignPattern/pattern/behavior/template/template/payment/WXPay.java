package template.payment;

public class WXPay extends AbstractPayment {

	@Override
	public PayResponse doPay(PayRequest payRequest) {
		return new PayResponse(PaymentChannel.WXPAY.name(), "success");
	}

	@Override
	protected void beforePay() {
		System.out.println("检查支付环境，支付渠道：" + PaymentChannel.WXPAY);
	}

	@Override
	protected void postPay() {
		System.out.println("支付成功，结束");
	}
	
}
