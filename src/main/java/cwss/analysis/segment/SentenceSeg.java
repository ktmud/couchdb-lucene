package cwss.analysis.segment;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cwss.analysis.util.Utility;


public class SentenceSeg {

	private static Log log = LogFactory.getLog(SentenceSeg.class);
	
	private AtomSeg atomseg;
	
	private ArrayList<Sentence> sens;
	
	public SentenceSeg(AtomSeg atomseg){
		this.atomseg=atomseg;		
		sens=split();
	}
	
	public ArrayList<Sentence> split() {
		
		ArrayList<Sentence> result = null;	
		
		ArrayList<Atom> atoms = atomseg.getAtoms();	
		
		int size = atoms.size();
		
		result = new ArrayList<Sentence>();
		
		StringBuffer sb;
		
		sb = new StringBuffer();
		
		sb.setLength(0);
		
		for(int i=0;i<size;i++){
			String atom = atoms.get(i).getWord();
			if(!atoms.get(i).isSearch()){
				result.add(new Sentence(sb.toString(),true));
				//log.info("bulid object of Sentence.1"+sb.toString());
				sb.delete(0, sb.length());
				result.add(new Sentence(atoms.get(i).getWord(),false));
				//log.info("bulid object of Sentence.2"+atoms.get(i).getWord());
				continue;
			}

			if ( Utility.SEPERATOR_C_SENTENCE.indexOf(atom) != -1
					|| Utility.SEPERATOR_LINK.indexOf(atom) != -1
					|| Utility.SEPERATOR_C_SUB_SENTENCE.indexOf(atom) != -1
					|| Utility.SEPERATOR_E_SUB_SENTENCE.indexOf(atom) != -1
					|| Utility.SEPERATOR_E_SENTENCE.indexOf(atom) != -1
					|| i == size -1 ) {
				
				//字符等于《、“
				if(atom.charAt(0) == 12298
						||atom.charAt(0) == 8220) {
					result.add(new Sentence(sb.toString(),true));
					//log.info("bulid object of Sentence.3"+sb.toString());
					sb.delete(0, sb.length());				
					continue;
				}
				
				//如果等于》、“,即不需要分词
				if(atom.charAt(0) == 12299
						||atom.charAt(0) == 8221) {
					result.add(new Sentence(sb.toString(),false));
					//log.info("bulid object of Sentence.4"+sb.toString());
					sb.delete(0, sb.length());				
					continue;
				}
				
				if(atoms.get(i).getWord().matches("[\u4E00-\u9FA5]")
						||(atoms.get(i).getWord().charAt(0)>=48&&atoms.get(i).getWord().charAt(0)<=57)
						||(atoms.get(i).getWord().charAt(0)>=65&&atoms.get(i).getWord().charAt(0)<=90)
						||(atoms.get(i).getWord().charAt(0)>=97&&atoms.get(i).getWord().charAt(0)<=122)){
					sb.append(atoms.get(i).getWord());
				}
				result.add(new Sentence(sb.toString(),true));
				//log.info("bulid object of Sentence.5"+sb.toString());
				sb.delete(0, sb.length());			
			}else{
				sb.append(atoms.get(i).getWord());
			}
			
		}
		return result;
	}
	
	public ArrayList<Sentence> getSens() {
		return sens;
	}
	
	public static void main(String args[]){
		String src = "三星SHX-123型号的<<手机>>";
		src = "千万不要出什么";
//		SentenceSeg seg = new SentenceSeg(src);
	}
	
}
