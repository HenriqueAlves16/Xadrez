package Xadrez;

import java.util.ArrayList;

public class Peao extends Peca implements MovableSpc{
	private ArrayList<Casa> casasAtacadas;
	private ArrayList<Lance> lancesEspeciais;
	
	//Construtor:
	public Peao(String cor, Casa posicao, int x, int y, String path) {
		super(cor, posicao, x, y, path);
		this.casasAtacadas = new ArrayList<Casa>();
		this.lancesEspeciais = new ArrayList<Lance>();
	}
	
	//Getters e Setters:
	public ArrayList<Casa> getCasasAtacadas() {
		return casasAtacadas;
	}

	public void setCasasAtacadas(ArrayList<Casa> casasAtacadas) {
		this.casasAtacadas = casasAtacadas;
	}
	
	public ArrayList<Lance> getLancesEspeciais() {
		return lancesEspeciais;
	}

	public void setLancesEspeciais(ArrayList<Lance> lancesEspeciais) {
		this.lancesEspeciais = lancesEspeciais;
	}
	
	public int lancesValidos() {
	    ArrayList<Casa> lancesValidos = new ArrayList<Casa>();
	    ArrayList<Casa> casasAtacadas = new ArrayList<Casa>();
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
        	casasAtacadas.add(casaDiagonalDireita);

		    if (casaDiagonalDireita.getPeca() != null && !casaDiagonalDireita.getPeca().getCor().equals(this.getCor())) {
		        lancesValidos.add(casaDiagonalDireita);
	        	capturasValidas.add(casaDiagonalDireita.getPeca());
		    }
	    } catch(IndexOutOfBoundsException e) {}
	    	    
	    try	{
		    Casa casaDiagonalEsquerda = Tabuleiro.getCasa((char) (colunaAtual - 1), linhaAtual + delta);
        	casasAtacadas.add(casaDiagonalEsquerda);

		    if (casaDiagonalEsquerda.getPeca() != null && !casaDiagonalEsquerda.getPeca().getCor().equals(this.getCor())) {
		        lancesValidos.add(casaDiagonalEsquerda);
	        	capturasValidas.add(casaDiagonalEsquerda.getPeca());
		    }
		} catch(IndexOutOfBoundsException e) {}
	    
	    this.setLancesPossiveis(lancesValidos);
	    this.setCasasAtacadas(casasAtacadas);
	    this.setCapturasPossiveis(capturasValidas);
	    return lancesValidos.size();
	}
	
	//Método que verifica se o movimento especial en passant é possível:
	public void movimentoEspecial(Lance ultimoLance) {
		if(!ultimoLance.getPecaMovida().getCor().equals(this.getCor())) {
			//System.out.println("\n\nUltimo lance: " + ultimoLance);
			//System.out.println(this.getCor() + "//" + ultimoLance.getPecaMovida().getCor());
			ArrayList<Lance> enPassants = new ArrayList<Lance>();
			
			int delta = (this.getCor().equals("branco")) ? 1 : -1;
			Casa casaDestino = Tabuleiro.getCasa(ultimoLance.getCasaDestino().getColuna(), ultimoLance.getCasaDestino().getLinha() + delta);
			//System.out.println("verificando enPassant para a casa " + casaDestino);
			
			if (ultimoLance != null && ultimoLance.getPecaMovida() instanceof Peao) {
				//System.out.println("ultimo movido foi um peao " + ultimoLance.getPecaMovida().getCor());
	            Peao ultimoPeaoMovido = (Peao) ultimoLance.getPecaMovida();
	            if (Math.abs(ultimoLance.getCasaDestino().getLinha() - ultimoLance.getCasaOrigem().getLinha()) == 2) {		// O último movimento foi um avanço de duas casas com um peão adversário
	                //System.out.println("último movimento foi um avanço de duas casas com um peão adversário");
	                if (this.getCor() != ultimoPeaoMovido.getCor() && Math.abs(this.getPosicao().getColuna() - 
	                	ultimoPeaoMovido.getPosicao().getColuna()) == 1 && this.getPosicao().getLinha() ==
	                	ultimoPeaoMovido.getPosicao().getLinha()){	
	                    // En passant é possível
	                	//System.out.println("enPassant possível!!!!!!");
	                	enPassants.add(new Lance(this, casaDestino, this.getPosicao()));
	                }
	            }
	        }
			setLancesEspeciais(enPassants);
		}
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
