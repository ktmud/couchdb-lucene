package cwss.analysis.segment;

public class Atom {
	
	private String word;//单词
	
	private int pos;//词性
	
	private int len;
	
	//开始偏移量(包含该点)
	private int offset;
	
	//结束偏移量(包含该点)
	private int end;
	
	/**
	 * 是否允许查找原子
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
