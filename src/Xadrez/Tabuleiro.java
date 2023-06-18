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
	private int xReleased, yReleased, xPressed, yPressed, mouseX, mouseY;
	private boolean dragging;
	private Peca pecaSelecionada;
	private Jogo jogo;
	private Peca pecaTemp;
	
	//Inicializa o tabuleiro na configuração inicial
	Tabuleiro(){
		tabuleiro = new Casa[8][8];
		for (int l = 0; l < 8; l++) {
		    for (int c = 0; c < 8; c++) {
		        tabuleiro[l][c] = new Casa((char)(c + 'a'), l + 1, null);
		    }
		}
		inicializaTabuleiro(tabuleiro);
	}

	//Getters e setters:
	
	public Peca getPecaSelecionada() {
		return pecaSelecionada;
	}


	public void setPecaSelecionada(Peca pecaSelecionada) {
		this.pecaSelecionada = pecaSelecionada;
	}


	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}

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
		tabuleiro[0][7].setPeca(new Torre("branco", tabuleiro[0][7], 0, 0, "Imagens/w_rook_png_128px.png"));
		tabuleiro[7][0].setPeca(new Torre("preto", tabuleiro[7][0], 0, 0, "Imagens/b_rook_png_128px.png"));
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
	    	tabuleiro[6][7 - i].setPeca(new Peao("preto", tabuleiro[6][7 - i], 0, 0, "Imagens/b_pawn_png_128px.png"));
	    }
	    
	    //Casas vazias:
	    for (int i = 2; i < 6; i++) {
	        for (int j = 0; j < 8; j++) {
	            tabuleiro[i][j].setPeca(null);
	        }
	    }
	}
	
	//Método que imprime o tabuleiro:
	public static void imprimeTabuleiro() {
		for (int l = 7; l >= 0; l--) {
		    for (int c = 0; c < 8; c++) {
		    	try {
	                String letra = tabuleiro[l][c].getPeca().toString();
	                System.out.print(letra + "  ");
	            } catch (NullPointerException e) {
	                System.out.print("-   ");
	            }
		    }
		    System.out.println("");
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
		System.out.println("mudando tabuleiro");
		Peca peca = casaOrigem.getPeca();
		if(peca.getLancesPossiveis().contains(casaDestino)){
			casaDestino.setPeca(peca);
			casaOrigem.setPeca(null);
			peca.setPosicao(casaDestino);
		}
		//imprimeTabuleiro();
	}
	
	public void mudaTabuleiroTemp(Casa casaOrigem, Casa casaDestino) {
		Peca peca = casaOrigem.getPeca();
		pecaTemp = casaDestino.getPeca();
		//System.out.println("peça temp antes movimento: " + pecaTemp);
	    
	    if (peca.getLancesPossiveis().contains(casaDestino)) {
	    	//System.out.println("casa de destino válida");
	        casaDestino.setPeca(peca);
	    	//System.out.println("casa de destino com a peça " + casaDestino.getPeca());
	        casaOrigem.setPeca(null);
	    	//System.out.println("casa de origem vazia: " + casaOrigem.getPeca());
	    	
	        peca.setPosicao(casaDestino);
	    }
	   // imprimeTabuleiro();
		//System.out.println("peça temp após movimento: " + pecaTemp);
	}

	public void desfazerLance(Casa casaOrigem, Casa casaDestino) {
	    //System.out.println("casa destino: " + casaDestino + casaDestino.getPeca());
		casaOrigem.setPeca(casaDestino.getPeca());
	  //  System.out.println("casa origem: " + casaOrigem + casaOrigem.getPeca());

		if(casaOrigem.getPeca() != null) {
			casaOrigem.getPeca().setPosicao(casaOrigem);
		}
		
	   // System.out.println("arrumando casaDestino");
		casaDestino.setPeca(pecaTemp);
		if(casaDestino.getPeca() != null) {
			casaDestino.getPeca().setPosicao(casaDestino);
		}
		
	    //imprimeTabuleiro();
	    //System.out.println("desfeito");
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
	            setVisible(true);                                                      
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
    
        //Desenha as imagens. Loops separados para as peças não serem sobrepostas pelas casas
        for (int linha = 0; linha < 8; linha++){
        	for(char col = 0; col < 8; col++) {
	            try {
	        		Peca peca = getCasa((char)(col + 'a'), linha + 1).getPeca();
	        		if(!dragging || !peca.equals(pecaSelecionada)) {	            
			            Image imagem;
			           	imagem = peca.getResizedIcon().getImage();
			           	if(peca instanceof Rainha) {															//Cada imagem tem tamanho levemente diferente. Esse bloco lida com isso
				           	g.drawImage(imagem, col * TAMANHO_CASA + 3, (7-linha) * TAMANHO_CASA + 6, this);
			           	}	else if(peca instanceof Rei || peca instanceof Bispo)	{
				           	g.drawImage(imagem, col * TAMANHO_CASA + 5, (7-linha) * TAMANHO_CASA + 6, this);
			           	}	else if(peca instanceof Cavalo || peca instanceof Torre)	{
				           	g.drawImage(imagem, col * TAMANHO_CASA + 9, (7-linha) * TAMANHO_CASA + 6, this);
			           	}	else	{
				           	g.drawImage(imagem, col * TAMANHO_CASA + 11, (7-linha) * TAMANHO_CASA + 6, this);
			           	}	
			        }
	            } catch(NullPointerException e)	{}   
        	}
        }
        
        //Caso da peça estar sendo arrastada
        if(dragging) {	
        	Image imagem;
            try {
            	imagem = pecaSelecionada.getResizedIcon().getImage();
            	pecaSelecionada.setVisible(false);
            	g.drawImage(imagem, mouseX - imagem.getWidth(pecaSelecionada) / 2, mouseY - imagem.getHeight(pecaSelecionada) / 2, this);	//posiciona o centro da peça no mouse
            }	catch(NullPointerException e)	{}
        }
	}
	
	//Evento em que o mouse é pressionado
	@Override
    public void mousePressed(MouseEvent e) {
        if(e.getX() < 8 * TAMANHO_CASA && e.getY() < 8 * TAMANHO_CASA){                    
            xPressed = e.getX();
            yPressed = e.getY();
            int linhaPressionada = 7 - yPressed/TAMANHO_CASA;
        	int colunaPressionada = xPressed/TAMANHO_CASA;
        	pecaSelecionada = getCasa((char)('a' + colunaPressionada), linhaPressionada + 1).getPeca();
            //System.out.println("Pressed, x=" + xPressed + "y=" + yPressed);
        }
    }
	
	//Evento em que o mouse é solto
	@Override
    public void mouseReleased(MouseEvent e) { 		//tratar caso de soltar fora do tabuleiro
		//try {
	        if(e.getX() < 8 * TAMANHO_CASA && e.getY() < 8 * TAMANHO_CASA && pecaSelecionada.getCor().equals(jogo.getTurno())){             //Permite movimento apenas dentro do tabuleiro e na vez do jogador correto ()poderia melhorar criando uma exceção
	        	xReleased = e.getX();
	        	yReleased = e.getY();
	            if(e.getButton() == MouseEvent.BUTTON1){           				//Movimenta com o botão esquerdo
	            	//Define as casas selecionada e de destino
	            	int linhaPressionada = 7 - yPressed/TAMANHO_CASA;
	            	int colunaPressionada = xPressed/TAMANHO_CASA;
	            	Casa casaSelecionada = getCasa((char)('a' + colunaPressionada), linhaPressionada + 1);
	            			
	            	int linhaLiberada = 7 - yReleased/TAMANHO_CASA;            	
	            	int colunaLiberada = xReleased/TAMANHO_CASA;
	            	Casa casaDestino = getCasa((char)('a' + colunaLiberada), linhaLiberada + 1);
	            	            	
	            	//A casa de seleção pode não ter peça, por isso o try-catch
	            	try {
		            	Peca pecaSelecionada = tabuleiro[linhaPressionada][colunaPressionada].getPeca();
		            	
		            	System.out.println(pecaSelecionada + "" + pecaSelecionada.getPosicao());
	                    System.out.println(pecaSelecionada.getLancesPossiveis());
	                    
	                    //Executa o movimento:
	                    if(pecaSelecionada.getLancesPossiveis().contains(casaDestino)){
	                    	System.out.println("tentativa de lance é possível");
	                    	jogo.fazLance(pecaSelecionada, casaDestino);
		                }
		                
	            	} catch(NullPointerException exp) {}
	            
	            }
	            dragging = false;	
	            repaint();
	            //imprimeTabuleiro();
	        }
		//} catch(NullPointerException t)	{}
    }
	
    @Override
    public void mouseDragged(MouseEvent e) {
    	//try {
	    	if(pecaSelecionada.getCor().equals(jogo.getTurno())) {
		    	mouseX = e.getX();
		        mouseY = e.getY();
		        dragging = true;
		        repaint();
	    	}
    	//} catch(NullPointerException t) {}
    }	
    
	//Métodos que devem ser "implementados" devido à interface
    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) { }

    @Override
    public void mouseClicked(MouseEvent e) { }

}
