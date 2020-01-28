import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

/** <p>De abstracte klasse voor een item op een Slide<p>
 * <p>Alle SlideItems hebben tekenfunctionaliteit.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
*/

public abstract class SlideItem {
	private int slideItemLevel = 0;

	public SlideItem(int level) {
		slideItemLevel = level;
	}

	public SlideItem() {
		this(0);
	}

	public int getLevel() {
		return slideItemLevel;
	}

	public abstract Rectangle getBoundingBox(Graphics g,
			ImageObserver observer, float scale, Style style);

	public abstract void drawItem(int x, int y, float scale,
			Graphics g, Style style, ImageObserver observer);
}
