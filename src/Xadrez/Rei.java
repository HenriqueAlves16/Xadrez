package Xadrez;

import java.util.ArrayList;

public class Rei extends Peca implements MovableSpc{
	private static final long serialVersionUID = 1L;
	private boolean movido;
	private ArrayList<Lance> lancesEspeciais;
	
	//Construtor:
	public Rei(String cor, Casa posicao, String path) {
		super(cor, posicao, path);
		this.movido = false;
		this.lancesEspeciais = new ArrayList<Lance>();
	}
	
	//Getters e setters:
	public boolean getMovido() {
		return movido;
	}

	public void setMovido(boolean movido) {
		this.movido = movido;
	}

	public ArrayList<Lance> getLancesEspeciais() {
		return lancesEspeciais;
	}

	public void setLancesEspeciais(ArrayList<Lance> lancesEspeciais) {
		this.lancesEspeciais = lancesEspeciais;
	}
	
	//toString()
	@Override
	public String toString() {
		return super.toString() + "K";
	}
	
	
	public int casasBase() {
		ArrayList<Casa> lancesValidos = new ArrayList<Casa>();
		ArrayList<Casa> casasAtacadas = new ArrayList<Casa>();
	    ArrayList<Peca> capturasValidas = new ArrayList<Peca>();
	    Jogador oponente = (getCor().equals(getJogo().getJogador1().getCor())) ? getJogo().getJogador2() : getJogo().getJogador1();
	    
	    char colunaAtual = getPosicao().getColuna();
	    int linhaAtual = getPosicao().getLinha();

	    //Percorre as casas em volta do rei
	    for(char c = (char)(colunaAtual - 1); c <= (char)(colunaAtual + 1); c++) {
	    	for(int l = linhaAtual - 1; l <= linhaAtual + 1; l++) {
    		
	    		if(l > 0 && l < 9 && c >= 'a' && c <= 'h') {
	    			Casa casa = Tabuleiro.getCasa(c, l);

	    			if(!casa.equals(getPosicao())) {
	    				casasAtacadas.add(casa);
				        if (casa.getPeca() == null && !casaInvalida(casa, oponente)) {		//Casa sem peça e válida
				            lancesValidos.add(casa);
				        } else if (casa.getPeca() != null && !(casa.getPeca().getCor().equals(this.getCor()))  && !casaInvalida(casa, oponente)) {		//Casa com peça adversária e válida
				            lancesValidos.add(casa);
				        	capturasValidas.add(casa.getPeca());
				        }
	    			}
	    		}
	    	}
	    }
	   
	    this.setCasasAtacadas(casasAtacadas);
	    this.setCasasBase(lancesValidos);
	    this.setCapturasPossiveis(capturasValidas);
	    
	    return lancesValidos.size();
	}

	private boolean casaInvalida(Casa casa, Jogador oponente) {
		ArrayList<Casa> casasInvalidas = oponente.getCasasAtacadas();
		for(int i = 0; i < casasInvalidas.size(); i++) {
			if(casasInvalidas.get(i).toString().equals(casa.toString())) {
				return true;
			}
		}
		return false;
	}
	
	public void movimentoEspecial(Lance lance) {
		ArrayList<Lance> roques = new ArrayList<Lance>();
		if(this.movido == false) {				//Rei não movimentado
			int linhaRei = this.getPosicao().getLinha();
			
			//Roque longo
			if(Tabuleiro.getCasa('a', linhaRei).getPeca() instanceof Torre) {		//Torre na casa correta
				Torre torreRoqueLongo = (Torre)Tabuleiro.getCasa('a', linhaRei).getPeca();
				if(torreRoqueLongo.getCor().equals(this.getCor()) && torreRoqueLongo.getMovido() == false) {	//Torre não movimentada
					if(caminhoLivre(1)) {		//Caminho do roque livre e não atacado
						roques.add(new Lance(this, Tabuleiro.getCasa('c', linhaRei), this.getPosicao()));
						roques.add(new Lance(this, Tabuleiro.getCasa('a', linhaRei), this.getPosicao()));
					}
				}

			}
			
			//Roque curto
			if(Tabuleiro.getCasa('h', linhaRei).getPeca() instanceof Torre) {		//Torre na casa correta
				Torre torreRoqueCurto = (Torre)Tabuleiro.getCasa('h', linhaRei).getPeca();
				if(torreRoqueCurto.getCor().equals(this.getCor()) && torreRoqueCurto.getMovido() == false) {	//Torre não movimentada
					if(caminhoLivre(0)) {		//Caminho do roque livre e não atacado
						roques.add(new Lance(this, Tabuleiro.getCasa('g', linhaRei), this.getPosicao()));
						roques.add(new Lance(this, Tabuleiro.getCasa('h', linhaRei), this.getPosicao()));
					}
				}
			}
			
		}
		setLancesEspeciais(roques);
	}
	
	//Verifica se o caminho do roque não está ocupado nem atacado
	private boolean caminhoLivre(int roque) {
		int linhaRei = this.getPosicao().getLinha();
		Jogador jogadorAdversario = (getJogo().getJogador1().getCor().equals(this.getCor())) ? getJogo().getJogador2() : getJogo().getJogador1();
		ArrayList<Casa> casasAtacadasAdv = jogadorAdversario.getCasasAtacadas();
		
		if(roque == 0) {		//Roque curto
			Casa curtoF = Tabuleiro.getCasa('f', linhaRei);
			Casa curtoG = Tabuleiro.getCasa('g', linhaRei);
			
			//Verifica se as casas entre o rei e a torre estão livres de peças
			if(!(curtoF.getPeca() == null && curtoG.getPeca() == null)) {
				return false;
			}
			
			//Verifica se as casas pelas quais o rei passa estão atacadas
			for(int i = 0; i < casasAtacadasAdv.size(); i++) {		//casas atacadas pelo adversário
				String casa = casasAtacadasAdv.get(i).toString();
				if(casa.equals(getPosicao().toString()) || casa.equals(curtoF.toString()) || casa.equals(curtoG.toString())) {		//Casas que o rei passa
					return false;
				}
			}
			return true;
			
		}	else	{
			Casa longoB = Tabuleiro.getCasa('b', linhaRei);
			Casa longoC = Tabuleiro.getCasa('c', linhaRei);
			Casa longoD = Tabuleiro.getCasa('d', linhaRei);
			
			//Verifica se as casas entre o rei e a torre estão livres de peças
			if(!(longoB.getPeca() == null && longoC.getPeca() == null && longoD.getPeca() == null)) {
				return false;
			}
			
			//Verifica se as casas pelas quais o rei passa estão atacadas
			for(int i = 0; i < casasAtacadasAdv.size(); i++) {		//casas atacadas pelo adversário
				String casa = casasAtacadasAdv.get(i).toString();
				if(casa.equals(getPosicao().toString()) || casa.equals(longoB.toString()) || casa.equals(longoC.toString()) || casa.equals(longoD.toString())) {		//Casas que o rei passa
					return false;
				}
			}
			return true;
		}
	}
	
	public String getImagePath() {
		if(getCor().equals("branco")) {
			return "Imagens/w_king_png_128px.png";
		}	else	{
			return "Imagens/b_king_png_128px.png";
		}
	}
	
}
