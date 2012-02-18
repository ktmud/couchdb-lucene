package cwss.analysis.dictionary;

import java.util.Arrays;

public class BinaryDictionary implements Dictionary{

	private Word[] ascWords;

	private final int start;
	private final int end;
	private final int count;
	
	/**
	 * ��һ���������еĴ��ﹹ���������ֵ�
	 * <p>
	 * 
	 * @param ascWords
	 *            �������д���
	 */
	public BinaryDictionary(Word[] ascWords) {
		this(ascWords, 0, ascWords.length);
	}
	
	public BinaryDictionary(Word[] ascWords, int start, int end) {
		this.ascWords = ascWords;
		this.start = start;
		this.end = end;
		this.count = end - start;
	}

	public Hit search(CharSequence input, int begin) {
		int left = this.start;
		int right = this.end - 1;
		//Ҫ�������ַ����ַ�����
		int count = input.length();
		int pointer = 0;//ָ��
		Word word = null;
		int relation;//��ϵ		
		while (left <= right) {
			pointer = (left + right) >> 1;
			
			word = ascWords[pointer];
			relation = compare(input, begin, count, word);
			
			//System.out.println(word.getText());
			//�ҵ�
			if(relation==0){
				int nextWordIndex = pointer + 1;
				if (nextWordIndex >= ascWords.length) {
					return new Hit(pointer, word, null);
				} else {
					return new Hit(pointer, word, ascWords[nextWordIndex]);
				}
			}
			if (relation < 0)//input�ַ���С��ascWords�����ַ���(��ôֻ��������ߵ��ַ�������)
				right = pointer - 1;
			else{//input�ַ�������ascWords�����ַ���(��ôֻ�������ұߵ��ַ�������)
				left = pointer + 1;
			}
		}
		return Hit.UNDEFINED;
	}

	public static int compare(CharSequence one, int begin, int count,
			CharSequence theOther) {
		
		for (int i = begin, j = 0; i < one.length()
				&& j < Math.min(theOther.length(), count); i++, j++) {
			
			if (one.charAt(i) > theOther.charAt(j)) {
				return 1;
			} else if (one.charAt(i) < theOther.charAt(j)) {
				return -1;
			}
		}
		return count - theOther.length();
	}
	
	public int size() {
		// TODO Auto-generated method stub
		return count;
	}
	
	public static void main(String args[]){
		Word w1 = new Word("��ƽ");//���������
		Word w2 = new Word("��ƽ��");
		Word w3 = new Word("��ɽ");
		Word w4 = new Word("��");
		Word w6 = new Word("����");
		Word[] words = new Word[5];
		words[0] = w1;words[1] = w2;words[2] = w3;words[3] = w4;words[4] = w6;
		Arrays.sort(words);
		for(int i=0;i<words.length;i++){
			System.out.println(words[i].getText()+" ");
		}
//		System.out.println();
//		char s[] = {'��','��','��','��','��'};
//		for(int i=0;i<s.length;i++){
//			System.out.println(s[i]+" "+(int)s[i]);
//		}
		BinaryDictionary bd = new BinaryDictionary(words);
		bd.search("��ƽ", 0);
	}

	public Word get(int index) {
		// TODO Auto-generated method stub
		return ascWords[start + index];
	}

}
