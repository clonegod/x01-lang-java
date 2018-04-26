package clonegod.kafka.client.consumer;

public class CalcGroupOffset {
	
	public static void main(String[] args) {
		// 计算 consumer group 存放在哪个consumer_offset文件中
		String groupName = "DemoConsumer";
		int groupOffsetFileCount = 50;
		System.out.println("__consumer_offsets-" + Math.abs(groupName.hashCode() % groupOffsetFileCount));
	}
	
}
