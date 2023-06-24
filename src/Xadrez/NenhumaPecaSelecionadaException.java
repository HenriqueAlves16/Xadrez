package Xadrez;

class NenhumaPecaSelecionadaException extends NullPointerException {
    public NenhumaPecaSelecionadaException() {
        super("Não há peças selecionadas.");
    }
}