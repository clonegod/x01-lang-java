package clonegod.dubbo.service.provider;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserServiceProvider_Node2 {
    public static void main(String[] args) throws Exception {
    	
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] {"META-INF/spring/dubbo-service-provider-node2.xml"});
       
        context.start();
        
        System.out.println("Dubbo User Service Provider Starting On Node2...");
        System.out.println("Press Any Key To Exit.");
        
        System.in.read(); // press any key to exit
        
        context.close();
    }
}