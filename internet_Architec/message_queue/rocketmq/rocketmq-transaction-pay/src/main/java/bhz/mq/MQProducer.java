package bhz.mq;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.alibaba.rocketmq.client.QueryResult;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.LocalTransactionExecuter;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.client.producer.TransactionCheckListener;
import com.alibaba.rocketmq.client.producer.TransactionMQProducer;
import com.alibaba.rocketmq.client.producer.TransactionSendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

@Component
public class MQProducer {
	
	private final String GROUP_NAME = "transaction-pay";
	private final String NAMESRV_ADDR = "192.168.1.201:9876;192.168.1.202:9876";
	private TransactionMQProducer producer;
	
	public MQProducer() {
		
		this.producer = new TransactionMQProducer(GROUP_NAME);
		this.producer.setNamesrvAddr(NAMESRV_ADDR);	//nameserver服务
		this.producer.setCheckThreadPoolMinSize(5);	// 事务回查最小并发数
		this.producer.setCheckThreadPoolMaxSize(20);	// 事务回查最大并发数
		this.producer.setCheckRequestHoldMax(2000);	// 队列数
		this.producer.setTransactionCheckListener(new TransactionCheckListener() {
			/** 
			 * 服务器回调Producer，检查本地事务分支成功还是失败 --- 开源版本砍掉了此功能，broker不会对未确认的消息进行回查。 
			 * */
			public LocalTransactionState checkLocalTransactionState(MessageExt msg) {
				System.out.println("checkLocalTransactionState -- "+ new String(msg.getBody()));
				return LocalTransactionState.COMMIT_MESSAGE;
			}
		});
		try {
			this.producer.start();
		} catch (MQClientException e) {
			e.printStackTrace();
		}	
	}
	
	// 查询broker上某个消息的状态
	public QueryResult queryMessage(String topic, String key, int maxNum, long begin, long end) throws Exception {
		return this.producer.queryMessage(topic, key, maxNum, begin, end);
	}
	
	public MessageExt viewMessage(String msgId) {
		try {
			return this.producer.viewMessage(msgId);
		} catch (RemotingException | MQBrokerException | InterruptedException | MQClientException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// --- 模拟“broker对未决事务的查询” --- 在producer端主动调用此方法查询事务执行结果
	public LocalTransactionState check(MessageExt me){
		// 调用TransactionCheckListener，检查本地事务的执行结果
		LocalTransactionState ls = this.producer.getTransactionCheckListener().checkLocalTransactionState(me);
		return ls;
	}
	
	/**
	 * 发送事务消息
	 * @param message
	 * @param localTransactionExecuter
	 * @param transactionMapArgs
	 * @throws Exception
	 */
	public void sendTransactionMessage(Message message, LocalTransactionExecuter localTransactionExecuter, Map<String, Object> transactionMapArgs) throws Exception {
		// 发送消息到broker，client回调本地事务处理器localTransactionExecuterImpl
		// localTransactionExecuterImpl本地事务执行结束后，才会返回tsr
		TransactionSendResult tsr = this.producer.sendMessageInTransaction(message, localTransactionExecuter, transactionMapArgs);
		System.out.println("send返回内容：" + tsr.toString());
	}
	
	
	/**
	 * 关闭producer，释放资源
	 */
	public void shutdown(){
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				producer.shutdown();
			}
		}));
		System.exit(0);
	}


}