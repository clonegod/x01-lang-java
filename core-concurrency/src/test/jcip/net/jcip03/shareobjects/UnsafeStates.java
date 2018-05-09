package net.jcip03.shareobjects;
/**
 * UnsafeStates
 * <p/>
 * Allowing internal mutable state to escape----内部私有状态被错误发布
 *
 * @author Brian Goetz and Tim Peierls
 */
class UnsafeStates {
    private String[] states = new String[]{
        "AK", "AL" /*...*/
    };

    public String[] getStates() {
        return states;
    }
}