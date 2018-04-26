package clonegod.dubbo.order.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderDaoImpl implements IOrderDao{
	
    @Autowired
    JdbcTemplate orderJdbcTemplate;

    @Override
    public void insertOrder() {
        orderJdbcTemplate.execute("insert t_order(status,price,create_time) values('1', 10, now())");
    }
}
