package distribuido.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import distribuido.cliente.InterfaceCli;
import distribuido.compromisso.Alerta;
import distribuido.compromisso.Compromisso;

public class ServerImpl extends UnicastRemoteObject implements InterfaceServer {

	public HashMap<String, InterfaceCli> clientesList;
	public HashMap<String, Compromisso> compromissoList;

	public HashMap<String, InterfaceCli> getClientesList() {
		return clientesList;
	}

	public void setClientesList(HashMap<String, InterfaceCli> clientesList) {
		this.clientesList = clientesList;
	}

	public HashMap<String, Compromisso> getCompromissoList() {
		return compromissoList;
	}

	public void setCompromissoList(HashMap<String, Compromisso> compromissoList) {
		this.compromissoList = compromissoList;
	}

	private KeyPair pair;
	private Signature sign;
	private PrivateKey priKey;
	private PublicKey pubKey;
	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServerImpl() throws RemoteException {
		super();
		clientesList = new HashMap<String, InterfaceCli>();
		compromissoList = new HashMap<String, Compromisso>();

		KeyPairGenerator kpg;
		try {
			kpg = KeyPairGenerator.getInstance("DSA");
			SecureRandom secureRan = new SecureRandom();
			kpg.initialize(512, secureRan);
			KeyPair kp = kpg.generateKeyPair();
			pubKey = kp.getPublic();
			priKey = kp.getPrivate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public PublicKey registrarNovoUsuario(String nome, InterfaceCli referenciaCliente) throws RemoteException {
		clientesList.put(nome, referenciaCliente);
		System.out.println("Novo Usuario Cadastrado com sucesso: " + nome);
		return pubKey;
	}

	public void cadastrarCompomiso(Compromisso compromisso) throws RemoteException {
		compromissoList.put(compromisso.getNomeCompromisso(), compromisso);
		InterfaceCli referenciaCliente;
		String horarioAlerta;
		for (String convidado : compromisso.getConvidados()) {
			referenciaCliente = clientesList.get(convidado);
			if (referenciaCliente != null) {
				byte[] assinatura = null;
				try {
					Signature signature = Signature.getInstance("DSA");
					signature.initSign(priKey);
					signature.update(compromisso.toString().getBytes());
					assinatura = signature.sign();

				} catch (Exception e) {
					e.printStackTrace();
				}
				horarioAlerta = referenciaCliente.notificar(compromisso.toString().getBytes(), assinatura);
				Alerta alerta = new Alerta();
				alerta.setHoraAlerta(horarioAlerta);
				alerta.setNome(convidado);
				compromisso.getAlertas().add(alerta);
			}
		}

		System.out.println("Novo Compromisso Cadastrado com sucesso: " + compromisso);
		return;
	}

	public void cancelarCompromissoAlerta(String nome, String nomeCompromisso) throws RemoteException {
		Compromisso compromisso = compromissoList.get(nomeCompromisso);
		if (compromisso.getAnfitriao().equals(nome)) {
			compromissoList.remove(nomeCompromisso);
		} else {
			compromisso.getAlertas().removeIf(cliente -> (cliente.getNome().equals(nome)));
		}

		System.out.println("Compromisso/alerta Cancelado com sucesso: " + nome);
		return;
	}

	public List<Compromisso> consultaCompromissoDia(String dataCompromisso) throws RemoteException {
		List<Compromisso> compromissosDoDia = new ArrayList<>();
		for (Entry<String, Compromisso> pair : compromissoList.entrySet()) {
			if (pair.getValue().getDataCompromisso().equalsIgnoreCase(dataCompromisso)) {
				compromissosDoDia.add(pair.getValue());
			}
		}
		return compromissosDoDia;
	}

	public void enviarNotificacaoAlerta(String nome, Compromisso compromisso) throws RemoteException {
		InterfaceCli referenciaCliente;
		referenciaCliente = clientesList.get(nome);
		if (referenciaCliente != null) {
			referenciaCliente.alerta(compromisso);
		}
		return;
	}

}
