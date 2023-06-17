package Xadrez;

import java.util.ArrayList;

public class Torre extends Peca {
	public Torre(String cor, Casa posicao, int x, int y, String path) {
		super(cor, posicao, x, y, path);
	}
	
	public int lancesValidos() {
	    ArrayList<Casa> lancesValidos = new ArrayList<Casa>();
	    ArrayList<Peca> capturasValidas = new ArrayList<Peca>();

	    char colunaAtual = getPosicao().getColuna();
	    int linhaAtual = getPosicao().getLinha();

	    // Movimento para cima
	    for (int i = linhaAtual + 1; i <= 8; i++) {
	        Casa casa = Tabuleiro.getCasa(colunaAtual, i);
	        if (casa.getPeca() == null) {
	        	lancesValidos.add(casa);
	        }	else if(!casa.getPeca().getCor().equals(this.getCor())) {
	        	capturasValidas.add(casa.getPeca());
	        	lancesValidos.add(casa);
	        	break;
	        }	else if(casa.getPeca().getCor().equals(this.getCor())) {
	        	break;
	        }
	    }

	    // Movimento para baixo
	    for (int i = linhaAtual - 1; i >= 1; i--) {
	        Casa casa = Tabuleiro.getCasa(colunaAtual, i);
	        if (casa.getPeca() == null) {
	        	lancesValidos.add(casa);
	        }	else if(!casa.getPeca().getCor().equals(this.getCor())) {
	        	capturasValidas.add(casa.getPeca());
	        	lancesValidos.add(casa);
	        	break;
	        }	else if(casa.getPeca().getCor().equals(this.getCor())) {
	        	break;
	        }
	    }

	    // Movimento para a direita
	    for (char c = (char) (colunaAtual + 1); c <= 'h'; c++) {
	        Casa casa = Tabuleiro.getCasa(c, linhaAtual);
	        if (casa.getPeca() == null) {
	        	lancesValidos.add(casa);
	        }	else if(!casa.getPeca().getCor().equals(this.getCor())) {
	        	capturasValidas.add(casa.getPeca());
	        	lancesValidos.add(casa);
	        	break;
	        }	else if(casa.getPeca().getCor().equals(this.getCor())) {
	        	break;
	        }
	    }

	    // Movimento para a esquerda
	    for (char c = (char) (colunaAtual - 1); c >= 'a'; c--) {
	        Casa casa = Tabuleiro.getCasa(c, linhaAtual);
	        if (casa.getPeca() == null) {
	        	lancesValidos.add(casa);
	        }	else if(!casa.getPeca().getCor().equals(this.getCor())) {
	        	capturasValidas.add(casa.getPeca());
	        	lancesValidos.add(casa);
	        	break;
	        }	else if(casa.getPeca().getCor().equals(this.getCor())) {
	        	break;
	        }
	    }
	    
	    
	    this.setLancesPossiveis(lancesValidos);
	    this.setCapturasPossiveis(capturasValidas);
	    this.setCasasAtacadas(lancesValidos);
	    return lancesValidos.size();
	}
	
	public String getImagePath() {
		if(getCor().equals("branco")) {
			return "Imagens/w_rook_png_128px.png";
		}	else	{
			return "Imagens/b_rook_png_128px.png";
		}
	}

	@Override
	public String toString() {
		return super.toString() + "T";
	}
	
	


}
