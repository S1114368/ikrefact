import java.util.Vector;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;


/** XMLAccessor, reads and writes XML files
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class XMLAccessor extends Accessor {
	/** namen van xml tags of attributen */
	protected static final String TITLE = "showtitle";
	protected static final String SLIDETITLE = "title";
	protected static final String SLIDE = "slide";
	protected static final String ITEM = "item";
	protected static final String LEVEL = "level";
	protected static final String KIND = "kind";
	protected static final String TEXT = "text";
	protected static final String IMAGE = "image";

	protected static final String DEFAULT_API_TO_USE = "dom";

	protected static final String PARSERCONFIGURATIONEXCEPTION = "Parser Configuration Exception";
	protected static final String UNKNOWNTYPE = "Unknown Element type";
	protected static final String NUMBERFORMATEXCEPTION = "Number Format Exception";


	private String getTitle(Element element, String tagName) {
		NodeList titles = element.getElementsByTagName(tagName);
		return titles.item(0).getTextContent();
	}

	public Document createJDOMDocument(String filename){
		Document document = null;
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = builder.parse(new File(filename)); // maak een JDOM document
		}
		catch (IOException iox) {
			System.err.println(iox.toString());
		}
		catch (SAXException sax) {
			System.err.println(sax.getMessage());
		}
		catch (ParserConfigurationException pcx) {
			System.err.println(PARSERCONFIGURATIONEXCEPTION);
		}
		return document;
	}

	public void loadFile(Presentation presentation, String filename) throws IOException {

		Element document = createJDOMDocument(filename).getDocumentElement();
		presentation.setTitle(getTitle(document, TITLE));

		NodeList slides = document.getElementsByTagName(SLIDE);

		for (int slideNumber = 0; slideNumber < slides.getLength(); slideNumber++) {
			Element xmlSlide = (Element) slides.item(slideNumber);
			Slide slide = new Slide();
			slide.setSlideTitle(getTitle(xmlSlide, SLIDETITLE));
			presentation.appendSlideToPresentation(slide);

			NodeList slideItems = xmlSlide.getElementsByTagName(ITEM);

			for (int itemNumber = 0; itemNumber < slideItems.getLength(); itemNumber++) {
				Element item = (Element) slideItems.item(itemNumber);
				loadSlideItem(slide, item);
			}
		}
	}

	protected void loadSlideItem(Slide slide, Element item) {
		int level = 1; // default
		NamedNodeMap attributes = item.getAttributes();
		String levelText = attributes.getNamedItem(LEVEL).getTextContent();
		if (levelText != null) {
			try {
				level = Integer.parseInt(levelText);
			}
			catch(NumberFormatException x) {
				System.err.println(NUMBERFORMATEXCEPTION);
			}
		}
		String type = attributes.getNamedItem(KIND).getTextContent();
		if (TEXT.equals(type)) {
			slide.appendSlideItem(new TextItem(level, item.getTextContent()));
		}
		else {
			if (IMAGE.equals(type)) {
				slide.appendSlideItem(new BitmapItem(level, item.getTextContent()));
			}
			else {
				System.err.println(UNKNOWNTYPE);
			}
		}
	}

	public void saveFile(Presentation presentation, String filename) throws IOException {
		PrintWriter file = new PrintWriter(new FileWriter(filename));
		file.println("<?xml version=\"1.0\"?>");
		file.println("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">");
		file.println("<presentation>");
		file.print("<showtitle>");
		file.print(presentation.getTitle());
		file.println("</showtitle>");
		for (int slideNumber=0; slideNumber<presentation.getSize(); slideNumber++) {
			Slide slide = presentation.getSlide(slideNumber);
			file.println("<slide>");
			file.println("<title>" + slide.getTitle() + "</title>");
			Vector<SlideItem> slideItems = slide.getSlideItems();
			for (int itemNumber = 0; itemNumber<slideItems.size(); itemNumber++) {
				SlideItem slideItem = (SlideItem) slideItems.elementAt(itemNumber);
				file.print("<item kind=");
				if (slideItem instanceof TextItem) {
					file.print("\"text\" level=\"" + slideItem.getLevel() + "\">");
					file.print( ( (TextItem) slideItem).getText());
				}
				else {
					if (slideItem instanceof BitmapItem) {
						file.print("\"image\" level=\"" + slideItem.getLevel() + "\">");
						file.print( ( (BitmapItem) slideItem).getImageName());
					}
					else {
						System.out.println("Ignoring " + slideItem);
					}
				}
				file.println("</item>");
			}
			file.println("</slide>");
		}
		file.println("</presentation>");
		file.close();
	}
}