package cwss.analysis.segment;

public class Atom {
	
	private String word;//����
	
	private int pos;//����
	
	private int len;
	
	//��ʼƫ����(�����õ�)
	private int offset;
	
	//����ƫ����(�����õ�)
	private int end;
	
	/**
	 * �Ƿ��������ԭ��
	 */
	private boolean search;

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public boolean isSearch() {
		return search;
	}

	public void setSearch(boolean search) {
		this.search = search;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
	 
}
