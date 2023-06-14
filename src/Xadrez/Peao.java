package Xadrez;

import java.util.ArrayList;

public class Peao extends Peca {
	public Peao(String cor, Casa posicao, int x, int y, String path) {
		super(cor, posicao, x, y, path);
	}
	
	public int lancesValidos() throws IndexOutOfBoundsException {
	    ArrayList<Casa> lancesValidos = new ArrayList<Casa>();
	    ArrayList<Peca> capturasValidas = new ArrayList<Peca>();

	    char colunaAtual = getPosicao().getColuna();
	    int linhaAtual = getPosicao().getLinha();
	    
	    // Delta de movimento dependendo da cor do peão:
	    int delta = (getCor().equals("branco")) ? 1 : -1; 
	    
	    try {
	        Casa casaFrente1 = Tabuleiro.getCasa(colunaAtual, linhaAtual + delta);
	        if (casaFrente1.getPeca() == null) {
	            lancesValidos.add(casaFrente1);
	            
	            // Movimento de duas casas para frente permitido apenas no primeiro movimento do peão:
	            if ((linhaAtual == 2 && delta == 1) || (linhaAtual == 7 && delta == -1)) {
	                Casa casaFrente2 = Tabuleiro.getCasa(colunaAtual, linhaAtual + 2 * delta);
	                if (casaFrente2.getPeca() == null) {
	                    lancesValidos.add(casaFrente2);
	                }
	            }
	        }
	    } catch (IndexOutOfBoundsException e) {}
	    
	    // Verifica possíveis capturas diagonais:
	    try {
	    	Casa casaDiagonalDireita = Tabuleiro.getCasa((char) (colunaAtual + 1), linhaAtual + delta);
		    if (!casaDiagonalDireita.getPeca().getCor().equals(this.getCor())) {
		    	System.out.println("diag dir");
		        lancesValidos.add(casaDiagonalDireita);
	        	capturasValidas.add(casaDiagonalDireita.getPeca());
		    }
	    } catch(IndexOutOfBoundsException e) {
	    	
	    } catch(NullPointerException e) {}
	    
	    try	{
		    Casa casaDiagonalEsquerda = Tabuleiro.getCasa((char) (colunaAtual - 1), linhaAtual + delta);
		    if (!casaDiagonalEsquerda.getPeca().getCor().equals(this.getCor())) {
		    	System.out.println("diag esq");
		        lancesValidos.add(casaDiagonalEsquerda);
	        	capturasValidas.add(casaDiagonalEsquerda.getPeca());
		    }
		} catch(IndexOutOfBoundsException e) {
	    	
	    } catch(NullPointerException e) {}
	    
	    this.setLancesPossiveis(lancesValidos);
	    this.setCapturasPossiveis(capturasValidas);
	    return lancesValidos.size();
	}
	
	public String getImagePath() {
		if(getCor().equals("branco")) {
			return "Imagens/w_pawn_png_128px.png";
		}	else	{
			return "Imagens/b_pawn_png_128px.png";
		}
	}
	
	@Override
	public String toString() {
		return super.toString() + "P";
	}
}
