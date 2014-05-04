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
	 * @return O índice do primeiro pixel encontrado ou -1 caso nenhum pixel
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
	 * Verifica se um pixel pertence a uma borda da região.
	 * @param p Índice do pixel
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
	 * Retorna o índice da região a qual pertence o pixel p.
	 * @param x
	 * @param y  
	 * @return O índice da região ou -1 caso as coordenadas estejam fora dos limites da imagem.
	 */
	public int getRegion(int x, int y) {
		
		//Verifica se existe a região desejada. Caso exista, retorna o índice correspondente
		if( exists( getPixel(x, y) ) != -1 )
			return getPixel(x, y);
		
		else
			return -1;
	}
	
}
