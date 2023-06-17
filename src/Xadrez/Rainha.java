package Xadrez;

import java.util.ArrayList;

public class Rainha extends Peca {
	public Rainha(String cor, Casa posicao, int x, int y, String path) {
		super(cor, posicao, x, y, path);
	}
	
	public int lancesValidos(){
		ArrayList<Casa> lancesValidos = new ArrayList<Casa>();
	    ArrayList<Peca> capturasValidas = new ArrayList<Peca>();
	    ArrayList<Casa> casasAtacadas = new ArrayList<Casa>();

	    char colunaAtual = getPosicao().getColuna();
	    int linhaAtual = getPosicao().getLinha();

	    // Movimento para cima
	    for (int i = linhaAtual + 1; i <= 8; i++) {
	        Casa casa = Tabuleiro.getCasa(colunaAtual, i);
	        casasAtacadas.add(casa);
	        
	        if (casa.getPeca() == null || (casa.getPeca() != null && casa.getPeca() instanceof Rei && !casa.getPeca().getCor().equals(this.getCor()))) {	//Se não há peça ou há um rei inimigo) {
	        	lancesValidos.add(casa);
	        }	else if(!casa.getPeca().getCor().equals(this.getCor())) {
	        	lancesValidos.add(casa);
	        	capturasValidas.add(casa.getPeca());
	        	break;
	        }	else if(casa.getPeca().getCor().equals(this.getCor())) {
	        	break;
	        }
	    }

	    // Movimento para baixo
	    for (int i = linhaAtual - 1; i >= 1; i--) {
	        Casa casa = Tabuleiro.getCasa(colunaAtual, i);
	        casasAtacadas.add(casa);
	        
	        if (casa.getPeca() == null || (casa.getPeca() != null && casa.getPeca() instanceof Rei && !casa.getPeca().getCor().equals(this.getCor()))) {	//Se não há peça ou há um rei inimigo) {
	        	lancesValidos.add(casa);
	        }	else if(!casa.getPeca().getCor().equals(this.getCor())) {
	        	lancesValidos.add(casa);
	        	capturasValidas.add(casa.getPeca());
	        	break;
	        }	else if(casa.getPeca().getCor().equals(this.getCor())) {
	        	break;
	        }
	    }

	    // Movimento para a direita
	    for (char c = (char) (colunaAtual + 1); c <= 'h'; c++) {
	        Casa casa = Tabuleiro.getCasa(c, linhaAtual);
	        casasAtacadas.add(casa);
	        
	        if (casa.getPeca() == null || (casa.getPeca() != null && casa.getPeca() instanceof Rei && !casa.getPeca().getCor().equals(this.getCor()))) {	//Se não há peça ou há um rei inimigo) {
	        	lancesValidos.add(casa);
	        }	else if(!casa.getPeca().getCor().equals(this.getCor())) {
	        	lancesValidos.add(casa);
	        	capturasValidas.add(casa.getPeca());
	        	break;
	        }	else if(casa.getPeca().getCor().equals(this.getCor())) {
	        	break;
	        }
	    }

	    // Movimento para a esquerda
	    for (char c = (char) (colunaAtual - 1); c >= 'a'; c--) {
	        Casa casa = Tabuleiro.getCasa(c, linhaAtual);
	        casasAtacadas.add(casa);
	        
	        if (casa.getPeca() == null || (casa.getPeca() != null && casa.getPeca() instanceof Rei && !casa.getPeca().getCor().equals(this.getCor()))) {	//Se não há peça ou há um rei inimigo) {
	        	lancesValidos.add(casa);
	        }	else if(!casa.getPeca().getCor().equals(this.getCor())) {
	        	lancesValidos.add(casa);
	        	capturasValidas.add(casa.getPeca());
	        	break;
	        }	else if(casa.getPeca().getCor().equals(this.getCor())) {
	        	break;
	        }
	    }
	    
	 // Movimento na diagonal superior direita
	    for (int coluna = (colunaAtual + 1), linha = linhaAtual + 1; coluna <= 'h' && linha <= 8; coluna++, linha++) {
	        Casa casa = Tabuleiro.getCasa((char)coluna, linha);
	        casasAtacadas.add(casa);
	        
	        if (casa.getPeca() == null || (casa.getPeca() != null && casa.getPeca() instanceof Rei && !casa.getPeca().getCor().equals(this.getCor()))) {	//Se não há peça ou há um rei inimigo) {
	            lancesValidos.add(casa);
	        } else if (!casa.getPeca().getCor().equals(this.getCor())) {
	            lancesValidos.add(casa);
	        	capturasValidas.add(casa.getPeca());
	            break;
	        } else if (casa.getPeca().getCor().equals(this.getCor())) {
	            break;
	        }
	    }

	    // Movimento na diagonal superior esquerda
	    for (int coluna = (colunaAtual - 1), linha = linhaAtual + 1; coluna >= 'a' && linha <= 8; coluna--, linha++) {
	        Casa casa = Tabuleiro.getCasa((char)coluna, linha);
	        casasAtacadas.add(casa);
	        
	        if (casa.getPeca() == null || (casa.getPeca() != null && casa.getPeca() instanceof Rei && !casa.getPeca().getCor().equals(this.getCor()))) {	//Se não há peça ou há um rei inimigo) {
	            lancesValidos.add(casa);
	        } else if (!casa.getPeca().getCor().equals(this.getCor())) {
	            lancesValidos.add(casa);
	        	capturasValidas.add(casa.getPeca());
	            break;
	        } else if (casa.getPeca().getCor().equals(this.getCor())) {
	            break;
	        }
	    }

	    // Movimento na diagonal inferior direita
	    for (int coluna = (colunaAtual + 1), linha = linhaAtual - 1; coluna <= 'h' && linha >= 1; coluna++, linha--) {
	        Casa casa = Tabuleiro.getCasa((char)coluna, linha);
	        casasAtacadas.add(casa);
	        
	        if (casa.getPeca() == null || (casa.getPeca() != null && casa.getPeca() instanceof Rei && !casa.getPeca().getCor().equals(this.getCor()))) {	//Se não há peça ou há um rei inimigo) {
	            lancesValidos.add(casa);
	        } else if (!casa.getPeca().getCor().equals(this.getCor())) {
	            lancesValidos.add(casa);
	        	capturasValidas.add(casa.getPeca());
	            break;
	        } else if (casa.getPeca().getCor().equals(this.getCor())) {
	            break;
	        }
	    }

	    // Movimento na diagonal inferior esquerda
	    for (int coluna = (colunaAtual - 1), linha = linhaAtual - 1; coluna >= 'a' && linha >= 1; coluna--, linha--) {
	        Casa casa = Tabuleiro.getCasa((char) coluna, linha);
	        casasAtacadas.add(casa);
	        
	        if (casa.getPeca() == null || (casa.getPeca() != null && casa.getPeca() instanceof Rei && !casa.getPeca().getCor().equals(this.getCor()))) {	//Se não há peça ou há um rei inimigo) {
	            lancesValidos.add(casa);
	        } else if (!casa.getPeca().getCor().equals(this.getCor())) {
	            lancesValidos.add(casa);
	        	capturasValidas.add(casa.getPeca());
	            break;
	        } else if (casa.getPeca().getCor().equals(this.getCor())) {
	            break;
	        }
	    }

	    this.setLancesPossiveis(lancesValidos);
	    this.setCapturasPossiveis(capturasValidas);
	    this.setCasasAtacadas(casasAtacadas);
	    return lancesValidos.size();
	}
	
	public String getImagePath() {
		if(getCor().equals("branco")) {
			return "Imagens/w_queen_png_128px.png";
		}	else	{
			return "Imagens/b_queen_png_128px.png";
		}
	}
	
	@Override
	public String toString() {
		return super.toString() + "Q";
	}
}
