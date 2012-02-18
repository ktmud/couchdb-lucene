package cwss.analysis.segment;

import java.util.ArrayList;
import java.util.List;

public class polysemySeg {

	private List<String> list;
	
	public polysemySeg() {
		list = new ArrayList<String>();
	}
	
	public void add(String word){
		list.add(word);
	}
}
