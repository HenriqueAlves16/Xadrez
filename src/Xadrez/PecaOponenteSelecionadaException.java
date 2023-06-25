package Xadrez;

public class PecaOponenteSelecionadaException extends Exception{
	private static final long serialVersionUID = 1L;

	public PecaOponenteSelecionadaException() {
        super("A peca selecionada é do oponente.");
    }
}
