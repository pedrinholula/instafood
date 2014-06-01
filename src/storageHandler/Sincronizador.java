package storageHandler;

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

/**
 * Cria um arquivo para salvar anotações de imagens e realiza a sincronização do programa com o mesmo
 * @author Felipe e Pedro
 *
 */

public class Sincronizador {
	File database;

	public Sincronizador() {
		database = new File(".//resources//anotacoes.xml");
		
	}
	
	/**
	 * Carrega todas as metatags para um array de imagens
	 * @return Imagem
	 */
	public Imagem[] readXML(){
		try{
			Imagem[] image;
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(database.getAbsolutePath());
			//Checa se o registro já existe
			NodeList listaImagens = doc.getElementsByTagName("imagem");
			int tamanhoLista = listaImagens.getLength();
			if(tamanhoLista > 0){
				image = new Imagem[tamanhoLista];
				for(int i = 0; i<tamanhoLista; i++) {
					Element e = (Element) listaImagens.item(i);						
					NamedNodeMap nnm = e.getAttributes();
					NodeList filhos = e.getChildNodes();
					String path = nnm.item(0).getNodeValue();
					image[i] = new Imagem(path);
					image[i].setTag(filhos.item(1).getTextContent());
					image[i].setLocal( filhos.item(2).getTextContent());
					image[i].setComida( filhos.item(3).getTextContent());
				}
				return image;
			}
			else
				return null;
			
			
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		   } catch (IOException ioe) {
			ioe.printStackTrace();
		   } catch (SAXException sae) {
			sae.printStackTrace();
		   }
		return null;
	}
	
	/**
	 * Procura se existe anotaï¿½ï¿½es do usuï¿½rio para uma imagem especï¿½fica. Caso exista, importa as anotaï¿½ï¿½es para a imagem
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
	 * Guardar o registro das anotaï¿½ï¿½es do usuï¿½rio para uma imagem no xml
	 * @param imagem
	 * @throws IOException
	 */
	public void guardaRegistro(Imagem imagem) throws IOException {		
		//Verifica se jï¿½ existe um registro de anotaï¿½ï¿½es do usuï¿½rio
		if(database.exists())
			modificaRegistro(imagem);
		else
			criaRegistro(imagem);
	}
	
	/**
	 * Escreve as modificaï¿½ï¿½es feitas por outras funï¿½ï¿½es no arquivo XML
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
	 * Sobrescreve as anotaï¿½ï¿½es de uma imagem ou adiciona registro de uma outra imagem
	 * @param imagem
	 * @throws IOException
	 */
	private void modificaRegistro(Imagem imagem) throws IOException {
		try{
			
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(database.getAbsolutePath());
			
	
			//Checa se o registro jï¿½ existe
			NodeList listaImagens = doc.getElementsByTagName("imagem");
			int tamanhoLista = listaImagens.getLength();
			
			for(int i = 0; i < tamanhoLista; i++) {
				
				Element e = (Element) listaImagens.item(i);				
				NamedNodeMap nnm = e.getAttributes();
				
				//Caso tenham encontrado anotaï¿½ï¿½es sobre a mesma imagem, sobrescreve as informaï¿½ï¿½es no banco
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
	 * Cria registro XML para armazenar anotaï¿½ï¿½es do usuï¿½rio e jï¿½ insere as informaï¿½ï¿½es da imagem recebida
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
