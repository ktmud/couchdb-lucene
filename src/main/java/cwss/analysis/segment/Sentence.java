package cwss.analysis.segment;

public class Sentence {

	private String content;

	// 是否需要进行分词.回车换行和空格是不需要的
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
