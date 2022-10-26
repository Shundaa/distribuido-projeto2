package distribuido.compromisso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Compromisso implements Serializable{

	public Compromisso() {
		super();
		convidados= new ArrayList<>();
		alertas= new ArrayList<>();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String anfitriao;
	private String nomeCompromisso;
	private String dataCompromisso;
	private String horarioCompromisso;
	private List<String> convidados;
	private List<Alerta> alertas;
	
	public String getNomeCompromisso() {
		return nomeCompromisso;
	}
	public void setNomeCompromisso(String nomeCompromisso) {
		this.nomeCompromisso = nomeCompromisso;
	}
	public String getDataCompromisso() {
		return dataCompromisso;
	}
	public void setDataCompromisso(String dataCompromisso) {
		this.dataCompromisso = dataCompromisso;
	}
	public String getHorarioCompromisso() {
		return horarioCompromisso;
	}
	public void setHorarioCompromisso(String horarioCompromisso) {
		this.horarioCompromisso = horarioCompromisso;
	}
	public List<String> getConvidados() {
		return convidados;
	}
	public void setConvidados(List<String> convidados) {
		this.convidados = convidados;
	}
	
	public String getAnfitriao() {
		return anfitriao;
	}
	public void setAnfitriao(String anfitriao) {
		this.anfitriao = anfitriao;
	}
	public List<Alerta> getAlertas() {
		return alertas;
	}
	public void setAlertas(List<Alerta> alertas) {
		this.alertas = alertas;
	}
	
	@Override
	public String toString() {
		return "Compromisso [anfitriao=" + anfitriao + ", nomeCompromisso=" + nomeCompromisso + ", dataCompromisso="
				+ dataCompromisso + ", horarioCompromisso=" + horarioCompromisso + ", convidados=" + convidados
				+ ", alertas=" + alertas + "]";
	}
	
}
