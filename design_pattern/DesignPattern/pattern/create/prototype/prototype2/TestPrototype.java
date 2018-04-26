package prototype2;

import java.util.ArrayList;
import java.util.List;

public class TestPrototype {
	
    public static void main(String[] args) {
    	
    	args = "Garbage AlphaVersion BetaVersion Nothing ReleaseCandidate".split("\\s+");
    	
        if (args.length > 0) {
            initializePrototypes();
            List<Prototype> prototypes = new ArrayList<>();
            /**
             * 6. Client uses the findAndClone() virtual constructor instead of the "new" operator
             */
            // 6. Client does not use "new"	客户端不再通过new创建对象，而是通过原型工厂获取新的克隆对象
            for (String protoName : args) {
                Prototype prototype = PrototypeModule.createPrototype(protoName);
                if (prototype != null) {
                    prototypes.add(prototype);
                }
            }
            // 基于原型模式，得到一批克隆对象，调用克隆对象上的方法
            for (Prototype p : prototypes) {
                p.execute();
            }
        } else {
            System.out.println("Run again with arguments of command string ");
        }
    }

    /**
     * 3. Populate the registry with an initializePrototypes() function
     */
    // 3. Populate the "registry"  发布注册原型实例对象
    public static void initializePrototypes() {
        PrototypeModule.addPrototype(new PrototypeAlpha());
        PrototypeModule.addPrototype(new PrototypeBeta());
        PrototypeModule.addPrototype(new ReleasePrototype());
    }
}