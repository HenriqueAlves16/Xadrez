package Xadrez;

public class LanceInvalidoException extends NullPointerException{
    public LanceInvalidoException() {
        super("Tentativa de lance inválida.");
    }
}
