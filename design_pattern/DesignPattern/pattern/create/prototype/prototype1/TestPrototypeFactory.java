package prototype1;
public class TestPrototypeFactory {
	
    public static void main(String[] args) {
    	
    	args = "tom dick harry jack".split("\\s+");
    	
        if (args.length > 0) {
            for (String type : args) {
                Person prototype = RegistyFactory.getPrototype(type);
                if (prototype != null) {
                    System.out.println(prototype);
                }
            }
        } else {
            System.out.println("Run again with arguments of command string ");
        }
    }
}