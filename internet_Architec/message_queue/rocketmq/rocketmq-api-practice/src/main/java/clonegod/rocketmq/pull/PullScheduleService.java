package clonegod.rocketmq.pull;

import com.alibaba.rocketmq.client.consumer.MQPullConsumer;
import com.alibaba.rocketmq.client.consumer.MQPullConsumerScheduleService;
import com.alibaba.rocketmq.client.consumer.PullResult;
import com.alibaba.rocketmq.client.consumer.PullTaskCallback;
import com.alibaba.rocketmq.client.consumer.PullTaskContext;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.message.MessageQueue;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;

/**
 * ConsumerScheduleService提供更好的消息拉取实现
 * 	1、 rocketmq-client内部维护消息偏移量，定时刷新到broker上（consumer端不需要维护下一次拉取消息的偏移量，使用更方便）
 *	2、pull模式的消息消费，逻辑都在consumer端维护，consumer通过offset来获取指定队列上的数据（broker根据消息的offset来判断需要返回哪些消息）
 *  3、对于批量拉取的消息，如果某条消息处理失败，那么必须记录处理失败消息所在的队列、偏移量等相关信息到日志/数据库，之后再重新从broker上拉取再次消费。
 *  4、批量消息适合于：业务对消息的实时消费要求不高，特点是非实时消费
 *  5、异常消息的处理-记录日志到数据库；对异常消息进行重试消费
 */
public class PullScheduleService {

    public static void main(String[] args) throws MQClientException {
    	String groupName = "schedule_consumer";
        final MQPullConsumerScheduleService scheduleService = new MQPullConsumerScheduleService(groupName);
        scheduleService.getDefaultMQPullConsumer().setNamesrvAddr("192.168.1.201:9876;192.168.1.202:9876");
        
        //scheduleService.setPullThreadNums(5);
        scheduleService.setMessageModel(MessageModel.CLUSTERING);
        
        scheduleService.registerPullTaskCallback("TopicSchedulePull", new PullTaskCallback() {
            @Override
            public void doPullTask(MessageQueue mq, PullTaskContext context) {
                MQPullConsumer consumer = context.getPullConsumer();
                try {
                    // 获取从哪里拉取
                    long offset = consumer.fetchConsumeOffset(mq, false);
                    if (offset < 0)
                        offset = 0;
                    
                    // 从队列拉取消息
                    PullResult pullResult = consumer.pull(mq, "*", offset, 32);
                    //System.out.println(offset + "\t" + mq + "\t" + pullResult);
                    switch (pullResult.getPullStatus()) {
	                    case FOUND:
	                    	consumeMsg(mq, offset, pullResult);
	                        break;
	                    case NO_MATCHED_MSG:
	                        break;
	                    case NO_NEW_MSG:
	                    	System.out.println(Thread.currentThread().getName() + ": 没有新的消息");
	                    case OFFSET_ILLEGAL:
	                        break;
	                    default:
	                        break;
                    }

                    /** 存储Offset，客户端每隔5s会定时刷新到Broker */
                    consumer.updateConsumeOffset(mq, pullResult.getNextBeginOffset());

                    /** 设置再过6000ms后重新拉取 */
                    // 客户端每5s更新一次偏移量到broker上，所以，consumer下次拉取的时间间隔应该大于5s
                    context.setPullNextDelayTimeMillis(5000 + 100);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        scheduleService.start();
    }
    
	/**
     *  消费消息
     *   - 异常处理：批量拉取消息，如果某条消息处理发生异常，则记录日志到数据库，继续处理后面的消息
     * @param pullResult
     */
    protected static void consumeMsg(MessageQueue mq, long offset, PullResult pullResult) {
    	for(MessageExt msg : pullResult.getMsgFoundList()) {
        	try {
        		String msgBody = new String(msg.getBody());
        		System.out.println(Thread.currentThread().getName() + ": " + msgBody);
        		if(msgBody.split("@")[1].matches("[13]")) {
        			throw new RuntimeException("处理消息发生异常");
        		}
        	}catch(Exception e) {
        		System.err.println(String.format("消费失败，记录消息到数据库：queueid=%s, offset=%s, pullstatus=%s", 
        				mq.getQueueId(), offset, pullResult.getPullStatus()));
        		continue; // 继续处理其它消息
        	}
    	}
	}
    
    static class FailedMsg {
    	MessageQueue mq;
    	long offset;
    	
		public FailedMsg(MessageQueue mq, long offset) {
			super();
			this.mq = mq;
			this.offset = offset;
		}
		public MessageQueue getMq() {
			return mq;
		}
		public long getOffset() {
			return offset;
		}
    }

}
