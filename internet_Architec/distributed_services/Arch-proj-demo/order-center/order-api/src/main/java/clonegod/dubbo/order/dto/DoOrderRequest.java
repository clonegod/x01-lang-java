package clonegod.dubbo.order.dto;

import java.io.Serializable;
import java.util.Date;

public class DoOrderRequest implements Serializable {
	
	private static final long serialVersionUID = 961112640545323094L;

	private String status; // 订单状态
	
	private long price; // 价格
	
	private Date createTime; // 创建日期

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "OrderRequest [status=" + status + ", price=" + price + ", createTime=" + createTime + "]";
	}
	
}
