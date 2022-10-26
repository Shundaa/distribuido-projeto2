package distribuido.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.PublicKey;
import java.util.List;

import distribuido.cliente.InterfaceCli;
import distribuido.compromisso.Compromisso;

public interface InterfaceServer extends Remote {
	
	PublicKey registrarNovoUsuario(String nome, InterfaceCli referenciaCliente) throws RemoteException;
	void cadastrarCompomiso(Compromisso compromisso) throws RemoteException;
	void cancelarCompromissoAlerta(String nome,String nomeCompromisso) throws RemoteException;
	List<Compromisso> consultaCompromissoDia(String dataCompromisso) throws RemoteException;
}
