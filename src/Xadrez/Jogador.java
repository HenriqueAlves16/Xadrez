package Xadrez;
import java.util.ArrayList;

public abstract class Jogador {
	private String cor;
	private ArrayList<Peca> capturasPossiveis;
	private ArrayList<Casa> casasAtacadas;
	private ArrayList<Lance> lancesPossiveis;
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
						//System.out.println("Lances validos para o " + peca + ": " + peca.getLancesPossiveis());
						for(int i = 0; i < peca.getLancesPossiveis().size(); i++) {				//Percorre cada possível lance 
							Casa casaDestinoPossivel = peca.getLancesPossiveis().get(i);
							listaLances.add(new Lance(peca, casaDestinoPossivel));
						}
					}
				}	catch(NullPointerException e) {}
			}
		}
		setLancesPossiveis(listaLances);
	}
	
	public void verificaCasasAtacadas() {
		ArrayList<Casa> listaCasas = new ArrayList<Casa>();
		
		for(int l = 0; l < 8; l++) {					//Percorre o tabuleiro
			for(char c = 'a'; c <= 'h'; c++) {
				try {
					Peca peca = Tabuleiro.getCasa(c, l + 1).getPeca(); 
					
					//Se a cor da peça é igual à cor do jogador, adicionamos as casas atacadas pela peça ao jogador
					if(peca.getCor().equals(this.cor)) {
						//System.out.println("casas atacadas pelo " + peca + " " + peca.getCasasAtacadas());
						if(peca instanceof Peao) {
							//System.out.println("casas atacadas peao" + ((Peao)peca).getCasasAtacadas());
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
		System.out.println("casas atacadas pelo jogador " + cor + " " + getCasasAtacadas());
	}
	
	//Método que executa a jogada:
	public abstract void fazJogada();

}
