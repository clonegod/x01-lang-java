package bhz.mq;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.producer.LocalTransactionExecuter;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.common.message.Message;

import bhz.entity.Pay;
import bhz.service.PayService;
import bhz.util.FastJsonConvert;


/**
 * 执行本地事务，由客户端回调
 */

//@Scope("prototype")
@Component("transactionExecuterImpl")
public class LocalTransactionExecuterImpl implements LocalTransactionExecuter {
   
	@Autowired
	private PayService payService;
	
	/**
	 * 执行本地事务
	 */
	@Override
	public LocalTransactionState executeLocalTransactionBranch(Message msg, Object arg) {
		try {
			//Message Body
			JSONObject messageBody = FastJsonConvert.convertJSONToObject(new String(msg.getBody(), "utf-8"), JSONObject.class);
			//Transaction MapArgs
			Map<String, Object> mapArgs = (Map<String, Object>) arg;
			
			// --------------------IN PUT---------------------- //
			System.out.println("message body = " + messageBody);
			System.out.println("message mapArgs = " + mapArgs);
			System.out.println("message tag = " + msg.getTags());
			// --------------------IN PUT---------------------- //
			
			String userid = messageBody.getString("userid");
			double money = messageBody.getDouble("money");
			String pay_mode = messageBody.getString("pay_mode");
			Pay pay = this.payService.selectByPrimaryKey(userid);
			
			// 开始执行本地事务，持久化数据
			this.payService.updateAmount(pay, pay_mode, money);
			
			//成功通知MQ消息变更 该消息变为：<确认发送>
			return LocalTransactionState.COMMIT_MESSAGE;
			
			/**
			 * 返回UNKNOW - 模拟确认消息发送失败，此时broker上的未确认的事务消息将得不到确认。
			 * 注意：真正程序里是不用返回UNKNOW状态的，该状态是在broker上对事务消息的一个状态描述-即不知道本地事务执行成功还是失败了---因此broker要进行“未决事务回查”
			 */
//			return LocalTransactionState.UNKNOW; 
			
		} catch (Exception e) {
			e.printStackTrace();
			//失败则不通知MQ 该消息一直处于：<暂缓发送>
			return LocalTransactionState.ROLLBACK_MESSAGE;
			
		}
		
	}
}