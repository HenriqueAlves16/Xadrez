package Xadrez;
import java.util.ArrayList;

public abstract class Jogador {
	private String cor;
	private ArrayList<Peca> capturasPossiveis;
	private ArrayList<Lance> lancesPossiveis;
	private boolean emXeque;
	private Jogo jogo;

	//Construtor:
	public Jogador(String cor) {
		this.cor = cor;
		this.emXeque = false;
		this.capturasPossiveis = new ArrayList<Peca>();
		this.lancesPossiveis = new ArrayList<Lance>();
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
		
		for(int l = 0; l < 8; l++) {
			for(char c = 'a'; c <= 'h'; c++) {
				try {
					Peca peca = Tabuleiro.getCasa(c, l).getPeca(); 
					String corPeca = peca.getCor();
					
					if(corPeca.equals(this.cor)) {
						listaPecas.addAll(peca.getCapturasPossiveis());
					}
				}	catch(NullPointerException e) {}
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
					
					if(corPeca.equals(this.cor)) {
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
	
	//Método que executa a jogada:
	public abstract void fazJogada();

}
