package Xadrez;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) {
		
		Jogador jogador1 = null;
		Jogador jogador2 = null;
		int opcaoJogo = JOptionPane.showOptionDialog(null, "Escolha o modo de jogo", "Modo de jogo", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new String[] {"Jogador vs Jogador", "Jogador vs Computador"}, null);
		if (opcaoJogo == 0) {
            // Modo Jogador vs Jogador
            jogador1 = new JogadorHumano("branco");
            jogador2 = new JogadorHumano("preto");
        } else if(opcaoJogo == 1){
            // Modo Jogador vs Computador
            jogador1 = new JogadorHumano("branco");
            jogador2 = new JogadorMaquina("preto");
        } else	{
        	System.exit(0);
        }
		Tabuleiro tabuleiro = new Tabuleiro();
	
		
		JFrame f = new JFrame("Xadrez");        //Inicializa a janela
        f.add(tabuleiro);                                                              //Adiciona a interface (JPanel) no frame
        f.setSize(615, 637);                                                     
        f.setVisible(true);                                                      
        f.setResizable(false);                                                   
        f.setLocationRelativeTo(null);                                           
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//System.out.println("jogo criado");
		Jogo jogo = new Jogo(jogador1, jogador2, tabuleiro);

	}

}
