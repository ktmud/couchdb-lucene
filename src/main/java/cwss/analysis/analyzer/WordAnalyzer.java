package cwss.analysis.analyzer;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cwss.analysis.dictionary.Dictionary;
import cwss.analysis.dictionary.Hit;
import cwss.analysis.dictionary.Word;
import cwss.analysis.help.toolbox.MiddleResult;
import cwss.analysis.help.toolbox.MiddleResultListener;
import cwss.analysis.segment.Atom;
import cwss.analysis.segment.AtomSeg;
import cwss.analysis.segment.Sentence;
import cwss.analysis.segment.SentenceSeg;
import cwss.analysis.util.Utility;


public class WordAnalyzer {

	private static Log log = LogFactory.getLog(WordAnalyzer.class);
	
	private String str;
	
	private TokenCollector tokenCollector;
	
	/**
	 * �ֵ�
	 */
	private Dictionary hashDiction;
	
	public WordAnalyzer(String str,TokenCollector tokenCollector,Dictionary hashDiction){
		this.str = str;
		this.tokenCollector = tokenCollector;
		this.hashDiction = hashDiction;
		//ԭ��
		AtomSeg atomseg = new AtomSeg(str,hashDiction);
		//�Ͼ�
		SentenceSeg sentence = new SentenceSeg(atomseg);
		//����Ͼ�
		ArrayList<Sentence> sens = sentence.getSens();
		Iterator<Sentence> iter = sens.iterator();
		while(iter.hasNext()){
			Sentence sen = iter.next();
			analysis(sen);
		}
	}
	
	public void analysis(Sentence sen){
		
		String str = sen.getContent();
		
		MiddleResultListener mrl = new MiddleResultListener(str,tokenCollector);
		
		if(str==null||str.length()==0){
			
			//log.info("str is null.");
			
			return;
			
		}
		
		//�öϾ䲻����ִ�,ֱ�����
		if(!sen.isSeg()){

			MiddleResult mr = null;
			
			mr = new MiddleResult();
			
			mr.addWord(new Word(str));
			
			mr.setEnd(str.length());
			
			mr.setOffset(0);
			
			mrl.addWord(mr);
			
			mrl.noWord();
			
			return;
			
		}

		/***********���뱣֤�����ԭ�Ӳ�������һ��ԭ�ӷ�ʽ����,�����Զ���ַ��γɵ�ԭ�ӷ�ʽ����***********/
		
		//AtomSeg atomseg = new AtomSeg(str,hashDiction);
		
		//ArrayList<Atom> atoms = atomseg.getAtoms();
		
		String atoms[] = Utility.atomSplit(str);
		
		int size = atoms.length;
		
		for(int i=0;i<size;i++){
			
			int j = i + 1;

			StringBuffer sb = null;
			
			sb = new StringBuffer();
			
			String atomStr1;
			
			atomStr1 = atoms[i];
			
			sb.append(atomStr1);
			
			MiddleResult mr = search(sb.toString(),i,i);
			
			if(mr!=null){
				
				mrl.addWord(mr);
				
			}
			
			for(;j<size;j++){
				
				String atomStr2;
				
				atomStr2 = atoms[j];

				mr = search(sb.append(atomStr2).toString(),i,j);
				
				if(mr!=null){
					
					mrl.addWord(mr);
					
				}
				
			}
			
		}
		
		/***********���뱣֤�����ԭ�Ӳ�������һ��ԭ�ӷ�ʽ����,�����Զ���ַ��γɵ�ԭ�ӷ�ʽ����***********/
		
		//mrl.noWord();
		mrl.polysemy();
	}
	
	/**
	 * 
	 * @param �����ַ���
	 * @param offset ��ʼƫ����(������ƫ����)
	 * @param end ����ƫ����(������ƫ����)
	 */
	public MiddleResult search(String str,int offset,int end){
		Hit hit = hashDiction.search(str, 0);
		Word word = hit.getWord();
		MiddleResult mr = null;
		if(word!=null) {		
			
			mr = new MiddleResult();
			mr.addWord(word);
			mr.setEnd(end);
			mr.setOffset(offset);
			//System.out.println("word="+word.getText()+" location:("+offset+","+end+")");
		}
		hit = null;
		word = null;
		return mr;
	}
}
