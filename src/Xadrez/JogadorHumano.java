package Xadrez;

import java.util.ArrayList;

public class JogadorHumano extends Jogador {
	
	JogadorHumano(String cor){
		super(cor);
	}
	

	//Tratar seguintes casos: jogador em xeque, lance especial, lance comum, chamar função para escrever texto
	@Override
	public Lance fazJogada(Peca pecaSelecionada, Casa casaDestino) {
		//Verificação se o lance pretendido vai deixar o jogador em xeque:
		Lance lanceRetorno = null;
		String xeque = getJogo().verificaXeque();
		boolean ehEspecial = ehEspecial(pecaSelecionada, casaDestino);
		
		if(xeque.equals(getCor())) {							//Jogador em xeque
			lanceRetorno = fazLanceEmXeque(pecaSelecionada, casaDestino);
		}   else if(ehEspecial)	{								//Não está em xeque e é lance especial
			lanceRetorno = fazLanceEspecial(pecaSelecionada, casaDestino);

		}	else if(pecaSelecionada.getCasasBase().contains(casaDestino))	{	//Não está em xeque e é um lance comum
			lanceRetorno = fazLanceComum(pecaSelecionada, casaDestino);
		}

		//Atualiza lances e capturas
		getJogo().atualizaLancesECapturas();

		//Escreve texto
		String texto = (getJogo().getTurno().equals("branco")) ? Lance.escreveLance(pecaSelecionada, casaDestino, getJogo().getNumeroLance()) : Lance.escreveLance(pecaSelecionada, casaDestino);
		Lance.escreveNoArquivo(texto);
		
		return lanceRetorno;
	}
	
	//Verifica se o lance em tentativa vai tirar o rei de xeque. Se sim, retorna o lance
	public Lance fazLanceEmXeque(Peca pecaSelecionada, Casa casaDestino) {
		ArrayList<Lance> lancesPossiveis = getLancesPossiveisXeque();
		Casa origem = pecaSelecionada.getPosicao();
		//Verifica se é um lance possível
		for(int i = 0; i < lancesPossiveis.size(); i++) {
			if(lancesPossiveis.get(i).getCasaDestino().equals(casaDestino)){
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
			for (int i = 0; i < lancesEspeciais.size(); i++) {
				if(lancesEspeciais.get(i).getPecaMovida().equals(pecaSelecionada) && lancesEspeciais.get(i).getCasaDestino().equals(casaDestino)) {			//Lance especial igual ao lance feito
					getJogo().getTabuleiro().mudaTabuleiroEspecial(lancesEspeciais.get(i));
					return new Lance(pecaSelecionada, casaDestino, origem);
				}
			}
		}
		
		if(pecaSelecionada instanceof Rei) {
			for (int i = 0; i < lancesEspeciais.size(); i++) {
				if(lancesEspeciais.get(i).getPecaMovida().equals(pecaSelecionada) && lancesEspeciais.get(i).getCasaDestino().equals(casaDestino)) {			//Lance especial igual ao lance feito
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
