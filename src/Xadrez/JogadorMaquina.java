package Xadrez;

import java.util.ArrayList;
import java.util.Random;

public class JogadorMaquina extends Jogador {

	JogadorMaquina(String cor){
		super(cor);
	}
	
	@Override
	public Lance fazJogada(Peca peca, Casa casa) {
		System.out.println("xeque maquina:" + getJogo().verificaXeque());
		Peca pecaSelecionada;
		Casa casaOrigem;
		Casa casaDestino;
		String xeque = getJogo().verificaXeque();
		Lance lanceEscolhido;
		Random random = new Random();
		int n = 0;
		
		//Atualiza os lances e capturas possíveis
		verificaLancesPossiveis();
		
		if(xeque.equals(getCor())) {
			//System.out.println("Lance aleatório xeque");
			ArrayList<Lance> lancesPossiveis = getLancesPossiveisXeque();
			int numeroAleatorio = random.nextInt(getLancesPossiveisXeque().size());
			lanceEscolhido = lancesPossiveis.get(numeroAleatorio);
			
			pecaSelecionada = lanceEscolhido.getPecaMovida();
			casaOrigem = pecaSelecionada.getPosicao();
			casaDestino = lanceEscolhido.getCasaDestino();
			
		}	else	{
			System.out.println("Lance aleatório sem xeque");
			ArrayList<Lance> lancesEspeciais = getLancesEspeciais();
			System.out.println("qtd lances normais possiveis: " + getLancesPossiveis().size());
			System.out.println("qtd lances especiais possiveis: " + lancesEspeciais.size());
			System.out.println("Lances especiais possíveis: " + lancesEspeciais);
			
			//Escolhe um lance normal aleatório
			int numeroAleatorioNormal = random.nextInt(getLancesPossiveis().size());
			Lance lanceNormalAleatorio = getLancesPossiveis().get(numeroAleatorioNormal);
			lanceEscolhido = lanceNormalAleatorio;

			//Escolhe um lance especial aleatório
			if(lancesEspeciais.size() > 0) {
				int numeroAleatorioSpc = random.nextInt(lancesEspeciais.size());
				Lance lanceSpcAleatorio = getLancesEspeciais().get(numeroAleatorioSpc);
				System.out.println("Lance Especial candidato: " + lanceSpcAleatorio);
				
				//Escolhe um lance normal ou especial:
				n = random.nextInt(2);
				lanceEscolhido = (n == 0) ? lanceNormalAleatorio : lanceSpcAleatorio;
			}
			
			System.out.println("//////////Lance Escolhido://///////// " + lanceEscolhido);
			
			pecaSelecionada = lanceEscolhido.getPecaMovida();
			casaOrigem = pecaSelecionada.getPosicao();
			casaDestino = lanceEscolhido.getCasaDestino();
		}
		
		//Muda o tabuleiro fazendo o lance aleatório
		if(n == 0) {
			getJogo().getTabuleiro().mudaTabuleiro(casaOrigem, casaDestino);
		}	else	{
			getJogo().getTabuleiro().mudaTabuleiroEspecial(lanceEscolhido);
		}
		
		//Atualiza as listas de controle
		getJogo().atualizaLancesECapturas();
		
		//Escreve o lance feito
		String texto = (getCor().equals("branco")) ? Lance.escreveLance(pecaSelecionada, casaDestino, getJogo().getNumeroLance()) : Lance.escreveLance(pecaSelecionada, casaDestino);
		Lance.escreveNoArquivo(texto);
		
		System.out.println("MAQUINA: Peca selecionada: " + pecaSelecionada + "origem: " + casaOrigem + "casaDestino: " + casaDestino);
		return new Lance(pecaSelecionada, casaDestino, casaOrigem);
	}

}
