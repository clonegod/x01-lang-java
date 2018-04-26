package bhz.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bhz.entity.Pay;
import bhz.mapper.PayMapper;

@Service("payService")
public class PayService {

	@Autowired
	private PayMapper payMapper;
	
	public Pay selectByPrimaryKey(String userid) throws Exception {
		return this.payMapper.selectByPrimaryKey(userid);
	}
	
	public int insert(Pay record) throws Exception {
		return this.payMapper.insert(record);
	}
	
	public int updateByPrimaryKey(Pay record) throws Exception {
		return this.payMapper.updateByPrimaryKey(record);
	}
	
	/**
	 * 标识事务注解 @Transactional
	 *  最好将同一个业务操作涉及到的数据库写操作放到一个事务方法中进行。
	 *  如果分散到多个方法中执行，会导致事务整体性被破坏！
	 *  
	 * @throws Exception
	 */
	@Transactional
	public void updateAmount(Pay record, String mode, double money) throws Exception{
		if("IN".equals(mode)){
			record.setAmount(record.getAmount() + Math.abs(money));
		} else if("OUT".equals(mode)){
			record.setAmount(record.getAmount() - Math.abs(money));
		}
		record.setUpdateTime(new Date());
		this.updateByPrimaryKey(record);
		//失败测试：
		//int a = 1/0;
	}
	
	public void updateDetail(Pay record, String detail) throws Exception {
		record.setDetail(detail);
		this.updateByPrimaryKey(record);
	}
	
	
	
	
	
	
	
	
	

	
}
