/** Een ingebouwde demo-presentatie
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

class DemoPresentation extends Accessor {

	public void loadFile(Presentation presentation, String unusedFilename) {
		presentation.setTitle("Demo Presentation");
		presentation.appendSlideToPresentation(makeFirstSlide());
		presentation.appendSlideToPresentation(makeSecondSlide());
		presentation.appendSlideToPresentation(makeThrirdSlide());
	}

	public void saveFile(Presentation presentation, String unusedFilename) {
		throw new IllegalStateException("Save As->Demo! aangeroepen");
	}

	public Slide makeFirstSlide() {
		Slide slide;
		slide = new Slide();
		slide.setSlideTitle("JabberPoint");
		slide.appendText(1, "Het Java Presentatie Tool");
		slide.appendText(2, "Copyright (c) 1996-2000: Ian Darwin");
		slide.appendText(2, "Copyright (c) 2000-now:");
		slide.appendText(2, "Gert Florijn en Sylvia Stuurman");
		slide.appendText(4, "JabberPoint aanroepen zonder bestandsnaam");
		slide.appendText(4, "laat deze presentatie zien");
		slide.appendText(1, "Navigeren:");
		slide.appendText(3, "Volgende slide: PgDn of Enter");
		slide.appendText(3, "Vorige slide: PgUp of up-arrow");
		slide.appendText(3, "Stoppen: q or Q");
		return slide;
	}

	public Slide makeSecondSlide() {
		Slide slide;
		slide = new Slide();
		slide.setSlideTitle("Demonstratie van levels en stijlen");
		slide.appendText(1, "Level 1");
		slide.appendText(2, "Level 2");
		slide.appendText(1, "Nogmaals level 1");
		slide.appendText(1, "Level 1 heeft stijl nummer 1");
		slide.appendText(2, "Level 2 heeft stijl nummer 2");
		slide.appendText(3, "Zo ziet level 3 er uit");
		slide.appendText(4, "En dit is level 4");
		return slide;
	}

	public Slide makeThrirdSlide() {
		Slide slide;
		slide = new Slide();
		slide.setSlideTitle("De derde slide");
		slide.appendText(1, "Om een nieuwe presentatie te openen,");
		slide.appendText(2, "gebruik File->Open uit het menu.");
		slide.appendText(1, " ");
		slide.appendText(1, "Dit is het einde van de presentatie.");
		slide.appendSlideItem(new BitmapItem(1, "JabberPoint.jpg"));
		return slide;
	}
}