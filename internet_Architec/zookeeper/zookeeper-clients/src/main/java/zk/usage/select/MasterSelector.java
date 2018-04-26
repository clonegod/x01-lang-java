package zk.usage.select;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNodeExistsException;


public class MasterSelector {

    private ZkClient zkClient;

    private final static String MASTER_PATH="/master"; //需要争抢的节点，哪个客户端先创建成功，它就是Master

    private IZkDataListener dataListener; //注册节点内容变化

    private NodeServer node;  //当前服务器节点

    private NodeServer master;  //master节点

    private boolean isRunning=false;

    private ScheduledExecutorService scheduledExecutorService= Executors.newSingleThreadScheduledExecutor();

    public MasterSelector(NodeServer node,ZkClient zkClient) {
        this.node = node;
        this.zkClient=zkClient;

        this.dataListener= new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
            	// master selector not care this event!
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                //当master节点被删除时， 发起选主操作 --- 删除动作，是为了模拟master发生故障。
            	System.out.println("[NodeDeleted]: " + s);
                chooseMaster();
            }
        };
    }

    /**
     * 开始选举
     */
    public void start(){
        if(!isRunning){
            isRunning=true;
            zkClient.subscribeDataChanges(MASTER_PATH,dataListener); //注册节点事件
            chooseMaster();
        }
    }


    /**
     * 停止选举
     */
    public void stop(){
        if(isRunning){
            isRunning=false;
            scheduledExecutorService.shutdown();
            zkClient.unsubscribeDataChanges(MASTER_PATH,dataListener);
            releaseMaster();
        }
    }


    /**
     * 选master的实现逻辑
     */
    private void chooseMaster(){
        if(!isRunning){
            System.out.println(node + "当前服务没有启动");
            return ;
        }
        System.out.println("\n-----------------------------");
        System.out.println("["+node+"] 去争抢master权限");
        try {
            zkClient.createEphemeral(MASTER_PATH, node);
            this.master=node; //把node节点赋值给master
            System.err.println(node+"->我现在已经是master，你们要听我的");

            //定时器，对master进行释放(模拟 master 出现故障）
            scheduledExecutorService.schedule(()->{
                releaseMaster();//释放锁
            },2, TimeUnit.SECONDS);
        }catch (ZkNodeExistsException e){
            //表示master已经存在
        	NodeServer master=zkClient.readData(MASTER_PATH,true);
            if(master==null) {
                System.out.println(node + "：没有Master了，启动选主操作：");
                chooseMaster(); //再次获取master
            }else{
            	System.out.println("--->"+node + "：已有Master，退出");
                this.master=master;
            }
        }
    }

    /**
     * 释放锁(故障模拟过程-master失联/宕机)
     * 判断当前node是不是master，只有master才需要释放
     */
    private void releaseMaster(){
        if(checkIsMaster()){
            zkClient.delete(MASTER_PATH); //删除
        }
    }

    /**
     * 判断当前的node是不是master
     * @return
     */
    private boolean checkIsMaster(){
    	return master != null;
    }

}
