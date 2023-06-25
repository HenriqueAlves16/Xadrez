package Xadrez;

class NenhumaPecaSelecionadaException extends NullPointerException {
	private static final long serialVersionUID = 1L;

	public NenhumaPecaSelecionadaException() {
        super("Não há peças selecionadas.");
    }
}