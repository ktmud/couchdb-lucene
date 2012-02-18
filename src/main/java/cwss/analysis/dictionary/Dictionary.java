package cwss.analysis.dictionary;

/**
 * Dictionary是一个只读字典，用于查找是否包含某个词语，以及相关信息。
 * <p>
 * 
 * @author Zhiliang Wang [qieqie.wang@gmail.com]
 * 
 * @see BinaryDictionary
 * @see HashBinaryDictionary
 * 
 * @since 1.0
 * 
 */
public interface Dictionary {

	/**
	 * 返回字典中词语数>=0
	 * 
	 * @return
	 */
	public int size();

	/**
	 * 返回给定位置的词语
	 * 
	 * @param index
	 *            0,1,2,...,size-1
	 * @return
	 */
	public Word get(int index);

	/**
	 * 搜索词典是否收集input[offset]到input[offset+count-1]之间字符串(包含边界)的词。<br>
	 * 搜索结果以非空Hit对象给出。
	 * <p>
	 * @param input 要搜索的字符串是其中连续的一部分
	 * @param offset 要搜索的字符串开始位置相对input的偏移
	 * @param count 要搜索的字符串字符个数
	 * @return 返回的Hit对象非空，程序通过hit对象提供的方法判断搜索结果
	 * 
	 * @see Hit
	 */
	public Hit search(CharSequence input, int offset);
}
