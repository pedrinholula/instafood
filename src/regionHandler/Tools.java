package regionHandler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public final class Tools {

	/**
	 * 
	 * Reduz o brilho dos pixels que est�o fora de uma regi�o, destacando os pixels internos.
	 * 
	 * @param sourceImage Imagem original.
	 * @param labeledImage Mapa de r�tulos.
	 * @param region �ndice da regi�o.
	 * @param level N�vel de redu��o do brilho.
	 * @return
	 */
	
	public static PPMImage highlightRegion(PPMImage sourceImage, LabeledImage labeledImage, int region, int level) {
		//Copia imagem orginal para imagem final
		PPMImage imagem_final = sourceImage;
		
		//Pega dimens�es da figura
		int altura = imagem_final.getHeight();
		int largura = imagem_final.getWidth();
		
		int pixel_temp[];
		
		//Percorre todos os pixels da imagem verificando quais pertencem ou n�o a regi�o a ser destacada
		for(int i = 0; i < largura; i++) {
			for(int j = 0; j < altura; j++) {
				//Pega valores RGB do pixel
				pixel_temp = imagem_final.getPixel(i, j);

				//Como otimizar a execu��o?
				//Caso o pixel analisado n�o fa�a parte da regi�o a ser destacada, � aplicado um afeito de % de brilho no mesmo
				if( labeledImage.getRegion(i, j) != region )
					imagem_final.setPixel(i, j, (pixel_temp[0]*level)/100, (pixel_temp[1]*level)/100, (pixel_temp[2]*level)/100 );
					
			}
		}

		return imagem_final;
		
	}
	
	public static void writePGM(LabeledImage image, String imagePath) {
		
		try {

			BufferedWriter bw = new BufferedWriter(new FileWriter(imagePath));
			
			bw.write("P2\n");
			bw.write(image.getWidth() + " " + image.getHeight() + "\n");
			bw.write(image.getMaxVal() + "\n");
			
			
			for (int j = 0; j < image.getHeight(); j++) {
				for (int i = 0; i < image.getWidth(); i++)
					bw.write(image.getPixel(i + j * image.getWidth()) + " ");
				bw.write("\n");
			}
			
			bw.close();
			
		} catch (IOException e) {
			System.err.println("Image cannot be write!");
		}
		
	}
	
	
	public static PGMImage readPGM(String imagePath) {
		LabeledImage image = null;
		int h, w, maxVal;
		String tmp;
		String[] buffer;

		
		try {
			BufferedReader br = new BufferedReader(new FileReader(imagePath));
						
			br.readLine();// P2
			
			while((tmp = br.readLine()).startsWith("#")); // Coments
			
			buffer = tmp.split(" "); // H W
			w = Integer.parseInt(buffer[0]);
			h = Integer.parseInt(buffer[1]);
			maxVal = Integer.parseInt(br.readLine());
			
			image = new LabeledImage(w,h,maxVal);
			
			int index = 0;
			while((tmp = br.readLine()) != null) {
//				System.out.println(tmp);
				buffer = tmp.split(" ");
				
				for (int i=0; i < buffer.length; i++) {
					image.setPixel(index, Integer.parseInt(buffer[i]));
//					System.out.println(image.getPixel(index));
					index++;
				}
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			System.err.println("Image " + imagePath +  " cannot be found!");
		} catch (IOException e) {
			System.err.println("Image " + imagePath +  " cannot be read!");
		}
		
		image.setImagePath(imagePath);
		
		return (PGMImage) image;
	}	
	
	public static void writePPM(PPMImage image, String imagePath) {
		
		try {

			BufferedWriter bw = new BufferedWriter(new FileWriter(imagePath));
			
			bw.write("P3\n");
			bw.write(image.getWidth() + " " + image.getHeight() + "\n");
			bw.write(image.getMaxVal() + "\n");
			
			
			for (int j = 0; j < image.getHeight(); j++) {
				for (int i = 0; i < image.getWidth(); i++)
					bw.write(image.getPixel(i + j * image.getWidth())[PPMImage.RED] + " " + image.getPixel(i + j * image.getWidth())[PPMImage.GREEN] + " " + image.getPixel(i + j * image.getWidth())[PPMImage.BLUE] + " ");
				bw.write("\n");
			}
			
			bw.close();
			
		} catch (IOException e) {
			System.err.println("Image cannot be write!");
		}
		
	}	
	
	/**
	 * 
	 * @param imagePath Caminho da imagem
	 * @return um objeto do tipo PPMImage
	 */
	public static PPMImage readPPM(String imagePath) {
		PPMImage image = null;
		int h, w, maxVal;
		String tmp;
		String[] buffer;

		
		try {
			BufferedReader br = new BufferedReader(new FileReader(imagePath));
						
			br.readLine();// P3
			
			while((tmp = br.readLine()).startsWith("#")); // Ignora coment�rios
			
			buffer = tmp.split(" "); // H W - Escreve altura e largura da imagem
			w = Integer.parseInt(buffer[0]);
			h = Integer.parseInt(buffer[1]);
			maxVal = Integer.parseInt(br.readLine());
			
			image = new PPMImage(w,h,maxVal);
			
			int index = 0;
			while((tmp = br.readLine()) != null) {
				buffer = tmp.split(" ");
				
				for (int i=0; i < buffer.length; i+=3) {
					image.setPixel(index, Integer.parseInt(buffer[i+PPMImage.RED]), Integer.parseInt(buffer[i+PPMImage.GREEN]), Integer.parseInt(buffer[i+PPMImage.BLUE]));
					index++;
				}
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			System.err.println("Image " + imagePath +  " cannot be found!");
		} catch (IOException e) {
			System.err.println("Image " + imagePath +  " cannot be read!");
		}
		
		image.setImagePath(imagePath);
		
		return image;
	}
	
	
	
}
