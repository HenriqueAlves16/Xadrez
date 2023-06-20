package Xadrez;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Bispo extends Peca {
	public Bispo(String cor, Casa posicao, String path) {
		super(cor, posicao, path);
	}
	
	
	public int lancesValidos() {
	    ArrayList<Casa> lancesValidos = new ArrayList<Casa>();
	    ArrayList<Peca> capturasValidas = new ArrayList<Peca>();
		ArrayList<Casa> casasAtacadas = new ArrayList<Casa>();
	    
	    char colunaAtual = getPosicao().getColuna();
	    int linhaAtual = getPosicao().getLinha();
	    
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

	public static String getImagePath(String cor) {
		if(cor.equals("branco")) {
			return "Imagens/w_bishop_png_128px.png";
		}	else	{
			return "Imagens/b_bihsop_png_128px.png";		
		}
	}
	
	public static ImageIcon getResizedIcon(String cor) {
		Bispo bispoBranco = new Bispo("branco", null, getImagePath("branco"));
		Bispo bispoPreto = new Bispo("preto", null, getImagePath("preto"));
		ImageIcon resizedIcon = (cor.equals("branco")) ? bispoBranco.getResizedIcon() : bispoPreto.getResizedIcon();
		
		return resizedIcon;
	}
	
	@Override
	public String toString() {
		return super.toString() + "B";
	}

}
