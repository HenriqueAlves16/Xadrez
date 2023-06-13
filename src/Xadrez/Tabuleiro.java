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
	private int xReleased, yReleased, xPressed, yPressed;
	
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
	
	//Método que muda o tabuleiro quando um lance ocorre:
	public void mudaTabuleiro(Casa casaOrigem, Casa casaDestino) {
		Peca peca = casaOrigem.getPeca();
		if(peca.getLancesPossiveis().contains(casaDestino)) {
			casaOrigem.setPeca(null);
			casaDestino.setPeca(peca);
			peca.setPosicao(casaDestino);
		}
	}

	//Método que imprime o tabuleiro graficamente:
	@Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);                      
        this.addMouseListener(this);                
        this.addMouseMotionListener(this);

        //Percorre tabuleiro
        for (int linha = 0; linha < 8; linha++){
        	for(char col = 0; col < 8; col++) {
	            setVisible(true);           // testar fora                                                   
	            setSize(640,640);
	            
	            //Pinta as casas
	            if((linha + col) % 2 == 1) {
		            g.setColor(Color.GRAY);                                     
		            g.fillRect(col * TAMANHO_CASA, linha * TAMANHO_CASA, TAMANHO_CASA, TAMANHO_CASA);               
	            }	else	{
	            	g.setColor(Color.WHITE);                                          
		            g.fillRect(col * TAMANHO_CASA, linha * TAMANHO_CASA, TAMANHO_CASA, TAMANHO_CASA);                       
	            } 
        	}
        }
        
        //Loops separados para as peças não serem sobrepostas pelas casas
        for (int linha = 0; linha < 8; linha++){
        	for(char col = 0; col < 8; col++) {
	            //Desenha as imagens:
	            Peca peca = getCasa((char)(col + 'a'), linha + 1).getPeca();
	            Image imagem;
	            try {
	            	imagem = peca.getResizedIcon().getImage();
	            	g.drawImage(imagem, col * TAMANHO_CASA, (7-linha) * TAMANHO_CASA, this);
	            }	catch(NullPointerException e)	{}   
        	}
        }
	}
	
	@Override
    public void mousePressed(MouseEvent e) {
        if(e.getX() < 8 * TAMANHO_CASA && e.getY() < 8 * TAMANHO_CASA){                    // gets the X and Y coordinates of where you click
            //if inside the board
            xPressed = e.getX();
            yPressed = e.getY();
            System.out.println("Pressed, x=" + xPressed + "y=" + yPressed);
        }
    }
	
	@Override
    public void mouseReleased(MouseEvent e) {                   
        if(e.getX() < 8 * TAMANHO_CASA && e.getY() < 8 * TAMANHO_CASA){             //Permite movimento apenas dentro do tabuleiro
        	xReleased = e.getX();
        	yReleased = e.getY();
            String dragPiece;
            if(e.getButton() == MouseEvent.BUTTON1){                 				//Movimenta com o botão esquerdo
            	int linhaPressionada = 7 - yPressed/TAMANHO_CASA;
            	int colunaPressionada = xPressed/TAMANHO_CASA;
            	Casa casaSelecionada = getCasa((char)('a' + colunaPressionada), linhaPressionada + 1);
            			
            	int linhaLiberada = 7 - yReleased/TAMANHO_CASA;            	
            	int colunaLiberada = xReleased/TAMANHO_CASA;
            	Casa casaDestino = getCasa((char)('a' + colunaLiberada), linhaLiberada + 1);
            	
            	//System.out.println(casaSelecionada +" "+ casaDestino);
            	try {
	            	Peca pecaSelecionada = tabuleiro[linhaPressionada][colunaPressionada].getPeca();
	            	String corPeca = pecaSelecionada.getCor();
	            	System.out.println(pecaSelecionada);
	            	
	                if(linhaLiberada == 0 && linhaPressionada == 1 && pecaSelecionada instanceof Peao && corPeca.equals("branco")) {                           // Promoção do peão branco (vai da linha 1 para 0 na interface) -> vira rainha
	                	//dragPiece = "" + xPressed/TAMANHO_CASA + xReleased/TAMANHO_CASA + ChessGame.board[newY/tile][newX/tile] +"Q" +"P";
	                }
	                else if(linhaLiberada == 7 && linhaPressionada == 6 && pecaSelecionada instanceof Peao && corPeca.equals("preto")) {                      //Promoção do peão branco (vai da linha 6 para 7 na interface) 
	                	//dragPiece = "" + xPressed/TAMANHO_CASA + xReleased/TAMANHO_CASA + ChessGame.board[newY/tile][newX/tile] +"q" +"p";
	                }
	                else{
	                	//dragPiece = "" + yPressed/TAMANHO_CASA + xPressed/TAMANHO_CASA + yReleased/TAMANHO_CASA + xReleased/TAMANHO_CASA + ChessGame.board[newY/tile][newX/tile];               // case for all other moves
	                }
	                
                    System.out.println(pecaSelecionada.getLancesPossiveis());
	                if(pecaSelecionada.getLancesPossiveis().contains(casaDestino)){		//é um lance possível
	                	System.out.println("é possível");
	                    mudaTabuleiro(casaSelecionada, casaDestino);
	                }
            	} catch(NullPointerException exp) {}
            	

            }
            repaint();
        }
    }
	 
	//even though we don't use these methods we still need them to avoid an error
    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseDragged(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) { }

    @Override
    public void mouseClicked(MouseEvent e) { }

}
