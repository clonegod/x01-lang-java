package clonegod.commons;

public class TestSystemEnv {
	public static void main(String[] args) {
		System.getenv().forEach((key, value) -> {
			System.out.println(String.join("=", key, value));
		});
	}
}
