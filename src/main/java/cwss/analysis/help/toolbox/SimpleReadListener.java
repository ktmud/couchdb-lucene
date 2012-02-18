package cwss.analysis.help.toolbox;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;

import cwss.analysis.dictionary.Word;


public class SimpleReadListener implements Listener{

	private HashSet/* <Word> */words = new HashSet/* <Word> */();
	private Map/* <String, Set<Word>> */dics = new Hashtable/* <String, Set<Word>> */();
	private String ext = ".dic";
	
	public boolean onFileBegin(String file) {
		if (!file.endsWith(ext)) {
			return false;
		}
		words = new HashSet/* <String> */();
		return true;
	}

	public void onFileEnd(String file) {
		String name = file.substring(0, file.length() - 4);
		dics.put(name, words);
		words = null;
	}
	
	public Map/* <String, Set<String>> */getResult() {
		return dics;
	}
	
	public void onWord(String wordText) {
		wordText = wordText.trim().toLowerCase();//所有字符转换成小写
		words.add(new Word(wordText));
	}
}
