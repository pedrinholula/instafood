package regionHandler;

import regionHandler.LabeledImage;
import regionHandler.PPMImage;
import regionHandler.Tools;

/**
 * Lê uma imagem e seu respectivo mapa de regiões. Cria uma cópia da imagem original com uma região específica destacada
 * @author Felipe
 *
 */
public final class DestacaRegiao {

	public static void main(String[] args) {
		
		String cImagePath = new String();
		String labelImagePath = new String();
		String destPath = new String();
		int x = 0, y = 0, level = 0;

		
		if(args.length == 6) {
			cImagePath = args[0];
			labelImagePath = args[1];			
			x = Integer.parseInt(args[2]);
			y = Integer.parseInt(args[3]);
			level = Integer.parseInt(args[4]);
			destPath = args[5];
		} else {
			
			//System.out.println("Uso: \n\t java -jar destaca_regiao.jar <imagem_origem> <mapa_de_rotulos> <x> <y> <nivel> <imagem_destino>\n");
			System.exit(0);
		}

		
		// Lê uma imagem colorida
		PPMImage colorImage = Tools.readPPM(cImagePath);
		
		// Lê uma imagem em nível de cinza (imagem de rótulos)
		LabeledImage grayImage = (LabeledImage) Tools.readPGM(labelImagePath);
		
		// Retorna o indice da regiao onde se encontra o pixel (x, y)
		int r = grayImage.getRegion(x, y);		
		
		// Escurece os pixels fora da regiao r 
		PPMImage paintedImage = Tools.highlightRegion(colorImage, grayImage, r, level);
		
		// Grava a imagem em disco
		Tools.writePPM(paintedImage, destPath);
	}

}
