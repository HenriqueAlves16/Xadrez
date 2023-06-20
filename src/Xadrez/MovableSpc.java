package Xadrez;

import java.util.ArrayList;

public interface MovableSpc {
	void movimentoEspecial(Lance lance);
	ArrayList<Lance> getLancesEspeciais();
	void setLancesEspeciais(ArrayList<Lance> lancesEspeciais);
}
