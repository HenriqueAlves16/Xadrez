package Xadrez;

import java.util.Random;

public class JogadorMaquina extends Jogador {

	JogadorMaquina(String cor){
		super(cor);
	}
	
	@Override
	public void fazJogada(Peca peca, Casa casa) {
		System.out.println("//////////////////////" + getJogo().verificaXeque() + "//////////////////");	
		Peca pecaSelecionada;
		Casa casaOrigem;
		Casa casaDestino;
		String xeque = getJogo().verificaXeque();
		
		//Atualiza os lances e capturas possíveis
		verificaLancesPossiveis();
		
		if(xeque.equals(getCor())) {
			System.out.println("Lance aleatório xeque");
			
			Random random = new Random();
			int numeroAleatorio = random.nextInt(getLancesPossiveisXeque().size());
			Lance lanceAleatorio = getLancesPossiveisXeque().get(numeroAleatorio);
			
			pecaSelecionada = lanceAleatorio.getPecaMovida();
			casaOrigem = pecaSelecionada.getPosicao();
			casaDestino = lanceAleatorio.getCasaDestino();
			
		}	else	{
			System.out.println("Lance aleatório sem xeque");
			
			//Escolhe um lance aleatório
			Random random = new Random();
			int numeroAleatorio = random.nextInt(getLancesPossiveis().size());
			Lance lanceAleatorio = getLancesPossiveis().get(numeroAleatorio);

			pecaSelecionada = lanceAleatorio.getPecaMovida();
			casaOrigem = pecaSelecionada.getPosicao();
			casaDestino = lanceAleatorio.getCasaDestino();
		}
		
		//Muda o tabuleiro fazendo o lance aleatório
		getJogo().getTabuleiro().mudaTabuleiro(casaOrigem, casaDestino);
		
		//Atualiza as listas de controle
		getJogo().atualizaLancesECapturas();
		
		//Escreve o lance feito
		String texto = (getCor().equals("branco")) ? Lance.escreveLance(pecaSelecionada, casaDestino, getJogo().getNumeroLance()) : Lance.escreveLance(pecaSelecionada, casaDestino);
		Lance.escreveNoArquivo(texto);

	}

}
