package cwss.analysis.analyzer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.Token;

public class MostWordsTokenCollector implements TokenCollector, Iterator{

	private List<LinkedToken> list = new ArrayList<LinkedToken>();
	
	public void collect(String word, int offset, int end) {
		// TODO Auto-generated method stub
		LinkedToken tokenToAdd = new LinkedToken(word, offset, end);
		list.add(tokenToAdd);
	}

	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	public Object next() {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove() {
		// TODO Auto-generated method stub
		
	}
	
	private static class LinkedToken extends Token {

		public LinkedToken(String word, int begin, int end) {
			super(word, begin, end);
		}
		
	}

	public Iterator iterator() {
		// TODO Auto-generated method stub
		return list.iterator();
	}

}
