package cwss.analysis.analyzer;

import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

import cwss.analysis.dictionary.Dictionary;
import cwss.analysis.dictionary.FileDictionary;
import cwss.analysis.dictionary.Hit;
import cwss.analysis.dictionary.Word;
import cwss.analysis.help.toolbox.MiddleResult;
import cwss.analysis.help.toolbox.MiddleResultListener;
import cwss.analysis.segment.Atom;
import cwss.analysis.segment.AtomSeg;


public class cwssAnalyzer extends Analyzer{

	private static final String DICHOME = "/dic/wss-base-word.dic";
	private static final String CHARSET = "UTF-8";
	private Dictionary hashDiction;
	
	public cwssAnalyzer(){
		
		FileDictionary fd = new FileDictionary(DICHOME, CHARSET);
		hashDiction = fd.getAllWordsDic();
	}

	@Override
	public TokenStream tokenStream(String fieldName, Reader reader) {
		TokenCollector collector = createTokenCollector();
		return new cwssTokenizer(reader, collector,hashDiction);
	}
	
	private TokenCollector createTokenCollector() {
		return new MostWordsTokenCollector();
	}
	
}
