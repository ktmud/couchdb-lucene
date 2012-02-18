package cwss.analysis.dictionary;

import java.util.HashMap;
import java.util.Map;

public class HashDictionary implements Dictionary{

	/**
	 * �ֵ������д�����ڷ���{@link #get(int)}����
	 */
	private Word[] ascWords;
	
	/**
	 * ���ַ����ִʵ��ӳ��
	 */
	private Map/* <Object, SubDictionaryWrap> */subs;
	
	private final int hashIndex;
	private final int start;
	private final int wordLength;
	private final int count;
	/**
	 * 
	 * @param ascWords
	 *            �������д���
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
	 * �����ִʵ�ӳ�䣬Ϊ���캯������
	 */
	protected void createSubDictionaries() {
		if (this.start >= ascWords.length) {
			return;
		}
		//��λ��ͬͷ�ַ�����Ŀ�ͷ�ͽ���λ����ȷ�Ϸ��ֵ�
		int beginIndex = this.start;
		int endIndex = this.start + 1;
		char beginHashChar = getChar(ascWords[start], hashIndex);//ȡ�ַ����ڼ����ַ�
		char endHashChar;
		for (; endIndex < this.count; endIndex++) {
			endHashChar = getChar(ascWords[endIndex], hashIndex);	
			if (endHashChar != beginHashChar) {				
				addSubDictionary(beginHashChar, beginIndex, endIndex);
				beginIndex = endIndex;//���±긳ֵ��ǰ�±�
				beginHashChar = endHashChar;//����ַ���ֵ��ǰ���ַ�
			}
		}
		//���һ���ִ�
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
	 * �ַ�����{@link #subs}��keyֵ��
	 */
	protected Object keyOf(char theChar) {
		// return theChar - 0x4E00;// 'һ'==0x4E00
		return new Integer(theChar);
	}
	
	/**
	 * �ִʵ����
	 */
	static class SubDictionaryWrap {
		/**
		 * �ִʵ�����ͷ�ַ�
		 */
		char hashChar;

		/**
		 * �ִʵ�
		 */
		Dictionary dic;

		/**
		 * �ִʵ��һ�����������д����е�ƫ��λ��
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
