package prototype2;

/**
 * 1. Create a "contract" with clone() and getName() entries
 */
// 1. The clone() contract - 原型克隆的契约
interface Prototype {
	/**
	 * 通过原型对象克隆得到新的对象
	 * */
    Prototype clone();
    
    /**
     * 原型对象具有的属性
     * @return
     */
    String getName();
    
    /**
     * 原型对象具有的行为
     */
    void execute();
}