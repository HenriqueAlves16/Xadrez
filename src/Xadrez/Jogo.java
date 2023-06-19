package Xadrez;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;



public class Jogo {
	private String listaLances;
	private Tabuleiro tabuleiro;
	private Jogador jogador1;
	private Jogador jogador2;
	private int numeroLance;
	private String turno;
	private Lance ultimoLance;
	
	public Jogo(Jogador jogador1, Jogador jogador2, Tabuleiro tabuleiro) {
		this.listaLances = "";
		this.numeroLance = 1;
		this.turno = "branco";
		
		this.tabuleiro = tabuleiro;	
		
		this.jogador1 = jogador1;
		this.jogador2 = jogador2;
		
		//System.out.println("associando coisas ao jogo");
		associacaoJogo();
		//System.out.println("Atualizando os lances e capturas");
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
	
	public Jogador getJogadorBranco() {
		Jogador jogadorBranco = (jogador1.getCor().equals("branco")) ? jogador1 : jogador2;
		return jogadorBranco;
	}
	
	public Jogador getJogadorPreto() {
		Jogador jogadorPreto = (jogador1.getCor().equals("preto")) ? jogador1 : jogador2;
		return jogadorPreto;
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
	
	//Método que adiciona um lance na lista de lances:
	public void adicionaLance(Peca peca, Casa casaDestino) {
		if(peca.getCor().equals("branco")) {
			Lance.escreveLance(peca, casaDestino, numeroLance);
		} else {
			Lance.escreveLance(peca, casaDestino);
			numeroLance++;
		}
	}
	
	//Atualiza os lances válidos para cada peça de determinada cor:
	public void atualizaLancesPeca(String cor) {
		for (int l = 0; l < 8; l++) {
		    for (int c = 0; c < 8; c++) {
		    	try {
		    		Peca peca = Tabuleiro.getTabuleiro()[l][c].getPeca();
		    		if(peca.getCor().equals(cor)) {
		    			peca.lancesValidos();
		    			if(peca instanceof Peao) {
		    				((Peao)peca).movimentoEspecial(ultimoLance);
		    			}
		    		}
		    	} catch(NullPointerException e) {}
		    }
		}
	}
	
	//Atualiza os lances possíveis para cada peça separadamente e para cada jogador
	public void atualizaLancesECapturas() {
		
		Jogador jogadorBranco = (jogador1.getCor().equals("branco")) ? jogador1 : jogador2;
		Jogador jogadorPreto = (jogador1.getCor().equals("preto")) ? jogador1 : jogador2;
		
		if(turno.equals("branco")) {
			atualizaLancesPeca("branco");
			atualizaLancesPeca("preto");
			
			jogadorBranco.atualizacaoLances();
			jogadorPreto.atualizacaoLances();
			
		}	else	{
			atualizaLancesPeca("preto");
			atualizaLancesPeca("branco");
						
			jogadorBranco.atualizacaoLances();
			jogadorPreto.atualizacaoLances();
		}
	}
	
	//Método que faz uma jogada a partir da peça selecionada
	//Arrumar situação de cravada
	//Rei as vezes some com xeque
	public void fazLance(Peca pecaSelecionada, Casa casaDestino) {
		if(pecaSelecionada.getCor().equals("branco")) {
    		ultimoLance = getJogadorBranco().fazJogada(pecaSelecionada, casaDestino);
    	}	else	{
    		ultimoLance = getJogadorPreto().fazJogada(pecaSelecionada, casaDestino);
    	}
		finalizaTurno();
	}
	
	//Muda turno e faz o lance do computador, se esse for o caso
	public void finalizaTurno() {
		//System.out.println("finalizando turno");
		numeroLance++;
		if (ultimoLance != null) {
			String novoTurno = (turno.equals("branco")) ? "preto" : "branco";
			setTurno(novoTurno);
			
			if(jogador2 instanceof JogadorMaquina) {
				((JogadorMaquina)jogador2).fazJogada(null, null);
				//Atualizando o numero de lances e turno:
				numeroLance++;
				String novoTurnoMaquina = (turno.equals("branco")) ? "preto" : "branco";
				setTurno(novoTurnoMaquina);
			}
			atualizaLancesECapturas();
		}
	}

	//Método que verifica se o jogo acabou:
	//Usar um enum:
	public int verificaFim() {
		Jogador jogadorBranco = (jogador1.getCor().equals("branco")) ? jogador1 : jogador2;
		Jogador jogadorPreto = (jogador1.getCor().equals("preto")) ? jogador1 : jogador2;
		
		Rei reiPreto = encontraRei("preto");
		Rei reiBranco = encontraRei("branco");
		
		//Xeque-Mate:
		if(turno.equals("preto")) {
			if(jogadorBranco.getCasasAtacadas().contains(reiPreto.getPosicao()) && reiPreto.getLancesPossiveis().size() == 0) {
				return 10;
			}
		}	else	{
			if(jogadorPreto.getCasasAtacadas().contains(reiBranco.getPosicao()) && reiBranco.getLancesPossiveis().size() == 0) {
				return -10;
			}
		}
		
		//Afogamento:
		if(turno.equals("branco"))	{
			if(jogadorBranco.getLancesPossiveis().size() == 0) {
				return 1;
			}
		}	else	{
			if(jogadorPreto.getLancesPossiveis().size() == 0) {
				return -1;
			}
		}
		
		//Material insuficiente:
		int cavalosBrancos = 0;
		int cavalosPretos = 0;
		int bisposBrancos = 0;
		int bisposPretos = 0;
		boolean outrasPecas = false;
		
		//Contando os cavalos e bispos:
		for (int l = 0; l < 8; l++) {
		    for (int c = 0; c < 8; c++) {
		        try {
		            if (Tabuleiro.getTabuleiro()[l][c].getPeca() instanceof Cavalo && Tabuleiro.getTabuleiro()[l][c].getPeca().getCor().equals("branco")) {
		                cavalosBrancos++;
		            } else if (Tabuleiro.getTabuleiro()[l][c].getPeca() instanceof Cavalo && Tabuleiro.getTabuleiro()[l][c].getPeca().getCor().equals("preto")) {
		                cavalosPretos++;
		            } else if (Tabuleiro.getTabuleiro()[l][c].getPeca() instanceof Bispo && Tabuleiro.getTabuleiro()[l][c].getPeca().getCor().equals("branco")) {
		                bisposBrancos++;
		            } else if (Tabuleiro.getTabuleiro()[l][c].getPeca() instanceof Bispo && Tabuleiro.getTabuleiro()[l][c].getPeca().getCor().equals("preto")) {
		                bisposPretos++;
		            } else if (!(Tabuleiro.getTabuleiro()[l][c].getPeca() instanceof Rei)) {
		                outrasPecas = true; //Define a variável de controle como true para interromper o loop
		                break;
		            }
		        } catch (NullPointerException e) {}
		    }

		    if (outrasPecas) {
		        break; // Interrompe o loop externo
		    }
		}

		//Casos possíveis de empate:
		if(!outrasPecas) {
			if(cavalosBrancos <= 2 && cavalosPretos <= 2 && bisposBrancos == 0 && bisposPretos == 0) {
				return 1;
			}	else if(cavalosBrancos == 0 && cavalosPretos == 0 && bisposBrancos <= 1 && bisposPretos <= 1) {
				return 1;
			}
		}	
		return 0;
	}
	
	//Método que verifica se o jogador de turno ativo está em xeque:
	//Usar Enum:
	public String verificaXeque() {
		Rei reiPreto = encontraRei("preto");
		Rei reiBranco = encontraRei("branco");
		Jogador jogadorBranco = (jogador1.getCor().equals("branco")) ? jogador1 : jogador2;
		Jogador jogadorPreto = (jogador1.getCor().equals("preto")) ? jogador1 : jogador2;
		
		if(turno.equals("preto")) {
			//System.out.println("casas atacadas pelo branco: " + jogadorBranco.getCasasAtacadas());
			//System.out.println("Posição rei preto " + reiPreto.getPosicao());
			
			//Preto em xeque
			for(int i = 0; i < jogadorBranco.getCasasAtacadas().size(); i++) {
				if(jogadorBranco.getCasasAtacadas().get(i).toString().equals(reiPreto.getPosicao().toString())) {
					return "preto";
				}
			}
			//Branco em xeque
		}	else	{
			//System.out.println("casas atacadas pelo preto: " + jogadorPreto.getCasasAtacadas());
			//System.out.println("Posição rei branco " + reiBranco.getPosicao());

			for(int i = 0; i < jogadorPreto.getCasasAtacadas().size(); i++) {
				if(jogadorPreto.getCasasAtacadas().get(i).toString().equals(reiBranco.getPosicao().toString())) {
					return "branco";
				}
			}
		}
		//Ninguém em xeque
		return "";
	}
	
	public Rei encontraRei(String cor) {
		for (int l = 0; l < 8; l++) {
		    for (int c = 0; c < 8; c++) {
		    	try {
			        if(Tabuleiro.getTabuleiro()[l][c].getPeca() instanceof Rei && Tabuleiro.getTabuleiro()[l][c].getPeca().getCor().equals(cor)) {
			        	return (Rei)Tabuleiro.getTabuleiro()[l][c].getPeca();
			        }
		    	} catch(NullPointerException e) {}
		    }
		}
		return null;
	}
	
	
	
	
	
}
