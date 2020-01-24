import java.util.ArrayList;


/**
 * <p>Presentation houdt de slides in de presentatie bij.</p>
 * <p>Er is slechts ��n instantie van deze klasse aanwezig.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Presentation {
	private String presentationTitle; // de titel van de presentatie
	private ArrayList<Slide> presentationSlides = null; // een ArrayList met de Slides
	private int currentSlideNumber = 0; // het slidenummer van de huidige Slide
	private SlideViewerComponent slideViewComponent = null; // de viewcomponent voor de Slides

	public Presentation() {
		slideViewComponent = null;
		clearPresentation();
	}

	public Presentation(SlideViewerComponent slideViewerComponent) {
		this.slideViewComponent = slideViewerComponent;
		clearPresentation();
	}

	public int getSize() {
		return presentationSlides.size();
	}

	public String getTitle() {
		return presentationTitle;
	}

	public void setTitle(String newTitle) {
		presentationTitle = newTitle;
	}

	public void setShowView(SlideViewerComponent slideViewerComponent) {
		this.slideViewComponent = slideViewerComponent;
	}

	// geef het nummer van de huidige slide
	public int getCurrentSlideNumber() {
		return currentSlideNumber;
	}

	// verander het huidige-slide-nummer en laat het aan het window weten.
	public void setCurrentSlideNumber(int number) {
		currentSlideNumber = number;
		if (slideViewComponent != null) {
			slideViewComponent.updatePresentation(this, getCurrentSlide());
		}
	}

	// ga naar de vorige slide tenzij je aan het begin van de presentatie bent
	public void previousSlide() {
		if (currentSlideNumber > 0) {
			setCurrentSlideNumber(currentSlideNumber - 1);
	    }
	}

	// Ga naar de volgende slide tenzij je aan het einde van de presentatie bent.
	public void nextSlide() {
		if (currentSlideNumber < (presentationSlides.size()-1)) {
			setCurrentSlideNumber(currentSlideNumber + 1);
		}
	}

	// Verwijder de presentatie, om klaar te zijn voor de volgende
	void clearPresentation() {
		presentationSlides = new ArrayList<Slide>();
		setCurrentSlideNumber(-1);
	}

	// Voeg een slide toe aan de presentatie
	public void appendSlideToPresentation(Slide slide) {
		presentationSlides.add(slide);
	}

	// Geef een slide met een bepaald slidenummer
	public Slide getSlide(int number) {
		if (number < 0 || number >= getSize()){
			return null;
	    }
			return (Slide)presentationSlides.get(number);
	}

	// Geef de huidige Slide
	public Slide getCurrentSlide() {
		return getSlide(currentSlideNumber);
	}

	public void exit(int n) {
		System.exit(n);
	}
}
