package Xadrez;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		/*ArrayList<Peca> listaPecas = new ArrayList<Peca>();
		
		// Torre
		Torre torreBranca = new Torre("branco", new Casa('a', 1), 0, 0, "Imagens/w_rook_png_128px.png");
		Torre torrePreta = new Torre("preto", new Casa('a', 8), 0, 600, "Imagens/b_rook_png_128px.png");
		// Cavalos
		Cavalo cavaloBranco = new Cavalo("branco", new Casa('b', 1), 70, 600, "Imagens/w_knight_png_128px.png");
		Cavalo cavaloPreto = new Cavalo("preto", new Casa('b', 8), 70, 0, "Imagens/b_knight_png_128px.png");
		// Bispos
		Bispo bispoBranco = new Bispo("branco", new Casa('c', 1), 140, 600, "Imagens/w_bishop_png_128px.png");
		Bispo bispoPreto = new Bispo("preto", new Casa('c', 8), 140, 0, "Imagens/b_bishop_png_128px.png");
		// Rainhas
		Rainha rainhaBranca = new Rainha("branco", new Casa('d', 1), 210, 600, "Imagens/w_queen_png_128px.png");
		Rainha rainhaPreta = new Rainha("preto", new Casa('d', 8), 210, 0, "Imagens/b_queen_png_128px.png");
		// Reis
		Rei reiBranco = new Rei("branco", new Casa('e', 1), 280, 600, "Imagens/w_king_png_128px.png");
		Rei reiPreto = new Rei("preto", new Casa('e', 8), 280, 0, "Imagens/b_king_png_128px.png");
		// Peões
	    Peao peaoBranco = new Peao("branco", new Casa('g', 2), 350, 600, "Imagens/w_pawn_png_128px.png");
	    Peao peaoPreto = new Peao("preto", new Casa('b', 7), 350, 0, "Imagens/b_pawn_png_128px.png");

		listaPecas.add(torreBranca);
		listaPecas.add(bispoPreto);
		listaPecas.add(reiBranco);
		listaPecas.add(reiPreto);
		listaPecas.add(rainhaPreta);
		listaPecas.add(cavaloPreto);
		listaPecas.add(cavaloBranco);
		listaPecas.add(peaoBranco);
		listaPecas.add(peaoPreto);

		
		new Tabuleiro(listaPecas);
		
		Tabuleiro.imprimeTabuleiro();
		/*System.out.println("Movimentos da torre branca:" + Tabuleiro.getCasa('b', 5).getPeca().lancesValidos());
		System.out.println("Movimentos do bispo preto:" + Tabuleiro.getCasa('e', 5).getPeca().lancesValidos());
		System.out.println("Movimentos do rei branco:" + Tabuleiro.getCasa('d', 4).getPeca().lancesValidos());
		System.out.println("Movimentos do rei preto:" + Tabuleiro.getCasa('h', 8).getPeca().lancesValidos());
		System.out.println("Movimentos da rainha preta:" + rainhaPreta.lancesValidos());
		System.out.println("Movimentos do cavalo branco:" + cavaloBranco.lancesValidos());
		System.out.println("Movimentos da cavalo preto:" + cavaloPreto.lancesValidos());
		System.out.println("Movimentos do peao branco 1:" + peaoBranco.lancesValidos());
		System.out.println("Movimentos da peao preto 1:" + peaoPreto.lancesValidos());
		
		for(Peca peca : listaPecas) {
			System.out.println("Capturas da peca: " + peca.getCapturasPossiveis());
		}
		
		Frame frame = new Frame();
		frame.add(torreBranca);
		frame.add(bispoPreto);
		frame.add(reiBranco);
		frame.add(reiPreto);
		frame.add(rainhaPreta);
		frame.add(cavaloPreto);
		frame.add(cavaloBranco);
		frame.add(peaoBranco);
		frame.add(peaoPreto);
		*/
		Tabuleiro tabuleiro = new Tabuleiro();
		JogadorHumano jogador1 = new JogadorHumano("branco");
		JogadorMaquina jogador2 = new JogadorMaquina("preto");
		
		JFrame f = new JFrame("Xadrez");        //Inicializa a janela
        f.add(tabuleiro);                                                              //Adiciona a interface (JPanel) no frame
        f.setSize(640, 640);                                                     
        f.setVisible(true);                                                      
        f.setResizable(false);                                                   
        f.setLocationRelativeTo(null);                                           
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Jogo jogo = new Jogo(jogador1, jogador2, tabuleiro);

	}

}
