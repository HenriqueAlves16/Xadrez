package Xadrez;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


public class ReiBranco extends JPanel {
	//Importando imagem:
	ImageIcon rei = new ImageIcon("Imagens/w_king_png_128px.png");
	
	//Mudando a escala:
	int newWidth = rei.getIconWidth() / 2;
    int newHeight = rei.getIconHeight() / 2;
    Image resizedImage = rei.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT);
    ImageIcon resizedIcon = new ImageIcon(resizedImage);
	
	Point imageCorner;
	Point prevPt;
	
	ReiBranco() {
		imageCorner = new Point(0,0);
		ClickListener clickListener = new ClickListener();
		DragListener dragListener = new DragListener();
		this.addMouseListener(clickListener);
		this.addMouseMotionListener(dragListener);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
        resizedIcon.paintIcon(this, g, (int) imageCorner.getX(), (int) imageCorner.getY());
	}
	
	private class ClickListener extends MouseAdapter {
		public void mousePressed(MouseEvent e) {	
			prevPt = e.getPoint();
		}
	}
	
	private class DragListener extends MouseMotionAdapter{
		public void mouseDragged(MouseEvent e) {
			Point currentPt = e.getPoint();
			imageCorner.translate(
					(int)(currentPt.getX() - prevPt.getX()),
					(int)(currentPt.getY() - prevPt.getY())
			);
			prevPt = currentPt;
			repaint();
		}
	}
}
