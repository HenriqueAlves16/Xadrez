package Xadrez;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;

public class PromocaoDialog extends JDialog implements ActionListener{
	
    private Peao peao;
    private JogadorHumano jogador;
    
    public PromocaoDialog(Peao peao, JogadorHumano jogador) {
        setTitle("Escolha a peça de promoção");
        setModal(true);
        setLayout(new GridLayout(1, 4));

        this.peao = peao;
        this.jogador = jogador;

        // Crie os botões de imagem para cada peça de promoção
        ImageIcon cavaloIcon = Cavalo.getResizedIcon(peao.getCor());
        JButton cavaloButton = new JButton(cavaloIcon);
        cavaloButton.setToolTipText("Cavalo");
        cavaloButton.addActionListener(this);

        ImageIcon bispoIcon = Bispo.getResizedIcon(peao.getCor());
        JButton bispoButton = new JButton(bispoIcon);
        bispoButton.setToolTipText("Bispo");
        bispoButton.addActionListener(this);

        ImageIcon torreIcon = Torre.getResizedIcon(peao.getCor());
        JButton torreButton = new JButton(torreIcon);
        torreButton.setToolTipText("Torre");
        torreButton.addActionListener(this);

        ImageIcon rainhaIcon = Rainha.getResizedIcon(peao.getCor());
        JButton rainhaButton = new JButton(rainhaIcon);
        rainhaButton.setToolTipText("Rainha");
        rainhaButton.addActionListener(this);

        // Adicione os botões de imagem ao diálogo
        add(cavaloButton);
        add(bispoButton);
        add(torreButton);
        add(rainhaButton);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String pecaEscolhida = button.getToolTipText();
        jogador.promoverPeao(peao, pecaEscolhida);

        // Feche a caixa de diálogo
        dispose();
    }
}


