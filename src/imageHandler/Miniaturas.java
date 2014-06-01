package imageHandler;

import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.name.Rename;

/**
 * Permite a criação de miniaturas de imagens
 * @author Felipe
 *
 */

public class Miniaturas {
	private String formatoOutput;
	File diretorio;
	String caminho;
	int width;
	int height;

	/**
	 * Inicializa o gerador de miniaturas com as configurações default
	 */
	public Miniaturas() {
		caminho = ".//resources//resultadosBusca";
		formatoOutput = "jpg";
		diretorio = new File(caminho);
		width = 300;
		height = 105;
	}
	
	
	/**
	 * Gera miniatura de uma imagem específcia
	 * @param arquivo
	 */
	public void geraThumbnailImg(File arquivo) {
		try {
			Thumbnails.of(arquivo)
			.size(width, height)
			.outputFormat(formatoOutput)
			.toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	/**
	 * Gera miniaturas de todas as imagens do diretório
	 * @param caminho
	 */
	public void geraThumbnailsDir() {
		
		try {
			Thumbnails.of(diretorio.listFiles())
			.size(width, height)
			.outputFormat(formatoOutput)
			.toFiles(Rename.PREFIX_DOT_THUMBNAIL);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
	}

}
