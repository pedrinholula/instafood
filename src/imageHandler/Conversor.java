package imageHandler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.imaging.ImageFormat;
import org.apache.commons.imaging.ImageFormats;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;


public class Conversor
{
	/**
	 * Converte uma imagem para o formato ppm 
	 * @param caminhoOrigem
	 * @param caminhoDestino
	 */
	public void convert2ppm(File caminhoOrigem, File caminhoDestino) {
			BufferedImage temp = null;
			
			try {
				temp = Imaging.getBufferedImage(caminhoOrigem);
			} catch (ImageReadException | IOException e) {
				e.printStackTrace();
			}
			
			ImageFormat format = ImageFormats.PPM;
			
			final Map<String, Object> optionalParams = new HashMap<String, Object>();
		     
	        try {
				Imaging.writeImage(temp, caminhoDestino, format, optionalParams);
			} catch (ImageWriteException | IOException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Converte uma imagem para o formato jpg 
	 * @param caminhoOrigem
	 * @param caminhoDestino
	 */
	public void convert2jpg(File caminhoOrigem, File caminhoDestino) {
		BufferedImage temp = null;
		
		try {
			temp = Imaging.getBufferedImage(caminhoOrigem);
		} catch (ImageReadException | IOException e) {
			e.printStackTrace();
		}
		
		ImageFormat format = ImageFormats.PNG;
		
		final Map<String, Object> optionalParams = new HashMap<String, Object>();
	     
        try {
			Imaging.writeImage(temp, caminhoDestino, format, optionalParams);
		} catch (ImageWriteException | IOException e) {
			e.printStackTrace();
		}
		
	}
}