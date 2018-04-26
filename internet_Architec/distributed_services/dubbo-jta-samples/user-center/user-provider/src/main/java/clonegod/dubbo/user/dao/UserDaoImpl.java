package clonegod.dubbo.user.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import clonegod.dubbo.user.dto.DebitRequest;

@Repository
public class UserDaoImpl implements UserDao{

    @Autowired
    JdbcTemplate userJdbcTemplate;

	@Override
	public void updateUser(DebitRequest debitReq) {
		userJdbcTemplate.update("update t_user set balance=balance-? where id = 1", debitReq.getDebit());
	}
}
