package imageHandler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import regionHandler.DestacaRegiao;
//import regionHandler.ProcessaRegiao;

import com.sun.media.jai.widget.DisplayJAI;

/**
 * 
 * Essa classe implementa uma área de visualização de imagem, assim como a interface sobre os principais eventos sobre a imagem
 * 
 * Adaptado por Felipe César e Pedro Lopes de @author Jefersson Alex dos Santos <jefersson@dcc.ufmg.br>
 * 
 */
@SuppressWarnings("serial")
public class ImageViewer extends DisplayJAI implements MouseListener,
		MouseMotionListener {

	// Armazenam as coordenadas atuais do mouse
	private String caminhoImagem, caminhoLabels;

	public ImageViewer() {
		super();

		addMouseListener(this);
		addMouseMotionListener(this);
		
		/*//Carrega imagem default
		String defaultImage = ".//resources//8.01.ppm";
		PlanarImage image = JAI.create("fileload", defaultImage);
		//ProcessaRegiao processaRegiao = new ProcessaRegiao(defaultImage); //Essa linha será utilizada para processar imagens de formatos diferentes
		this.caminhoImagem = defaultImage;
		this.caminhoLabels = this.caminhoImagem;
		this.caminhoLabels = this.caminhoLabels.replace(".ppm", "");
		this.caminhoLabels = this.caminhoLabels.concat(".labels.pgm");

		this.set(image);
		*/
	}

	/*
	 * Destaca a região da imagem que foi selecionada por um clique no mouse(non-Javadoc)
	 */
	public void mouseClicked(MouseEvent e) {

		String imgTemp = this.caminhoImagem.replace(".ppm", ".temp.ppm");
		String[] args = {this.caminhoImagem, this.caminhoLabels, "" +e.getX(), ""+e.getY(), "30", imgTemp};
		DestacaRegiao.main(args);
		PlanarImage newImage = JAI.create("fileload", imgTemp);
		this.set(newImage);
	}
	/*
	 * Abre um painel para seleção do arquivo de entrada e atualiza a imagem visualizada pela escolhida
	 */
	
	public void setImagem(String caminho) {
		PlanarImage image = JAI.create("fileload", caminho);
		this.set(image);
		
	}
	
	public String choseImage(String text, String ends) {

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
			
			return chooser.getSelectedFile().getAbsolutePath();
		}

		return null;
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