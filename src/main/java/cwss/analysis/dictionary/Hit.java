package cwss.analysis.dictionary;

public class Hit {

	public final static int UNCLOSED_INDEX = -1;

	public final static int UNDEFINED_INDEX = -2;
	
	public final static Hit UNDEFINED = new Hit(UNDEFINED_INDEX, null, null);
	
	/**
	 * Ŀ������ڴʵ��е�λ�ã��������ֵ�û�иô����Ǳ�ʾ������˼(�μ����Ͼ�̬������������)
	 */
	private int index;

	/**
	 * ��������ʱ���ʵ�����Ӧ�Ĵ�
	 */
	private Word word;

	/**
	 * �ʵ������дʵ���һ�����ʣ���{@link #isUnclosed()}Ϊtrueʱ��ӽ�����һ����(�μ������ע��)
	 */
	private Word next;
	
	/**
	 * 
	 * @param index
	 *            Ŀ������ڴʵ��е�λ�ã��������ֵ�û�иô����Ǳ�ʾ������˼(�μ����Ͼ�̬������������)
	 * @param word
	 *            ��������ʱ���ʵ�����Ӧ�Ĵ�
	 * @param next
	 *            �ʵ������дʵ���һ�����ʣ���{@link #isUnclosed()}Ϊtrueʱ��ӽ�����һ����(�μ������ע��)
	 */
	public Hit(int index, Word word, Word next) {
		this.index = index;
		this.word = word;
		this.next = next;
	}
	
	/**
	 * ��������ʱ���ʵ�����Ӧ�Ĵ�
	 */
	public Word getWord() {
		return word;
	}

	/**
	 * Ŀ������ڴʵ��е�λ�ã��������ֵ�û�иô����Ǳ�ʾ������˼(�μ����Ͼ�̬������������)
	 * @return
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * �ʵ������дʵ���һ�����ʣ���{@link #isUnclosed()}Ϊtrueʱ��ӽ�����һ����(�μ������ע��)
	 * @return
	 */
	public Word getNext() {
		return next;
	}

}
