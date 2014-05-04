package regionHandler;

/**
 * 
 * Essa classe implementa o formato de imagem PPM (P3). 
 * 
 * @author Jefersson Alex dos Santos <jefersson@dcc.ufmg.br>
 *
 */
public class PPMImage extends PNMImage {

	public static final int RED = 0;
	public static final int GREEN = 1;
	public static final int BLUE = 2;

	private int[][] val;
	
	public PPMImage(int width, int height, int maxVal) {
		super(width, height, maxVal);
		
		this.val = new int[height*width][3];

	}
	
	public int[] getPixel(int x, int y) {
		return val[x +getWidth()*y];
	}
	
	public int[] getPixel(int i) {
		return val[i];
	}	

	public void setPixel(int x, int y, int r, int g, int b) {
		val[x + getWidth()*y][RED] = r;
		val[x + getWidth()*y][GREEN] = g;
		val[x + getWidth()*y][BLUE] = b;
	}
	
	public void setPixel(int i, int r, int g, int b) {
		val[i][RED] 	= r;
		val[i][GREEN] 	= g;
		val[i][BLUE] 	= b;
	}
	

	

		
}

