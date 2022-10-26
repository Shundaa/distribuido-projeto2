package distribuido;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import distribuido.cliente.ClientImpl;
import distribuido.compromisso.Compromisso;
import distribuido.server.InterfaceServer;

public class Cliente {

	public static void main(String[] args) throws RemoteException, NotBoundException, AlreadyBoundException {

		System.out.println("Hello World Cliente");
		Scanner ler = new Scanner(System.in);
		Registry referenciaServicoNomes = LocateRegistry.getRegistry();
		InterfaceServer referenciaServidor = (InterfaceServer)
				referenciaServicoNomes.lookup("HelloWord");
		
		ClientImpl cliente = new ClientImpl(referenciaServidor);
		System.out.println();
		String line;
		int escolha;
		Compromisso compromisso = new Compromisso();
		System.out.println("Nome Anfitriao");
		String nome = ler.next();
		PublicKey publicKey = referenciaServidor.registrarNovoUsuario(nome, cliente);
		cliente.setPublicKey(publicKey);
		while(true) {

			System.out.println("Cadastrar novo compromisso = 1");
			System.out.println("Cancelar compromisso/alerta = 2");
			System.out.println("Consultar compromissos do dia = 3");
			System.out.println("Esperar alerta = 4");
			escolha = ler.nextInt();
			if(escolha==1) {
				System.out.println("Nome do compromisso");
				line = ler.next();
				compromisso.setNomeCompromisso(line);
				System.out.println("Data do compromisso (xx-xx-xx)");
				line = ler.next();
				compromisso.setDataCompromisso(line);
				System.out.println("Hora do compromisso (hh-mm-ss)");
				line = ler.next();
				compromisso.setHorarioCompromisso(line);
				compromisso.setAnfitriao(nome);
				System.out.println("Nome dos convidados, digite 0 para sair:");
				List<String> convidados = new ArrayList<String>();
				do{
					line = ler.next();
					if(!line.equals("0"))
						convidados.add(line);
				}while(!line.equals("0"));
				compromisso.setConvidados(convidados);
				referenciaServidor.cadastrarCompomiso(compromisso);
			}
			else if(escolha==2) {
				System.out.println("Nome do compromisso");
				line = ler.next();
				referenciaServidor.cancelarCompromissoAlerta(nome, line);
			}
			else if(escolha==3) {
				System.out.println("Data do compromisso (xx-xx-xx)");
				line = ler.next();
				System.out.println(referenciaServidor.consultaCompromissoDia(line));
			}
			else {
				try {
					Thread.sleep(60000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
}
