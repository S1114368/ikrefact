import java.awt.MenuBar;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JOptionPane;

/** <p>De controller voor het menu</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class MenuController extends MenuBar {
	private Frame parent; // het frame, alleen gebruikt als parent voor de Dialogs
	private Presentation presentation; // Er worden commando's gegeven aan de presentatie
	private MenuItem menuItem;

	private static final long serialVersionUID = 227L;

	protected static final String ABOUT = "About";
	protected static final String FILE = "File";
	protected static final String EXIT = "Exit";
	protected static final String GOTO = "Go to";
	protected static final String HELP = "Help";
	protected static final String NEW = "New";
	protected static final String NEXT = "Next";
	protected static final String OPEN = "Open";
	protected static final String PAGENUMBER = "Page number?";
	protected static final String PREVIOUS = "Prev";
	protected static final String SAVE = "Save";
	protected static final String VIEW = "View";

	protected static final String TESTFILE = "test.xml";
	protected static final String SAVEFILE = "dump.xml";

	protected static final String IOEXCEPTION = "IO Exception: ";
	protected static final String LOADERROR = "Load Error";
	protected static final String SAVEERROR = "Save Error";

	public MenuController(Frame frame, Presentation p) {
		parent = frame;
		presentation = p;
		String[] fileMenuOptions = {OPEN, NEW, SAVE, EXIT};
		String[] viewMenuOptions = {VIEW, NEXT, PREVIOUS, GOTO};
		String[] helpMenuOptions = {ABOUT};

		Menu fileMenu = new Menu(FILE);
		addOptionsToMenu(fileMenu, fileMenuOptions);
		add(fileMenu);

		Menu viewMenu = new Menu(VIEW);
		addOptionsToMenu(viewMenu, viewMenuOptions);
		add(viewMenu);

		Menu helpMenu = new Menu(HELP);
		addOptionsToMenu(helpMenu, helpMenuOptions);
		setHelpMenu(helpMenu);		// nodig for portability (Motif, etc.).
	}

	public MenuItem makeMenuItem(String name) {
		return new MenuItem(name, new MenuShortcut(name.charAt(0)));
	}

	public void addOptionsToMenu(Menu menu, String[] options){
		for(int i=0; i < options.length; i++){
			if(i == 3 && options[3] == EXIT){
				menu.addSeparator();
			}
			menu.add(menuItem = makeMenuItem(options[i]));
			addActionListener(menuItem, options[i]);
		}
	}

	public void addActionListener(MenuItem menuItem, String event){
		switch(event){

			case OPEN:
				menuItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent actionEvent) {
						presentation.clearPresentation();
						Accessor xmlAccessor = new XMLAccessor();
						try {
							xmlAccessor.loadFile(presentation, TESTFILE);
							presentation.setCurrentSlideNumber(0);
						} catch (IOException exc) {
							JOptionPane.showMessageDialog(parent, IOEXCEPTION + exc,
									LOADERROR, JOptionPane.ERROR_MESSAGE);
							}
						parent.repaint();
					}
				} );
				break;

			case NEW:
				menuItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent actionEvent) {
						presentation.clearPresentation();
						parent.repaint();
					}
				});
				break;

			case SAVE:
				menuItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Accessor xmlAccessor = new XMLAccessor();
						try {
							xmlAccessor.saveFile(presentation, SAVEFILE);
						} catch (IOException exc) {
							JOptionPane.showMessageDialog(parent, IOEXCEPTION + exc,
									SAVEERROR, JOptionPane.ERROR_MESSAGE);
							}
					}
				});
				break;

			case EXIT:
				menuItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent actionEvent) {
						presentation.exitPresentation(0);
					}
				});
				break;

			case NEXT:
				menuItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent actionEvent) {
						presentation.nextSlide();
					}
				});
			case PREVIOUS:
				menuItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent actionEvent) {
						presentation.previousSlide();
					}
				});
				break;

			case GOTO:
				menuItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent actionEvent) {
						int pageNumber = Integer.parseInt(JOptionPane.showInputDialog((Object)PAGENUMBER));
						presentation.setCurrentSlideNumber(pageNumber - 1);
					}
				});
				break;

			case ABOUT:
				menuItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent actionEvent) {
						AboutBox.show(parent);
					}
				});
				break;
		}
	}
}
