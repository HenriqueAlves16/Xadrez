package Xadrez;
import java.util.ArrayList;

public abstract class Jogador {
	private String cor;
	private ArrayList<Peca> capturasPossiveis;
	private boolean emXeque;
	
	//Construtor:
	public Jogador(String cor) {
		this.cor = cor;
		this.emXeque = false;
	}

	//Getters e setters:
	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
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
	
	//Método que verifica as peças atacadas pelo jogador:
	public void verificaCapturasPossiveis() throws NullPointerException{
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
	
	//Método que executa a jogada:
	public abstract void fazJogada();

}
