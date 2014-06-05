package searchHandler;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import storageHandler.Sincronizador;
import java.io.IOException;

import imageHandler.Imagem;

/**Realiza a busca de imagens por palavra chave
 * 
 * @author Felipe e Pedro
 *
 */

public class BuscaTags{
	private static StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_48);
    private static Directory index = new RAMDirectory();
    private static IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_48, analyzer);
	
	/**
	* Cria a biblioteca das imagens
	*/
	public static final void addImageDoc() throws IOException{
	Sincronizador sinc = new Sincronizador();
		Imagem[] image;
		image = sinc.readXML();
	    IndexWriter w = new IndexWriter(index, config);
	    for (int i = 0; i < image.length; i++) {
			addDoc(w, image[i]);
		}
	    w.close();
		return;
	}
		
	/**
	* Faz a busca nos documentos retornando 
	* todas as imagens que contenham o termo buscado
	* @param metatag
	* @param query
	* @return Array Imagem
	*/
	public static final Imagem[] buscaDoc(String metatag, String querystr) throws ParseException, IOException {	
		// Define o parser para a nova query
	    Imagem[] image;
	    
	    Query q = new QueryParser(Version.LUCENE_48, metatag, analyzer).parse(querystr);
	    int hitsPerPage = 10;
	    IndexReader reader = DirectoryReader.open(index);
	    IndexSearcher searcher = new IndexSearcher(reader);
	    TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);
	    searcher.search(q, collector);
	    ScoreDoc[] hits = collector.topDocs().scoreDocs;
		
		if(hits.length > 0){
			image = new Imagem[hits.length];
			for (int i = 0; i < hits.length; i++) {
				int docId = hits[i].doc;
    			Document d = searcher.doc(docId);
				image[i] = new Imagem(d.get("caminho"));
				image[i].setLocal (d.get("local"));
				image[i].setTag  (d.get("tag"));
				image[i].setComida(d.get("comida"));
			}
			return image;
		}
		else{
			return null;
		}
		
	}
	
	
	/**
	 * Add images metatag's to a index
	 * @param w
	 * @param image
	 * @throws IOException
	 * @return void
	 */
	private static void addDoc(IndexWriter w, Imagem image) throws IOException {
		// Place a new document
		Document doc = new Document();
		// Add infos to a doc
		doc.add(new StringField("caminho", image.getCaminho(), Field.Store.YES));
		doc.add(new StringField("nome"   , image.getNome()	 , Field.Store.YES));
		doc.add(new StringField("local"  , image.getLocal()  , Field.Store.YES));
		doc.add(new TextField(	"tag"	 , image.getTags()   , Field.Store.YES));
		doc.add(new TextField(	"comida" , image.getComida() , Field.Store.YES));
		w.addDocument(doc);
	}
}