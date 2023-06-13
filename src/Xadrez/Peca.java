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
	
	private Point imageCorner;
	private Point prevPt;
	
	private final String cor;
	private Casa posicao;
	private boolean capturado;
	private ArrayList<Casa> lancesPossiveis;
	private ArrayList<Peca> capturasPossiveis;
	
	//Construtor:
	public Peca(String cor, Casa posicao, int x, int y, String path) {
		this.cor = cor;
		this.posicao = posicao;
		this.capturado = false;
		lancesPossiveis = new ArrayList<Casa>();
		capturasPossiveis = new ArrayList<Peca>();
		
		imagem = new ImageIcon(path);
		imageCorner = new Point(x, y); //fazer com posicao ao invés de x,y

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

	public ArrayList<Casa> getLancesPossiveis() {
		return lancesPossiveis;
	}

	public void setLancesPossiveis(ArrayList<Casa> lancesPossiveis) {
		this.lancesPossiveis = lancesPossiveis;
	}

	public ArrayList<Peca> getCapturasPossiveis() {
		return capturasPossiveis;
	}

	public void setCapturasPossiveis(ArrayList<Peca> capturasPossiveis) {
		this.capturasPossiveis = capturasPossiveis;
	}

	public ImageIcon getResizedIcon() {
		return resizedIcon;
	}

	public void setResizedIcon(ImageIcon resizedIcon) {
		this.resizedIcon = resizedIcon;
	}

	//toString():
	@Override
	public String toString() {
		return "Peca [cor=" + cor + ", posicao=" + posicao + ", capturado=" + capturado + "]";
	}
	
	public abstract int lancesValidos();
	
	public abstract String getImagePath();
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
        resizedIcon.paintIcon(this, g, (int) imageCorner.getX(), (int) imageCorner.getY());
	}
	


}
