package Xadrez;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Cavalo extends Peca {
	public Cavalo(String cor, Casa posicao, String path) {
		super(cor, posicao, path);
		//System.out.println("cavalo " + getCor());
	}
	
	
	public int lancesValidos() {
		ArrayList<Casa> lancesValidos = new ArrayList<Casa>();
		ArrayList<Casa> casasAtacadas = new ArrayList<Casa>();
	    ArrayList<Peca> capturasValidas = new ArrayList<Peca>();

	    char colunaAtual = getPosicao().getColuna();
	    int linhaAtual = getPosicao().getLinha();
	    
	    int[] deltas = {-2, -1, 1, 2};
	    
	    for (int delta1 : deltas) {
	        for (int delta2 : deltas) {
	            if (Math.abs(delta1) != Math.abs(delta2)) {
	                try {
	                    Casa casa = Tabuleiro.getCasa((char) (colunaAtual + delta1), linhaAtual + delta2);
	                    casasAtacadas.add(casa);
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
	    this.setCasasAtacadas(casasAtacadas);
	    return lancesValidos.size();
	}
	
	public ArrayList<Casa> casasValidas() {
		ArrayList<Casa> casasValidas = new ArrayList<Casa>();
		Jogador jogadorCorrespondente = ("branco".equals(getCor())) ? getJogo().getJogadorBranco() : getJogo().getJogadorPreto();
		for(Casa casa : getLancesPossiveis()) {
			if(jogadorCorrespondente.lanceMantemReiProtegido(this, casa)) {
				casasValidas.add(casa);
			}
		}
		return casasValidas;
	}
	
	public static String getImagePath(String cor) {
		if(cor.equals("branco")) {
			return "Imagens/w_knight_png_128px.png";
		}	else	{
			return "Imagens/b_knight_png_128px.png";
		}
	}
	
	
	
	public static ImageIcon getResizedIcon(String cor) {
		Cavalo cavaloBranco = new Cavalo("branco", null, getImagePath("branco"));
		Cavalo cavaloPreto = new Cavalo("preto", null, getImagePath("preto"));
		ImageIcon resizedIcon = (cor.equals("branco")) ? cavaloBranco.getResizedIcon() : cavaloPreto.getResizedIcon();
		
		return resizedIcon;
	}
	
	@Override
	public String toString() {
		return super.toString() + "N";
	}
}
