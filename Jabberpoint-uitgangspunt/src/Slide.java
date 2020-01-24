import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Vector;

/** <p>Een slide. Deze klasse heeft tekenfunctionaliteit.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Slide {
	public final static int SLIDE_WIDTH = 1200;
	public final static int SLIDE_HEIGHT = 800;
	protected String slideTitle; // de titel wordt apart bewaard
	protected Vector<SlideItem> slideItems; // de slide-items worden in een Vector bewaard

	public Slide() {
		slideItems = new Vector<SlideItem>();
	}

	// Voeg een SlideItem toe
	public void appendSlideItem(SlideItem anItem) {
		slideItems.addElement(anItem);
	}

	// geef de titel van de slide
	public String getTitle() {
		return slideTitle;
	}

	// verander de titel van de slide
	public void setSlideTitle(String newSlideTitle) {
		slideTitle = newSlideTitle;
	}

	// Maak een TextItem van String, en voeg het TextItem toe
	public void append(int level, String message) {
		appendSlideItem(new TextItem(level, message));
	}

	// geef het betreffende SlideItem
	public SlideItem getSlideItem(int number) {
		return (SlideItem)slideItems.elementAt(number);
	}

	// geef alle SlideItems in een Vector
	public Vector<SlideItem> getSlideItems() {
		return slideItems;
	}

	// geef de afmeting van de Slide
	public int getSlideSize() {
		return slideItems.size();
	}

	// teken de slide
	public void drawSlide(Graphics g, Rectangle area, ImageObserver view) {
		float scale = getScale(area);
	    int y = area.y;

	// De titel wordt apart behandeld
	    SlideItem slideItem = new TextItem(0, getTitle());
	    Style style = Style.getStyle(slideItem.getLevel());
	    slideItem.drawItem(area.x, y, scale, g, style, view);
	    y += slideItem.getBoundingBox(g, view, scale, style).height;
	    for (int number=0; number<getSlideSize(); number++) {
	      slideItem = (SlideItem)getSlideItems().elementAt(number);
	      style = Style.getStyle(slideItem.getLevel());
	      slideItem.drawItem(area.x, y, scale, g, style, view);
	      y += slideItem.getBoundingBox(g, view, scale, style).height;
	    }
	  }

	// geef de schaal om de slide te kunnen tekenen
	private float getScale(Rectangle area) {
		return Math.min(((float)area.width) / ((float)SLIDE_WIDTH), ((float)area.height) / ((float)SLIDE_HEIGHT));
	}
}
