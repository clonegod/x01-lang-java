package zk.usage.select;

import java.io.Serializable;

public class NodeServer implements Serializable{

    private static final long serialVersionUID = -1776114173857775665L;
    
    private int nodeID; //机器信息

    private String nodeName;//机器名称

	public int getNodeID() {
		return nodeID;
	}

	public void setNodeID(int nodeID) {
		this.nodeID = nodeID;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	@Override
	public String toString() {
		return "Node [nodeID=" + nodeID + ", nodeName=" + nodeName + "]";
	}

}
