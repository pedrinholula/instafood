package imageHandler;

import java.io.File;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.sun.media.jai.widget.DisplayJAI;

/**
 * 
 * Essa classe implementa uma área de visualização de imagem, assim como a interface sobre os principais eventos sobre a imagem
 * 
 * Adaptado por Felipe César e Pedro Lopes de @author Jefersson Alex dos Santos <jefersson@dcc.ufmg.br>
 * 
 */
@SuppressWarnings("serial")
public class ImageViewer extends DisplayJAI {

	// Armazenam as coordenadas atuais do mouse
	protected String caminhoImagem, caminhoLabels;

	/**
	 * Construtor default
	 */
	public ImageViewer() {
		super();
		this.caminhoImagem = "null";

	}

	
	/**
	 * Abre um painel para seleção do arquivo de entrada e atualiza a imagem visualizada pela escolhida
	 */	
	public void setImagem(String caminho) {
		PlanarImage image = JAI.create("fileload", caminho);
		this.caminhoImagem = caminho;
		this.set(image);
		
	}
	
	/**
	 * Retorna o atributo caminho da imagem
	 * @return caminho
	 */
	public String getCaminhoImagem() {
		return this.caminhoImagem;
	}
	
	/**
	 * Permite que o usuário possa escolher uma imagem para ser visualizada de acordo com um determinado tipo de extensão
	 * @param text
	 * @param ends
	 */
	public void choseImage(String text, String ends) {

		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle(text);
		chooser.addChoosableFileFilter(new Filter(ends));

		int returnVal = chooser.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {			
			String newImage = chooser.getSelectedFile().getAbsolutePath();
			PlanarImage image = JAI.create("fileload", newImage);
			this.set(image);
			
			//Cria caminhos para o arquivo de labels da imagem e uma cópia temporária para guardar a imagem com região destacada
			this.caminhoImagem = chooser.getSelectedFile().getAbsolutePath();
			this.caminhoLabels = this.caminhoImagem;
			this.caminhoLabels = this.caminhoLabels.replace(".ppm", "");
			this.caminhoLabels = this.caminhoLabels.concat(".labels.pgm");
			
			//return chooser.getSelectedFile().getAbsolutePath();
		}
	}

}

/**
 * Filtro simples para tipo de arquivo a ser aberto.
 * 
 * @author Jefersson Alex
 * 
 */
class Filter extends FileFilter {

	private String ends;

	public Filter(String ends) {
		this.ends = ends;
	}

	public boolean accept(File file) {
		String filename = file.getName();
		return filename.endsWith(ends) || file.isDirectory();
	}

	public String getDescription() {
		return "*" + ends;
	}
}