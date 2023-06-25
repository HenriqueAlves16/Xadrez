package Xadrez;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

public abstract class Peca extends JPanel{
	//Importando imagem:
	private ImageIcon imagem;
	
	//Mudando a escala:
	private ImageIcon resizedIcon;
		
	private final String cor;
	private Casa posicao;
	private boolean capturado;
	private ArrayList<Casa> casasBase;
	private ArrayList<Casa> casasValidas;
	private ArrayList<Casa> casasAtacadas;
	

	private ArrayList<Peca> capturasPossiveis;
	private Jogo jogo;

	
	//Construtor:
	public Peca(String cor, Casa posicao, String path) {
		this.cor = cor;
		this.posicao = posicao;
		this.capturado = false;
		casasBase = new ArrayList<Casa>();
		capturasPossiveis = new ArrayList<Peca>();
		casasAtacadas = new ArrayList<Casa>();
		
		imagem = new ImageIcon(path);

		//Mudando a escala:
		int newWidth = imagem.getIconWidth() / 2;
		int newHeight = imagem.getIconHeight() / 2;
		Image resizedImage = imagem.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
		resizedIcon = new ImageIcon(resizedImage);
	}

	//Getters e setters:
	public String getCor() {
		return cor;
	}

	public Casa getPosicao() {
		return posicao;
	}

	public void setPosicao(Casa posicao) {
		this.posicao = posicao;
	}

	public boolean isCapturado() {
		return capturado;
	}

	public void setCapturado(boolean capturado) {
		this.capturado = capturado;
	}

	public ArrayList<Casa> getCasasBase() {
		casasBase();
		return casasBase;
	}

	public void setCasasBase(ArrayList<Casa> casasBase) {
		this.casasBase = casasBase;
	}

	public ArrayList<Peca> getCapturasPossiveis() {
		return capturasPossiveis;
	}

	public void setCapturasPossiveis(ArrayList<Peca> capturasPossiveis) {
		this.capturasPossiveis = capturasPossiveis;
	}
	
	public ArrayList<Casa> getCasasAtacadas() {
		return casasAtacadas;
	}

	public void setCasasAtacadas(ArrayList<Casa> casasAtacadas) {
		this.casasAtacadas = casasAtacadas;
	}
	
	public ImageIcon getResizedIcon() {
		return resizedIcon;
	}

	public void setResizedIcon(ImageIcon resizedIcon) {
		this.resizedIcon = resizedIcon;
	}

	public Jogo getJogo() {
		return jogo;
	}

	public void setJogo(Jogo jogo) {
		this.jogo = jogo;
	}
	
	public ArrayList<Casa> getCasasValidas() {
		return casasValidas;
	}

	public void setCasasValidas(ArrayList<Casa> casasValidas) {
		this.casasValidas = casasValidas;
	}

	//toString():
	@Override
	public String toString() {
		if(this.cor.equals("branco")) {
			return "W";
		}
		return "B";
	}
	
	public ArrayList<Casa> casasValidas() {
		ArrayList<Casa> casasValidas = new ArrayList<Casa>();
		ArrayList<Casa> casasBase = getCasasBase();
		Jogador jogadorCorrespondente = ("branco".equals(getCor())) ? getJogo().getJogadorBranco() : getJogo().getJogadorPreto();
		
		for(int i = 0; i < casasBase.size(); i++) {
			Casa casaBase = casasBase.get(i);
			if(jogadorCorrespondente.lanceMantemReiProtegido(this, casaBase)) {
				casasValidas.add(casaBase);
			}
		}
		setCasasValidas(casasValidas);
		return casasValidas;
	}
	
	public abstract int casasBase();
			
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
        resizedIcon.paintIcon(this, g, 0, 0);
	}
	


}
