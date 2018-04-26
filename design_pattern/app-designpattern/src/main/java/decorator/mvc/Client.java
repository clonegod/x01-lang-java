package decorator.mvc;

public class Client {
	public static void main(String[] args) throws Exception {
		
		String text = "你好\n中国\n你好\n台湾\n你好\n中国";
		
		String target = "中国";
		
		new GrepController().handleRequest(text, target);
	}
}
