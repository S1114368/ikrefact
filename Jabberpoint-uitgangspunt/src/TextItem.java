import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.TextLayout;
import java.awt.font.TextAttribute;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.ImageObserver;
import java.text.AttributedString;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

/** <p>Een tekst item.</p>
 * <p>Een TextItem heeft tekenfunctionaliteit.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class TextItem extends SlideItem {
	private static final String EMPTYTEXT = "No Text Given";

	private String text;

	public TextItem(int level, String string) {
		super(level);
		text = string;
	}

// een leeg textitem
	public TextItem() {
		this(0, EMPTYTEXT);
	}

	public Rectangle getBoundingBox(Graphics graphics, ImageObserver observer, float scale, Style myStyle) {
		List<TextLayout> layouts = getLayouts(graphics, myStyle, scale);
		int boundsXSize = 0, boundsYSize = (int) (myStyle.leading * scale);
		Iterator<TextLayout> iterator = layouts.iterator();
		while (iterator.hasNext()) {
			TextLayout layout = iterator.next();
			Rectangle2D bounds = layout.getBounds();
			if (bounds.getWidth() > boundsXSize) {
				boundsXSize = (int) bounds.getWidth();
			}
			if (bounds.getHeight() > 0) {
				boundsYSize += bounds.getHeight();
			}
			boundsYSize += layout.getLeading() + layout.getDescent();
		}
		return new Rectangle((int) (myStyle.indent*scale), 0, boundsXSize, boundsYSize );
	}

	public String toString() {
		return "TextItem[" + getLevel()+","+getText()+"]";
	}

	public String getText() {
		return text == null ? "" : text;
	}

	public AttributedString createAttributedString(Style style, float scale){
		AttributedString attributedString = new AttributedString(getText());
		attributedString.addAttribute(TextAttribute.FONT, style.getFont(scale), 0, text.length());
		return attributedString;
	}

	public AttributedString getAttributedString(Style style, float scale) {
		return createAttributedString(style, scale);
	}

	public List createNewLayoutsList(){
		List<TextLayout> layouts = new ArrayList<TextLayout>();
		return layouts;
	}

	public LineBreakMeasurer createNewLineBreakMeasurer(AttributedString attributedString, FontRenderContext frontRenderContext){
		return new LineBreakMeasurer(attributedString.getIterator(), frontRenderContext);
	}

	public List<TextLayout> createLayouts(Graphics graphics, Style style, float scale){
		List<TextLayout> layouts = createNewLayoutsList();
		AttributedString attributedString = getAttributedString(style, scale);
		Graphics2D graphics2D = (Graphics2D) graphics;
		FontRenderContext frontRenderContext = graphics2D.getFontRenderContext();
		LineBreakMeasurer measurer = createNewLineBreakMeasurer(attributedString, frontRenderContext);
		float wrappingWidth = (Slide.SLIDE_WIDTH - style.indent) * scale;
		while (measurer.getPosition() < getText().length()) {
			TextLayout layout = measurer.nextLayout(wrappingWidth);
			layouts.add(layout);
		}
		return layouts;
	}

	private List<TextLayout> getLayouts(Graphics graphics, Style style, float scale) {
		return createLayouts(graphics, style, scale);
	}

	public void drawItem(int itemXSize, int itemYSize, float scale, Graphics graphics, Style myStyle, ImageObserver imageObserver) {
		if (text == null || text.length() == 0) {
			return;
		}
		List<TextLayout> layouts = getLayouts(graphics, myStyle, scale);
		Point pen = new Point(itemXSize + (int)(myStyle.indent * scale),
				itemYSize + (int) (myStyle.leading * scale));
		Graphics2D graphics2d = (Graphics2D)graphics;
		graphics2d.setColor(myStyle.color);
		Iterator<TextLayout> it = layouts.iterator();
		while (it.hasNext()) {
			TextLayout layout = it.next();
			pen.y += layout.getAscent();
			layout.draw(graphics2d, pen.x, pen.y);
			pen.y += layout.getDescent();
		}
	}
}
