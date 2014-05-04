package imageHandler;

import java.io.File;
import java.io.IOException;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

/**
 * Classe responsável por armazenar uma imagem e seus respectivos metadados principais
 * @author Felipe
 *
 */
public class Imagem {
	
	PlanarImage imagem;
	String caminho;
	String nome;
	String tags;
	String local;
	String comida;

	/**
	 * Salva em um .txt os principais metadados da imagem
	 * @throws IOException
	 * @throws CloneNotSupportedException 
	 */
	/*
	public void guardaRegistro()  throws IOException {
		try {
			 
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	 
			// root elements
			Document doc = docBuilder.newDocument();
			Element imagem = doc.createElement("imagem");
			doc.appendChild(imagem);
			
	 
			//Elemento caminho
			Element caminho = doc.createElement("Caminho");
			caminho.appendChild(doc.createTextNode(this.caminho));
			imagem.appendChild(caminho);
			
			//Elemento nome
			Element nome = doc.createElement("Nome");
			nome.appendChild(doc.createTextNode(this.nome));
			imagem.appendChild(nome);
			
			//Elemento tags
			Element tags = doc.createElement("Tags");
			tags.appendChild(doc.createTextNode(this.tags));
			imagem.appendChild(tags);
			
			//Elemento local
			Element local = doc.createElement("Local");
			local.appendChild(doc.createTextNode(this.local));
			imagem.appendChild(local);
			
			//Elemento comida
			Element comida = doc.createElement("Comida");
			comida.appendChild(doc.createTextNode(this.comida));
			imagem.appendChild(comida);
	 
	
	 
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(".//resources//anotacoes.xml"));
	 
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
	 
			transformer.transform(source, result);
	 
			System.out.println("File saved!");
	 
		  } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		  } catch (TransformerException tfe) {
			tfe.printStackTrace();
		  }
		}
		*/
	
	
	
	
	/*
	 * Construtor da classe
	 */
	public Imagem(String caminho) throws IOException {
		File aux = new File(caminho);
		this.imagem = JAI.create("fileload", caminho);
		this.caminho = caminho;
		this.nome = aux.getName();
		this.tags = "";
		this.local = "";
		this.comida = "";	
		
		//this.guardaRegistro();
	}
	
	/*
	 * Retorna em texto as propriedades da imagem
	 */
	public String printPropriedades() {
		return "Nome: " + this.nome + "\nCaminho: " + this.caminho + "\nTags: " + 
	this.tags + "\nLocal: " + this.local + "\nComida: " + this.comida;
	}

	public PlanarImage getImagem() {
		return imagem;
	}

	public String getCaminho() {
		return caminho;
	}

	public String getNome() {
		return nome;
	}
	
	/**
	 * Adiciona uma tag a imagem
	 * @param newTag
	 * @throws IOException
	 */
	public void addTag(String newTag) throws IOException {
		if(this.tags == "")
			this.tags = newTag;
		else
			this.tags = this.tags + ", " + newTag;
		
		//this.guardaRegistro();
	}

	public String getTags() {
		return tags;
	}

	public String getLocal() {
		return local;
	}

	public String getComida() {
		return comida;
	}
	
	public void setLocal(String local) {
		this.local = local;
	}
	
	public void setComida(String comida) {
		this.comida = comida;
	}

}
