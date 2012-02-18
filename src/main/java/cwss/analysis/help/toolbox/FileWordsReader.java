package cwss.analysis.help.toolbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import cwss.analysis.util.Utility;


public class FileWordsReader {

	public static Map/*<String, Set<Word>>*/ readWords(String path,String charsetName) throws IOException{
		SimpleReadListener l = new SimpleReadListener();
		readWords(path, l, charsetName);
		return l.getResult();
	}
	
	public static void readWords(String path, SimpleReadListener l, String charsetName)
	throws IOException {
		
		File f = new File(path);
		if (!l.onFileBegin(f.getAbsolutePath())) {
			return;
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(
				new FileInputStream(f), charsetName));
		String word;
		while ((word = in.readLine()) != null) {
			if(word.indexOf("[")!=-1){
				word = word.substring(0, word.indexOf("["));
			}
			if (word.length() > 0 && Utility.isBom(word.charAt(0))) {
				word = word.substring(1);
			}
			l.onWord(word);
		}
		l.onFileEnd(f.getAbsolutePath());
		in.close();
	}
	
	public static void main(String args[]){
		try {
			FileWordsReader.readWords("G:/loiy/paoding/dic/.compiled/most-words-mode/wss-base-word.dic", "utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
