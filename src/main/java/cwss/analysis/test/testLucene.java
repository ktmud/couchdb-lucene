package cwss.analysis.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;

import cwss.analysis.analyzer.cwssAnalyzer;


public class testLucene {

	private Analyzer analyzer = new cwssAnalyzer();
	
	/**
	 *  content文件夹存在两个文件。文件内容分别是
	 *  1、朝鲜队小组必出线内幕：赛前全队必读知音和故事会
	 *  2、CWSS是一个开源的，基于java语言开发的轻量级的中文分词工具包,并提供对lucene3.0的支持。目前正在测试阶段,暂不开源代码.测试完毕. 在以GPL开源协议发布.
	 *  3、世界杯朝鲜输掉了
	 */
	private String getString(Reader input) throws IOException{
		BufferedReader buf;
		buf = new BufferedReader(input);
		String str;
		StringBuffer sb = new StringBuffer();
		while ((str = buf.readLine()) != null) {        
			sb.append(str);    
	    }
		str = sb.toString();
		sb = null;
		return str;
	}
	
	private void index() throws CorruptIndexException, LockObtainFailedException, IOException{
		 File indexDir=new File("D:/luceneIndex/");  
		 //需要建立索引的文档集合的位置  
		  File docDir = new File("D:/content/");   
		 //创建索引器(核心)  
		 IndexWriter standardWriter = new IndexWriter(FSDirectory.open(indexDir), analyzer, true , IndexWriter.MaxFieldLength.LIMITED);//new IndexWriter(FSDirectory.open(indexDir),analyzer, true, IndexWriter.MaxFieldLength.LIMITED);           
		 //不建立复合式索引文件，默认的情况下是复合式的索引文件  
		 standardWriter.setUseCompoundFile(false);  
		 //为原文档集合中的每个文档的相关信息建立索引  
		 for (File fileSrc : docDir.listFiles()) {     
		         //Lucene的文档结构  
		         Document doc = new Document();                       
		         //文件名称，可查询，不分词  
		         String fileName=fileSrc.getName().substring(0,fileSrc.getName().indexOf("."));
		         doc.add(new Field("name",fileName, Field.Store.YES, Field.Index.NOT_ANALYZED));    
		          //文件路径，可查询，不分词  
		         String filePath=fileSrc.getPath();  
		         doc.add(new Field("path", filePath, Field.Store.YES, Field.Index.NOT_ANALYZED));  
		         //文件内容，需要检索
		         doc.add(new Field("content", getString(new FileReader(fileSrc)),Field.Store.YES,Field.Index.ANALYZED));              
		         //使用索引器对Document文档建索引  
		        standardWriter.addDocument(doc);    
		 }    
		 //关闭索引器，并写入磁盘索引文件  
		 standardWriter.optimize();    
		 standardWriter.close();  
	}
	
	private void search(String keyword){
		File indexDir=new File("D:/luceneIndex/"); 
		Directory directory;
		IndexSearcher isearcher = null;
		//实例化搜索器   
		try {
			directory = FSDirectory.open(indexDir);
			isearcher = new IndexSearcher(directory);
			QueryParser parser = new QueryParser(Version.LUCENE_CURRENT, "content",
					analyzer);
			//使用IKQueryParser查询分析器构造Query对象
			Query query = parser.parse(keyword);
			
			//搜索相似度最高的5条记录
			TopDocs topDocs = isearcher.search(query, 2);
			System.out.println("命中：" + topDocs.totalHits);
			//输出结果
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (int i = 0; i < topDocs.totalHits; i++){
				Document targetDoc = isearcher.doc(scoreDocs[i].doc);
				System.out.println("内容：" + targetDoc.toString());
			}
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws IOException {
		testLucene lucene = new testLucene();
		lucene.index();
		lucene.search("java");
		lucene.search("朝鲜");
	}
}
