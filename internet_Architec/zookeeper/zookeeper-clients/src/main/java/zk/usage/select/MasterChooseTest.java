package zk.usage.select;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;

/**
 * 腾讯课堂搜索 咕泡学院
 * 加群获取视频：608583947
 * 风骚的Michael 老师
 */
public class MasterChooseTest {

    private final static String CONNECTSTRING="192.168.1.201:2181,192.168.1.202:2181,192.168.1.203:2181";

    /**
     * 测试说明：
     * 	已被选为master的节点，会在2秒后被定时任务从zookeeper上删除-模拟master故障后的重新选主操作。
     */
    public static void main(String[] args) throws IOException {
        List<MasterSelector> selectorLists=new ArrayList<>();
        try {
        	// 每隔1秒加入1个新的节点，进行选主。
            for(int i=0;i<10;i++) {
                ZkClient zkClient = new ZkClient(CONNECTSTRING, 5000,
                        5000,
                        new SerializableSerializer());
                NodeServer  node = new NodeServer();
                node.setNodeID(i);
                node.setNodeName("客户端：" + i);

                MasterSelector selector = new MasterSelector(node,zkClient);
                selectorLists.add(selector);
                selector.start();//触发选举操作
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            for(MasterSelector selector:selectorLists){
                selector.stop();
            }
        }
    }
}
