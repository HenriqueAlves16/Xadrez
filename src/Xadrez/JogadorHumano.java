package Xadrez;

import java.util.ArrayList;
import java.util.Random;

public class JogadorHumano extends Jogador {
	
	JogadorHumano(String cor){
		super(cor);
	}
	

	//Tratar seguintes casos: jogador em xeque, lance especial, lance comum, chamar função para escrever texto
	@Override
	public Lance fazJogada(Peca pecaSelecionada, Casa casaDestino) {
		//Verificação se o lance pretendido vai deixar o jogador em xeque:
		//System.out.println("xeque humano: " + getJogo().verificaXeque());
		//System.out.println("peca selecionada: " + pecaSelecionada + " casa de destino " + casaDestino);
		System.out.println("////////////// COMECEI EXECUTAR UM LANCE " + getJogo().getTabuleiro().test);
		Lance lanceRetorno = null;
		String xeque = getJogo().verificaXeque();
		boolean ehEspecial = ehEspecial(pecaSelecionada, casaDestino);
		
		if(xeque.equals(getCor())) {							//Jogador em xeque
			//System.out.println("HUMANO EM XEQUE");
			lanceRetorno = fazLanceEmXeque(pecaSelecionada, casaDestino);
			System.out.println("////////////// FIZ LANCE EM XEQUE " + getJogo().getTabuleiro().test);
		}   else if(ehEspecial)	{								//Não está em xeque e é lance especial
			//System.out.println("REI OU PEAO");
			lanceRetorno = fazLanceEspecial(pecaSelecionada, casaDestino);
			System.out.println("////////////// FIZ LANCE ESPECIAL " + getJogo().getTabuleiro().test);

		}	else if(pecaSelecionada.getCasasBase().contains(casaDestino))	{	//Não está em xeque e é um lance comum
			//System.out.println("LANCE COMUM");
			lanceRetorno = fazLanceComum(pecaSelecionada, casaDestino);
			System.out.println("////////////// FIZ LANCE COMUM " + getJogo().getTabuleiro().test);
		}
		//System.out.println("//////////////////////////////////" + lanceRetorno + "/////////////////////////////");
		
		//System.out.println("chamada para atualizar as capturas. Tabuleiro antes da atualização:");
		//Tabuleiro.imprimeTabuleiro();
		//Atualiza lances e capturas
		getJogo().atualizaLancesECapturas();
		System.out.println("////////////// ATUALIZEI LANCES E CAPTURAS " + getJogo().getTabuleiro().test);
		//System.out.println("/ Tabuleiro depois da atualizacao:");
		//Tabuleiro.imprimeTabuleiro();
		//Escreve texto
		String texto = (getJogo().getTurno().equals("branco")) ? Lance.escreveLance(pecaSelecionada, casaDestino, getJogo().getNumeroLance()) : Lance.escreveLance(pecaSelecionada, casaDestino);
		Lance.escreveNoArquivo(texto);
		
		//System.out.println("HUMANO: Peca selecionada: " + pecaSelecionada + " origem: " + origem + " casaDestino: " + casaDestino + "poscao da Peca Selecionada após movimento" + pecaSelecionada.getPosicao());
		return lanceRetorno;
	}
	
	//Verifica se o lance em tentativa vai tirar o rei de xeque. Se sim, retorna o lance
	public Lance fazLanceEmXeque(Peca pecaSelecionada, Casa casaDestino) {
		ArrayList<Lance> lancesPossiveis = getLancesPossiveisXeque();
		Casa origem = pecaSelecionada.getPosicao();
		//System.out.println("////////Lance humano xeque//////////");
		//Verifica se é um lance possível
		//System.out.println("Lances possíveis: " + getLancesPossiveisXeque());
		for(int i = 0; i < lancesPossiveis.size(); i++) {
			if(lancesPossiveis.get(i).getCasaDestino().equals(casaDestino)){
				//System.out.println("chamada para mudar o tabuleiro humano em xeque");
	            getJogo().getTabuleiro().mudaTabuleiro(pecaSelecionada.getPosicao(), casaDestino);
	            return new Lance(pecaSelecionada, casaDestino, origem);
	        }
		}
		return null;
	}
	
	//Tentativa de fazer uma jogada em que ele não está em xeque
	public Lance fazLanceComum(Peca pecaSelecionada, Casa casaDestino) {
		Casa origem = pecaSelecionada.getPosicao();
		if(lanceMantemReiProtegido(pecaSelecionada, casaDestino))	{	//Não está em xeque e é um lance possível
			//System.out.println("/////////Lance normal/////////");
			//System.out.println("chamada para mudar o tabuleiro");
	        getJogo().getTabuleiro().mudaTabuleiro(pecaSelecionada.getPosicao(), casaDestino);
	        if((pecaSelecionada.getPosicao().getLinha() == 8 || pecaSelecionada.getPosicao().getLinha() == 1) && pecaSelecionada instanceof Peao) {
				promocao((Peao)pecaSelecionada);
			}
	        return new Lance(pecaSelecionada, casaDestino, origem);
		}
		return null;
	}
	
	//Tentativa de fazer um lance especial
	public Lance fazLanceEspecial(Peca pecaSelecionada, Casa casaDestino) {	
		ArrayList<Lance> lancesEspeciais = ((MovableSpc)pecaSelecionada).getLancesEspeciais();
		Casa origem = pecaSelecionada.getPosicao();
		if(pecaSelecionada instanceof Peao)	{	//Não está em xeque e é um lance especial de peão
			//System.out.println("//////////Lance especial de peão///////////");
			for (int i = 0; i < lancesEspeciais.size(); i++) {
				//System.out.println("Lance especial peao: " + lancesEspeciais.get(i));
				if(lancesEspeciais.get(i).getPecaMovida().equals(pecaSelecionada) && lancesEspeciais.get(i).getCasaDestino().equals(casaDestino)) {			//Lance especial igual ao lance feito
					//System.out.println("En passant válido!!! Mudando o tabuleiro:");
					getJogo().getTabuleiro().mudaTabuleiroEspecial(lancesEspeciais.get(i));
					return new Lance(pecaSelecionada, casaDestino, origem);
				}
			}
		}
		
		if(pecaSelecionada instanceof Rei) {
			//System.out.println("/////////////////////////////////////////////////////////////////////////////Lance especial de rei///////////");
			for (int i = 0; i < lancesEspeciais.size(); i++) {
				//System.out.println("Lance especial rei: " + lancesEspeciais.get(i));
				if(lancesEspeciais.get(i).getPecaMovida().equals(pecaSelecionada) && lancesEspeciais.get(i).getCasaDestino().equals(casaDestino)) {			//Lance especial igual ao lance feito
					//System.out.println("Roque válido!!! Mudando o tabuleiro:");
					getJogo().getTabuleiro().mudaTabuleiroEspecial(lancesEspeciais.get(i));
					return new Lance(pecaSelecionada, casaDestino, origem);
				}
			}
		}
	
		return null;
	}
	
	public boolean ehEspecial(Peca pecaSelecionada, Casa casaDestino) {
		boolean ehEspecial = false;
		if(pecaSelecionada instanceof MovableSpc) {
			ArrayList<Lance> lancesEspeciais = ((MovableSpc)pecaSelecionada).getLancesEspeciais();
			for(int i = 0; i < lancesEspeciais.size(); i++) {
				if(lancesEspeciais.get(i).getCasaDestino().toString().equals(casaDestino.toString())) {
					ehEspecial = true;
				}
			}
	}
		return ehEspecial;
	}
	
	public void promocao(Peao peao) {
		if ((peao.getCor().equals("branco") && peao.getPosicao().getLinha() == 8) || (peao.getCor().equals("preto") && peao.getPosicao().getLinha() == 1)) {
			getJogo().getTabuleiro().repaint();
	        // Crie a caixa de diálogo personalizada
	        PromocaoDialog dialog = new PromocaoDialog(peao, this);
	        dialog.setVisible(true);
		}
	}

}
