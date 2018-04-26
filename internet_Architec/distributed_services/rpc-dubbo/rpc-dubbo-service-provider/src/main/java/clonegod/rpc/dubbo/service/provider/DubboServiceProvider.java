package clonegod.rpc.dubbo.service.provider;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboServiceProvider {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] {"META-INF/spring/dubbo-demo-provider.xml"});
       
        context.start();
        
        System.out.println("Dubbo Service Provider Starting...");
        System.out.println("Press Any Key To Exit.");
        
        System.in.read(); // press any key to exit
        
        context.close();
    }
}