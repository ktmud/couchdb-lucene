package cwss.analysis.dictionary;

/**
 * Dictionary��һ��ֻ���ֵ䣬���ڲ����Ƿ����ĳ������Լ������Ϣ��
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
	 * �����ֵ��д�����>=0
	 * 
	 * @return
	 */
	public int size();

	/**
	 * ���ظ���λ�õĴ���
	 * 
	 * @param index
	 *            0,1,2,...,size-1
	 * @return
	 */
	public Word get(int index);

	/**
	 * �����ʵ��Ƿ��ռ�input[offset]��input[offset+count-1]֮���ַ���(�����߽�)�Ĵʡ�<br>
	 * ��������Էǿ�Hit���������
	 * <p>
	 * @param input Ҫ�������ַ���������������һ����
	 * @param offset Ҫ�������ַ�����ʼλ�����input��ƫ��
	 * @param count Ҫ�������ַ����ַ�����
	 * @return ���ص�Hit����ǿգ�����ͨ��hit�����ṩ�ķ����ж��������
	 * 
	 * @see Hit
	 */
	public Hit search(CharSequence input, int offset);
}
