import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;


/** <p>De klasse voor een Bitmap item</p>
 * <p>Bitmap items hebben de verantwoordelijkheid om zichzelf te tekenen.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class BitmapItem extends SlideItem {
	private BufferedImage bufferedImage;
	private String imageName;

	protected static final String FILE = "Bestand ";
	protected static final String NOTFOUND = " niet gevonden";

	public BitmapItem(int itemlevel, String imageFileName) {
		super(itemlevel);
		try {
			bufferedImage = ImageIO.read(new File(imageFileName));
		}
		catch (IOException e) {
			System.err.println(FILE + imageFileName + NOTFOUND) ;
		}
	}

	// Een leeg bitmap-item
	public BitmapItem() {
		this(0, null);
	}

	public String getImageName() {
		return imageName;
	}

	public Rectangle getBoundingBox(Graphics graphic, ImageObserver observer, float scale, Style myStyle) {
		return new Rectangle((int) (myStyle.indent * scale), 0, (int) (bufferedImage.getWidth(observer) * scale),
				((int) (myStyle.leading * scale)) + (int) (bufferedImage.getHeight(observer) * scale));
	}

	public void drawItem(int widthX, int heightY, float scale, Graphics graphic, Style myStyle, ImageObserver observer) {
		int width = widthX + (int) (myStyle.indent * scale);
		int height = heightY + (int) (myStyle.leading * scale);
		graphic.drawImage(bufferedImage, width, height,(int) (bufferedImage.getWidth(observer)*scale),
				(int) (bufferedImage.getHeight(observer)*scale), observer);
	}

	public String bitmapItemtoString() {
		return "BitmapItem[" + getLevel() + "," + imageName + "]";
	}
}