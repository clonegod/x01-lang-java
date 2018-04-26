package state2.rmisever;

import java.rmi.Remote;
import java.rmi.RemoteException;

import state2.state.State;

/**
 * RMI远程服务的接口
 */
public interface GumballMachineRemote extends Remote {
	
	public int getCount() throws RemoteException;
	
	public String getLocation() throws RemoteException;
	
	public State getState() throws RemoteException;
}
