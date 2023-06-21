package Xadrez;

import java.util.ArrayList;
import java.util.Random;

public class JogadorHumano extends Jogador {
	
	JogadorHumano(String cor){
		super(cor);
	}
	

	@Override
	public Lance fazJogada(Peca pecaSelecionada, Casa casaDestino) {
		//Verificação se o lance pretendido vai deixar o jogador em xeque:
		if(lanceMantemReiProtegido(pecaSelecionada, casaDestino)) {
			System.out.println("xeque humano: " + getJogo().verificaXeque());
			System.out.println("peca selecionada: " + pecaSelecionada + " casa de destino " + casaDestino);
			
			String xeque = getJogo().verificaXeque();
			boolean sucesso = false, aux = true;
			Casa origem = pecaSelecionada.getPosicao();
			
			//Jogador em xeque:
			if(xeque.equals(getCor())) {
				System.out.println("////////Lance humano xeque//////////");
				ArrayList<Lance> lancesPossiveis = getLancesPossiveisXeque();
				//Verifica se é um lance possível
				//System.out.println("Lances possíveis: " + getLancesPossiveisXeque());
				for(int i = 0; i < lancesPossiveis.size(); i++) {
					//System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" + i);
					if(lancesPossiveis.get(i).getCasaDestino().equals(casaDestino)){
						//System.out.println("chamada para mudar o tabuleiro humano em xeque");
			            getJogo().getTabuleiro().mudaTabuleiro(pecaSelecionada.getPosicao(), casaDestino);
			            sucesso = true;
			            aux = false;
			        }	else	{
			        	//System.out.println("lance humano ilegal");
			        }
				}
				if(!sucesso)	return null;
				
			}   else if(pecaSelecionada.getLancesPossiveis().contains(casaDestino))	{	//Não está em xeque e é um lance possível
				System.out.println("/////////Lance normal/////////");
				//System.out.println("chamada para mudar o tabuleiro");
		        getJogo().getTabuleiro().mudaTabuleiro(pecaSelecionada.getPosicao(), casaDestino);
		        aux = false;
			}
			
			if(aux && pecaSelecionada instanceof Peao)	{	//Não está em xeque e é um lance especial de peão
				System.out.println("//////////Lance especial de peão///////////");
				ArrayList<Lance> lancesEspeciais = ((Peao)pecaSelecionada).getLancesEspeciais();
				for (int i = 0; i < lancesEspeciais.size(); i++) {
					//System.out.println("Lance especial peao: " + lancesEspeciais.get(i));
					if(lancesEspeciais.get(i).getPecaMovida().equals(pecaSelecionada) && lancesEspeciais.get(i).getCasaDestino().equals(casaDestino)) {			//Lance especial igual ao lance feito
						//System.out.println("En passant válido!!! Mudando o tabuleiro:");
						getJogo().getTabuleiro().mudaTabuleiroEspecial(lancesEspeciais.get(i));
					}
				}
			}
			
			if(aux && pecaSelecionada instanceof Rei) {
				System.out.println("//////////Lance especial de rei///////////");
				ArrayList<Lance> lancesEspeciais = ((Rei)pecaSelecionada).getLancesEspeciais();
				for (int i = 0; i < lancesEspeciais.size(); i++) {
					//System.out.println("Lance especial rei: " + lancesEspeciais.get(i));
					if(lancesEspeciais.get(i).getPecaMovida().equals(pecaSelecionada) && lancesEspeciais.get(i).getCasaDestino().equals(casaDestino)) {			//Lance especial igual ao lance feito
						//System.out.println("Roque válido!!! Mudando o tabuleiro:");
						getJogo().getTabuleiro().mudaTabuleiroEspecial(lancesEspeciais.get(i));
					}
				}
			}
			
			
			System.out.println("chamada para atualizar as capturas");
			//Atualiza lances e capturas
			getJogo().atualizaLancesECapturas();
			
			//Escreve texto
			String texto = (getJogo().getTurno().equals("branco")) ? Lance.escreveLance(pecaSelecionada, casaDestino, getJogo().getNumeroLance()) : Lance.escreveLance(pecaSelecionada, casaDestino);
			Lance.escreveNoArquivo(texto);
			
			System.out.println("HUMANO: Peca selecionada: " + pecaSelecionada + " origem: " + origem + " casaDestino: " + casaDestino + "poscao da Peca Selecionada após movimento" + pecaSelecionada.getPosicao());
			return new Lance(pecaSelecionada, casaDestino, origem);
		}
		return null;
	}

}
