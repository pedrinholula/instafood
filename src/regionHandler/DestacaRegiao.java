package regionHandler;

import regionHandler.LabeledImage;
import regionHandler.PPMImage;
import regionHandler.Tools;

/**
 * L� uma imagem e seu respectivo mapa de regi�es. Cria uma c�pia da imagem original com uma regi�o espec�fica destacada
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

		
		// L� uma imagem colorida
		PPMImage colorImage = Tools.readPPM(cImagePath);
		
		// L� uma imagem em n�vel de cinza (imagem de r�tulos)
		LabeledImage grayImage = (LabeledImage) Tools.readPGM(labelImagePath);
		
		// Retorna o indice da regiao onde se encontra o pixel (x, y)
		int r = grayImage.getRegion(x, y);		
		
		// Escurece os pixels fora da regiao r 
		PPMImage paintedImage = Tools.highlightRegion(colorImage, grayImage, r, level);
		
		// Grava a imagem em disco
		Tools.writePPM(paintedImage, destPath);
	}

}
