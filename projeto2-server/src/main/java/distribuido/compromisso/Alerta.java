package distribuido.compromisso;

import java.io.Serializable;

public class Alerta implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nome;
	private String horaAlerta;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getHoraAlerta() {
		return horaAlerta;
	}

	public void setHoraAlerta(String horaAlerta) {
		this.horaAlerta = horaAlerta;
	}

	@Override
	public String toString() {
		return "Alerta [nome=" + nome + ", horaAlerta=" + horaAlerta + "]";
	}
}
