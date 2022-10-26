package distribuido.cliente;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Scanner;

import distribuido.compromisso.Alerta;
import distribuido.compromisso.Compromisso;
import distribuido.server.InterfaceServer;

public class ClientImpl extends UnicastRemoteObject implements InterfaceCli {

	private InterfaceServer referenciaServidor;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private PublicKey publicKeyServer;

	public PublicKey getPublicKey() {
		return publicKeyServer;
	}

	public void setPublicKey(PublicKey publicKeyServer) {
		this.publicKeyServer = publicKeyServer;
	}

	protected ClientImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public ClientImpl(InterfaceServer referenciaServidor) throws RemoteException {
		this.referenciaServidor = referenciaServidor;
	}

	public String notificar(byte[] compromisso, byte[] signature) throws RemoteException {
		try {

			Signature sig = Signature.getInstance("DSA");
			sig.initVerify(publicKeyServer);
			sig.update(compromisso);

			if (sig.verify(signature)) {
				System.out.println("Assinatura Correta");
			} else {
				System.out.println("Assinatura Errada");
				return null;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		Scanner ler = new Scanner(System.in);
		int escolha;
		String line;
		System.out.println("Deseja participar do compromisso? 0- para nao ");
		System.out.println(compromisso);
		escolha = ler.nextInt();
		if (escolha == 0)
			return null;
		System.out.println("Horario do alerta: (hh-mm-ss)");
		line = ler.next();
		return line;
	}

	public String alerta(Compromisso compromisso) throws RemoteException {
		System.out.println("ALERTA DE COMPROMISSO:");
		System.out.println(compromisso);
		return null;
	}

}
