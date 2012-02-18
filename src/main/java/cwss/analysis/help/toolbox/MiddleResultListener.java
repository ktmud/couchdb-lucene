package cwss.analysis.help.toolbox;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Token;

import cwss.analysis.analyzer.TokenCollector;
import cwss.analysis.dictionary.Word;
import cwss.analysis.segment.polysemySeg;
import cwss.analysis.util.Utility;


public class MiddleResultListener implements Listener{

	private static Log log = LogFactory.getLog(MiddleResultListener.class);
	
	private int start = -1;
	
	private int end = -1;
	
	private List<MiddleResult> listMR;
	
	private String str;
	
	private TokenCollector tokenCollector;
	
	private polysemySeg polySeg;
	
	public MiddleResultListener(String str,TokenCollector tokenCollector){
		listMR = new ArrayList<MiddleResult>();
		this.tokenCollector = tokenCollector;
		this.str = str;
	}
	
	public void addWord(MiddleResult middleR){
		listMR.add(middleR);
	}
	
	public void polysemy(){
		int polysemyCount = 0;
		Word endWord = null;
		Iterator iter = listMR.iterator();
		while(iter.hasNext()){
			MiddleResult mr = (MiddleResult)iter.next();
			if(start==-1&&end==-1){
				polysemyCount++;
				start = mr.getOffset();
				end = mr.getEnd();
			}else{
				if(end==mr.getOffset()
						&&mr.getWord()!=null
						&&mr.getWord().getText().length()==2){
					//单数
					if(polysemyCount%2!=0){
						log.info("交集型歧义词:"+mr.getWord().getText()+" polysemyCount="+polysemyCount);
						mr.getWord().setNoiseWord(true);
					}		
					polysemyCount++;
				}else{
					if(polysemyCount%2==0){
						endWord.setNoiseWord(false);
					}
					polysemyCount = 1;
					log.info("非交集型歧义�?"+mr.getWord().getText()+" 已经不相�?长链�?"+polysemyCount);
				}
				start = mr.getOffset();
				end = mr.getEnd();
			}
			endWord = mr.getWord();
		}
		if(endWord!=null&&polysemyCount%2==0){
			endWord.setNoiseWord(false);
		}
		noWord();
	}
	/**
	 * 没有被分词的词语
	 */
	public void noWord(){
		start = -1;end = -1;
		Iterator iter = listMR.iterator();
		while(iter.hasNext()){
			MiddleResult mr = (MiddleResult)iter.next();
//			System.out.println(mr.getWord().getText()+" noise:"+mr.getWord().isNoiseWord()+" location:("+mr.getOffset()+","+mr.getEnd()+")");
			if(mr.getWord().isNoiseWord()){
				continue;
			}
			if(start==-1&&end==-1){
				start = mr.getOffset();
				end = mr.getEnd();
				if(start!=0){
					BinaryWord(str.substring(0, end-1),0);
					//log.info("没有被切词的词语:"+str.substring(0, end-1));
				}
			}
			//处理与上次坐标的是否相连
			else{
				//说明两个坐标是相�?�?0,1)(0,2)=(0,2) or (0,2)(2,3)=(0,3) or (0,2)(3,5)=(0,5)
				if(end>mr.getOffset()||end+1==mr.getOffset()){
					//不做处理
				}else if(end==mr.getOffset()){
					mr.getWord().setNoiseWord(true);
					//log.info("交集型歧义词:"+mr.getWord().getText());
				}else{
					int temp = mr.getOffset() - end;
					String strTemp;
					strTemp = str.substring(end+1, mr.getOffset());
					new SaveNoWord(strTemp);
					BinaryWord(strTemp,mr.getOffset());
					//log.info("没有被切词的词语:"+strTemp);
				}
				start = mr.getOffset();
				end = mr.getEnd();
			}
			tokenCollector.collect(mr.getWord().getText(), start, end);
		}
		//被切的词不是处于�?��,即处�?
		if(end!=str.length()){
			String strTemp = str.substring(end+1, str.length());
			new SaveNoWord(strTemp);
			BinaryWord(strTemp,end+1);
		}
	}
	
	protected class SaveNoWord{
		
		//�?��位置,不包含start位置,从start+1�?��
		private String noword;
		
		public SaveNoWord(String noword){
		
			this.noword = noword;
			
		}
		
	}
	
	/**
	 * 
	 *  进行�?��二元分词
	 *  考虑:包含�?��数字、一个字母的情况
	 */
	public void BinaryWord(String word,int offset){
		//二元分词之策�?word小于等于3,当成�?��词语;大于3,两两组合
		int length = word.length();

		if(length==0){
			return;
		}
		log.info("二元分词:"+word);
		//预处�?
		if(length>=2){
			//处理“我”字�?��
			if((word.charAt(0) == 25105 //�?
					|| word.charAt(0) == 20320 //�?
					|| word.charAt(0) == 20182 //�?
					|| word.charAt(0) == 22905 //�?
					|| word.charAt(0) == 23427 //�?
					|| word.charAt(0) == 21644 //�?
					|| word.charAt(0) == 20026 //�?
					|| word.charAt(0) == 29233 //�?
					|| word.charAt(0) == 23681 //�?
					|| word.charAt(0) == 26159 //�?
					|| word.charAt(0) == 30340 //�?
				)
				&& (word.charAt(1) == 30340 //�?
				|| word.charAt(1) == 35762 //�?
				|| word.charAt(1) == 23558 //�?
				|| word.charAt(1) == 20250 //�?
				|| word.charAt(1) == 35828 //�?
				|| word.charAt(1) == 35813 //�?
				|| word.charAt(1) == 35201 //�?
				|| word.charAt(1) == 25105 //�?
				|| word.charAt(1) == 20320 //�?
				|| word.charAt(1) == 20182 //�?
				|| word.charAt(1) == 22905 //�?
				|| word.charAt(1) == 23427 //�?
				|| word.charAt(1) == 26159 //�?
				|| word.charAt(1) == 29233 //�?
				|| word.charAt(1) == 30475 //�?
				|| word.charAt(1) == 22826 //�?
				|| word.charAt(1) == 36807//�?
				)) {
				tokenCollector.collect(word.substring(0, 1), offset, length);
				tokenCollector.collect(word.substring(1, 2), offset, length);
				BinaryWord(word.substring(2, word.length()),offset+2);
				return;
			}
		}
		
		if(length>0&&length<=3){
			
			tokenCollector.collect(word, offset, length);
			return;
		}

		int i = 0;
		
		//记录是否连接数字形式
		int count = 0;
		String temp;
		//穷尽二元分词(此时可能有些问题)
		for(;i<length-1;i++){
			//<<�?��后个字符是否数字形式>>策略				
			/*if((word.charAt(i)!=19975||word.charAt(i+1)==27431||//19975-‘万�?27431-‘欧�?26085-‘日�?
					word.charAt(i+1)==26085)&&Utility.toNumber(word.charAt(i+1))!=-1){
				System.out.println(word+" "+word.charAt(i));
				++count;
				continue;
			}else{
				//之所以减�?以例子说�?
				//"�?5�?,�?��天后面是1,count�?.继续读取1,�?��后面字符�?,count�?.
				//继续读取5,�?��后面非数字形�?即跳入else语句�?此时实际只是没有�?字符累加�?
				//�?��要减1.否则,就会出现"�?5�?
				boolean bool = false;
				if(count!=0){
					count--;
					bool = true;
				}*/
				temp = word.substring(i-count, i+2);
				/*if(bool) i++;
			}*/
			tokenCollector.collect(temp, offset, offset++);
			count = 0;
		}
	}
	
	public void clear(){
		listMR.clear();
	}
	
	public static void main(String args[]){
		/**
		 *  word=广东 location:(0,1)
			word=广东�?location:(0,2)
			word=湛江 location:(3,4)
			word=雷州 location:(5,6)
			word=雷州�?location:(5,7)
			word=金山 location:(12,13)
			word=火蒜 location:(14,15)
			word=恩平 location:(19,20)
		 */
		String text = "中国人民解放军副总参谋长马晓�?日在第九届亚洲安全大会上回答记�?提问时说";
		MiddleResultListener mr = new MiddleResultListener("",null);
		mr.BinaryWord(text, 0);
	}
}
