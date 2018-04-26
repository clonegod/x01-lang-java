package clonegod.dubbo.api.order;

import java.io.Serializable;

public class OrderResponse implements Serializable {
	
	private static final long serialVersionUID = -7143149263076564023L;

	private String status; // 结果状态码
	
	private String message; // 结果说明
	
	private String orderNo; // 订单号
	
	public OrderResponse() {
		super();
	}

	public OrderResponse(String status, String message, String orderNo) {
		super();
		this.status = status;
		this.message = message;
		this.orderNo = orderNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	@Override
	public String toString() {
		return "OrderResponse [status=" + status + ", message=" + message + ", orderNo=" + orderNo + "]";
	}
	
}
