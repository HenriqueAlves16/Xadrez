package Xadrez;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


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
		this.addMouseListener(this);                
        this.addMouseMotionListener(this);
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
		tabuleiro[0][0].setPeca(new Torre("branco", tabuleiro[0][0], "Imagens/w_rook_png_128px.png"));
		tabuleiro[0][7].setPeca(new Torre("branco", tabuleiro[0][7], "Imagens/w_rook_png_128px.png"));
		tabuleiro[7][0].setPeca(new Torre("preto", tabuleiro[7][0], "Imagens/b_rook_png_128px.png"));
		tabuleiro[7][7].setPeca(new Torre("preto", tabuleiro[7][7], "Imagens/b_rook_png_128px.png"));
		
		// Cavalos:
	    tabuleiro[0][1].setPeca(new Cavalo("branco", tabuleiro[0][1], "Imagens/w_knight_png_128px.png"));
	    tabuleiro[0][6].setPeca(new Cavalo("branco", tabuleiro[0][6], "Imagens/w_knight_png_128px.png"));
	    tabuleiro[7][1].setPeca(new Cavalo("preto", tabuleiro[7][1], "Imagens/b_knight_png_128px.png"));
	    tabuleiro[7][6].setPeca(new Cavalo("preto", tabuleiro[7][6], "Imagens/b_knight_png_128px.png"));

	    // Bispos:
	    tabuleiro[0][2].setPeca(new Bispo("branco", tabuleiro[0][2], "Imagens/w_bishop_png_128px.png"));
	    tabuleiro[0][5].setPeca(new Bispo("branco", tabuleiro[0][5], "Imagens/w_bishop_png_128px.png"));
	    tabuleiro[7][2].setPeca(new Bispo("preto", tabuleiro[7][2], "Imagens/b_bishop_png_128px.png"));
	    tabuleiro[7][5].setPeca(new Bispo("preto", tabuleiro[7][5], "Imagens/b_bishop_png_128px.png"));

	    // Rainhas:
	    tabuleiro[0][3].setPeca(new Rainha("branco", tabuleiro[0][3], "Imagens/w_queen_png_128px.png"));
	    tabuleiro[7][3].setPeca(new Rainha("preto", tabuleiro[7][3], "Imagens/b_queen_png_128px.png"));

	    // Reis:
	    tabuleiro[0][4].setPeca(new Rei("branco", tabuleiro[0][4], "Imagens/w_king_png_128px.png"));
	    tabuleiro[7][4].setPeca(new Rei("preto", tabuleiro[7][4], "Imagens/b_king_png_128px.png"));
	    
	    //Peões:
	    for(int i = 0; i < 8; i++) {
	    	tabuleiro[1][i].setPeca(new Peao("branco", tabuleiro[1][i], "Imagens/w_pawn_png_128px.png"));
	    	tabuleiro[6][7 - i].setPeca(new Peao("preto", tabuleiro[6][7 - i], "Imagens/b_pawn_png_128px.png"));
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
		//System.out.println("Origem: " + casaOrigem + " / " + "Destino: " + casaDestino + " / Peca: " + peca);
		if(peca.getLancesPossiveis().contains(casaDestino)){
			casaDestino.setPeca(peca);
			casaOrigem.setPeca(null);
			peca.setPosicao(casaDestino);
		}
		imprimeTabuleiro();
	}
	
	//Método que muda o tabuleiro temporariamente:
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

	//Método que volta o tabuleiro ao estado anterior após uma mudança temporária:
	public void desfazerLance(Casa casaOrigem, Casa casaDestino) {
	    //System.out.println("casa destino: " + casaDestino + casaDestino.getPeca());
		casaOrigem.setPeca(casaDestino.getPeca());
	    //System.out.println("casa origem: " + casaOrigem + casaOrigem.getPeca());

		if(casaOrigem.getPeca() != null) {
			casaOrigem.getPeca().setPosicao(casaOrigem);
		}
		
	   //System.out.println("arrumando casaDestino");
		casaDestino.setPeca(pecaTemp);
		if(casaDestino.getPeca() != null) {
			casaDestino.getPeca().setPosicao(casaDestino);
		}
		
	    //imprimeTabuleiro();
	    System.out.println("desfeito");
	    try {
			//System.out.println("posicao peca casa origem" + casaOrigem.getPeca().getPosicao());
			//System.out.println("posicao peca casa destino" + casaDestino.getPeca().getPosicao());
	    } catch(NullPointerException e) {}
	}
	
	//Método que muda o tabuleiro a partir de um lance especial:
	public void mudaTabuleiroEspecial(Lance lanceEspecial) {
		//System.out.println("\nTabuleiro antes da mudança:");
		imprimeTabuleiro();
		Casa casaOrigem = lanceEspecial.getCasaOrigem();
		Casa casaDestino = lanceEspecial.getCasaDestino();
		//System.out.println("casa de origem lance especial: " + casaOrigem + " casa Destino lance especial: " + casaDestino);
		
		if(lanceEspecial.getPecaMovida() instanceof Peao) {		//En Passant
			Casa casaPeaoCapturado = getCasa(casaDestino.getColuna(), casaOrigem.getLinha());
			System.out.println("Lance especial: enPassant");
			casaOrigem.setPeca(null);
			casaDestino.setPeca(lanceEspecial.getPecaMovida());
			casaPeaoCapturado.setPeca(null);
			lanceEspecial.getPecaMovida().setPosicao(casaDestino);
		}	else if(lanceEspecial.getPecaMovida() instanceof Rei) {		//Roque
			System.out.println("Lance especial: roque");
			Peca rei = lanceEspecial.getPecaMovida();
			
			char colOrigemTorre = (casaDestino.getColuna() == 'g' || casaDestino.getColuna() == 'h')  ?  'h' : 'a';
			char colDestinoTorre = (casaDestino.getColuna() == 'g' || casaDestino.getColuna() == 'h')  ?  'f' : 'd';
			char colDestinoRei = (casaDestino.getColuna() == 'g' || casaDestino.getColuna() == 'h')  ?  'g' : 'c';
			
			Casa casaOrigemTorre = getCasa(colOrigemTorre, casaOrigem.getLinha());
			Casa casaDestinoTorre = getCasa(colDestinoTorre, casaOrigem.getLinha());
			Casa casaDestinoRei = getCasa(colDestinoRei, casaOrigem.getLinha());
			
			Torre torre = (Torre)casaOrigemTorre.getPeca();
			
			casaOrigem.setPeca(null);
			casaDestinoRei.setPeca(rei);
			casaDestinoTorre.setPeca(torre);
			casaOrigemTorre.setPeca(null);
			
			torre.setPosicao(casaDestinoTorre);
			rei.setPosicao(casaDestinoRei);
		}
		//System.out.println("Tabuleiro após a mudança:");
		//imprimeTabuleiro();
	}

	//Método que imprime o tabuleiro graficamente:
	@Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);                      

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
    public void mouseReleased(MouseEvent e) { 
		//tratar caso de soltar fora do tabuleiro
		//try {
	        if(e.getX() < 8 * TAMANHO_CASA && e.getY() < 8 * TAMANHO_CASA && pecaSelecionada.getCor().equals(jogo.getTurno())){             //Permite movimento apenas dentro do tabuleiro e na vez do jogador correto ()poderia melhorar criando uma exceção
	        	xReleased = e.getX();
	        	yReleased = e.getY();
	            if(e.getButton() == MouseEvent.BUTTON1){           				//Movimenta com o botão esquerdo
	            	//Define as casas selecionada e de destino
	            	int linhaPressionada = 7 - yPressed/TAMANHO_CASA;
	            	int colunaPressionada = xPressed/TAMANHO_CASA;
	            	//Casa casaSelecionada = getCasa((char)('a' + colunaPressionada), linhaPressionada + 1);
	            			
	            	int linhaLiberada = 7 - yReleased/TAMANHO_CASA;            	
	            	int colunaLiberada = xReleased/TAMANHO_CASA;
	            	Casa casaDestino = getCasa((char)('a' + colunaLiberada), linhaLiberada + 1);
	            	            	
	            	//A casa de seleção pode não ter peça, por isso o try-catch
	            	try {
		            	Peca pecaSelecionada = tabuleiro[linhaPressionada][colunaPressionada].getPeca();
		            	
		            	//System.out.println("pecaSelecionada antes do movimento: " + pecaSelecionada + " posicao antes do movimento: " + pecaSelecionada.getPosicao());
	                    //System.out.println(pecaSelecionada.getLancesPossiveis());
	                    
	                    //Executa o movimento:
	                    if(pecaSelecionada.getLancesPossiveis().contains(casaDestino)){ //É lance normal
	                    	//System.out.println("tentativa de lance é possível");
	                    	jogo.fazLance(pecaSelecionada, casaDestino);
	                    //É en passant
		                }	else if(pecaSelecionada instanceof Peao) {		
		                	ArrayList<Lance> lancesEspeciais = ((Peao)pecaSelecionada).getLancesEspeciais();
		                	for(int i = 0; i < lancesEspeciais.size(); i++) {	//Percorre os lances especiais do peão
		                		if(lancesEspeciais.get(i).getCasaDestino().toString().equals(casaDestino.toString())) {	//Lista de lances especiais contém a casa de destino
		                			System.out.println("tentativa de enPassant");
		                			jogo.fazLance(pecaSelecionada, casaDestino);
		                		}
		                	}
		                //É roque
		                }	else if(pecaSelecionada instanceof Rei) {
		                	ArrayList<Lance> lancesEspeciais = ((Rei)pecaSelecionada).getLancesEspeciais();
		                	try {
			                	for(int i = 0; i < lancesEspeciais.size(); i++) {	//Percorre os lances especiais do rei
			                		if(lancesEspeciais.get(i).getCasaDestino().toString().equals(casaDestino.toString())) {	//Lista de lances especiais contém a casa de destino
			                			System.out.println("tentativa de roque");
			                			jogo.fazLance(pecaSelecionada, casaDestino);
			                			System.out.println("roque finalizado");
			                		}
			                	}
		                	} catch(ArrayIndexOutOfBoundsException g) {}
		                }
	            	} catch(NullPointerException exp) {}
	            
	            }
	            dragging = false;
    			System.out.println("reimprimindo");
	            repaint();
	            //imprimeTabuleiro();
	        }
		//} catch(NullPointerException t)	{}
	    System.out.println("mouseReleased finalizado");
        jogo.verificaFimDoJogo();
        
        if(jogo.getJogador2() instanceof JogadorMaquina) {
        	do {
	        	jogo.fazLance(null, null);
	        	repaint();
	        	jogo.verificaFimDoJogo();
        	} while(jogo.getUltimoLance() == null);
        }
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
