package cwss.analysis.dictionary;

public class Word implements Comparable,CharSequence{

	private String text;
	private boolean noiseWord = false;
	public Word() {
	}

	public Word(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isNoiseWord() {
		return noiseWord;
	}

	public void setNoiseWord(boolean noiseWord) {
		this.noiseWord = noiseWord;
	}
	
	public int length() {
		return text.length();
	}
	
	public char charAt(int j) {
		return text.charAt(j);
	}

	public int compareTo(Object obj) {
		// TODO Auto-generated method stub
		return this.text.compareTo(((Word) obj).text);
	}

	public CharSequence subSequence(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
