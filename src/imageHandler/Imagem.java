package imageHandler;

import java.io.File;
import java.io.IOException;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

/**
 * Classe responsável por armazenar uma imagem e seus respectivos metadados principais
 * @author Felipe e Pedro
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
	}
	
	/**
	 * Retorna em texto as propriedades da imagem
	 */
	public String printPropriedades() {
		return "Nome: " + this.nome + "\nCaminho: " + this.caminho + "\nTags: " + 
	this.tags + "\nLocal: " + this.local + "\nComida: " + this.comida;
	}

	/**
	 * Retorna imagem utilizada pela classe
	 * @return
	 */
	public PlanarImage getImagem() {
		return this.imagem;
	}

	/**
	 * Retorna o atributo Caminho da imagem
	 * @return
	 */
	public String getCaminho() {
		return this.caminho;
	}

	/**
	 * Retorna nome da imagem
	 * @return
	 */
	public String getNome() {
		return this.nome;
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
	}
	
	/**
	 * Edita as tags associadas a imagem
	 * @param newTags
	 */
	public void setTag(String newTags) {
		this.tags = newTags;
	}

	/**
	 * Retorna todas as tags da imagem
	 * @return
	 */
	public String getTags() {
		return this.tags;
	}

	/**
	 * Retorna local da imagem
	 * @return
	 */
	public String getLocal() {
		return this.local;
	}

	/**
	 * Retorna atributo Comida da imagem
	 * @return
	 */
	public String getComida() {
		return this.comida;
	}
	
	/**
	 * Edita atributo Local da imagem
	 * @param local
	 */
	public void setLocal(String local) {
		this.local = local;
	}
	
	/**
	 * Edita atributo Comida da imagem
	 * @param comida
	 */
	public void setComida(String comida) {
		this.comida = comida;
	}

}
