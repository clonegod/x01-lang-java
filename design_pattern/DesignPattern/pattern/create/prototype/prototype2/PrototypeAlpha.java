package prototype2;
// 5. Sign-up for the clone() contract.
// Each class calls "new" on itself FOR the client.
class PrototypeAlpha implements Prototype {
    private String name = "AlphaVersion";

    /**
     * All classes relate themselves to the clone() contract
     */
    @Override
    public Prototype clone() {
        return new PrototypeAlpha(); // 内部通过new操作符创建对象---对客户端隐藏
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute() {
        System.out.println(name + ": does something");
    }
}