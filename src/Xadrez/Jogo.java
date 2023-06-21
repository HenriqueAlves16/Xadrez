package Xadrez;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;



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
	
	public Lance getUltimoLance() {
		return ultimoLance;
	}

	public void setUltimoLance(Lance ultimoLance) {
		this.ultimoLance = ultimoLance;
	}

	
	public Jogador getJogadorBranco() {
		Jogador jogadorBranco = (jogador1.getCor().equals("branco")) ? jogador1 : jogador2;
		return jogadorBranco;
	}
	
	public Jogador getJogadorPreto() {
		Jogador jogadorPreto = (jogador1.getCor().equals("preto")) ? jogador1 : jogador2;
		return jogadorPreto;
	}
	
	public Jogador getJogadorAtivo() {
		Jogador jogadorAtivo = (turno.equals("branco")) ? getJogadorBranco() : getJogadorPreto();
		return jogadorAtivo;
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
		    			}	else if (peca instanceof Rei) {
		    				((Rei)peca).movimentoEspecial(null);
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
		System.out.println(turno);
		if(turno.equals("branco")) {
    		setUltimoLance(getJogadorBranco().fazJogada(pecaSelecionada, casaDestino));
    	}	else	{
    		setUltimoLance(getJogadorPreto().fazJogada(pecaSelecionada, casaDestino));
    	}
		System.out.println("ultimo lance: " + ultimoLance);
		finalizaTurno();
	}
	
	//Muda turno e faz o lance do computador, se esse for o caso
	public void finalizaTurno() {
		System.out.println("finalizando turno");
		if (ultimoLance != null) {
			numeroLance++;
			System.out.println("MUDANDO O TURNO! ANTES: " + turno);
			String novoTurno = (turno.equals("branco")) ? "preto" : "branco";
			setTurno(novoTurno);
			System.out.println("DEPOIS: " + turno);
			atualizaLancesECapturas();
			System.out.println("turno finalizado");
		}
	}

	//Método que verifica se o jogo acabou:
	//Usar um enum:
	//arrumar material insuficiente
	public int verificaFim() {
		Jogador jogadorBranco = (jogador1.getCor().equals("branco")) ? jogador1 : jogador2;
		Jogador jogadorPreto = (jogador1.getCor().equals("preto")) ? jogador1 : jogador2;
		
		//Xeque-Mate:
		if(turno.equals("preto") && verificaXeque().equals("preto")) {
			if(jogadorPreto.getLancesPossiveisXeque().size() == 0) {
				return 10;
			}
		}	else if (turno.equals("branco") && verificaXeque().equals("branco"))	{
			if(jogadorBranco.getLancesPossiveisXeque().size() == 0) {
				return -10;
			}
		}
		
		//Afogamento:
		if(turno.equals("branco"))	{
			if(jogadorBranco.getLancesPossiveis().size() == 0 && jogadorBranco.getLancesEspeciais().size() == 0 && jogadorBranco.getLancesPossiveisXeque().size() == 0) {
				return -1;
			}
		}	else if(jogadorPreto.getLancesPossiveis().size() == 0 && jogadorPreto.getLancesEspeciais().size() == 0 && jogadorPreto.getLancesPossiveisXeque().size() == 0)	{
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
	
	//Método que mostra uma mensagem de acordo com o resultado final do jogo
	public void verificaFimDoJogo() {
        // Verifique o resultado do jogo (branco ganhou, preto ganhou, empate)
        int resultado = verificaFim();

        // Exiba a mensagem adequada com base no resultado
        if (resultado == 10) {
            JOptionPane.showMessageDialog(null, "XEQUE MATE! O jogador branco venceu!");
            System.exit(0);
        } else if (resultado == -10) {
            JOptionPane.showMessageDialog(null, "XEQUE MATE! O jogador preto venceu!");
            System.exit(0);
        } else if (resultado == -1) {
            JOptionPane.showMessageDialog(null, "O jogo terminou em empate por afogamento!");
            System.exit(0);
        } else if (resultado == 1) {
            JOptionPane.showMessageDialog(null, "O jogo terminou em empate por material insuficiente!");
            System.exit(0);
        }	
	}
	
	//Método que verifica se o jogador de turno ativo está em xeque:
	//Usar Enum:
	public String verificaXeque() {
		Rei reiPreto = encontraRei("preto");
		Rei reiBranco = encontraRei("branco");
		Jogador jogadorBranco = (jogador1.getCor().equals("branco")) ? jogador1 : jogador2;
		Jogador jogadorPreto = (jogador1.getCor().equals("preto")) ? jogador1 : jogador2;
		ArrayList<Casa> casasAtacadasJogadorBranco = jogadorBranco.getCasasAtacadas();
		ArrayList<Casa> casasAtacadasJogadorPreto = jogadorPreto.getCasasAtacadas();		
		
		if(turno.equals("preto")) {
			//System.out.println("casas atacadas pelo branco: " + jogadorBranco.getCasasAtacadas());
			//System.out.println("Posição rei preto " + reiPreto.getPosicao());
			
			//Preto em xeque
			for(int i = 0; i < casasAtacadasJogadorBranco.size(); i++) {
				if(casasAtacadasJogadorBranco.get(i).toString().equals(reiPreto.getPosicao().toString())) {
					return "preto";
				}
			}
			//Branco em xeque
		}	else	{
			//System.out.println("casas atacadas pelo preto: " + jogadorPreto.getCasasAtacadas());
			//System.out.println("Posição rei branco " + reiBranco.getPosicao());

			for(int i = 0; i < casasAtacadasJogadorPreto.size(); i++) {
				if(casasAtacadasJogadorPreto.get(i).toString().equals(reiBranco.getPosicao().toString())) {
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
