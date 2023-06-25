package Xadrez;

import java.util.ArrayList;
import java.util.Random;

public class JogadorMaquina extends Jogador {

	JogadorMaquina(String cor){
		super(cor);
	}
	
	@Override
	public Lance fazJogada(Peca peca, Casa casa) {
		Peca pecaSelecionada;
		Casa casaOrigem;
		Casa casaDestino;
		String xeque = getJogo().verificaXeque();
		Lance lanceEscolhido;
		Random random = new Random();
		int n = 0;
		
		//Atualiza os lances e capturas possíveis
		verificaLancesPossiveis();
		
		if(xeque.equals(getCor())) {		//Jogador maquina em xeque
			ArrayList<Lance> lancesPossiveis = getLancesPossiveisXeque();
			int numeroAleatorio = random.nextInt(getLancesPossiveisXeque().size());
			lanceEscolhido = lancesPossiveis.get(numeroAleatorio);
			
			pecaSelecionada = lanceEscolhido.getPecaMovida();
			casaOrigem = pecaSelecionada.getPosicao();
			casaDestino = lanceEscolhido.getCasaDestino();
			
		}	else	{			//Jogador maquina não está em xeque
			ArrayList<Lance> lancesEspeciais = getLancesEspeciais();

			//Escolhe um lance normal aleatório
			int numeroAleatorioNormal = random.nextInt(getLancesPossiveis().size());
			Lance lanceNormalAleatorio = getLancesPossiveis().get(numeroAleatorioNormal);
			lanceEscolhido = lanceNormalAleatorio;

			//Escolhe um lance especial aleatório
			if(lancesEspeciais.size() > 0) {
				int numeroAleatorioSpc = random.nextInt(lancesEspeciais.size());
				Lance lanceSpcAleatorio = getLancesEspeciais().get(numeroAleatorioSpc);
				
				//Escolhe um lance normal ou especial:
				n = random.nextInt(2);
				lanceEscolhido = (n == 0) ? lanceNormalAleatorio : lanceSpcAleatorio;
			}
						
			pecaSelecionada = lanceEscolhido.getPecaMovida();
			casaOrigem = pecaSelecionada.getPosicao();
			casaDestino = lanceEscolhido.getCasaDestino();
		}
		
		if(lanceMantemReiProtegido(pecaSelecionada, casaDestino)) {

			//Muda o tabuleiro fazendo o lance aleatório
			if(n == 0) {
				getJogo().getTabuleiro().mudaTabuleiro(casaOrigem, casaDestino);
			}	else	{
				getJogo().getTabuleiro().mudaTabuleiroEspecial(lanceEscolhido);
			}
			if((pecaSelecionada.getPosicao().getLinha() == 8 || pecaSelecionada.getPosicao().getLinha() == 1) && pecaSelecionada instanceof Peao) {
				promocao((Peao)pecaSelecionada);
			}
			//Atualiza as listas de controle
			getJogo().atualizaLancesECapturas();
			
			//Escreve o lance feito
			String texto = (getCor().equals("branco")) ? Lance.escreveLance(pecaSelecionada, casaDestino, getJogo().getNumeroLance()) : Lance.escreveLance(pecaSelecionada, casaDestino);
			Lance.escreveNoArquivo(texto);
			
			return new Lance(pecaSelecionada, casaDestino, casaOrigem);
		}
		return fazJogada(null, null);
	}
	
	public void promocao(Peao peao) {
		if ((peao.getCor().equals("branco") && peao.getPosicao().getLinha() == 8) || (peao.getCor().equals("preto") && peao.getPosicao().getLinha() == 1)) {
			getJogo().getTabuleiro().repaint();
			Random random = new Random();
			int prob = random.nextInt(100);	//Numero aleatório que define para qual peça a promoção ocorrerá
			if(prob < 2) {
				promoverPeao(peao, "Cavalo");
			} else if(prob < 5) {
				promoverPeao(peao, "Bispo");
			} else if(prob < 30) {
				promoverPeao(peao, "Torre");
			} else if(prob < 100) {
				promoverPeao(peao, "Rainha");
			}
		}
	}

}
