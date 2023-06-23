package Xadrez;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Torre extends Peca {
	private boolean movido;
	
	//Construtor:
	public Torre(String cor, Casa posicao, String path) {
		super(cor, posicao, path);
		this.movido = false;
		//System.out.println("torre " + getCor());
	}
	
	//Getters e setters:
	public boolean getMovido() {
		return movido;
	}

	public void setMovido(boolean movido) {
		this.movido = movido;
	}

	public int lancesValidos() {
	    ArrayList<Casa> lancesValidos = new ArrayList<Casa>();
		ArrayList<Casa> casasAtacadas = new ArrayList<Casa>();
	    ArrayList<Peca> capturasValidas = new ArrayList<Peca>();

	    char colunaAtual = getPosicao().getColuna();
	    int linhaAtual = getPosicao().getLinha();

	    // Movimento para cima
	    for (int i = linhaAtual + 1; i <= 8; i++) {
	        Casa casa = Tabuleiro.getCasa(colunaAtual, i);
	        casasAtacadas.add(casa);
	        if (casa.getPeca() == null || (casa.getPeca() != null && casa.getPeca() instanceof Rei && !casa.getPeca().getCor().equals(this.getCor()))) {	//Se não há peça ou há um rei inimigo
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
	        casasAtacadas.add(casa);
	        if (casa.getPeca() == null || (casa.getPeca() != null && casa.getPeca() instanceof Rei && !casa.getPeca().getCor().equals(this.getCor()))) {	//Se não há peça ou há um rei inimigo) {
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
	        casasAtacadas.add(casa);
	        if (casa.getPeca() == null || (casa.getPeca() != null && casa.getPeca()
	        	instanceof Rei && !casa.getPeca().getCor().equals(this.getCor()))) {	//Se não há peça ou há um rei inimigo) {
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
	        casasAtacadas.add(casa);
	        if (casa.getPeca() == null || (casa.getPeca() != null && casa.getPeca()
	        	instanceof Rei && !casa.getPeca().getCor().equals(this.getCor()))) {	//Se não há peça ou há um rei inimigo) {
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
	    this.setCasasAtacadas(casasAtacadas);
	    return lancesValidos.size();
	}

	public static String getImagePath(String cor) {
		if(cor.equals("branco")) {
			return "Imagens/w_rook_png_128px.png";
		}	else	{
			return "Imagens/b_rook_png_128px.png";		
		}
	}
	
	public static ImageIcon getResizedIcon(String cor) {
		Torre torreBranca = new Torre("branco", null, getImagePath("branco"));
		Torre torrePreta = new Torre("preto", null, getImagePath("preto"));
		ImageIcon resizedIcon = (cor.equals("branco")) ? torreBranca.getResizedIcon() : torrePreta.getResizedIcon();
		
		return resizedIcon;
	}

	@Override
	public String toString() {
		return super.toString() + "R";
	}
	
	


}
