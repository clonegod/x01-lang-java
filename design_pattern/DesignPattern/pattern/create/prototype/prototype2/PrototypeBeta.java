package prototype2;
class PrototypeBeta implements Prototype {
    private String name = "BetaVersion";

    /**
     * All classes relate themselves to the clone() contract
     */
    @Override
    public Prototype clone() {
        return new PrototypeBeta(); // 内部通过new操作符创建对象---对客户端隐藏
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