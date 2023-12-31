package Xadrez;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Jogo {
	private Tabuleiro tabuleiro;
	private Jogador jogador1, jogador2;
	private int numeroLance;
	private String turno;
	private Lance ultimoLance;
	private String listaLances;

	public Jogo(Jogador jogador1, Jogador jogador2, Tabuleiro tabuleiro) {
		this.listaLances = "";
		this.numeroLance = 1;
		this.turno = "branco";
		this.tabuleiro = tabuleiro;	
		this.jogador1 = jogador1;
		this.jogador2 = jogador2;
		
		associacaoJogo();
		atualizaLancesECapturas();
		
		if(jogador2 instanceof JogadorMaquina && jogador2.getCor().equals("branco")) {
			fazLance(null, null);
		}
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
	
	//toString
	@Override
	public String toString() {
		return jogador1.getClass() + " vs " + jogador2.getClass();
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
		tabuleiro.setOrientacao(jogador1.getCor());
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
	private void atualizaLancesPeca(String cor) {
		for (int l = 0; l < 8; l++) {
		    for (int c = 0; c < 8; c++) {
		    	try {
		    		Peca peca = Tabuleiro.getTabuleiro()[l][c].getPeca();
		    		if(peca.getCor().equals(cor)) {
		    			peca.casasBase();
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
	public boolean fazLance(Peca pecaSelecionada, Casa casaDestino) {
		if(turno.equals("branco")) {
    		setUltimoLance(getJogadorBranco().fazJogada(pecaSelecionada, casaDestino));
    	}	else	{
    		setUltimoLance(getJogadorPreto().fazJogada(pecaSelecionada, casaDestino));
    	}
		finalizaTurno();
		return (ultimoLance != null) ? true : false;
	}
	
	//Muda turno e faz o lance do computador, se esse for o caso
	public void finalizaTurno() {
		tabuleiro.setPecaClicada(null);
		try {
			if(ultimoLance.getPecaMovida() instanceof Rei) {
				((Rei)ultimoLance.getPecaMovida()).setMovido(true);
			} else if(ultimoLance.getPecaMovida() instanceof Torre) {
				((Torre)ultimoLance.getPecaMovida()).setMovido(true);
			}
		}	catch(NullPointerException e) {}
		
		if (ultimoLance != null) {
			String novoTurno = (turno.equals("branco")) ? "preto" : "branco";
			setTurno(novoTurno);
			if(novoTurno.equals("branco")) {
				numeroLance++;
			}
			atualizaLancesECapturas();
        	finalizaJogo();
			
			if(getJogador2() instanceof JogadorMaquina && turno.equals(jogador2.getCor())) {
	        	do {
	        		atualizaLancesECapturas();
	            	finalizaJogo();
		        	setUltimoLance(jogador2.fazJogada(null, null));
		        	tabuleiro.repaint();
	        	} while(getUltimoLance() == null);
	        	finalizaTurno();
	        }
		}
	}

	//Método que verifica se o jogo acabou:
	//Usar um enum:
	//Arrumar rei sumindo quando há xeque por tras (?)
	public int verificaFim() {
		Jogador jogadorBranco = getJogadorBranco();
		Jogador jogadorPreto = getJogadorPreto();
		
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
		        	Peca peca = Tabuleiro.getTabuleiro()[l][c].getPeca();
		            if (peca instanceof Cavalo && peca.getCor().equals("branco")) {
		                cavalosBrancos++;
		            } else if (peca instanceof Cavalo && peca.getCor().equals("preto")) {
		                cavalosPretos++;
		            } else if (peca instanceof Bispo && peca.getCor().equals("branco")) {
		                bisposBrancos++;
		            } else if (peca instanceof Bispo && peca.getCor().equals("preto")) {
		                bisposPretos++;
		            } else if (!(peca instanceof Rei)) {
		                outrasPecas = true; //Define a variável de controle como true para interromper o loop
		                break;
		            }
		        } catch (NullPointerException e) {}
		    }

		    if (outrasPecas) {
		        break; // Interrompe o loop externo
		    }
		}
		//Casos possíveis de empate por material insuficiente:
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
	public void finalizaJogo() {
        //Verifica o resultado do jogo (branco ganhou, preto ganhou, empate)
        int resultado = verificaFim();
        int opcao = -1;
        
        if(resultado != 0) {
        	tabuleiro.repaint();			//Atualiza a interface antes do jogo acabar
        	Lance.escreveNoArquivo("\n");	//Pula linha no arquivo
        }

        //Exibe a mensagem adequada com base no resultado
        if (resultado == 10) {
            opcao = JOptionPane.showOptionDialog(null, "XEQUE MATE! O jogador branco venceu!", "Fim de Jogo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Jogar Novamente", "Sair"}, null);
        } else if (resultado == -10) {
        	opcao = JOptionPane.showOptionDialog(null, "XEQUE MATE! O jogador preto venceu!", "Fim de Jogo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Jogar Novamente", "Sair"}, null);
        } else if (resultado == -1) {
        	opcao = JOptionPane.showOptionDialog(null, "O jogo terminou em empate por afogamento!", "Fim de Jogo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Jogar Novamente", "Sair"}, null);
        } else if (resultado == 1) {
        	opcao = JOptionPane.showOptionDialog(null, "O jogo terminou em empate por material insuficiente", "Fim de Jogo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Jogar Novamente", "Sair"}, null);
        }
        
        //Verifica a opção escolhida
        if (opcao == JOptionPane.YES_OPTION) {
            reiniciarPrograma();
        } else if (opcao == JOptionPane.NO_OPTION) {
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
			//Preto em xeque
			for(int i = 0; i < casasAtacadasJogadorBranco.size(); i++) {
				if(casasAtacadasJogadorBranco.get(i).toString().equals(reiPreto.getPosicao().toString())) {
					return "preto";
				}
			}
			//Branco em xeque
		}	else	{
			for(int i = 0; i < casasAtacadasJogadorPreto.size(); i++) {
				if(casasAtacadasJogadorPreto.get(i).toString().equals(reiBranco.getPosicao().toString())) {
					return "branco";
				}
			}
		}
		//Ninguém em xeque
		return "";
	}
	
	//Acho que ele tentou fazer esse lance, mas a chamada ocorreu antes do problema
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
	
	
    private static void reiniciarPrograma() {
        // Reinicia o programa
        String comando = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        String caminhoPrograma = Main.class.getCanonicalName();
        try {
            ProcessBuilder pb = new ProcessBuilder(comando, "-cp", System.getProperty("java.class.path"), caminhoPrograma);
            pb.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Encerra o programa atual
        System.exit(0);
    }
	
}
