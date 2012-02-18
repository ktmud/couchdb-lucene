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
	 *  content�ļ��д��������ļ����ļ����ݷֱ���
	 *  1�����ʶ�С��س�����Ļ����ǰȫ�ӱض�֪���͹��»�
	 *  2��CWSS��һ����Դ�ģ�����java���Կ����������������ķִʹ��߰�,���ṩ��lucene3.0��֧�֡�Ŀǰ���ڲ��Խ׶�,�ݲ���Դ����.�������. ����GPL��ԴЭ�鷢��.
	 *  3�����籭���������
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
		 //��Ҫ�����������ĵ����ϵ�λ��  
		  File docDir = new File("D:/content/");   
		 //����������(����)  
		 IndexWriter standardWriter = new IndexWriter(FSDirectory.open(indexDir), analyzer, true , IndexWriter.MaxFieldLength.LIMITED);//new IndexWriter(FSDirectory.open(indexDir),analyzer, true, IndexWriter.MaxFieldLength.LIMITED);           
		 //����������ʽ�����ļ���Ĭ�ϵ�������Ǹ���ʽ�������ļ�  
		 standardWriter.setUseCompoundFile(false);  
		 //Ϊԭ�ĵ������е�ÿ���ĵ��������Ϣ��������  
		 for (File fileSrc : docDir.listFiles()) {     
		         //Lucene���ĵ��ṹ  
		         Document doc = new Document();                       
		         //�ļ����ƣ��ɲ�ѯ�����ִ�  
		         String fileName=fileSrc.getName().substring(0,fileSrc.getName().indexOf("."));
		         doc.add(new Field("name",fileName, Field.Store.YES, Field.Index.NOT_ANALYZED));    
		          //�ļ�·�����ɲ�ѯ�����ִ�  
		         String filePath=fileSrc.getPath();  
		         doc.add(new Field("path", filePath, Field.Store.YES, Field.Index.NOT_ANALYZED));  
		         //�ļ����ݣ���Ҫ����
		         doc.add(new Field("content", getString(new FileReader(fileSrc)),Field.Store.YES,Field.Index.ANALYZED));              
		         //ʹ����������Document�ĵ�������  
		        standardWriter.addDocument(doc);    
		 }    
		 //�ر�����������д����������ļ�  
		 standardWriter.optimize();    
		 standardWriter.close();  
	}
	
	private void search(String keyword){
		File indexDir=new File("D:/luceneIndex/"); 
		Directory directory;
		IndexSearcher isearcher = null;
		//ʵ����������   
		try {
			directory = FSDirectory.open(indexDir);
			isearcher = new IndexSearcher(directory);
			QueryParser parser = new QueryParser(Version.LUCENE_CURRENT, "content",
					analyzer);
			//ʹ��IKQueryParser��ѯ����������Query����
			Query query = parser.parse(keyword);
			
			//�������ƶ���ߵ�5����¼
			TopDocs topDocs = isearcher.search(query, 2);
			System.out.println("���У�" + topDocs.totalHits);
			//������
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (int i = 0; i < topDocs.totalHits; i++){
				Document targetDoc = isearcher.doc(scoreDocs[i].doc);
				System.out.println("���ݣ�" + targetDoc.toString());
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
		lucene.search("����");
	}
}
