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
	protected Vector<SlideItem> slideItems;

	public Slide() {
		slideItems = new Vector<SlideItem>();
	}

	public void appendSlideItem(SlideItem anItem) {
		slideItems.addElement(anItem);
	}

	public void setSlideTitle(String newSlideTitle) {
		slideTitle = newSlideTitle;
	}

	public void appendText(int level, String message) {
		appendSlideItem(turnStringIntoTextItem(level, message));
	}

	public TextItem turnStringIntoTextItem(int level, String message){
		return new TextItem(level, message);
	}

	public SlideItem getSlideItem(int number) {
		return (SlideItem)slideItems.elementAt(number);
	}

	public Vector<SlideItem> getSlideItems() {
		return slideItems;
	}

	public String getTitle() {
		return slideTitle;
	}

	public int getSlideSize() {
		return slideItems.size();
	}

	// geef de schaal om de slide te kunnen tekenen
	private float getScale(Rectangle area) {
		return Math.min(((float)area.width) / ((float)SLIDE_WIDTH), ((float)area.height) / ((float)SLIDE_HEIGHT));
	}

	public void drawSlide(Graphics graphics, Rectangle area, ImageObserver view) {
		// De titel wordt apart behandeld
		SlideItem slideItem = new TextItem(0, getTitle());
		Style style = Style.getStyle(slideItem.getLevel());

		slideItem.drawItem(area.x, area.y, getScale(area), graphics, style, view);
		area.y += slideItem.getBoundingBox(graphics, view, getScale(area), style).height;

		for (int number=0; number<getSlideSize(); number++) {
			slideItem = (SlideItem)getSlideItems().elementAt(number);
			style = Style.getStyle(slideItem.getLevel());
			slideItem.drawItem(area.x, area.y, getScale(area), graphics, style, view);
			area.y += slideItem.getBoundingBox(graphics, view, getScale(area), style).height;
		}
	}
}