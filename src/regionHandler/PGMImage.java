package regionHandler;

/**
 * 
 * Essa classe implementa o formato de imagem PGM (P2).
 * 
 * @author Jefersson Alex dos Santos <jefersson@dcc.ufmg.br>
 *
 */
public class PGMImage extends PNMImage {

	private int[] val;
	
	public PGMImage(int width, int height, int maxVal) {
		super(width, height, maxVal);
		this.val = new int[height*width];
	}
	
	public int getPixel(int x, int y) {
		return val[x + getWidth()*y];
	}
	
	public int getPixel(int i) {
		return val[i];
	}	
	
	public void setPixel(int x, int y, int value) {
		val[x + getWidth()*y] = value;
	}
	
	public void setPixel(int i, int value) {
		val[i] = value;
	}
	
	public int getX(int i) {
		return i % getWidth();
	}
	
	public int getY(int i) {
		return i / getWidth();
	}

	public int[] getVal() {
		return val;
	}

}