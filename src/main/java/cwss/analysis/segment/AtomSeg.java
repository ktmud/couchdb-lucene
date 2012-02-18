package cwss.analysis.segment;

import java.util.ArrayList;

import cwss.analysis.dictionary.Dictionary;
import cwss.analysis.dictionary.Hit;
import cwss.analysis.util.Utility;


/**
 * 原子分词
 */
public class AtomSeg {

	private String str;
	
	private ArrayList<Atom> atoms;
	
	/**
	 * 字典
	 */
	private Dictionary hashDiction;
	
	public AtomSeg(String src,Dictionary hashDiction){
		this.str = src;
		this.hashDiction = hashDiction;
		atoms = atomSplit();
	}
	
	private ArrayList<Atom> atomSplit() {
		ArrayList<Atom> result = null;

		if (str != null && str.length() > 0) {
			String sAtom = "";
			result = new ArrayList<Atom>();
			String[] ss = Utility.atomSplit(str);
			//如果是数字、字母、单字节符号，则把相邻的这些做为一个原子。比如：三星SHX-123型号的手机，则其中的SHX-123就是一个原子
			boolean search = true;//默认原子能被查找,一般汉字都是被查找,非汉字不可被查找.如:SHX-123
			for (int i = 0; i < ss.length; i++) {
				//只有<<、>>的情况,数组才会为null
				if(ss[i]==null)continue;
				//如果非汉字,即累加到sAtom
				//处理”什“字符,非数字形式可能也包含”什“字符,比如”什么“
				//AABB
				if(i+3<ss.length
						&&ss[i].charAt(0)==ss[i+1].charAt(0)
						&&ss[i+2].charAt(0)==ss[i+3].charAt(0)){
					sAtom = ss[i]+ss[i+1]+ss[i+2]+ss[i+3];
					//AABB
					Atom atom = new Atom();
					atom.setWord(sAtom);
					atom.setLen(sAtom.length());
					atom.setPos(i);
					atom.setSearch(!search);
					result.add(atom);
					//AB
					atom = new Atom();
					atom.setWord(ss[i+1]+ss[i+2]);
					atom.setLen((ss[i+1]+ss[i+2]).length());
					atom.setPos(i);
					atom.setSearch(!search);
					result.add(atom);
					i = i + 3;
					sAtom = "";
					continue;
				}
				//A不A
				if(i+2<ss.length
						&&ss[i+1].charAt(0)==19981
						&&ss[i].charAt(0)==ss[i+2].charAt(0)
						&&ss[i].charAt(0)!=21916){//21916==喜
					sAtom = ss[i] + ss[i+1] + ss[i+2];
					Atom atom = new Atom();
					atom.setWord(sAtom);
					atom.setLen(sAtom.length());
					atom.setPos(i);
					atom.setSearch(!search);
					result.add(atom);
					i = i + 2;
					sAtom = "";
					continue;
				}
				if(Utility.SEPERATOP_E.indexOf(ss[i]) != -1
						//去掉这个,处理10数字加写《什》类型有出现问题.SEPERATOP_E不包含"什"
						/*&& i+1 < ss.length && ss[i+1].charAt(0) != 20040*/){//20040 '么'
					//System.out.println("相等，进入来了:"+ss[i]);
					sAtom += ss[i];
					//如果非汉字串处于最后,即执行if块.如:手机SHX-123
					if(i==ss.length-1){
						Atom atom = new Atom();
						atom.setWord(sAtom);
						atom.setLen(sAtom.length());
						atom.setPos(0);
						atom.setSearch(!search);
						result.add(atom);
						//System.out.println("sAtom="+sAtom+" sAtom="+sAtom.length());
						sAtom = "";
					}
					continue;
				}else{					
					//System.out.println(sAtom+"----不相等，进入来了:"+ss[i]);
					//sAtom没有被累加,即汉字
					if(sAtom.length()==0) {						
						/**********************/
						//处理“不”字符开头					
						if(ss[i].charAt(0) == 19981) {
							
							sAtom += ss[i];	

							//System.out.println("sAtom1="+sAtom);
							Hit hit = hashDiction.search(sAtom, 0);
							if(hit.getWord() != null){
								continue;
							}
							
						}
						/**********************/
						sAtom = ss[i];
						
					}else{

						/**********************/
						//处理“不”字符开头
						if(sAtom.charAt(0) == 19981){
							sAtom += ss[i];		
							
							Hit hit = hashDiction.search(sAtom, 0);
							if(hit.getWord() != null){
								//如果是处于最后一个字符,那么也需要保存该字串,再进行返回.如:不漂亮
								if(i == ss.length - 1) {
									Atom atom = new Atom();
									atom.setWord(sAtom);
									atom.setLen(sAtom.length());
									atom.setPos(0);
									atom.setSearch(!search);
									result.add(atom);
								}
								continue;
							}else{
								//已经查询不到(如:不需要你,该查不到)	
								sAtom = sAtom.substring(0, sAtom.length()-1);
							}
						}			
						/**********************/
						//如果非汉字串不处于最后.如:三星SHX-123手机
						//假设读取到“手”,那么累加的字串sAtom需要添加到新的Atom中.还要继续把“手”赋值于sAtom中
						Atom atom = new Atom();
						atom.setWord(sAtom);
						atom.setLen(sAtom.length());
						atom.setPos(0);
						//等于1,强行转换成可分词
						if(sAtom.length()==1){
							atom.setSearch(search);
						}else{
							atom.setSearch(!search);
						}
						result.add(atom);
						//System.out.println("sAtom="+sAtom+" sAtom="+sAtom.length());
						sAtom = ss[i];

					}
				}

				Atom atom = new Atom();
				atom.setWord(sAtom);
				atom.setLen(sAtom.length());
				atom.setPos(0);
				atom.setSearch(search);
				result.add(atom);
				//System.out.println("sAtom="+sAtom+" sAtom="+sAtom.length());
				sAtom = "";
				//添加完一个原子,即恢复search变量为true.
				search = true;
			}

		}
		return result;
	}
	public ArrayList<Atom> getAtoms() {
		return atoms;
	}

}
