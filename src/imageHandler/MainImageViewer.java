package imageHandler;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;

import regionHandler.DestacaRegiao;

/**
 * Exibe uma imagem e permite que, quando uma região da mesma seja clicada, esta seja destacada
 * @author Felipe e Pedro
 *
 */

public class MainImageViewer extends ImageViewer implements MouseListener {

	private static final long serialVersionUID = 1L;


	/**
	 * Construtor da classe
	 */
	public MainImageViewer() {
		super();
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	

	/**
	 * Destaca a região da imagem que foi selecionada por um clique no mouse
	 */
	public void mouseClicked(MouseEvent e) {
		
		if(this.caminhoImagem == "null")
			return;
		
		this.caminhoLabels = this.caminhoImagem.toString();
		this.caminhoLabels = this.caminhoLabels.replace(".ppm", ".labels.pgm");
		

		String imgTemp = this.caminhoImagem.replace(".ppm", ".temp.ppm");
		String[] args = {this.caminhoImagem, this.caminhoLabels, "" +e.getX(), ""+e.getY(), "30", imgTemp};
		DestacaRegiao.main(args);
		PlanarImage newImage = JAI.create("fileload", imgTemp);
		this.set(newImage);
	}

}
