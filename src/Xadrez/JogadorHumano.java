package Xadrez;

import java.util.ArrayList;
import java.util.Random;

public class JogadorHumano extends Jogador {
	
	JogadorHumano(String cor){
		super(cor);
	}
	

	@Override
	public Lance fazJogada(Peca pecaSelecionada, Casa casaDestino) {
		System.out.println("xeque humano: " + getJogo().verificaXeque());
		String xeque = getJogo().verificaXeque();
		boolean sucesso = false;
		Casa origem = pecaSelecionada.getPosicao();
		
		//Jogador em xeque:
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
			if(!sucesso)	return null;
			
		}   else if(pecaSelecionada.getLancesPossiveis().contains(casaDestino))	{	//Não está em xeque e é um lance possível
			System.out.println("Lance normal");
			//System.out.println("chamada para mudar o tabuleiro");
	        getJogo().getTabuleiro().mudaTabuleiro(pecaSelecionada.getPosicao(), casaDestino);
	        
		}   else if(pecaSelecionada instanceof Peao)	{	//Não está em xeque e é um lance especial
			System.out.println("Não está em xeque e é peão");
			ArrayList<Lance> lancesEspeciais = ((Peao)pecaSelecionada).getLancesEspeciais();
			for (int i = 0; i < lancesEspeciais.size(); i++) {
				System.out.println("Lance especial: " + lancesEspeciais.get(i));
				if(lancesEspeciais.get(i).getPecaMovida().equals(pecaSelecionada) && lancesEspeciais.get(i).getCasaDestino().equals(casaDestino)) {
					System.out.println("Lance válido!!! Mudando o tabuleiro:");
					getJogo().getTabuleiro().mudaTabuleiroEspecial(lancesEspeciais.get(i));
				}
			}
			
		}
		
		
		//System.out.println("chamada para atualizar as capturas");
		//Atualiza lances e capturas
		getJogo().atualizaLancesECapturas();
		
		//Escreve texto
		String texto = (getJogo().getTurno().equals("branco")) ? Lance.escreveLance(pecaSelecionada, casaDestino, getJogo().getNumeroLance()) : Lance.escreveLance(pecaSelecionada, casaDestino);
		Lance.escreveNoArquivo(texto);
		
		System.out.println("HUMANO: Peca selecionada: " + pecaSelecionada + " origem: " + origem + " casaDestino: " + casaDestino);
		return new Lance(pecaSelecionada, casaDestino, origem);
	}

}
