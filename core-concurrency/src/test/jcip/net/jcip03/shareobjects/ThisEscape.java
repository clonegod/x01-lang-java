package net.jcip03.shareobjects;
/**
 * ThisEscape
 * <p/>
 * Implicitly allowing the this reference to escape----------不要在构造函数中发布对象，会造成this引用逸出
 *
 * @author Brian Goetz and Tim Peierls
 */
public class ThisEscape {
    public ThisEscape(EventSource source) {
        source.registerListener(new EventListener() {
            public void onEvent(Event e) {
                doSomething(e);
            }
        });
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
