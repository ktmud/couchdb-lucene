package cwss.analysis.util;

public class Utility {

	// Seperator type
	public static final String SEPERATOR_C_SENTENCE = "������������";

	public static final String SEPERATOR_C_SUB_SENTENCE = "����������������";

	public static final String SEPERATOR_E_SENTENCE = "!?:;<>����";

	public static final String SEPERATOR_E_SUB_SENTENCE = ",()\"'";

	public static final String SEPERATOR_LINK = "\n\r ��";
	
	public static final String SEPERATOP_E = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-.@�㩖һҼ�������E������������������߰ư˾ž�ʮ�ٰ�ǧǪ���f�ڃ|";

	public static boolean isBom(char ch) {
		return ch == 0xFEFF || ch == 0xFFFE;
	}
	
	/**
	 * ���ַ�������ԭ�ӷָ�,����:��ž���101ҽԺ----�� �� �� �� 1 0 1 ҽ Ժ
	 * ����ַ���<ת���ɡ�,>ת���ɡ�,'��"ת���ɡ�
	 * @param str
	 * @return
	 */
	public static String[] atomSplit(String str) {		
		if (str==null) {return null;}
		
		String[] result = null;
		int nLen=str.length();
		result = new String[nLen];
		
		//���������,��������������
		boolean mark = false;
		for (int i = 0; i < nLen; i++) {
			result[i] = str.substring(i, i + 1);
			if(result[i].charAt(0) == 60
					){
				
				result[i] = "��";
				
				//����<<�����
				if(i+1 < nLen && str.substring(i+1, i + 2).charAt(0) == 60 ){
					i++;
				}
				
				continue;
				
			}
			if(result[i].charAt(0) == 62){
				
				result[i] = "��";
				
				//����>>�����
				if(i+1 < nLen && str.substring(i+1, i + 2).charAt(0) == 62 ){
					i++;
				}
				
				continue;
			}
			
			if(!mark && (result[i].charAt(0) == 39
					|| result[i].charAt(0) == 34)){
				result[i] = "��";
				mark = true;
				continue;
			}
			
			if(mark && (result[i].charAt(0) == 39
					|| result[i].charAt(0) == 34)){
				result[i] = "��";
				mark = false;
				continue;
			}
			
		}
		return result;
	}
	
	/**
	 * �ж�һ���ַ����Ƿ�������
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (str != null) {

			try {
				str = str.trim();
				double d = Double.parseDouble(str);
				d = d + 1;
				return true;
			} catch (NumberFormatException e) {

			}
		}
		return false;
	}
		
	/**
	 * �ַ�����{@link #subs}��keyֵ��
	 */
	public static Object keyOf(char theChar) {
		// return theChar - 0x4E00;// 'һ'==0x4E00
		return new Integer(theChar);
	}
	
	//�ð취�Ƿ����
	public static int toNumber(char ch) {
		switch (ch) {
		case '0':
		case '��':
		case '��':
			return 0;
		case '1':
		case 'һ':
		case 'Ҽ':
			return 1;
		case '2':
		case '��':
		case '��':
		case '��':
		case '�E':
			return 2;
		case '3':
		case '��':
		case '��':
			return 3;
		case '4':
		case '��':
		case '��':
			return 4;
		case '5':
		case '��':
		case '��':
			return 5;
		case '6':
		case '��':
		case '�':
			return 6;
		case '7':
		case '��':
		case '��':
			return 7;
		case '8':
		case '��':
		case '��':
			return 8;
		case '9':
		case '��':
		case '��':
			return 9;
		case 'ʮ':
		case 'ʲ':
			return 10;
		case '��':
		case '��':
			return 100;
		case 'ǧ':
		case 'Ǫ':
			return 1000;
		case '��':
		case '�f':
			return 10000;
		case '��':
		case '�|':
			return 100000000;
		case '��':
			return 31532;
		case '��':
			return 26085;
		case 'ŷ':
			return 27431;
		case 'a':
			return 97;
		case 'A':
			return 65;
		case 'B':
			return 66;
		case 'C':
			return 67; 
		case 'D':
			return 68;
		case 'E': 
			return 69;
		case 'F': 
			return 70;
		case 'G': 
			return 71;
		case 'H': 
			return 72;
		case 'I': 
			return 73;
		case 'J': 
			return 74;
		case 'K': 
			return 75;
		case 'L': 
			return 76;
		case 'M': 
			return 77;
		case 'N': 
			return 78;
		case 'O': 
			return 79;
		case 'P': 
			return 80;
		case 'Q': 
			return 81;
		case 'R': 
			return 82;
		case 'S': 
			return 83;
		case 'T': 
			return 84;
		case 'U':
			return 85;
		case 'V': 
			return 86;
		case 'W': 
			return 87;
		case 'X': 
			return 88;
		case 'Y': 
			return 89;
		case 'Z': 
			return 90;
		default:
			return -1;
		}
	}

}
