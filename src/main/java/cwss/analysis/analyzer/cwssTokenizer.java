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
	 * �ı��ַ�Դ
	 * 
	 * @see #next()
	 */
	private final Reader input;
	
	/**
	 * 	��Ԫ�ı�����
	 */
	private TermAttribute termAtt;
	
	/**
	 * ��Ԫλ������
	 */
	private OffsetAttribute offsetAtt;
	
	private TokenCollector tokenCollector;
	
	/**
	 * tokens������������next()����˳���ȡtokens�е�Token����
	 * 
	 * @see #tokens
	 * @see #next()
	 */
	private Iterator/* <Token> */ tokenIteractor;
	
	public cwssTokenizer(Reader input, TokenCollector tokenCollector,Dictionary hashDiction) {
		this.input = input;
		this.tokenCollector = tokenCollector;//MostWordsTokenCollector
		/*******�ִʴ���*******/
		try{
			new WordAnalyzer(getString(input),tokenCollector,hashDiction);
			this.tokenIteractor = this.tokenCollector.iterator();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			termAtt = addAttribute(TermAttribute.class);
			offsetAtt = addAttribute(OffsetAttribute.class);
		}
		/*******�ִʴ���*******/
	}
	
	public Token next() throws IOException {
		//����<Token>�����
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
		//������еĴ�Ԫ����
		clearAttributes();
		Token nextLexeme = next();
		if(nextLexeme != null){
			//��Lexemeת��Attributes
			//���ô�Ԫ�ı�
			termAtt.setTermBuffer(nextLexeme.term());
			//���ô�Ԫ����
			termAtt.setTermLength(nextLexeme.termLength());
			//���ô�Ԫλ��
			offsetAtt.setOffset(nextLexeme.startOffset(), nextLexeme.endOffset());
			//����true��֪�����¸���Ԫ
			return true;
		}
		//����false��֪��Ԫ������
		return false;
	}
	
}
