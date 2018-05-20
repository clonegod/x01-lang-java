package clonegod.core.serialize;

import java.io.Serializable;

public class Employee implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
 
    public Employee(Integer id, String name) {
           this.id = id;
           this.name = name;
    }
 
    @Override
    public String toString() {
           return "Employee [id=" + id + ", name=" + name + "]";
    }
 
}