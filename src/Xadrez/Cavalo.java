package Xadrez;

import java.util.ArrayList;

public class Cavalo extends Peca {
	public Cavalo(String cor, Casa posicao, int x, int y, String path) {
		super(cor, posicao, x, y, path);
	}
	
	
	public int lancesValidos() throws IndexOutOfBoundsException{
		ArrayList<Casa> lancesValidos = new ArrayList<Casa>();
	    ArrayList<Peca> capturasValidas = new ArrayList<Peca>();

	    char colunaAtual = getPosicao().getColuna();
	    int linhaAtual = getPosicao().getLinha();
	    
	    int[] deltas = {-2, -1, 1, 2};
	    
	    for (int delta1 : deltas) {
	        for (int delta2 : deltas) {
	            if (Math.abs(delta1) != Math.abs(delta2)) {
	                try {
	                    Casa casa = Tabuleiro.getCasa((char) (colunaAtual + delta1), linhaAtual + delta2);
	                    if (casa.getPeca() == null) {
	                        lancesValidos.add(casa);
	                    }	else if(!casa.getPeca().getCor().equals(this.getCor())){
	                    	lancesValidos.add(casa);
	        	        	capturasValidas.add(casa.getPeca());
	                    }
	                } catch (IndexOutOfBoundsException e) {}
	            }
	        }
	    }
	    
	    this.setLancesPossiveis(lancesValidos);
	    this.setCapturasPossiveis(capturasValidas);
	    return lancesValidos.size();
	}
	
	public String getImagePath() {
		if(getCor().equals("branco")) {
			return "Imagens/w_knight_png_128px.png";
		}	else	{
			return "Imagens/b_knight_png_128px.png";
		}
	}
	
	@Override
	public String toString() {
		return "N";
	}
}
