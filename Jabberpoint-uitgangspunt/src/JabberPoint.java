import javax.swing.JOptionPane;

import java.io.IOException;

/** JabberPoint Main Programma
 * <p>This program is distributed under the terms of the accompanying
 * COPYRIGHT.txt file (which is NOT the GNU General Public License).
 * Please read it. Your use of the software constitutes acceptance
 * of the terms in the COPYRIGHT.txt file.</p>
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.1 2002/12/17 Gert Florijn
 * @version 1.2 2003/11/19 Sylvia Stuurman
 * @version 1.3 2004/08/17 Sylvia Stuurman
 * @version 1.4 2007/07/16 Sylvia Stuurman
 * @version 1.5 2010/03/03 Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class JabberPoint {
	protected static final String IOERROR = "IO Error: ";
	protected static final String JABBERPOINTERROR = "Jabberpoint Error ";
	protected static final String JABBERPOINTVERSION = "Jabberpoint 1.6 - OU version";

	/** Het Main Programma */
	public static void main(String argv[]) {
		Style.createStyles();
		Presentation presentation = new Presentation();
		new SlideViewerFrame(JABBERPOINTVERSION, presentation);
		try {
			if (argv.length == 0) { // een demo presentatie
				Accessor.getDemoAccessor().loadFile(presentation, "");
			} else {
				new XMLAccessor().loadFile(presentation, argv[0]);
			}
			presentation.setCurrentSlideNumber(0);
		} catch (IOException exception) {
			JOptionPane.showMessageDialog(null,
					IOERROR + exception, JABBERPOINTERROR,
					JOptionPane.ERROR_MESSAGE);
			}
	}
}