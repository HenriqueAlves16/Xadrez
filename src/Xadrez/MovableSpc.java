package Xadrez;

import java.util.ArrayList;

public interface MovableSpc {
	public void movimentoEspecial(Lance lance);
	public ArrayList<Lance> getLancesEspeciais();
	public void setLancesEspeciais(ArrayList<Lance> lancesEspeciais);
}
