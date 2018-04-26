package singleton.multi;


public class Client {
	public static void main(String[] args) {
		
		LingualResource lingUS = LingualResource.getInstance("en", "US");
		String usd1 = lingUS.getLocaleString("USD");
		System.out.println("USD="+usd1);
		
		LingualResource lingCN = LingualResource.getInstance("zh", "CN");
		String usd2 = lingCN.getLocaleString("USD");
		System.out.println("USD="+usd2);
		
	}
}
