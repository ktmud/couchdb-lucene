package cwss.analysis.dictionary;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cwss.analysis.help.toolbox.FileWordsReader;


public class FileDictionary {

	protected Log log = LogFactory.getLog(this.getClass());
	protected Map/* <String, Set<String>> */allWords;
	private String dicHome;
	private String charsetName;
	public FileDictionary(String dicHome,String charsetName){
		this.dicHome = System.getProperty("user.dir") + dicHome;
		this.charsetName = charsetName;
	}
	
	public Map loadBaseWordDic(){
		log.info("loading dictionaries from " + dicHome);
//		allWords得到的是dic文件所有的单词
		try {
			allWords = FileWordsReader.readWords(dicHome, charsetName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allWords;
	}
	
	protected Word[] getVocabularyWords() {
		Map/* <String, Set<Word>> */dics = loadBaseWordDic();//得到的是dic文件所有的单词Map<String,HashSet>
		Set/* <Word> */set = null;
		Iterator/* <Word> */iter = dics.keySet().iterator();
		while (iter.hasNext()) {
			String name = (String) iter.next();
			Set/* <Word> */dic = (Set/* <Word> */) dics.get(name);
			if (set == null) {
				set = new HashSet/* <Word> */(dic);
			} else {
				set.addAll(dic);
			}
		}
		Word[] words = (Word[]) set.toArray(new Word[set.size()]);
		Arrays.sort(words);
		return words;
	}
	
	public HashDictionary getAllWordsDic(){
		//加载词库
		return new HashDictionary(getVocabularyWords(), 0x2fff, 0.75f);		
	}
	
	public static void main(String args[]){
		FileDictionary fd = new FileDictionary("G:/loiy/paoding/dic/.compiled/most-words-mode/wss-base-word.dic", "utf-8");
		fd.getAllWordsDic();
	}
}
