package bhz.test;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import bhz.entity.Balance;
import bhz.mq.MQConsumer;
import bhz.service.BalanceService;

import com.alibaba.rocketmq.client.QueryResult;
import com.alibaba.rocketmq.common.message.MessageExt;

/** 
 * <br>类 名: BaseTest 
 * <br>描 述: 描述类完成的主要功能 
 * <br>作 者: bhz
 * <br>创 建： 2013年5月8日 
 * <br>版 本：v1.0.0 
 * <br>
 * <br>历 史: (版本) 作者 时间 注释
 */

@ContextConfiguration(locations = {"classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional(rollbackFor = Exception.class)
public class BaseTest {
	
	
	@Autowired
	private MQConsumer mQConsumer;
	
	@Autowired
	private BalanceService balanceService;
	
	@Test
	public void testSave() throws Exception {
		Balance balance = new Balance();
		balance.setUserid("z3");
		balance.setUsername("张三");
		balance.setAmount(5000d);
		balance.setUpdateBy("z3");
		balance.setUpdateTime(new Date());
		this.balanceService.insert(balance);
	}
	
	@Test
	public void testUpdate() throws Exception {
		Balance balance = this.balanceService.selectByPrimaryKey("z3");
		balance.setAmount(balance.getAmount() + 1000d);
		balance.setUpdateTime(new Date());
		this.balanceService.updateByPrimaryKey(balance);
	}
	
	
	@Test
	public void test1() throws Exception {
		System.out.println(this.mQConsumer);
		long end = new Date().getTime();
		long begin = end - 60 * 1000 * 60 * 24;
		QueryResult qr = this.mQConsumer.queryMessage("topic_pay", "k8", 10, begin, end);
		List<MessageExt> list = qr.getMessageList();
		for(MessageExt me : list){
			Map<String, String> m = me.getProperties();
			System.out.println(m.keySet().toString());
			System.out.println(m.values().toString());
			System.out.println(me.toString());
			System.err.println("内容: " + new String(me.getBody(), "utf-8"));
			System.out.println("Prepared :" + me.getPreparedTransactionOffset());
		}
	}

}
