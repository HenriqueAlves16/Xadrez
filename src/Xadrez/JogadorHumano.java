package Xadrez;

public class JogadorHumano extends Jogador {
	
	JogadorHumano(String cor){
		super(cor);
	}
	

	@Override
	public void fazJogada(Peca pecaSelecionada, Casa casaDestino) {
		System.out.println("//////////////////////" + getJogo().verificaXeque() + "//////////////////");
		//Verifica se é um lance possível
		if(pecaSelecionada.getLancesPossiveis().contains(casaDestino)){
			//System.out.println("chamada para mudar o tabuleiro");
            getJogo().getTabuleiro().mudaTabuleiro(pecaSelecionada.getPosicao(), casaDestino);		
        }
		//System.out.println("chamada para atualizar as capturas");
		//Atualiza lances e capturas
		getJogo().atualizaLancesECapturas();
		
		//Escreve texto
		String texto = (getJogo().getTurno().equals("branco")) ? Lance.escreveLance(pecaSelecionada, casaDestino, getJogo().getNumeroLance()) : Lance.escreveLance(pecaSelecionada, casaDestino);
		Lance.escreveNoArquivo(texto);
	}

}
