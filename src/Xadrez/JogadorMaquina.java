package Xadrez;

import java.util.Random;

public class JogadorMaquina extends Jogador {

	JogadorMaquina(String cor){
		super(cor);
	}
	
	@Override
	public void fazJogada() {
		verificaLancesPossiveis();
		Random random = new Random();
		int numeroAleatorio = random.nextInt(getLancesPossiveis().size());
		Lance lanceAleatorio = getLancesPossiveis().get(numeroAleatorio);

		Peca pecaSelecionada = lanceAleatorio.getPecaMovida();
		Casa casaOrigem = pecaSelecionada.getPosicao();

		getJogo().getTabuleiro().mudaTabuleiro(casaOrigem, lanceAleatorio.getCasaDestino());
	}

}
