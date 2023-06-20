package Xadrez;


public class Casa {
    private int linha;
    private char coluna;
    private Peca peca;
    private Peca pecaTemp;

    public Casa(char coluna, int linha, Peca peca) {
        this.linha = linha;
        this.coluna = coluna;
        this.peca = peca;
    }

	public Casa(Casa casa) {
    	this.linha = casa.getLinha();
    	this.coluna = casa.getColuna();
    	this.peca = casa.getPeca();
    }
    
    public int getLinha() {
        return linha;
    }
    
    public void setLinha(int linha) {
		this.linha = linha;
	}

    public char getColuna() {
        return coluna;
    }

	public void setColuna(char coluna) {
		this.coluna = coluna;
	}

    public Peca getPeca() {
        return peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }   
    
    public Peca getPecaTemp() {
		return pecaTemp;
	}

	public void setPecaTemp(Peca pecaTemp) {
		this.pecaTemp = pecaTemp;
	}

	@Override
    public String toString() {
    	return "" + coluna + linha;
    }

}
