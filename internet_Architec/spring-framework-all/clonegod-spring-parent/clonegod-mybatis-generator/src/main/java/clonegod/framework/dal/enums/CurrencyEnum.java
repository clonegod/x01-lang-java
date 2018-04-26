package clonegod.framework.dal.enums;

public enum CurrencyEnum {
	RMB("人民币", "￥"), USA("美元", "$");
	
	private String alias;
	
	private String symbol;
	
	private CurrencyEnum(String alias, String symbol) {
		this.alias = alias;
		this.symbol = symbol;
	}

	/**
	 * 枚举映射到数据库中
	 */
	public String getAlias() {
		return this.name() + ":" + this.alias;
	}
	
	public String getSymbol() {
		return this.name() + ":" + this.symbol;
	}
	
	/**
	 * 查询结果还原到枚举实例
	 */
	public static CurrencyEnum ofType(String value) {
		if(value.matches("RMB:(￥|人民币)")) {
			return RMB;
		}
		if(value.equals("USA:($|美元)")) {
			return USA;
		}
		return null;
	}
	
	@Override
	public String toString() {
		return this.name()+"-"+this.alias+"-"+this.symbol;
	}
}
