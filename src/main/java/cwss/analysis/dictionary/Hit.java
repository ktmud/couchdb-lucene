package cwss.analysis.dictionary;

public class Hit {

	public final static int UNCLOSED_INDEX = -1;

	public final static int UNDEFINED_INDEX = -2;
	
	public final static Hit UNDEFINED = new Hit(UNDEFINED_INDEX, null, null);
	
	/**
	 * 目标词语在词典中的位置，或者在字典没有该词语是表示其他意思(参见以上静态变量定义的情况)
	 */
	private int index;

	/**
	 * 查找命中时，词典中相应的词
	 */
	private Word word;

	/**
	 * 词典中命中词的下一个单词，或{@link #isUnclosed()}为true时最接近的下一个词(参见本类的注释)
	 */
	private Word next;
	
	/**
	 * 
	 * @param index
	 *            目标词语在词典中的位置，或者在字典没有该词语是表示其他意思(参见以上静态变量定义的情况)
	 * @param word
	 *            查找命中时，词典中相应的词
	 * @param next
	 *            词典中命中词的下一个单词，或{@link #isUnclosed()}为true时最接近的下一个词(参见本类的注释)
	 */
	public Hit(int index, Word word, Word next) {
		this.index = index;
		this.word = word;
		this.next = next;
	}
	
	/**
	 * 查找命中时，词典中相应的词
	 */
	public Word getWord() {
		return word;
	}

	/**
	 * 目标词语在词典中的位置，或者在字典没有该词语是表示其他意思(参见以上静态变量定义的情况)
	 * @return
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * 词典中命中词的下一个单词，或{@link #isUnclosed()}为true时最接近的下一个词(参见本类的注释)
	 * @return
	 */
	public Word getNext() {
		return next;
	}

}
