package Xadrez;

import java.util.Random;

public class JogadorHumano extends Jogador {
	
	JogadorHumano(String cor){
		super(cor);
	}
	

	@Override
	public boolean fazJogada(Peca pecaSelecionada, Casa casaDestino) {
		System.out.println("//////////////////////" + getJogo().verificaXeque() + "//////////////////");
		String xeque = getJogo().verificaXeque();
		boolean sucesso = false;
		
		if(xeque.equals(getCor())) {
			System.out.println("Lance humano xeque");
			
			//Verifica se é um lance possível
			System.out.println(getLancesPossiveisXeque());
			for(int i = 0; i < getLancesPossiveisXeque().size(); i++) {
				if(getLancesPossiveisXeque().get(i).getCasaDestino().equals(casaDestino)){
					System.out.println("chamada para mudar o tabuleiro humano");
		            getJogo().getTabuleiro().mudaTabuleiro(pecaSelecionada.getPosicao(), casaDestino);
		            sucesso = true;
		        }	else	{
		        	System.out.println("lance humano ilegal");
		        }
			}
			if(!sucesso)	return false;
		}	else	{	//Não está em xeque
			//Verifica se é um lance possível
			if(pecaSelecionada.getLancesPossiveis().contains(casaDestino)){
				//System.out.println("chamada para mudar o tabuleiro");
	            getJogo().getTabuleiro().mudaTabuleiro(pecaSelecionada.getPosicao(), casaDestino);		
	        }
		}
		
		
		//System.out.println("chamada para atualizar as capturas");
		//Atualiza lances e capturas
		getJogo().atualizaLancesECapturas();
		
		//Escreve texto
		String texto = (getJogo().getTurno().equals("branco")) ? Lance.escreveLance(pecaSelecionada, casaDestino, getJogo().getNumeroLance()) : Lance.escreveLance(pecaSelecionada, casaDestino);
		Lance.escreveNoArquivo(texto);
		return true;
	}

}
