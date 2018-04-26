package template.coffee;

public class TestTemplateExample {
	public static void main(String[] args) {
		TeaWithHook teaHook = new TeaWithHook();
		System.out.println("\nMaking tea...");
		teaHook.prepareRecipe();
		
		
		System.out.println("\nMaking coffee...");
		Coffee coffee = new Coffee();
		coffee.prepareRecipe();
	}
}
