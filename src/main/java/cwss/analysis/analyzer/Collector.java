package cwss.analysis.analyzer;

public interface Collector {

	/**
	 * 
	 * �統�յ����㶫ʡ�����С��ı����еġ����ݡ�ʱ������Ĳ����ֱ��ǣ�(�����ݡ�, 3, 5)
	 * 
	 * @param word
	 *            ���յ��Ĵ���
	 * @param offset
	 *            �ô������ı����е�ƫ��λ��
	 * @param end
	 *            �ô������ı����еĽ���λ��(���ﲻ�����ı���endλ�õ��ַ�)��end-offset��Ϊword�ĳ���
	 * 
	 *         
	 */
	public void collect(String word, int offset, int end);
}
