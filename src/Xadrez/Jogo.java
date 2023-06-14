package Xadrez;
import java.util.ArrayList;

public class Jogo {
	private String listaLances;
	private Tabuleiro tabuleiro;
	private Jogador jogador1;
	private Jogador jogador2;
	private int numeroLance;
	private String turno;
	
	public Jogo(Jogador jogador2, Tabuleiro tabuleiro) {
		this.listaLances = "";
		//this.jogador1 = new JogadorHumano();
		this.jogador2 = jogador2;
		this.numeroLance = 1;
		this.turno = "branco";
		this.tabuleiro = tabuleiro;
		tabuleiro.setJogo(this);
		atualizaLancesECapturas();
	}
	
	//getters e setters:
	public String getListaLances() {
		return listaLances;
	}

	public void setListaLances(String listaLances) {
		this.listaLances = listaLances;
	}

	public Tabuleiro getTabuleiro() {
		return tabuleiro;
	}

	public void setTabuleiro(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
	}

	public Jogador getJogador1() {
		return jogador1;
	}

	public void setJogador1(Jogador jogador1) {
		this.jogador1 = jogador1;
	}

	public Jogador getJogador2() {
		return jogador2;
	}

	public void setJogador2(Jogador jogador2) {
		this.jogador2 = jogador2;
	}

	public int getNumeroLance() {
		return numeroLance;
	}

	public void setNumeroLance(int numeroLance) {
		this.numeroLance = numeroLance;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}
	
	//Método que verifica se um lance é válido:
	public boolean validaLance(Peca peca, Casa casaDestino) {
		if(peca.getCor().equals(turno) && peca.getLancesPossiveis().contains(casaDestino)) {
			return true;
		}
		return false;
	}
	
	//Método que adiciona um lance na lista de lances:
	public void adicionaLance(Peca peca, Casa casaDestino) {
		if(peca.getCor().equals("branco")) {
			Lance.escreveLance(peca, casaDestino, numeroLance);
		} else {
			Lance.escreveLance(peca, casaDestino);
			numeroLance++;
		}
	}
	
	//Método que verifica se o jogador de turno ativo está em xeque:
	public boolean verificaXeque() {
		if(turno.equals("branco")) {
			for(Peca peca : jogador2.getCapturasPossiveis()) {
				if(peca instanceof Rei) return true;
			}
		}	else	{
			for(Peca peca : jogador1.getCapturasPossiveis()) {
				if(peca instanceof Rei) return true;
			}
		}
		return false;
	}
	
	public void atualizaLancesECapturas() {
		for (int l = 0; l < 8; l++) {
		    for (int c = 0; c < 8; c++) {
		    	try {
		    		Tabuleiro.getTabuleiro()[l][c].getPeca().lancesValidos();
		    	} catch(NullPointerException e) {}
		    }
		}
	}
	
	//
	public void finalizaTurno() {
		atualizaLancesECapturas();
		String novoTurno = (turno.equals("branco")) ? "preto" : "branco";
		setTurno(novoTurno);
	}

	
	
	//Método que verifica se o jogo acabou:
	/*public boolean verificaFim() {
		//Xeque-Mate:
		if(turno.equals("preto")) {
			
		}
		//Afogamento:
		//Material insuficiente:
	}*/
	
	
	
	
	
	
}
