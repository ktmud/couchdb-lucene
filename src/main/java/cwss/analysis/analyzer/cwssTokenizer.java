package cwss.analysis.analyzer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;

import cwss.analysis.dictionary.Dictionary;


public class cwssTokenizer extends TokenStream{

	/**
	 * 文本字符源
	 * 
	 * @see #next()
	 */
	private final Reader input;
	
	/**
	 * 	词元文本属性
	 */
	private TermAttribute termAtt;
	
	/**
	 * 词元位移属性
	 */
	private OffsetAttribute offsetAtt;
	
	private TokenCollector tokenCollector;
	
	/**
	 * tokens迭代器，用于next()方法顺序读取tokens中的Token对象
	 * 
	 * @see #tokens
	 * @see #next()
	 */
	private Iterator/* <Token> */ tokenIteractor;
	
	public cwssTokenizer(Reader input, TokenCollector tokenCollector,Dictionary hashDiction) {
		this.input = input;
		this.tokenCollector = tokenCollector;//MostWordsTokenCollector
		/*******分词处理*******/
		try{
			new WordAnalyzer(getString(input),tokenCollector,hashDiction);
			this.tokenIteractor = this.tokenCollector.iterator();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			termAtt = addAttribute(TermAttribute.class);
			offsetAtt = addAttribute(OffsetAttribute.class);
		}
		/*******分词处理*******/
	}
	
	public Token next() throws IOException {
		//返回<Token>结果集
		if(tokenIteractor.hasNext()){
			return (Token) tokenIteractor.next();
		}
		return null;
	}
	
	private String getString(Reader input) throws IOException{
		BufferedReader buf;
		buf = new BufferedReader(input);
		String str;
		StringBuffer sb = new StringBuffer();
		while ((str = buf.readLine()) != null) {        
			sb.append(str);    
	    }
		str = sb.toString();
		sb = null;
		return str;
	}

	@Override
	public boolean incrementToken() throws IOException {
		// TODO Auto-generated method stub
		//清除所有的词元属性
		clearAttributes();
		Token nextLexeme = next();
		if(nextLexeme != null){
			//将Lexeme转成Attributes
			//设置词元文本
			termAtt.setTermBuffer(nextLexeme.term());
			//设置词元长度
			termAtt.setTermLength(nextLexeme.termLength());
			//设置词元位移
			offsetAtt.setOffset(nextLexeme.startOffset(), nextLexeme.endOffset());
			//返会true告知还有下个词元
			return true;
		}
		//返会false告知词元输出完毕
		return false;
	}
	
}
