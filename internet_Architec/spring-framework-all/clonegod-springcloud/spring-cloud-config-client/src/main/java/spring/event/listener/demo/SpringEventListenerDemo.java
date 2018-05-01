package spring.event.listener.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringEventListenerDemo {
	
	public static void main(String[] args) {
		// Annotation 驱动的 spring 上下文
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		
		// 注册监听器
		context.addApplicationListener(new ApplicationListener<MyApplicationEvent>() {
			// 监听器得到事件通知
			@Override
			public void onApplicationEvent(MyApplicationEvent event) {
				Object eventSource = event.getSource();
				long eventTime = event.getTimestamp();
				ApplicationContext appContext = event.getApplicationContext();
				System.out.printf("[Thread - %s] 接收到事件：%s, \n\t 事件发生时间：%s，\n\t 应用程序上下文：%s\n", 
						Thread.currentThread().getName(), 
						eventSource, 
						eventTime,
						appContext);
			}
		});
		
		// ApplicationEventMulticaster not initialized - call 'refresh' before multicasting events via the context
		// 先刷新context，才能发布事件
		context.refresh();
		
		// 发布事件
		context.publishEvent(new MyApplicationEvent(context, "Hello,World"));
		context.publishEvent(new MyApplicationEvent(context, 1));
		context.publishEvent(new MyApplicationEvent(context, new Integer(100)));
	
		
		context.close();
	}
	
	// ApplicationListener<E extends ApplicationEvent> 要求传入的事件对象必须是ApplicationEvent类型的
	private static class MyApplicationEvent extends ApplicationEvent {

		private final ApplicationContext context;

		public MyApplicationEvent(ApplicationContext context, Object source) {
			super(source);
			this.context = context;
		}
		
		public ApplicationContext getApplicationContext() {
			return this.context;
		}
		
	}

}
