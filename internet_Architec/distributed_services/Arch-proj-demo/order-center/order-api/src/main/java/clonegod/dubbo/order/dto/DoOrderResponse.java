package clonegod.dubbo.order.dto;

import java.io.Serializable;

public class DoOrderResponse implements Serializable {
	
	private static final long serialVersionUID = -7143149263076564023L;

	private String code; // 结果状态码
	
	private String memo; // 结果说明
	
	private String orderNo; // 订单号
	
	public DoOrderResponse() {
		super();
	}

	public DoOrderResponse(String code, String message, String orderNo) {
		super();
		this.code = code;
		this.memo = message;
		this.orderNo = orderNo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Override
	public String toString() {
		return "OrderResponse [code=" + code + ", memo=" + memo + ", orderNo=" + orderNo + "]";
	}

}
