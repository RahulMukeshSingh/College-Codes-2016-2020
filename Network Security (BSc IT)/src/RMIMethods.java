import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIMethods extends UnicastRemoteObject implements RMIInterface {

	public RMIMethods() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int add(int x, int y) throws RemoteException {
		// TODO Auto-generated method stub
		return x+y;
	}

	@Override
	public int sub(int x, int y) throws RemoteException {
		// TODO Auto-generated method stub
		return x-y;
	}



}
