package prototype1;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstract Factory might store a set of Prototypes from which to clone and return product objects.
 *
 */
class RegistyFactory {
	
    private static final Map<String, Person> prototypes = new HashMap<>();

    /**
     * 原型实例初始化，内部使用集合进行注册
     */
    static {
        prototypes.put("tom", new Tom());
        prototypes.put("dick", new Dick());
        prototypes.put("harry", new Harry());
    }

    public static Person getPrototype(String type) {
        try {
            return prototypes.get(type).clone();
        } catch (NullPointerException ex) {
            System.out.println("Prototype with name: " + type + ", doesn't exist");
            return null;
        }
    }
}