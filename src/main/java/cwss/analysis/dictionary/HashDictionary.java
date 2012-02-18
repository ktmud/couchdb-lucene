package cwss.analysis.dictionary;

import java.util.HashMap;
import java.util.Map;

public class HashDictionary implements Dictionary{

	/**
	 * 字典中所有词语，用于方便{@link #get(int)}方法
	 */
	private Word[] ascWords;
	
	/**
	 * 首字符到分词典的映射
	 */
	private Map/* <Object, SubDictionaryWrap> */subs;
	
	private final int hashIndex;
	private final int start;
	private final int wordLength;
	private final int count;
	/**
	 * 
	 * @param ascWords
	 *            升序排列词语
	 * @param initialCapacity
	 * @param loadFactor
	 */
	public HashDictionary(Word[] ascWords, int initialCapacity,
			float loadFactor) {
		this(ascWords, 0, 0, ascWords.length, initialCapacity, loadFactor);
	}
	
	public HashDictionary(Word[] ascWords, int hashIndex, int start,
			int wordLength, int initialCapacity, float loadFactor) {
		//initialCapacity= 0x2fff=12287,loadFactor=0.75f
		this.ascWords = ascWords;
		this.start = start;//0
		this.wordLength = wordLength;//ascWords.length		
		this.count = wordLength - start;
		this.hashIndex = hashIndex;//0
		subs = new HashMap/* <Object, SubDictionaryWrap> */(initialCapacity,
				loadFactor);
		createSubDictionaries();
	}
	
	/**
	 * 创建分词典映射，为构造函数调用
	 */
	protected void createSubDictionaries() {
		if (this.start >= ascWords.length) {
			return;
		}
		//定位相同头字符词语的开头和结束位置以确认分字典
		int beginIndex = this.start;
		int endIndex = this.start + 1;
		char beginHashChar = getChar(ascWords[start], hashIndex);//取字符串第几个字符
		char endHashChar;
		for (; endIndex < this.count; endIndex++) {
			endHashChar = getChar(ascWords[endIndex], hashIndex);	
			if (endHashChar != beginHashChar) {				
				addSubDictionary(beginHashChar, beginIndex, endIndex);
				beginIndex = endIndex;//后下标赋值于前下标
				beginHashChar = endHashChar;//后个字符赋值给前个字符
			}
		}
		//最后一个字串
		addSubDictionary(beginHashChar, beginIndex, endIndex);
	}
	
	protected void addSubDictionary(char hashChar, int beginIndex, int endIndex) {
		Dictionary subDic = createSubDictionary(ascWords, beginIndex, endIndex);
		SubDictionaryWrap subDicWrap = new SubDictionaryWrap(hashChar,
				subDic, beginIndex);
		subs.put(keyOf(hashChar), subDicWrap);
	}
	
	protected Dictionary createSubDictionary(Word[] ascWords, int beginIndex,
			int endIndex) {
		return new BinaryDictionary(ascWords, beginIndex, endIndex);
		/*if (count < 16) {
			return new BinaryDictionary(ascWords, beginIndex, endIndex);
		} else {
			return new HashBinaryDictionary(ascWords, hashIndex + 1,
					beginIndex, endIndex, getCapacity(count), 0.75f);
		}*/
	}
	
	protected char getChar(Word word, int index) {
		if (index >= word.getText().length()) {
			return (char) 0;
		}
		return word.getText().charAt(index);
	}
	
	/**
	 * 字符的在{@link #subs}的key值。
	 */
	protected Object keyOf(char theChar) {
		// return theChar - 0x4E00;// '一'==0x4E00
		return new Integer(theChar);
	}
	
	/**
	 * 分词典封箱
	 */
	static class SubDictionaryWrap {
		/**
		 * 分词典词组的头字符
		 */
		char hashChar;

		/**
		 * 分词典
		 */
		Dictionary dic;

		/**
		 * 分词典第一个词语在所有词语中的偏移位置
		 */
		int wordIndexOffset;

		public SubDictionaryWrap(char hashChar, Dictionary dic,
				int wordIndexOffset) {
			this.hashChar = hashChar;
			this.dic = dic;
			this.wordIndexOffset = wordIndexOffset;
		}
	}

	public Word get(int index) {
		// TODO Auto-generated method stub
		return ascWords[start + index];
	}

	public Hit search(CharSequence input, int begin) {
		// TODO Auto-generated method stub
		SubDictionaryWrap subDic = (SubDictionaryWrap) subs.get(keyOf(input
				.charAt(hashIndex + begin)));
		if (subDic == null) {
			return Hit.UNDEFINED;
		}
		Dictionary dic = subDic.dic;
		return dic.search(input, begin);
	}

	public int size() {
		// TODO Auto-generated method stub
		return count;
	}
}
