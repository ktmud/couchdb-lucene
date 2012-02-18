package cwss.analysis.help.toolbox;

import java.util.ArrayList;
import java.util.List;

import cwss.analysis.dictionary.Word;


public class MiddleResult {
	
	//°üÀ¨offset
	private int offset;
	
	//°üÀ¨end
	private int end;
	
	private List<Word> list = new ArrayList<Word>();
	
	private Word word;
	
	public MiddleResult(){
	}
	
	public void addWord(Word word){
		this.word = word;
		//list.add(word);
	}
	
	public Word getWord(){
		return this.word;
	}
	
	public void setEnd(int end){
		this.end = end;
	}
	
	public void setOffset(int offset){
		this.offset = offset;
	}

	public int getEnd() {
		return end;
	}

	public int getOffset() {
		return offset;
	}
	
}
