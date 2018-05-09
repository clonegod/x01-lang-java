package net.jcip03.shareobjects;
/**
 * SafeListener
 * <p/>
 * Using a factory method to prevent the this reference from escaping during construction
 *	使用静态工厂方法阻止this引用从构造函数中逸出
 *
 * @author Brian Goetz and Tim Peierls
 */
public class SafeListener {
    private final EventListener listener;

    private SafeListener() {
        listener = new EventListener() {
            public void onEvent(Event e) {
                doSomething(e);
            }
        };
    }
    
    /**
     * 静态工厂方法：先创建对象，待构造函数调用完成后，再发布对象出去
     */
    public static SafeListener newInstance(EventSource source) {
        SafeListener safe = new SafeListener();
        source.registerListener(safe.listener);
        return safe;
    }

    void doSomething(Event e) {
    }


    interface EventSource {
        void registerListener(EventListener e);
    }

    interface EventListener {
        void onEvent(Event e);
    }

    interface Event {
    }
}
