package cwss.analysis.segment;

public class Sentence {

	private String content;

	// �Ƿ���Ҫ���зִ�.�س����кͿո��ǲ���Ҫ��
	private boolean isSeg;
	
	public Sentence(){
		
	}
	
	public Sentence(String content){
		this.content=content;
	}
	
	public Sentence(String content,boolean isSeg){
		this.content=content;
		this.isSeg=isSeg;
	}
	
	public boolean isSeg() {
		return isSeg;
	}
	
	public String getContent() {
		return content;
	}
	
}
