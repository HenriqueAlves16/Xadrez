package Xadrez;

public class Lance {
	private String pecaMovida;
	private String casaDestino;
	private int numeroLance;
	
	public Lance(String pecaMovida, String casaDestino, int numeroLance) {
		this.pecaMovida = pecaMovida;
		this.casaDestino = casaDestino;
		this.numeroLance = numeroLance;
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
	
}
