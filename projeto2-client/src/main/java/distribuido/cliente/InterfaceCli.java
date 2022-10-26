package distribuido.cliente;

import java.rmi.Remote;
import java.rmi.RemoteException;

import distribuido.compromisso.Compromisso;

public interface InterfaceCli extends Remote{
	String notificar(byte[] compromisso,byte[] signature) throws RemoteException;
	String alerta(Compromisso compromisso) throws RemoteException;
}
