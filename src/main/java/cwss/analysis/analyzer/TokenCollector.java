package cwss.analysis.analyzer;

import java.util.Iterator;

public interface TokenCollector extends Collector{

	/**
	 * 
	 * 如当收到“广东省雷州市”文本流中的“雷州”时，传入的参数分别将是：(“雷州”, 3, 5)
	 * 
	 * @param word
	 *            接收到的词语
	 * @param offset
	 *            该词语在文本流中的偏移位置
	 * @param end
	 *            该词语在文本流中的结束位置(词语不包括文本流end位置的字符)，end-offset是为word的长度
	 * 
	 *         
	 */
	
	public Iterator/* <Token> */ iterator();
}
