package regionHandler;

/**
 * Essa classe representa um mapa de labels.
 * 
 * @author Jefersson Alex dos Santos <jefersson@dcc.ufmg.br>
 */
public class LabeledImage extends PGMImage {

	public LabeledImage(int width, int height, int maxVal) {
		super(width, height, maxVal);
	}
	
	/**
	 * Verifica se um determinado valor existe na imagem.
	 * 
	 * @return O �ndice do primeiro pixel encontrado ou -1 caso nenhum pixel
	 *         seja encontrado com esse valor
	 */
	public int exists(int value) {

		int[] val = this.getVal();

		for (int i = 0; i < val.length; i++) {
			if (val[i] == value)
				return i;
		}

		return -1;
	}

	/**
	 * Verifica se um pixel pertence a uma borda da regi�o.
	 * @param p �ndice do pixel
	 * @return
	 */
	public boolean isBorder(int p) {
		int x = this.getX(p);
		int y = this.getY(p);
		
		int value = this.getPixel(x, y);
		
		// 8 neighbor
		if((x-1) > 0) {
			
			if(value != this.getPixel(x-1, y))
				return true;
	
		}
		
		if((y-1) > 0)
			if(value != this.getPixel(x, y-1))
				return true;
		
		if((y+1) < this.getHeight())
			if(value != this.getPixel(x, y+1))
				return true;
		
		if((x+1) < this.getWidth()) {
			
			if(value != this.getPixel(x+1, y))
				return true;

		}
		
		return false;
	}	
	
	/**
	 * Retorna o �ndice da regi�o a qual pertence o pixel p.
	 * @param x
	 * @param y  
	 * @return O �ndice da regi�o ou -1 caso as coordenadas estejam fora dos limites da imagem.
	 */
	public int getRegion(int x, int y) {
		
		//Verifica se existe a regi�o desejada. Caso exista, retorna o �ndice correspondente
		if( exists( getPixel(x, y) ) != -1 )
			return getPixel(x, y);
		
		else
			return -1;
	}
	
}
