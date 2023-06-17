package Xadrez;
import java.util.ArrayList;

public class Jogo {
	private String listaLances;
	private Tabuleiro tabuleiro;
	private Jogador jogador1;
	private Jogador jogador2;
	private int numeroLance;
	private String turno;
	
	public Jogo(Jogador jogador1, Jogador jogador2, Tabuleiro tabuleiro) {
		this.listaLances = "";
		this.numeroLance = 1;
		this.turno = "branco";
		
		this.tabuleiro = tabuleiro;	
		
		this.jogador1 = jogador1;
		this.jogador2 = jogador2;
		
		System.out.println("associando coisas ao jogo");
		associacaoJogo();
		System.out.println("Atualizando os lances e capturas");
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
	
	//Associa o jogo ao tabuleiro e peças:
	public void associacaoJogo() {
		tabuleiro.setJogo(this);
		for (int l = 0; l < 8; l++) {
		    for (int c = 0; c < 8; c++) {
		    	try {
		    		Tabuleiro.getTabuleiro()[l][c].getPeca().setJogo(this);
		    	} catch(NullPointerException e) {}
		    }
		}
		jogador1.setJogo(this);
		jogador2.setJogo(this);
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
		Jogador jogadorBranco = (jogador1.getCor().equals("branco")) ? jogador1 : jogador2;
		Jogador jogadorPreto = (jogador1.getCor().equals("preto")) ? jogador1 : jogador2;
		
		if(turno.equals("branco")) {
			for(Peca peca : jogadorPreto.getCapturasPossiveis()) {
				if(peca instanceof Rei) return true;
			}
		}	else	{
			for(Peca peca : jogadorBranco.getCapturasPossiveis()) {
				if(peca instanceof Rei) return true;
			}
		}
		return false;
	}
	
	//Atualiza os lances possíveis para cada peça separadamente e para cada jogador
	public void atualizaLancesECapturas() {
		for (int l = 0; l < 8; l++) {
		    for (int c = 0; c < 8; c++) {
		    	try {
		    		Tabuleiro.getTabuleiro()[l][c].getPeca().lancesValidos();
		    	} catch(NullPointerException e) {}
		    }
		}
		
		jogador1.verificaCapturasPossiveis();
		jogador1.verificaCasasAtacadas();
		jogador1.verificaLancesPossiveis();
		jogador2.verificaCapturasPossiveis();
		jogador2.verificaCasasAtacadas();
		jogador2.verificaLancesPossiveis();
	}
	
	//Método que faz uma jogada a partir da peça selecionada
	//Passar parte da implementação para JogadorHumano
	public void fazLance(Peca pecaSelecionada, Casa casaDestino) {
		if(pecaSelecionada.getLancesPossiveis().contains(casaDestino)){
			System.out.println("chamada para mudar o tabuleiro");
            tabuleiro.mudaTabuleiro(pecaSelecionada.getPosicao(), casaDestino);		
        }
		System.out.println("chamada para atualizar as capturas");
		atualizaLancesECapturas();
		finalizaTurno();
	}
	
	//Muda turno e faz o lance do computador, se esse for o caso
	public void finalizaTurno() {
		System.out.println("finalizando turno");
		String novoTurno = (turno.equals("branco")) ? "preto" : "branco";
		setTurno(novoTurno);
		if(jogador2 instanceof JogadorMaquina) {
			((JogadorMaquina)jogador2).fazJogada();
			String novoTurnoMaquina = (turno.equals("branco")) ? "preto" : "branco";
			setTurno(novoTurnoMaquina);
		}
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
