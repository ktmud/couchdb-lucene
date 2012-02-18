package cwss.analysis.segment;

import java.util.ArrayList;

import cwss.analysis.dictionary.Dictionary;
import cwss.analysis.dictionary.Hit;
import cwss.analysis.util.Utility;


/**
 * ԭ�ӷִ�
 */
public class AtomSeg {

	private String str;
	
	private ArrayList<Atom> atoms;
	
	/**
	 * �ֵ�
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
			//��������֡���ĸ�����ֽڷ��ţ�������ڵ���Щ��Ϊһ��ԭ�ӡ����磺����SHX-123�ͺŵ��ֻ��������е�SHX-123����һ��ԭ��
			boolean search = true;//Ĭ��ԭ���ܱ�����,һ�㺺�ֶ��Ǳ�����,�Ǻ��ֲ��ɱ�����.��:SHX-123
			for (int i = 0; i < ss.length; i++) {
				//ֻ��<<��>>�����,����Ż�Ϊnull
				if(ss[i]==null)continue;
				//����Ǻ���,���ۼӵ�sAtom
				//����ʲ���ַ�,��������ʽ����Ҳ������ʲ���ַ�,���硱ʲô��
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
				//A��A
				if(i+2<ss.length
						&&ss[i+1].charAt(0)==19981
						&&ss[i].charAt(0)==ss[i+2].charAt(0)
						&&ss[i].charAt(0)!=21916){//21916==ϲ
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
						//ȥ�����,����10���ּ�д��ʲ�������г�������.SEPERATOP_E������"ʲ"
						/*&& i+1 < ss.length && ss[i+1].charAt(0) != 20040*/){//20040 'ô'
					//System.out.println("��ȣ���������:"+ss[i]);
					sAtom += ss[i];
					//����Ǻ��ִ��������,��ִ��if��.��:�ֻ�SHX-123
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
					//System.out.println(sAtom+"----����ȣ���������:"+ss[i]);
					//sAtomû�б��ۼ�,������
					if(sAtom.length()==0) {						
						/**********************/
						//���������ַ���ͷ					
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
						//���������ַ���ͷ
						if(sAtom.charAt(0) == 19981){
							sAtom += ss[i];		
							
							Hit hit = hashDiction.search(sAtom, 0);
							if(hit.getWord() != null){
								//����Ǵ������һ���ַ�,��ôҲ��Ҫ������ִ�,�ٽ��з���.��:��Ư��
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
								//�Ѿ���ѯ����(��:����Ҫ��,�ò鲻��)	
								sAtom = sAtom.substring(0, sAtom.length()-1);
							}
						}			
						/**********************/
						//����Ǻ��ִ����������.��:����SHX-123�ֻ�
						//�����ȡ�����֡�,��ô�ۼӵ��ִ�sAtom��Ҫ��ӵ��µ�Atom��.��Ҫ�����ѡ��֡���ֵ��sAtom��
						Atom atom = new Atom();
						atom.setWord(sAtom);
						atom.setLen(sAtom.length());
						atom.setPos(0);
						//����1,ǿ��ת���ɿɷִ�
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
				//�����һ��ԭ��,���ָ�search����Ϊtrue.
				search = true;
			}

		}
		return result;
	}
	public ArrayList<Atom> getAtoms() {
		return atoms;
	}

}
