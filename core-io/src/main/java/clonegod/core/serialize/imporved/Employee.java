package clonegod.core.serialize.imporved;

import java.io.Serializable;

class Employee implements Serializable {
    
    private static final long serialVersionUID = 1L; // 明确生成serialVersionUID，防止类发生修改后，导致反序列化失败
    private String name;
//    private int age; // 明确设置serialVersionUID后，修改类新增字段后，反序列化操作也能正确执行。
 
    public Employee(String name) {
           this.name = name;
    }
 
    @Override
    public String toString() {
           return "Employee [name=" + name + "]";
    }
 
}