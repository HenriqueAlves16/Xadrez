package Xadrez;
import java.util.ArrayList;
import java.util.Random;

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
        	String corHumano = escolhaCor();
        	String corComputador = (corHumano.equals("branco")) ? "preto" : "branco";
        	
            jogador1 = new JogadorHumano(corHumano);
            jogador2 = new JogadorMaquina(corComputador);
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
	
	public static String escolhaCor() {
		String[] opcoes = {"Brancas", "Pretas", "Aleatório"};
        int escolha = JOptionPane.showOptionDialog(null, "Com quais peças você deseja jogar?", "Configuração de Jogo",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[0]);
     // Verificar a escolha do usuário
        switch (escolha) {
            case 0:
                return "branco";
            case 1:
                // Jogar com as peças pretas
                return "preto";
            case 2:
                Random random = new Random();
                int cor = random.nextInt(2);
                return (cor == 0) ? "branco" : "preto";
            default:
                System.exit(0);
                return "";
        }
	}
}
