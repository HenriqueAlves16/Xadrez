package Xadrez;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Tabuleiro extends JPanel implements  MouseListener, MouseMotionListener{
	private static final long serialVersionUID = 1L;
	private static Casa[][] tabuleiro;
	private int xPressed, yPressed, mouseX, mouseY;
	private boolean dragging;
	private Peca pecaSelecionada, pecaClicada, pecaTemp;
	private Casa casaSelecionada;
	private Jogo jogo;
	private String orientacao;
	
	
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
	
	public Casa getCasaSelecionada() {
		return casaSelecionada;
	}

	public void setCasaSelecionada(Casa casaSelecionada) {
		this.casaSelecionada = casaSelecionada;
	}

	public Peca getPecaClicada() {
		return pecaClicada;
	}

	public void setPecaClicada(Peca pecaClicada) {
		this.pecaClicada = pecaClicada;
	}
	
	public String getOrientacao() {
		return orientacao;
	}

	public void setOrientacao(String orientacao) {
		this.orientacao = orientacao;
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

	//Método que muda o tabuleiro quando um lance ocorre:
	public void mudaTabuleiro(Casa casaOrigem, Casa casaDestino) {
		Peca peca = casaOrigem.getPeca();
		if(peca.getCasasBase().contains(casaDestino)){
			casaDestino.setPeca(peca);
			casaOrigem.setPeca(null);
			peca.setPosicao(casaDestino);
			repaint();
		}
	}

	//Método que muda o tabuleiro a partir de um lance especial:
	public void mudaTabuleiroEspecial(Lance lanceEspecial) {
		Casa casaOrigem = lanceEspecial.getCasaOrigem();
		Casa casaDestino = lanceEspecial.getCasaDestino();
		
		if(lanceEspecial.getPecaMovida() instanceof Peao) {		//En Passant
			Casa casaPeaoCapturado = getCasa(casaDestino.getColuna(), casaOrigem.getLinha());
			casaOrigem.setPeca(null);
			casaDestino.setPeca(lanceEspecial.getPecaMovida());
			casaPeaoCapturado.setPeca(null);
			lanceEspecial.getPecaMovida().setPosicao(casaDestino);
		}	else if(lanceEspecial.getPecaMovida() instanceof Rei) {		//Roque
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
	}
	
	//Método que muda o tabuleiro temporariamente:
	public boolean mudaTabuleiroTemp(Casa casaOrigem, Casa casaDestino) {
		Peca peca = casaOrigem.getPeca();
		pecaTemp = casaDestino.getPeca();
	    if (peca.getCasasBase().contains(casaDestino)) {
	        casaDestino.setPeca(peca);
	        casaOrigem.setPeca(null);
	    	
	        peca.setPosicao(casaDestino);
	        return true;
	    }
		return false;
	}

	//Método que volta o tabuleiro ao estado anterior após uma mudança temporária:
	public void desfazerLance(Casa casaOrigem, Casa casaDestino) {
	    casaOrigem.setPeca(casaDestino.getPeca());

		if(casaOrigem.getPeca() != null) {
			casaOrigem.getPeca().setPosicao(casaOrigem);
		}
		
		casaDestino.setPeca(pecaTemp);
		if(casaDestino.getPeca() != null) {
			casaDestino.getPeca().setPosicao(casaDestino);
		}
	}

	//Método que imprime o tabuleiro graficamente:
	@Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);                
        setSize(600,600);
        
        desenhaCasas(g);
        desenhaUltimoLance(g);
        if(casaSelecionada != null) {
        	int x = (orientacao.equals("branco")) ? ((casaSelecionada.getColuna() - 'a') * Casa.getTamanhoCasa()) : ((7 - (casaSelecionada.getColuna() - 'a')) * Casa.getTamanhoCasa());
        	int y = (orientacao.equals("branco")) ? ((8 - casaSelecionada.getLinha()) * Casa.getTamanhoCasa()) : ((casaSelecionada.getLinha() - 1) * Casa.getTamanhoCasa());
        	desenhaCasaDegrade(x, y, Color.black, Color.white, g);
        }
        if(pecaClicada != null) {
        	desenhaLancesPossiveis(pecaClicada, g);
        }
        acusaXeque(g);
        desenhaPecas(g);
        if(mouseX < 600 && mouseY < 600 && mouseX > 0 && mouseY > 0) {
        	desenhaPecaArrastada(g);
        }	else	{
        	dragging = false;
        	desenhaPecas(g);
        }
	}
	
	//Método que pinta a casa do rei se ele está em xeque
	private void acusaXeque(Graphics g) {
		String jogadorEmXeque = jogo.verificaXeque();
		if(!jogadorEmXeque.equals("")) {
			Rei reiEmXeque = jogo.encontraRei(jogadorEmXeque);
			Casa casaRei = reiEmXeque.getPosicao();
		    int y = orientacao.equals("branco") ? ((8 - casaRei.getLinha()) * Casa.getTamanhoCasa()) : ((casaRei.getLinha() - 1) * Casa.getTamanhoCasa());
		    int x = orientacao.equals("branco") ? ((casaRei.getColuna() - 'a') * Casa.getTamanhoCasa()) : ((7 - (casaRei.getColuna() - 'a')) * Casa.getTamanhoCasa());
			desenhaCasaDegrade(x, y, new Color(120, 0, 0), new Color(255, 51, 51), g);
		}
	}
	
	//Método que pinta as casas em que foram feitos os últimos lances
	private void desenhaUltimoLance(Graphics g) {
		try {
			Lance ultimoLance = jogo.getUltimoLance();
			Casa casaOrigem = ultimoLance.getCasaOrigem();
			Casa casaDestino = ultimoLance.getCasaDestino();
			Color corOrigem = ((casaOrigem.getColuna() + casaOrigem.getLinha()) % 2 == 1) ? Color.WHITE : Color.GRAY;
			Color corDestino = ((casaDestino.getColuna() + casaDestino.getLinha()) % 2 == 1) ? Color.WHITE : Color.GRAY;
			int x0, y0, x1, y1;
			
			if(orientacao.equals("branco")) {
			    x0 = (casaOrigem.getColuna() - 'a') * Casa.getTamanhoCasa();
				y0 = (8 - casaOrigem.getLinha()) * Casa.getTamanhoCasa();
			    x1 = (casaDestino.getColuna() - 'a') * Casa.getTamanhoCasa();
			    y1 = (8 - casaDestino.getLinha()) * Casa.getTamanhoCasa();
			}	else	{
				x0 = (7 - (casaOrigem.getColuna() - 'a')) * Casa.getTamanhoCasa();
				y0 = (casaOrigem.getLinha() - 1) * Casa.getTamanhoCasa();
			    x1 = (7 - (casaDestino.getColuna() - 'a')) * Casa.getTamanhoCasa();
			    y1 = (casaDestino.getLinha() - 1) * Casa.getTamanhoCasa(); 
			}
		    
		    desenhaCasaDegrade(x0, y0, new Color(255, 255, 150), corOrigem, g);
		    desenhaCasaDegrade(x1, y1, new Color(255, 255, 150), corDestino, g);

		}	catch(NullPointerException e) {}
	}
	
	//Método que desenha as casas no tabuleiro:
	private void desenhaCasas(Graphics g) {
		//Percorre tabuleiro
        for (int linha = 0; linha < 8; linha++){
        	for(char col = 0; col < 8; col++) {
	            setVisible(true);                                                              
	            //Pinta as casas
	            if((linha + col) % 2 == 1) {
		            g.setColor(Color.WHITE);                                     
		            g.fillRect(col * Casa.getTamanhoCasa(), (7 - linha) * Casa.getTamanhoCasa(), Casa.getTamanhoCasa(), Casa.getTamanhoCasa());               
	            }	else	{
	            	g.setColor(Color.GRAY);                                          
		            g.fillRect(col * Casa.getTamanhoCasa(), (7 - linha) * Casa.getTamanhoCasa(), Casa.getTamanhoCasa(), Casa.getTamanhoCasa());                       
	            }
        	}
        }
	}
	
	//Método que desenha a peça sendo arrastada
	private void desenhaPecaArrastada(Graphics g) {
		//Caso da peça estar sendo arrastada
        if(dragging) {	
        	Image imagem;
            try {
            	imagem = pecaSelecionada.getResizedIcon().getImage();
            	pecaSelecionada.setVisible(false);
            	g.drawImage(imagem, mouseX - imagem.getWidth(pecaSelecionada) / 2, mouseY - imagem.getHeight(pecaSelecionada) / 2, this);	//posiciona o centro da peça no mouse
            }	catch(NenhumaPecaSelecionadaException e)	{}
        }
	}
	
	//Método que desenha as peças em suas posições
	private void desenhaPecas(Graphics g) {
		for (int linha = 0; linha < 8; linha++) {
        	for(char col = 0; col < 8; col++) {
	            try {
	        		Peca peca = (orientacao.equals("branco")) ? getCasa((char)(col + 'a'), linha + 1).getPeca() : getCasa((char)('h' - col), 8 - linha).getPeca();
	        		if(!dragging || !peca.equals(pecaSelecionada)) {	            
			            Image imagem;
			           	imagem = peca.getResizedIcon().getImage();
			           	if(peca instanceof Rainha) {															//Cada imagem tem tamanho levemente diferente. Esse bloco lida com isso
				           	g.drawImage(imagem, col * Casa.getTamanhoCasa() + 3, (7-linha) * Casa.getTamanhoCasa() + 6, this);
			           	}	else if(peca instanceof Rei || peca instanceof Bispo)	{
				           	g.drawImage(imagem, col * Casa.getTamanhoCasa() + 5, (7-linha) * Casa.getTamanhoCasa() + 6, this);
			           	}	else if(peca instanceof Cavalo || peca instanceof Torre)	{
				           	g.drawImage(imagem, col * Casa.getTamanhoCasa() + 9, (7-linha) * Casa.getTamanhoCasa() + 6, this);
			           	}	else	{
				           	g.drawImage(imagem, col * Casa.getTamanhoCasa() + 11, (7-linha) * Casa.getTamanhoCasa() + 6, this);
			           	}	
			        }
	            } catch(NullPointerException e)	{}   
        	}
        }
	}
	
	//Método que desenha as casas atacadas por uma peça selecionada
	private void desenhaLancesPossiveis(Peca peca, Graphics g) {
		ArrayList<Casa> casasValidas = peca.casasValidas();

		for(int i = 0; i < casasValidas.size(); i++) {
			Casa casa = casasValidas.get(i);
		    int x = (orientacao.equals("branco")) ? ((casa.getColuna() - 'a') * Casa.getTamanhoCasa()) : ((7 - (casa.getColuna() - 'a')) * Casa.getTamanhoCasa());
		    int y = (orientacao.equals("branco")) ? ((8 - casa.getLinha()) * Casa.getTamanhoCasa()) : ((casa.getLinha() - 1) * Casa.getTamanhoCasa());
		    if(casa.getPeca() == null) {
		    	if(casa.equals(casaSelecionada)) {
		    		desenhaCasaDegrade(x, y, new Color(80, 144, 255), Color.white, g);
		    	}	else	{
		    		desenhaCasaDegrade(x, y, new Color(137, 180, 255), Color.white, g);
		    	}
		    }	else	{
		    	if(casa.equals(casaSelecionada)) {
		    		desenhaCasaDegrade(x, y, new Color(255, 23, 23), Color.white, g);
		    	}	else	{
		    		desenhaCasaDegrade(x, y, new Color(255, 80, 80), Color.white, g);
		    	}
		    }
		}
		
		if(peca instanceof MovableSpc) {
			ArrayList<Lance> lancesEspeciais = ((MovableSpc)peca).getLancesEspeciais();
			for(int i = 0; i < lancesEspeciais.size(); i++) {
				Casa casa = lancesEspeciais.get(i).getCasaDestino();
				int x = (orientacao.equals("branco")) ? ((casa.getColuna() - 'a') * Casa.getTamanhoCasa()) : ((7 - (casa.getColuna() - 'a')) * Casa.getTamanhoCasa());
			    int y = (orientacao.equals("branco")) ? ((8 - casa.getLinha()) * Casa.getTamanhoCasa()) : ((casa.getLinha() - 1) * Casa.getTamanhoCasa());
			   
			    if(casa.equals(casaSelecionada)) {
		    		desenhaCasaDegrade(x, y, new Color(113, 63, 228), Color.white, g);
		    	}	else	{
		    		desenhaCasaDegrade(x, y, new Color(148, 114, 228), Color.white, g);
		    	}
			}
		}
	}
	
	private void desenhaCasaDegrade(int x, int y, Color corBorda, Color corCentro, Graphics g) {

		int centroX = x + Casa.getTamanhoCasa() / 2;
	    int centroY = y + Casa.getTamanhoCasa() / 2;

	    double diagonal = Math.sqrt(2) * Casa.getTamanhoCasa();  // Calcula a diagonal do quadrado
	    int raioMaximo = (int) (diagonal / 2);  // Define o raio limite

	    for (int raio = raioMaximo; raio > 0; raio--) {
	    	int Dr = (corCentro.getRed() - corBorda.getRed()) / (raioMaximo);
	    	int Dg = (corCentro.getGreen() - corBorda.getGreen()) / (raioMaximo);
	    	int Db = (corCentro.getBlue() - corBorda.getBlue()) / (raioMaximo);
	    	
	        int red = (int) (corCentro.getRed() - Dr * raio);
	        int green = (int) (corCentro.getGreen() - Dg * raio);
	        int blue = (int) (corCentro.getBlue() - Db * raio);
	        Color currentColor = new Color(red, green, blue);

	        g.setColor(currentColor);
	        g.clipRect(x, y, Casa.getTamanhoCasa(), Casa.getTamanhoCasa());
	        g.fillOval(centroX - raio, centroY - raio, raio * 2, raio * 2);
	        g.setClip(null);
	    }
	}

	//Evento em que o mouse é pressionado
	@Override
    public void mousePressed(MouseEvent e) {
        if(e.getX() < 8 * Casa.getTamanhoCasa() && e.getY() < 8 * Casa.getTamanhoCasa()){                    
            xPressed = e.getX();
            yPressed = e.getY();
            int linhaPressionada = (orientacao.equals("branco")) ? (7 - yPressed / Casa.getTamanhoCasa()) : (yPressed / Casa.getTamanhoCasa());
        	int colunaPressionada = (orientacao.equals("branco")) ? (xPressed/Casa.getTamanhoCasa()) : (7 - xPressed / Casa.getTamanhoCasa());
        	pecaSelecionada = getCasa((char)('a' + colunaPressionada), linhaPressionada + 1).getPeca();
        }
    }
	
	//Evento em que o mouse é solto
	@Override
    public void mouseReleased(MouseEvent e) { 
		//tratar caso de soltar fora do tabuleiro
		try {
	        if(e.getX() < 8 * Casa.getTamanhoCasa() && e.getY() < 8 * Casa.getTamanhoCasa() && pecaSelecionada.getCor().equals(jogo.getTurno())){             //Permite movimento apenas dentro do tabuleiro e na vez do jogador correto ()poderia melhorar criando uma exceção
	        	int xReleased = e.getX();
	        	int yReleased = e.getY();
	            if(e.getButton() == MouseEvent.BUTTON1){           				//Movimenta com o botão esquerdo
	            	//Define as casas selecionada e de destino
	            	int linhaPressionada = (orientacao.equals("branco")) ? (7 - yPressed / Casa.getTamanhoCasa()) : (yPressed / Casa.getTamanhoCasa());
	            	int colunaPressionada = (orientacao.equals("branco")) ? (xPressed/Casa.getTamanhoCasa()) : (7 - xPressed / Casa.getTamanhoCasa());
	            	
	            	int linhaLiberada = (orientacao.equals("branco")) ? (7 - yReleased / Casa.getTamanhoCasa()) : (yReleased / Casa.getTamanhoCasa());
	            	int colunaLiberada = (orientacao.equals("branco")) ? (xReleased/Casa.getTamanhoCasa()) : (7 - xReleased / Casa.getTamanhoCasa());
	            	Casa casaDestino = getCasa((char)('a' + colunaLiberada), linhaLiberada + 1);
	            	//A casa de seleção pode não ter peça, por isso o try-catch
	            	try {
		            	Peca pecaSelecionada = tabuleiro[linhaPressionada][colunaPressionada].getPeca();

	                    //Executa o movimento:
	                    if(pecaSelecionada.getCasasBase().contains(casaDestino)){ //É lance normal
	                    	jogo.fazLance(pecaSelecionada, casaDestino);
	                    //É en passant
		                }	else if(pecaSelecionada instanceof Peao) {		
		                	ArrayList<Lance> lancesEspeciais = ((Peao)pecaSelecionada).getLancesEspeciais();
		                	for(int i = 0; i < lancesEspeciais.size(); i++) {	//Percorre os lances especiais do peão
		                		if(lancesEspeciais.get(i).getCasaDestino().toString().equals(casaDestino.toString())) {	//Lista de lances especiais contém a casa de destino
		                			jogo.fazLance(pecaSelecionada, casaDestino);
		                		}
		                	}
		                //É roque
		                }	else if(pecaSelecionada instanceof Rei) {
		                	ArrayList<Lance> lancesEspeciais = ((Rei)pecaSelecionada).getLancesEspeciais();
		                	try {
			                	for(int i = 0; i < lancesEspeciais.size(); i++) {	//Percorre os lances especiais do rei
			                		if(lancesEspeciais.get(i).getCasaDestino().toString().equals(casaDestino.toString())) {	//Lista de lances especiais contém a casa de destino
			                			jogo.fazLance(pecaSelecionada, casaDestino);
			                		}
			                	}
		                	} catch(ArrayIndexOutOfBoundsException g) {}
		                }
	            	} catch(NullPointerException exp) {}
	            
	            }
	            dragging = false;
	            repaint();
	        }
		} catch(NullPointerException t)	{
			
		} catch(ArrayIndexOutOfBoundsException exc2) {}
    }
	
    @Override
    public void mouseDragged(MouseEvent e) {
    	try {
    		if(pecaSelecionada == null) {
    			throw new NenhumaPecaSelecionadaException();
    		}
	    	if(pecaSelecionada.getCor().equals(jogo.getTurno())) {
		    	mouseX = e.getX();
		        mouseY = e.getY();
		        dragging = true;
		        repaint();
	    	}
    	} catch(NenhumaPecaSelecionadaException exc) {}
    }	
    
    @Override
    public void mouseEntered(MouseEvent e) { 
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setCursor(Cursor.getDefaultCursor());
    }

    @Override
    public void mouseMoved(MouseEvent e) {    	
	    int x = e.getX();
	    int y = e.getY();
	    int linha = (orientacao.equals("branco")) ? (7 - y / Casa.getTamanhoCasa()) : (y / Casa.getTamanhoCasa());
	    int coluna = (orientacao.equals("branco")) ? (x / Casa.getTamanhoCasa()) : (7 - x / Casa.getTamanhoCasa());
	    try {
	        Casa casaMouse = tabuleiro[linha][coluna];
		    
		    //Verifique se as coordenadas do mouse estão dentro do tabuleiro
		    if (!casaMouse.equals(getCasaSelecionada()) && linha >= 0 && linha < 8 && coluna >= 0 && coluna < 8) {
		        //Atualize a aparência da casa correspondente
		        setCasaSelecionada(casaMouse);
		        repaint();
		    }
	    }	catch(IndexOutOfBoundsException exc) {}
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    	int x = e.getX();
	    int y = e.getY();
	    int linha = (orientacao.equals("branco")) ? (8 - y / Casa.getTamanhoCasa()) : (y / Casa.getTamanhoCasa() + 1);
	    char coluna = (orientacao.equals("branco")) ? ((char)((x / Casa.getTamanhoCasa()) + 'a')) : ((char)('h' - (x / Casa.getTamanhoCasa())));
    	boolean fezLance = false;
	    Casa casaClicada = getCasa(coluna, linha);

    	try	{
    		if(pecaClicada == null) {
        		throw new NenhumaPecaSelecionadaException();
        	}
		    if(e.getButton() == MouseEvent.BUTTON1 && pecaClicada.getCor().equals(jogo.getTurno())){           				//Movimenta com o botão esquerdo
	        	Casa casaDestino = getCasa(coluna, linha);	
	        	Peca pecaSelecionada = pecaClicada;
	            //Executa o movimento:
	            if(pecaSelecionada.getCasasBase().contains(casaDestino)){ //É lance normal
	            	fezLance = jogo.fazLance(pecaSelecionada, casaDestino);
	            //É en passant
	            }	else if(pecaSelecionada instanceof Peao) {
	            	ArrayList<Lance> lancesEspeciais = ((Peao)pecaSelecionada).getLancesEspeciais();
	            	for(int i = 0; i < lancesEspeciais.size(); i++) {	//Percorre os lances especiais do peão
	            		if(lancesEspeciais.get(i).getCasaDestino().toString().equals(casaDestino.toString())) {	//Lista de lances especiais contém a casa de destino
	            			fezLance = jogo.fazLance(pecaSelecionada, casaDestino);
	            		}
	            	}
	            //É roque
	            }	else if(pecaSelecionada instanceof Rei) {
	            	ArrayList<Lance> lancesEspeciais = ((Rei)pecaSelecionada).getLancesEspeciais();
	            	try {
	                	for(int i = 0; i < lancesEspeciais.size(); i++) {	//Percorre os lances especiais do rei
	                		if(lancesEspeciais.get(i).getCasaDestino().toString().equals(casaDestino.toString())) {	//Lista de lances especiais contém a casa de destino
	                			fezLance = jogo.fazLance(pecaSelecionada, casaDestino);
	                		}
	                	}
	            	} catch(ArrayIndexOutOfBoundsException g) {}
	            }
	            jogo.finalizaJogo();
	        }
    	}	catch(NenhumaPecaSelecionadaException exc) {
    		System.out.println("Impossível fazer o lance: " + exc.getMessage());
    	}
    	
    	//Atualizando a pecaClicada
    	if(fezLance || pecaClicada == casaClicada.getPeca()) {
	    	setPecaClicada(null);
	    }	else   {
	    	try {
	    		pecaClicada = casaClicada.getPeca();
	    		if(!pecaClicada.getCor().equals(jogo.getTurno())) {
	    			throw new PecaOponenteSelecionadaException();
	    		}
	    	}	catch(PecaOponenteSelecionadaException exc) {
	    		setPecaClicada(null);
	    	}	catch(NullPointerException exc2) {}
	    }		    
        repaint();
    }
}
