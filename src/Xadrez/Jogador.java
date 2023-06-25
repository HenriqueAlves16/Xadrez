package Xadrez;
import java.util.ArrayList;

public abstract class Jogador {
	private String cor;
	private ArrayList<Peca> capturasPossiveis;
	private ArrayList<Casa> casasAtacadas;
	private ArrayList<Lance> lancesPossiveis;
	private ArrayList<Lance> lancesEspeciais;
	private boolean emXeque;
	private Jogo jogo;

	//Construtor:
	public Jogador(String cor) {
		this.cor = cor;
		this.emXeque = false;
		this.capturasPossiveis = new ArrayList<Peca>();
		this.lancesPossiveis = new ArrayList<Lance>();
		this.casasAtacadas = new ArrayList<Casa>();
	}
	
	//Getters e setters:
	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}
	
	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}

	public ArrayList<Peca> getCapturasPossiveis() {
		return capturasPossiveis;
	}

	public void setCapturasPossiveis(ArrayList<Peca> capturasPossiveis) {
		this.capturasPossiveis = capturasPossiveis;
	}
	
	public ArrayList<Casa> getCasasAtacadas() {
		return casasAtacadas;
	}

	public void setCasasAtacadas(ArrayList<Casa> casasAtacadas) {
		this.casasAtacadas = casasAtacadas;
	}

	public boolean isEmXeque() {
		return emXeque;
	}

	public void setEmXeque(boolean emXeque) {
		this.emXeque = emXeque;
	}
	
	public ArrayList<Lance> getLancesPossiveis() {
		return lancesPossiveis;
	}

	public void setLancesPossiveis(ArrayList<Lance> lancesPossiveis) {
		this.lancesPossiveis = lancesPossiveis;
	}
	
	public ArrayList<Lance> getLancesEspeciais() {
		return lancesEspeciais;
	}

	public void setLancesEspeciais(ArrayList<Lance> lancesEspeciais) {
		this.lancesEspeciais = lancesEspeciais;
	}

	//Método que verifica se o lance pretendido vai deixar o jogador em xeque:
	public boolean lanceMantemReiProtegido(Peca peca, Casa casaDestino) {
		boolean reiProtegido; 
		Casa casaOrigem = peca.getPosicao();
		getJogo().getTabuleiro().mudaTabuleiroTemp(casaOrigem, casaDestino);
		jogo.atualizaLancesECapturas();
		reiProtegido = (getJogo().verificaXeque().equals(this.getCor())) ? false : true;
		getJogo().getTabuleiro().desfazerLance(casaOrigem, casaDestino);
		jogo.atualizaLancesECapturas();
		return reiProtegido;
	}
	
	//Método que verifica as peças atacadas pelo jogador:
	public void verificaCapturasPossiveis(){
		ArrayList<Peca> listaPecas = new ArrayList<Peca>();
		
		//Percorre o tabuleiro
		for(int l = 0; l < 8; l++) {
			for(char c = 'a'; c <= 'h'; c++) {
				try {
					Peca peca = Tabuleiro.getCasa(c, l + 1).getPeca(); 
					String corPeca = peca.getCor();
					
					//Se a peça é da cor do jogador, vamos adicionar todas as capturas possíveis da peça para o jogador
					if(corPeca.equals(this.cor)) {
						listaPecas.addAll(peca.getCapturasPossiveis());
					}
				}	catch(NullPointerException e) {
					
				}	catch(IndexOutOfBoundsException e) {}
			}
		}
		setCapturasPossiveis(listaPecas);
	}
	
	public void verificaLancesPossiveis() {
	ArrayList<Lance> listaLances = new ArrayList<Lance>();
		
		for(int l = 0; l < 8; l++) {					//Percorre o tabuleiro
			for(char c = 'a'; c <= 'h'; c++) {
				try {
					Peca peca = Tabuleiro.getCasa(c, l + 1).getPeca(); 
					String corPeca = peca.getCor();
					
					//Se a peça é da cor do jogador, vamos adicionar todas os lances possíveis da peça para o jogador
					if(corPeca.equals(this.cor)) {
						ArrayList<Casa> casasBase = peca.getCasasBase();
						for(int i = 0; i < casasBase.size(); i++) {				//Percorre cada possível lance 
							Casa casaDestinoPossivel = casasBase.get(i);
							listaLances.add(new Lance(peca, casaDestinoPossivel));
						}
					}
				}	catch(NullPointerException e) {}
			}
		}
		setLancesPossiveis(listaLances);
	}
	
	//Verificar se é necessário o if é peão
	public void verificaCasasAtacadas() {
		ArrayList<Casa> listaCasas = new ArrayList<Casa>();
		
		for(int l = 0; l < 8; l++) {					//Percorre o tabuleiro
			for(char c = 'a'; c <= 'h'; c++) {
				try {
					Peca peca = Tabuleiro.getCasa(c, l + 1).getPeca(); 
					
					//Se a cor da peça é igual à cor do jogador, adicionamos as casas atacadas pela peça ao jogador
					if(peca.getCor().equals(this.cor)) {
						if(peca instanceof Peao) {
							for(int g = 0; g < ((Peao)peca).getCasasAtacadas().size(); g++) {
								Casa casa = ((Peao)peca).getCasasAtacadas().get(g);
								if(!listaCasas.contains(casa)) {
									listaCasas.add(casa);
								}
							}
						}	else	{
							for(int g = 0; g < peca.getCasasAtacadas().size(); g++) {
								Casa casa = peca.getCasasAtacadas().get(g);
								if(!listaCasas.contains(casa)) {
									listaCasas.add(casa);
								}
							}
						}
					}
				}	catch(NullPointerException e) {
					
				}	catch(IndexOutOfBoundsException e) {}
			}
		}
		setCasasAtacadas(listaCasas);
	}
	
	public void verificaLancesEspeciais() {
		ArrayList<Lance> listaLancesEspeciais = new ArrayList<Lance>();
		for(int l = 0; l < 8; l++) {					//Percorre o tabuleiro
			for(char c = 'a'; c <= 'h'; c++) {
				try {
					Peca peca = Tabuleiro.getCasa(c, l + 1).getPeca(); 	
					if(peca instanceof MovableSpc && peca.getCor().equals(this.getCor())) {
						for(int i = 0; i < ((MovableSpc)peca).getLancesEspeciais().size(); i++) {				//Percorre cada possível lance especial
							Lance lanceEspecial = ((MovableSpc)peca).getLancesEspeciais().get(i);
							listaLancesEspeciais.add(lanceEspecial);
						}
					}
				}	catch(NullPointerException e) {}
			}
		}
		setLancesEspeciais(listaLancesEspeciais);
	}
	
	public ArrayList<Lance> getLancesPossiveisXeque() {
		jogo.atualizaLancesECapturas();
		ArrayList<Lance> lancesPossiveis = this.getLancesPossiveis();
		ArrayList<Lance> lancesSemXeque = new ArrayList<Lance>();
	    for (Lance lance : lancesPossiveis) {
	    	Casa origem = lance.getPecaMovida().getPosicao();
	        // Realizar o lance temporariamente
	        boolean tabuleiroMudado = jogo.getTabuleiro().mudaTabuleiroTemp(lance.getPecaMovida().getPosicao(), lance.getCasaDestino());
	        
	        if(tabuleiroMudado) {
		        jogo.atualizaLancesECapturas();
		        
		        // Verificar se o jogador fica em xeque após o lance
		        String xeque = jogo.verificaXeque();
		        
		        // Desfazer o lance realizado
		        jogo.getTabuleiro().desfazerLance(origem, lance.getCasaDestino());
		        
		        if (!xeque.equals(this.cor)) {
		            // O lance não deixa o jogador em xeque, adiciona à lista de lances sem xeque
		            lancesSemXeque.add(lance);
		        }
	        }
	    }
	    return lancesSemXeque;
	}

	public void atualizacaoLances() {
		this.verificaCapturasPossiveis();
		this.verificaCasasAtacadas();
		this.verificaLancesEspeciais();
		this.verificaLancesPossiveis();
	}
	
	//Método que substitui um peão por uma peça escolhida durante a promoção
	public void promoverPeao(Peao peao, String pecaEscolhida) {
		Casa casaPromocao = Tabuleiro.getCasa(peao.getPosicao().getColuna(), peao.getPosicao().getLinha());
		String corPecaNova = peao.getCor(); 
		switch(pecaEscolhida) {
			case "Rainha":
				Rainha novaRainha = new Rainha(corPecaNova, casaPromocao, Rainha.getImagePath(corPecaNova));
				novaRainha.setJogo(jogo);
				casaPromocao.setPeca(novaRainha);
				break;
			case "Torre":
				Torre novaTorre = new Torre(corPecaNova, casaPromocao, Torre.getImagePath(corPecaNova));
				novaTorre.setJogo(jogo);
				casaPromocao.setPeca(novaTorre);
				break;
			case "Bispo":
				Bispo novoBispo = new Bispo(corPecaNova, casaPromocao, Bispo.getImagePath(corPecaNova));
				novoBispo.setJogo(jogo);
				casaPromocao.setPeca(novoBispo);
				break;
			case "Cavalo":
				Cavalo novoCavalo = new Cavalo(corPecaNova, casaPromocao, Cavalo.getImagePath(corPecaNova));
				novoCavalo.setJogo(jogo);
				casaPromocao.setPeca(novoCavalo);
				break;
		}
	}
	
	//Método que executa a jogada:
	public abstract Lance fazJogada(Peca peca, Casa casa);
	
	//Método que implementa a lógica de promoção de um peão:
	public abstract void promocao(Peao peao);

}
