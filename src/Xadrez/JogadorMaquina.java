package Xadrez;

import java.util.Random;

public class JogadorMaquina extends Jogador {

	JogadorMaquina(String cor){
		super(cor);
	}
	
	@Override
	public void fazJogada() {
		//Atualiza os lances e capturas possíveis (?)
		verificaLancesPossiveis();
		
		//Escolhe um lance aleatório
		Random random = new Random();
		int numeroAleatorio = random.nextInt(getLancesPossiveis().size());
		Lance lanceAleatorio = getLancesPossiveis().get(numeroAleatorio);

		Peca pecaSelecionada = lanceAleatorio.getPecaMovida();
		Casa casaOrigem = pecaSelecionada.getPosicao();
		Casa casaDestino = lanceAleatorio.getCasaDestino();
		
		//Muda o tabuleiro fazendo o lance aleatório
		getJogo().getTabuleiro().mudaTabuleiro(casaOrigem, casaDestino);
		
		//Atualiza as listas de controle
		getJogo().atualizaLancesECapturas();
		
		//Escreve o lance feito
		String texto = (getCor().equals("branco")) ? Lance.escreveLance(pecaSelecionada, casaDestino, getJogo().getNumeroLance()) : Lance.escreveLance(pecaSelecionada, casaDestino);
		Lance.escreveNoArquivo(texto);
	}

}
