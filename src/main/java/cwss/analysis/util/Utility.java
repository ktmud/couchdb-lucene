package cwss.analysis.util;

public class Utility {

	// Seperator type
	public static final String SEPERATOR_C_SENTENCE = "。！？：；…";

	public static final String SEPERATOR_C_SUB_SENTENCE = "、，（）“”‘’";

	public static final String SEPERATOR_E_SENTENCE = "!?:;<>《》";

	public static final String SEPERATOR_E_SUB_SENTENCE = ",()\"'";

	public static final String SEPERATOR_LINK = "\n\r 　";
	
	public static final String SEPERATOP_E = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-.@零〇一壹二两俩貳三叁四肆五伍六陸柒七捌八九玖十百佰千仟万萬亿億";

	public static boolean isBom(char ch) {
		return ch == 0xFEFF || ch == 0xFFFE;
	}
	
	/**
	 * 对字符串进行原子分隔,比如:解放军第101医院----解 放 军 第 1 0 1 医 院
	 * 如果字符是<转换成《,>转换成》,'、"转换成”
	 * @param str
	 * @return
	 */
	public static String[] atomSplit(String str) {		
		if (str==null) {return null;}
		
		String[] result = null;
		int nLen=str.length();
		result = new String[nLen];
		
		//标记性作用,无其他特殊意义
		boolean mark = false;
		for (int i = 0; i < nLen; i++) {
			result[i] = str.substring(i, i + 1);
			if(result[i].charAt(0) == 60
					){
				
				result[i] = "《";
				
				//处理<<的情况
				if(i+1 < nLen && str.substring(i+1, i + 2).charAt(0) == 60 ){
					i++;
				}
				
				continue;
				
			}
			if(result[i].charAt(0) == 62){
				
				result[i] = "》";
				
				//处理>>的情况
				if(i+1 < nLen && str.substring(i+1, i + 2).charAt(0) == 62 ){
					i++;
				}
				
				continue;
			}
			
			if(!mark && (result[i].charAt(0) == 39
					|| result[i].charAt(0) == 34)){
				result[i] = "“";
				mark = true;
				continue;
			}
			
			if(mark && (result[i].charAt(0) == 39
					|| result[i].charAt(0) == 34)){
				result[i] = "”";
				mark = false;
				continue;
			}
			
		}
		return result;
	}
	
	/**
	 * 判断一个字符串是否是数字
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
	 * 字符的在{@link #subs}的key值。
	 */
	public static Object keyOf(char theChar) {
		// return theChar - 0x4E00;// '一'==0x4E00
		return new Integer(theChar);
	}
	
	//该办法是否改造
	public static int toNumber(char ch) {
		switch (ch) {
		case '0':
		case '零':
		case '〇':
			return 0;
		case '1':
		case '一':
		case '壹':
			return 1;
		case '2':
		case '二':
		case '两':
		case '俩':
		case '貳':
			return 2;
		case '3':
		case '三':
		case '叁':
			return 3;
		case '4':
		case '四':
		case '肆':
			return 4;
		case '5':
		case '五':
		case '伍':
			return 5;
		case '6':
		case '六':
		case '陸':
			return 6;
		case '7':
		case '柒':
		case '七':
			return 7;
		case '8':
		case '捌':
		case '八':
			return 8;
		case '9':
		case '九':
		case '玖':
			return 9;
		case '十':
		case '什':
			return 10;
		case '百':
		case '佰':
			return 100;
		case '千':
		case '仟':
			return 1000;
		case '万':
		case '萬':
			return 10000;
		case '亿':
		case '億':
			return 100000000;
		case '第':
			return 31532;
		case '日':
			return 26085;
		case '欧':
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
