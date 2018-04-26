package bhz.mq;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import bhz.service.BalanceService;
import bhz.util.FastJsonConvert;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.QueryResult;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;

@Component
public class MQConsumer {
	
	private final String GROUP_NAME = "transaction-balance";
	private final String NAMESRV_ADDR = "192.168.1.201:9876;192.168.1.202:9876";
	private DefaultMQPushConsumer consumer;
	
	@Autowired
	private BalanceService balanceService;
	
	
	public MQConsumer() {
		try {
			this.consumer = new DefaultMQPushConsumer(GROUP_NAME);
			this.consumer.setNamesrvAddr(NAMESRV_ADDR);
			this.consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
			this.consumer.subscribe("pay", "*");
			this.consumer.registerMessageListener(new Listener());
			this.consumer.start();
			System.out.println("consumer start");
			System.out.println(balanceService);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public QueryResult queryMessage(String topic, String key, int maxNum, long begin, long end) throws Exception {
		return this.consumer.queryMessage(topic, key, maxNum, begin, end);
	}
	
	class Listener implements MessageListenerConcurrently {
		public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
			MessageExt msg = msgs.get(0);
			try {
				String topic = msg.getTopic();
				//Message Body
				JSONObject messageBody = FastJsonConvert.convertJSONToObject(new String(msg.getBody(), "utf-8"), JSONObject.class);
				String tags = msg.getTags();
				String keys = msg.getKeys();
				
				System.out.println("balance服务收到消息, keys : " + keys + ", body : " + new String(msg.getBody(), "utf-8"));
				String userid = messageBody.getString("userid");
				double money = messageBody.getDoubleValue("money");
				String balance_mode = messageBody.getString("balance_mode");
				//业务逻辑处理
				while(balanceService == null) {
					System.out.println("ApplicationContext未初始化完成，consumer就开始接收到消息，此时balanceService可能就是null");
					Thread.sleep(100);
				}
				Assert.notNull(balanceService, "Spring还没有完成对balanceService的实例化！");
				balanceService.updateAmount(userid, balance_mode, money);
				
			} catch (Exception e) {	
				e.printStackTrace();
				//重试次数为3情况 
				if(msg.getReconsumeTimes() == 3){
					//记录日志
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				}
				return ConsumeConcurrentlyStatus.RECONSUME_LATER;
			}			
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
	}

}
