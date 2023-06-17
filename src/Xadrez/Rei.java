package Xadrez;

import java.util.ArrayList;

public class Rei extends Peca {
	public Rei(String cor, Casa posicao, int x, int y, String path) {
		super(cor, posicao, x, y, path);
	}
	
	public int lancesValidos() {
		ArrayList<Casa> lancesValidos = new ArrayList<Casa>();
		ArrayList<Casa> casasAtacadas = new ArrayList<Casa>();
	    ArrayList<Peca> capturasValidas = new ArrayList<Peca>();
	    Jogador oponente = (getCor().equals(getJogo().getJogador1().getCor())) ? getJogo().getJogador2() : getJogo().getJogador1();
	    System.out.println("cor rei:" + this.getCor() + " // oponente: " + oponente.getCor());
	    
	    char colunaAtual = getPosicao().getColuna();
	    int linhaAtual = getPosicao().getLinha();
		System.out.println("casa rei " + getCor() + " " + this.getPosicao());

	    //Percorre as casas em volta do rei
	    for(char c = (char)(colunaAtual - 1); c <= (char)(colunaAtual + 1); c++) {
	    	for(int l = linhaAtual - 1; l <= linhaAtual + 1; l++) {
	    	
	    		//System.out.println("linha " + l + " coluna " + c);

	    		//System.out.println("casas inválidas para o rei " + this.getCor() + casasInvalidas);
	    		
	    		if(l > 0 && l < 9 && c >= 'a' && c <= 'h') {
	    			Casa casa = Tabuleiro.getCasa(c, l);
		    		//System.out.println("rei " + getCor() + " iterando na casa " + casa);
		    		
	    			casasAtacadas.add(casa);
	    			
	    			//System.out.println("casa " + casa + " adicionada na lista de casas atacadas");
	    			//System.out.println("casa " + casa + " inválida: " + casaInvalida(casa, oponente));
	    			
			        if (casa.getPeca() == null && !casaInvalida(casa, oponente)) {		//Casa sem peça e válida
			        	System.out.println("casa livre: " + casa);
			            lancesValidos.add(casa);
			        } else if (casa.getPeca() != null && !(casa.getPeca().getCor().equals(this.getCor()))  && !casaInvalida(casa, oponente)) {		//Casa com peça adversária e válida
			        	System.out.println("casa ocupada e válida: " + casa);
			            lancesValidos.add(casa);
			        	capturasValidas.add(casa.getPeca());
			        }
	    		}
	    	}
	    }
	   
	    this.setCasasAtacadas(casasAtacadas);
	    this.setLancesPossiveis(lancesValidos);
	    this.setCapturasPossiveis(capturasValidas);
	    
	    System.out.println("Lances válidos para o rei " + getCor() + " após atualização: " + getLancesPossiveis());
	    return lancesValidos.size();
	}
	
	public boolean casaInvalida(Casa casa, Jogador oponente) {
		ArrayList<Casa> casasInvalidas = oponente.getCasasAtacadas();
		for(int i = 0; i < casasInvalidas.size(); i++) {
			//System.out.println(casasInvalidas.get(i) + " //// " + casa);
			if(casasInvalidas.get(i).toString().equals(casa.toString())) {
				System.out.println("casa inválida: " + casa);
				return true;
			}
		}
		return false;
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
