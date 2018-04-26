package bhz.test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import bhz.mq.MQProducer;
import bhz.util.FastJsonConvert;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.QueryResult;
import com.alibaba.rocketmq.client.producer.LocalTransactionExecuter;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.common.message.Message;
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
public class PayTest {
	
	
	@Autowired
	private MQProducer mQProducer;
	
	@Autowired
	private LocalTransactionExecuter localTransactionExecuterImpl;
	
	/**
	 * 事务消息发送的执行流程：
	 * 	producer调用sendMessageInTransaction发送事务消息
	 * 	1、producer发送prepared预消息到broker
	 *  2、rocketmq-client回调本地事务执行器，开始执行本地事务
	 *  3、本地事务执行器执行结束，返回成功或失败给broker。---broker根据结果更新事务消息（成功，则事务消息对consumer端可见，否则不可见）
	 *  4、sendMessageInTransaction返回消息发送结果
	 *  	注意：该方法返回的结果sendStatus=SEND_OK，仅表示producer执行了发送确认消息的操作是成功的，至于消息是否被broker成功接收则是不确定的！
	 */
	@Test
	public void testSendTransactionMsg() {
		try {
			System.out.println(this.mQProducer);
			System.out.println(this.localTransactionExecuterImpl);
			for(int i = 0; i < 2; i++) {
				//构造消息数据
				Message message = new Message();
				message.setTopic("pay");//主题
				message.setTags("tag");//子标签
				String uuid = UUID.randomUUID().toString();
				System.out.println("key: " + uuid);
				message.setKeys(uuid); //key
				JSONObject body = new JSONObject();
				body.put("userid", "z3");
				body.put("money", "1000");
				body.put("pay_mode", "OUT");
				body.put("balance_mode", "IN");
				message.setBody(FastJsonConvert.convertObjectToJSON(body).getBytes());
				
				//添加attachment参数
				Map<String, Object> transactionMapArgs = new HashMap<String, Object>();
				
				this.mQProducer.sendTransactionMessage(message, this.localTransactionExecuterImpl, transactionMapArgs);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 测试本地查询以确认消息的状态
	 * 
	 * 结论：
	 * 	在producer端根据msgId或者key查询消息，即使对prepared消息的确认发送成功了，查询得到的消息状态仍然是sysFlag=4
	 *  因此，在客户端是无法跟踪事务消息状态的。
	 *  ---可能的原因：broker端屏蔽了对事务消息状态查询的支持。
	 *  
	 * @throws Exception
	 */
	@Test
	@Ignore
	public void testCheckMsgStatus() throws Exception {
		// 根据id查询msg
		MessageExt msg = this.mQProducer.viewMessage("C0A801C900002A9F0000000000047A4D");
		System.out.println(msg);
		
		// 根据key查询msg，可查询一段时间范围内的消息
		long end = new Date().getTime();
		long begin = end - 60 * 1000 * 60 * 24;
		QueryResult qr = this.mQProducer.queryMessage("pay", "298ace79-76ed-43ce-956e-458052a6375c", 10, begin, end);
		List<MessageExt> list = qr.getMessageList();
		for(MessageExt me : list){
			
			Map<String, String> m = me.getProperties();
			System.out.println(m.keySet().toString());
			System.out.println(m.values().toString());
			System.out.println(me.toString());
			System.err.println("内容: " + new String(me.getBody(), "utf-8"));
			System.out.println("Prepared :" + me.getPreparedTransactionOffset());
			LocalTransactionState ls = this.mQProducer.check(me);
			System.out.println(ls);
		}
	}

}
