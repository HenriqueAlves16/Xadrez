package Xadrez;

import java.util.ArrayList;

public class Rei extends Peca {
	public Rei(String cor, Casa posicao, int x, int y, String path) {
		super(cor, posicao, x, y, path);
	}
	
	public int lancesValidos() throws IndexOutOfBoundsException{
		ArrayList<Casa> lancesValidos = new ArrayList<Casa>();
	    ArrayList<Peca> capturasValidas = new ArrayList<Peca>();
	    
	    char colunaAtual = getPosicao().getColuna();
	    int linhaAtual = getPosicao().getLinha();
	    
	    for(char c = (char)(colunaAtual - 1); c <= (char)(colunaAtual + 1); c++) {
	    	for(int l = linhaAtual - 1; l <= linhaAtual + 1; l++) {
	    		try {
		    		Casa casa = Tabuleiro.getCasa(c, l);
			        if (casa.getPeca() == null) {
			            lancesValidos.add(casa);
			        } else if (!casa.getPeca().getCor().equals(this.getCor())) {
			            lancesValidos.add(casa);
			        	capturasValidas.add(casa.getPeca());
			        }
	    		}	catch (IndexOutOfBoundsException e) {}
	    	}
	    }    
	    this.setLancesPossiveis(lancesValidos);
	    this.setCapturasPossiveis(capturasValidas);
	    return lancesValidos.size();
	}
	
	public String getImagePath() {
		if(getCor().equals("branco")) {
			return "Imagens/w_king_png_128px.png";
		}	else	{
			return "Imagens/b_king_png_128px.png";
		}
	}
	
	@Override
	public String toString() {
		return super.toString() + "K";
	}
	
	
}
