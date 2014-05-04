package regionHandler;

/**
 * 
 * Essa classe é uma abstração de imagens no formato PNM. 
 * 
 * @author Jefersson Alex dos Santos <jefersson@dcc.ufmg.br>
 * @see {@link http://en.wikipedia.org/wiki/Netpbm_format}
 * 
 */
public abstract class PNMImage {

	private int height; 		// Altura
	private int width;			// Largura
	
	private int maxVal;			// Valor maximo
	private String imagePath;	// Armazena o caminho da imagem no disco
	
	/**
	 * Cria um objeto PNMImage.
	 * 
	 * @param width Largura da imagem.
	 * @param height Altura da imagem.
	 * @param maxVal Valor máximo.
	 */
	public PNMImage(int width, int height, int maxVal) {
		this.width = width;
		this.height = height;
		this.maxVal = maxVal;
	}
	
	/**
	 * Imagem vazia.
	 */
	public PNMImage() {
		this.width = 0;
		this.height = 0;
		this.maxVal = 0;		
	}
	
	/**
	 * @return Altura da imagem
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return Largura da imagem
	 */	
	public int getWidth() {
		return width;
	}
	
	/**
	 * @return Valor Máximo
	 */		
	public int getMaxVal() {
		return maxVal;
	}
	
	/**
	 * 
	 * Define o valor máximo.
	 *  
	 * @param maxVal
	 */
	public void setMaxVal(int maxVal) {
		this.maxVal = maxVal;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getImagePath() {
		return imagePath;
	}

}
