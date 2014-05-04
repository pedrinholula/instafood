package storageHandler;

/*Document builder*/
import imageHandler.Imagem;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Attr;

public class Sincronizador {
	File database;

	public Sincronizador() {
		database = new File(".//resources//anotacoes.xml");
		
	}
	
	/**
	 * Procura se existe anotações do usuário para uma imagem específica. Caso exista, importa as anotações para a imagem
	 * @param imagem
	 */
	public void importaAnotacoes(Imagem imagem) {
		
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(database.getAbsolutePath());
			
	
			//Checa se o registro já existe
			NodeList listaImagens = doc.getElementsByTagName("imagem");
			int tamanhoLista = listaImagens.getLength();
			
			for(int i = 0; i < tamanhoLista; i++) {
				
				Element e = (Element) listaImagens.item(i);				
				NamedNodeMap nnm = e.getAttributes();
				
				//Caso tenham encontrado anotações sobre a mesma imagem, sobrescreve as informações no banco
				if( nnm.item(0).getNodeValue().compareTo(imagem.getCaminho() ) == 0 ) {
					
					NodeList filhos = e.getChildNodes();
					
					imagem.setTag( filhos.item(1).getTextContent() );
					imagem.setLocal( filhos.item(2).getTextContent() );
					imagem.setComida( filhos.item(3).getTextContent() );
					
					return;
				
				}
			}
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		   } catch (IOException ioe) {
			ioe.printStackTrace();
		   } catch (SAXException sae) {
			sae.printStackTrace();
		   }
		
	}
	
	/**
	 * Guardar o registro das anotações do usuário para uma imagem no xml
	 * @param imagem
	 * @throws IOException
	 */
	public void guardaRegistro(Imagem imagem) throws IOException {		
		//Verifica se já existe um registro de anotações do usuário
		if(database.exists())
			modificaRegistro(imagem);
		else
			criaRegistro(imagem);
	}
	
	/**
	 * Escreve as modificações feitas por outras funções no arquivo XML
	 * @param doc
	 */
	private void escreveConteudoXML(Document doc) {
		
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(this.database);
			transformer.transform(source, result);
		
		}
		
		catch (TransformerException tfe) {
			tfe.printStackTrace();
		}

	}
	
	/**
	 * Sobrescreve as anotações de uma imagem ou adiciona registro de uma outra imagem
	 * @param imagem
	 * @throws IOException
	 */
	private void modificaRegistro(Imagem imagem) throws IOException {
		try{
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(database.getAbsolutePath());
			
	
			//Checa se o registro já existe
			NodeList listaImagens = doc.getElementsByTagName("imagem");
			int tamanhoLista = listaImagens.getLength();
			
			for(int i = 0; i < tamanhoLista; i++) {
				
				Element e = (Element) listaImagens.item(i);				
				NamedNodeMap nnm = e.getAttributes();
				
				//Caso tenham encontrado anotações sobre a mesma imagem, sobrescreve as informações no banco
				if( nnm.item(0).getNodeValue().compareTo(imagem.getCaminho() ) == 0 ) {
					
					NodeList filhos = e.getChildNodes();
					
					filhos.item(0).setTextContent(imagem.getNome());
					filhos.item(1).setTextContent(imagem.getTags());
					filhos.item(2).setTextContent(imagem.getLocal());
					filhos.item(3).setTextContent(imagem.getComida());
					
					escreveConteudoXML(doc);
					
					return;
				}
				
			}
			
			//Insere elemento no XML
			Element root = doc.getDocumentElement();

			Element image = doc.createElement("imagem");
			root.appendChild(image);
			
			Attr attr = doc.createAttribute("caminho");
			attr.setValue(imagem.getCaminho());
			image.setAttributeNode(attr);
			
			//Elemento nome
			Element nome = doc.createElement("Nome");
			nome.appendChild(doc.createTextNode(imagem.getNome()));
			image.appendChild(nome);
			
			//Elemento tags
			Element tags = doc.createElement("Tags");
			System.out.println("Tags :> " + imagem.getTags());
			tags.appendChild(doc.createTextNode(imagem.getTags()));
			image.appendChild(tags);
			
			//Elemento local
			Element local = doc.createElement("Local");
			local.appendChild(doc.createTextNode(imagem.getLocal()));
			image.appendChild(local);
			
			//Elemento comida
			Element comida = doc.createElement("Comida");
			comida.appendChild(doc.createTextNode(imagem.getComida()));
			image.appendChild(comida);
			
			escreveConteudoXML(doc);
			
			
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		   } catch (IOException ioe) {
			ioe.printStackTrace();
		   } catch (SAXException sae) {
			sae.printStackTrace();
		   }
		
		
		
	}
	/**
	 * Cria registro XML para armazenar anotações do usuário e já insere as informações da imagem recebida
	 * @param imagem
	 * @throws IOException
	 */
	private void criaRegistro(Imagem imagem) throws IOException {

		try {
			 
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// root elements
			Document doc = docBuilder.newDocument();
			Element root = doc.createElement("database");
			doc.appendChild(root);
			
			Element image = doc.createElement("imagem");
			root.appendChild(image);
			
			Attr attr = doc.createAttribute("caminho");
			attr.setValue(imagem.getCaminho());
			image.setAttributeNode(attr);
			
			//Elemento nome
			Element nome = doc.createElement("Nome");
			nome.appendChild(doc.createTextNode(imagem.getNome()));
			image.appendChild(nome);
			
			//Elemento tags
			Element tags = doc.createElement("Tags");
			tags.appendChild(doc.createTextNode(imagem.getTags()));
			image.appendChild(tags);
			
			//Elemento local
			Element local = doc.createElement("Local");
			local.appendChild(doc.createTextNode(imagem.getLocal()));
			image.appendChild(local);
			
			//Elemento comida
			Element comida = doc.createElement("Comida");
			comida.appendChild(doc.createTextNode(imagem.getComida()));
			image.appendChild(comida);
	 
			escreveConteudoXML(doc);
	 
		  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  }
	}
		
}
