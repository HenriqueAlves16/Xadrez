package Xadrez;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Lance {
	private Peca pecaMovida;
	private Casa casaOrigem;
	private Casa casaDestino;
	private int numeroLance;
	private static File file = new File("Texto.txt");;
	
	public Lance(Peca pecaMovida, Casa casaDestino, int numeroLance) {
		this.pecaMovida = pecaMovida;
		this.casaDestino = casaDestino;
		this.numeroLance = numeroLance;
		this.casaOrigem = pecaMovida.getPosicao();
	}
	
	public Lance(Peca pecaMovida, Casa casaDestino) {
		this.pecaMovida = pecaMovida;
		this.casaDestino = casaDestino;
		this.casaOrigem = pecaMovida.getPosicao();
	}
	
	public Lance(Peca pecaMovida, Casa casaDestino, Casa casaOrigem) {
		this.pecaMovida = pecaMovida;
		this.casaDestino = casaDestino;
		this.casaOrigem = casaOrigem;
	}
	
	//getters e setters:
	public Peca getPecaMovida() {
		return pecaMovida;
	}

	public void setPecaMovida(Peca pecaMovida) {
		this.pecaMovida = pecaMovida;
	}

	public Casa getCasaDestino() {
		return casaDestino;
	}

	public void setCasaDestino(Casa casaDestino) {
		this.casaDestino = casaDestino;
	}

	public int getNumeroLance() {
		return numeroLance;
	}

	public void setNumeroLance(int numeroLance) {
		this.numeroLance = numeroLance;
	}
	
	public Casa getCasaOrigem() {
		return casaOrigem;
	}

	public void setCasaOrigem(Casa casaOrigem) {
		this.casaOrigem = casaOrigem;
	}

	public static String escreveLance(Peca pecaBrancaMovida, Casa casaDestinoBranco, Peca pecaPretaMovida, Casa casaDestinoPreto, int numeroLance) {
		String lance = "";
		lance += numeroLance + ". ";
		lance += pecaBrancaMovida.toString() + casaDestinoBranco.toString() + " ";
		lance += pecaPretaMovida.toString() + casaDestinoPreto.toString() + " ";
		
		//1. e4 c5 2. Nf3 Nc6 3. g3 g6 4. Bg2 Bg7 5. d3 d6 6. O-O Nf6 7. c3 Bg4 8. h3 Bd7 9. Be3 Qc8 10. Kh2 h5
		return lance;
	}
	
	public static String escreveLance(Peca pecaBrancaMovida, Casa casaDestinoBranco, int numeroLance) {
		String lance = "";
		lance += numeroLance + ". ";
		lance += pecaBrancaMovida.toString() + casaDestinoBranco.toString() + " ";
		
		return lance;
	}
	
	public static String escreveLance(Peca pecaPretaMovida, Casa casaDestinoPreto) {
		String lance = "";
		lance += pecaPretaMovida.toString() + casaDestinoPreto.toString() + " ";
		
		//1. e4 c5 2. Nf3 Nc6 3. g3 g6 4. Bg2 Bg7 5. d3 d6 6. O-O Nf6 7. c3 Bg4 8. h3 Bd7 9. Be3 Qc8 10. Kh2 h5
		return lance;
	}
	
	public static void escreveNoArquivo(String texto) {
	    try {
	        FileWriter fileWriter = new FileWriter(file, true); // O segundo parâmetro 'true' indica que será feita uma escrita em modo de acréscimo
	        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	        bufferedWriter.write(texto);
	        bufferedWriter.close();
	        System.out.println("Texto escrito no arquivo com sucesso!");
	    } catch (IOException e) {
	        System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
	    }
	}


	@Override
	public String toString() {
		return pecaMovida + " from " + casaOrigem + " to " + casaDestino;
	}
	
	
	
}
