package distribuido;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;

import javax.print.attribute.standard.DateTimeAtCompleted;

import distribuido.compromisso.Alerta;
import distribuido.compromisso.Compromisso;
import distribuido.server.ServerImpl;

public class Server {

	static ServerImpl registerUser;

	public static void main(String[] args) throws RemoteException {
		registerUser = new ServerImpl();
		System.out.println("Hello World Server");
		Registry referenciaServicoNomes = LocateRegistry.createRegistry(1099);

		try {
			referenciaServicoNomes.bind("HelloWord", registerUser);
		} catch (Exception e) {
			e.printStackTrace();
		}

		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(550);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (registerUser != null) {
					while (true) {
						HashMap<String, Compromisso> compromissoList = registerUser.getCompromissoList();
						if (compromissoList != null) {
							for (Entry<String, Compromisso> pair : compromissoList.entrySet()) {
								List<Alerta> alertas = pair.getValue().getAlertas();
								if (alertas != null)
									for (int i = 0; i < alertas.size(); i++) {
										if (alertas.get(i).getHoraAlerta()
												.equals(LocalDateTime.now().getHour() + "-"
														+ LocalDateTime.now().getMinute() + "-"
														+ LocalDateTime.now().getSecond())) {
											try {
												registerUser.enviarNotificacaoAlerta(alertas.get(i).getNome(),
														pair.getValue());
											} catch (RemoteException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}
									}
							}
						}
					}
				}

			}
		}.start();

		while (true) {
			String nomeCompromisso;
			System.out.println("Simular envio de notificacao? 1 = sim");
			Scanner ler = new Scanner(System.in);
			if (ler.nextInt() == 1) {
				System.out.println("Nome do compromisso?");
				nomeCompromisso = ler.next();
				HashMap<String, Compromisso> compromissoList = registerUser.getCompromissoList();
				Compromisso compromisso = compromissoList.get(nomeCompromisso);
				if (compromisso != null)
					for (int i = 0; i < compromisso.getAlertas().size(); i++) {
						registerUser.enviarNotificacaoAlerta(compromisso.getAlertas().get(i).getNome(), compromisso);
					}
			}
		}

	}

}
