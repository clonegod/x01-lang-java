package bhz.service;

import java.util.ConcurrentModificationException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bhz.entity.Balance;
import bhz.mapper.BalanceMapper;

@Service
public class BalanceService {

	@Autowired
	private BalanceMapper balanceMapper;
	
	public int insert(Balance record){
		return this.balanceMapper.insert(record);
	}
	
	public Balance selectByPrimaryKey(String userid){
		return this.balanceMapper.selectByPrimaryKey(userid);
	}
	
	public int updateByPrimaryKey(Balance record){
		return this.balanceMapper.updateByPrimaryKey(record);
	}
	
	/**
	 * 这里存在多线程并发更新问题，如果同一个用户有两条消息同时被消费，就会出现更新丢失的问题！！！
	 *  解决办法：使用乐观锁，更新时基于版本号更新
	 *  
	 * @param userid
	 * @param mode
	 * @param money
	 * @throws Exception
	 */
	@Transactional
	public void updateAmount(String userid, String mode, double money) throws Exception{
		try {
			Balance balance = this.selectByPrimaryKey(userid);
			if("IN".equals(mode)){
				balance.setAmount(balance.getAmount() + Math.abs(money));
			} else if("OUT".equals(mode)){
				balance.setAmount(balance.getAmount() - Math.abs(money));
			}
			balance.setUpdateTime(new Date());
			System.out.println(Thread.currentThread().getName() + " execute update...");
			int count = this.updateByPrimaryKey(balance);
			if(count == 0) {
				throw new ConcurrentModificationException("出现多线程并发更新，请重试");
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
}
