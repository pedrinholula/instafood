package regionHandler;

import regionHandler.LabeledImage;
import regionHandler.PPMImage;
import regionHandler.Tools;

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
			
			System.out.println("Uso: \n\t java -jar destaca_regiao.jar <imagem_origem> <mapa_de_rotulos> <x> <y> <nivel> <imagem_destino>\n");
			System.exit(0);
		}
		/* Para facilitar os testes do programa (Obs: Durante os testes realizados, utilizar acentos dificulta o envio dos argumentos adequados para o programa)
		int x = 300, y = 600, level = 50;
		cImagePath = "C:\\Users\\Felipe\\Dropbox\\UFMG\\6 - Programação Modular\\tp1\\TP1.PM2014.1\\resources\\9.10.ppm";
		labelImagePath = "C:\\Users\\Felipe\\Dropbox\\UFMG\\6 - Programação Modular\\tp1\\TP1.PM2014.1\\resources\\9.10.labels.pgm";
		destPath = "C:\\Users\\Felipe\\Desktop\\imagem.ppm";
		*/
		
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
