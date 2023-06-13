package Xadrez;
import java.util.ArrayList;

public class Jogo {
	private String listaLances;
	private Tabuleiro tabuleiro;
	private Jogador jogador1;
	private Jogador jogador2;
	private int numeroLance;
	private int turno;
	
	public Jogo(Jogador jogador2) {
		this.listaLances = "";
		this.tabuleiro = new Tabuleiro();
		//this.jogador1 = new JogadorHumano();
		this.jogador2 = jogador2;
		this.numeroLance = 1;
		this.turno = 0;
	}
	
	//Método que verifica se um lance é válido:
	public boolean validaLance(Peca peca, Casa casaDestino) {
		if(turno == 0 && peca.getCor().equals("branco") && peca.getLancesPossiveis().contains(casaDestino)) {
			return true;
		} else if(turno == 1 && peca.getCor().equals("preto") && peca.getLancesPossiveis().contains(casaDestino)) {
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
		if(turno == 0) {
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
	
	/*Método que verifica se o jogo acabou:
	public boolean verificaFim() {
		//Xeque-Mate:
		//Afogamento:
		//Material insuficiente:
	}*/
	
	
	
	
	
	
}
