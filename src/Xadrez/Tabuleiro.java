package Xadrez;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;


public class Tabuleiro extends JPanel implements  MouseListener, MouseMotionListener{
	private static Casa[][] tabuleiro;
	private static final int TAMANHO_CASA = 75;
	
	//Inicializa o tabuleiro na configuração inicial
	Tabuleiro(){
		tabuleiro = new Casa[8][8];
		for (int l = 0; l < 8; l++) {
		    for (int c = 0; c < 8; c++) {
		        tabuleiro[l][c] = new Casa((char)(c + 'a'), l + 1, null);
		    }
		}
		inicializaTabuleiro(tabuleiro);
		atualizaLancesECapturas();
		
	}
	
	//Inicializa o tabuleiro com uma peça em determinada posicao:
	/*Tabuleiro(Peca peca){
		tabuleiro = new Casa[8][8];
		for (int l = 0; l < 8; l++) {
		    for (int c = 0; c < 8; c++) {
		        tabuleiro[l][c] = new Casa((char)(c + 'a'), l + 1);
		    }
		}
		adicionaPeca(peca);
		atualizaLancesECapturas();
	}
	
	//Inicializa o tabuleiro com uma lista de peças nas posições determinadas:
	Tabuleiro(ArrayList<Peca> pecas){
		tabuleiro = new Casa[8][8];
		for (int l = 0; l < 8; l++) {
		    for (int c = 0; c < 8; c++) {
		        tabuleiro[l][c] = new Casa((char)(c + 'a'), l + 1);
		    }
		}
		for(int i = 0; i < pecas.size(); i++) {
			adicionaPeca(pecas.get(i));
		}
		atualizaLancesECapturas();		
	}*/
	
	
	//Getters e setters:
	public static Casa[][] getTabuleiro() {
		return tabuleiro;
	}

	public static void setTabuleiro(Casa[][] tabuleiroNovo) {
		tabuleiro = tabuleiroNovo;
	}
	
	public static Casa getCasa(char coluna, int linha) {
		return tabuleiro[linha - 1][coluna - 'a'];
	}
	
	//Método que inicializa um tabuleiro em sua configuração inicial:
	public void inicializaTabuleiro(Casa[][] tabuleiro) {
		//Torres:
		tabuleiro[0][0].setPeca(new Torre("branco", tabuleiro[0][0], 0, 0, "Imagens/w_rook_png_128px.png"));
		tabuleiro[0][7].setPeca(new Torre("branco", tabuleiro[0][0], 0, 0, "Imagens/w_rook_png_128px.png"));
		tabuleiro[7][0].setPeca(new Torre("preto", tabuleiro[7][7], 0, 0, "Imagens/b_rook_png_128px.png"));
		tabuleiro[7][7].setPeca(new Torre("preto", tabuleiro[7][7], 0, 0, "Imagens/b_rook_png_128px.png"));
		
		// Cavalos:
	    tabuleiro[0][1].setPeca(new Cavalo("branco", tabuleiro[0][1], 0, 0, "Imagens/w_knight_png_128px.png"));
	    tabuleiro[0][6].setPeca(new Cavalo("branco", tabuleiro[0][6], 0, 0, "Imagens/w_knight_png_128px.png"));
	    tabuleiro[7][1].setPeca(new Cavalo("preto", tabuleiro[7][1], 0, 0, "Imagens/b_knight_png_128px.png"));
	    tabuleiro[7][6].setPeca(new Cavalo("preto", tabuleiro[7][6], 0, 0, "Imagens/b_knight_png_128px.png"));

	    // Bispos:
	    tabuleiro[0][2].setPeca(new Bispo("branco", tabuleiro[0][2], 0, 0, "Imagens/w_bishop_png_128px.png"));
	    tabuleiro[0][5].setPeca(new Bispo("branco", tabuleiro[0][5], 0, 0, "Imagens/w_bishop_png_128px.png"));
	    tabuleiro[7][2].setPeca(new Bispo("preto", tabuleiro[7][2], 0, 0, "Imagens/b_bishop_png_128px.png"));
	    tabuleiro[7][5].setPeca(new Bispo("preto", tabuleiro[7][5], 0, 0, "Imagens/b_bishop_png_128px.png"));

	    // Rainhas:
	    tabuleiro[0][3].setPeca(new Rainha("branco", tabuleiro[0][3], 0, 0, "Imagens/w_queen_png_128px.png"));
	    tabuleiro[7][3].setPeca(new Rainha("preto", tabuleiro[7][3], 0, 0, "Imagens/b_queen_png_128px.png"));

	    // Reis:
	    tabuleiro[0][4].setPeca(new Rei("branco", tabuleiro[0][4], 0, 0, "Imagens/w_king_png_128px.png"));
	    tabuleiro[7][4].setPeca(new Rei("preto", tabuleiro[7][4], 0, 0, "Imagens/b_king_png_128px.png"));
	    
	    //Peões:
	    for(int i = 0; i < 8; i++) {
	    	tabuleiro[1][i].setPeca(new Peao("branco", tabuleiro[1][i], 0, 0, "Imagens/w_pawn_png_128px.png"));
	    	tabuleiro[6][i].setPeca(new Peao("preto", tabuleiro[1][i], 0, 0, "Imagens/b_pawn_png_128px.png"));
	    }
	    
	    //Casas vazias:
	    for (int i = 2; i < 6; i++) {
	        for (int j = 0; j < 8; j++) {
	            tabuleiro[i][j].setPeca(null);
	        }
	    }
	}
	
	//Método que imprime o tabuleiro:
	public static void imprimeTabuleiro() throws NullPointerException{
		for (int l = 7; l >= 0; l--) {
		    for (int c = 0; c < 8; c++) {
		    	try {
	                String letra = tabuleiro[l][c].getPeca().toString();
	                System.out.print(letra + "  ");
	            } catch (NullPointerException e) {
	                System.out.print("-  ");
	            }
		    }
		    System.out.println("");
		}
	}
	
	public void atualizaLancesECapturas() {
		for (int l = 0; l < 8; l++) {
		    for (int c = 0; c < 8; c++) {
		    	try {
		    		tabuleiro[l][c].getPeca().lancesValidos();
		    	} catch(NullPointerException e) {}
		    }
		}
	}
	
	//Método que adiciona uma peça ao tabuleiro e o atualiza:
	public void adicionaPeca(Peca peca) {
	    int linha = peca.getPosicao().getLinha();
	    int coluna = peca.getPosicao().getColuna();
	    tabuleiro[linha - 1][coluna - 'a'].setPeca(peca);
	}

	//Método que remove uma ´peça e atualiza o tabuleiro:
	public void removePeca(char coluna, int linha, Peca peca) {
	    tabuleiro[linha][coluna - 'a'].setPeca(null);
	}

	//Método que imprime o tabuleiro graficamente:
	@Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);                      
        this.addMouseListener(this);                
        this.addMouseMotionListener(this);

        for (int linha = 0; linha < 8; linha += 1){
        	for(char col = 0; col < 8; col++) {
	            setVisible(true);           // testar fora                                                   
	            setSize(600,600);
	            
	            if((linha + col) % 2 == 0) {
		            g.setColor(Color.GRAY);                                     
		            g.fillRect(col * TAMANHO_CASA, linha * TAMANHO_CASA, TAMANHO_CASA, TAMANHO_CASA);               
	            }	else	{
	            	g.setColor(Color.WHITE);                                          
		            g.fillRect(col * TAMANHO_CASA, linha * TAMANHO_CASA, TAMANHO_CASA, TAMANHO_CASA);                       
	            } 
	            
	            Peca peca = getCasa((char)(col + 'a'), linha + 1).getPeca();
	            Image imagem;
	            try {
	            	imagem = peca.getResizedIcon().getImage();
	            	g.drawImage(imagem, col * TAMANHO_CASA, linha * TAMANHO_CASA, this);
	            }	catch(NullPointerException e)	{}
	            
        	}
        }
	}
}
