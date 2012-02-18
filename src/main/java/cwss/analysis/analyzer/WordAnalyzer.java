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
	 * 字典
	 */
	private Dictionary hashDiction;
	
	public WordAnalyzer(String str,TokenCollector tokenCollector,Dictionary hashDiction){
		this.str = str;
		this.tokenCollector = tokenCollector;
		this.hashDiction = hashDiction;
		//原子
		AtomSeg atomseg = new AtomSeg(str,hashDiction);
		//断句
		SentenceSeg sentence = new SentenceSeg(atomseg);
		//处理断句
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
		
		//该断句不允许分词,直接添加
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

		/***********必须保证下面的原子操作都是一个原子方式运行,不可以多个字符形成的原子方式运行***********/
		
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
		
		/***********必须保证下面的原子操作都是一个原子方式运行,不可以多个字符形成的原子方式运行***********/
		
		//mrl.noWord();
		mrl.polysemy();
	}
	
	/**
	 * 
	 * @param 搜索字符串
	 * @param offset 开始偏移量(包含该偏移量)
	 * @param end 结束偏移量(包含该偏移量)
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
