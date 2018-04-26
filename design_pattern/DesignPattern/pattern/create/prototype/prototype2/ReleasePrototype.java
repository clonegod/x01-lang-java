package prototype2;
class ReleasePrototype implements Prototype {
    private String name = "ReleaseCandidate";
    
    /**
     * All classes relate themselves to the clone() contract
     */
    @Override
    public Prototype clone() {
        return new ReleasePrototype(); // 内部通过new操作符创建对象---对客户端隐藏
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute() {
        System.out.println(name + ": does real work");
    }
}