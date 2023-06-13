package Xadrez;


public class Casa {
    private int linha;
    private char coluna;
    private Peca peca;

    public Casa(char coluna, int linha, Peca peca) {
        this.linha = linha;
        this.coluna = coluna;
        this.peca = peca;
    }
    
    public int getLinha() {
        return linha;
    }

    public char getColuna() {
        return coluna;
    }

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }

}
