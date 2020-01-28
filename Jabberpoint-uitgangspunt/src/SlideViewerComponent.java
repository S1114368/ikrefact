import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JFrame;


/** <p>SlideViewerComponent is een grafische component die Slides kan laten zien.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class SlideViewerComponent extends JComponent {
	private static final int FONTSTYLE = Font.BOLD;
	private static final int FONTHEIGHT = 10;
	private static final int XPOSITION = 1100;
	private static final int YPOSITION = 20;
	private static final Color BACKGROUNDCOLOR = Color.white;
	private static final Color COLOR = Color.black;
	private static final String FONTNAME = "Dialog";
	private static final long serialVersionUID = 227L;

	private Slide currentSlide;
	private Font labelFont = null;
	private Presentation presentation = null;
	private JFrame frame = null;

	public SlideViewerComponent(Presentation pres, JFrame frame) {
		setBackground(BACKGROUNDCOLOR);
		presentation = pres;
		labelFont = new Font(FONTNAME, FONTSTYLE, FONTHEIGHT);
		this.frame = frame;
	}

	public Dimension getPreferredSize() {
		return new Dimension(Slide.SLIDE_WIDTH, Slide.SLIDE_HEIGHT);
	}

	public void updatePresentation(Presentation presentation, Slide slideData) {
		if (slideData == null) {
			repaint();
			return;
		}
		this.presentation = presentation;
		this.currentSlide = slideData;
		repaint();
		frame.setTitle(presentation.getTitle());
	}

	public void paintComponent(Graphics graphics) {
		graphics.setColor(BACKGROUNDCOLOR);
		graphics.fillRect(0, 0, getSize().width, getSize().height);

		if (presentation.getCurrentSlideNumber() < 0 || currentSlide == null) {
			return;
		}
		graphics.setFont(labelFont);
		graphics.setColor(COLOR);
		graphics.drawString("Slide " + (1 + presentation.getCurrentSlideNumber()) + " of " +
				presentation.getSize(), XPOSITION, YPOSITION);
		Rectangle area = new Rectangle(0, YPOSITION, getWidth(), (getHeight() - YPOSITION));
		currentSlide.drawSlide(graphics, area, this);
	}
}