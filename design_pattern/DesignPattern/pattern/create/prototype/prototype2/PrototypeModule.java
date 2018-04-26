package prototype2;

import java.util.ArrayList;
import java.util.List;

/**
 * 2. Design a "registry" that maintains a cache of prototypical objects
 */
class PrototypeModule {
	
    // 2. "registry" of prototypical objs	注册所有的原型实例
    private static List<Prototype> prototypes = new ArrayList<>();

    // Adds a feature to the Prototype attribute of the PrototypesModule class obj 为原型实例管理器添加新的原型对象  
    // The feature to be added to the Prototype attribute	将新的原型实例添加到集合中进行管理
    public static void addPrototype(Prototype p) {
        prototypes.add(p);
    }

    /**
     * client invoke interface
     * 
     * The registry has a findAndClone() "virtual constructor" 
     * that can transform a String into its correct object (it calls clone() which then calls "new")
     * 
     * @param name	prototype name
     * @return	cloned object
     */
    public static Prototype createPrototype(String name) {
        // 4. The "virtual constructor" 虚拟构造器-通过传入的字符串名称，基于原型对象创建该类型的实例对象
        for (Prototype p : prototypes) {
            if (p.getName().equals(name)) {
                return p.clone();
            }
        }
        System.out.println(name + ": doesn't exist");
        return null;
    }
}