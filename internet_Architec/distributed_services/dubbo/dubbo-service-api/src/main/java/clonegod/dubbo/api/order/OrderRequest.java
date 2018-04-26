package clonegod.dubbo.api.order;

import java.io.Serializable;

public class OrderRequest implements Serializable {
	
	private static final long serialVersionUID = 961112640545323094L;

	private String productNo; // 商品编号

	private int quantity; // 数量

	private String username; // 用户名

	public OrderRequest() {
		super();
	}

	public OrderRequest(String productNo, int quantity, String username) {
		super();
		this.productNo = productNo;
		this.quantity = quantity;
		this.username = username;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "OrderRequest [productNo=" + productNo + ", quantity=" + quantity + ", username=" + username + "]";
	}

}
