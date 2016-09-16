package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import spotlight.delphi.view.Overlay;
import view.markup.Highlight;
import view.settings.SettingsWindow;


/**
 * The controller class for the Zeus- Athene project.
 * 
 * @author Julius Huelsmann
 * @version %I%, %U%
 * @since 1.0
 */
public class Control implements ActionListener {

	
  /**
   * Java-Highlight window for being able to implement the own java classes
   * as dictionaries. 
   * Class is part of the Zeus project.
   */
  private Highlight viewHiZeus;
  
  
  /**
   * Overlay window for detecting the location at which the user clicked
   * for maintaining a special word.
   */
  private Overlay viewOverlayDelphi;
  
  
  /**
   * Settings window that comes with a tray icon. Classes are stored inside the
   * zeus project.
   */
  private SettingsWindow viewSWZeus ;
  
	public Control() {
		
		//
		// check for updates
		boolean updatesFound = checkForUpdates();
		
		//
		// Launch the program in case updates have been found.
		if (!updatesFound) {
			
		  
		  
		  viewSWZeus = new SettingsWindow(this);
			
			// launch c - part of the program
			boolean success = launchCFile();
			
			if (success) {
				System.out.println("overlay..");
				viewOverlayDelphi = new Overlay();
			}
		}
	}
	
	
	
	/**
	 * Launch the c - part of the project.
	 * @return if this function was successfully executed.
	 */
	private boolean launchCFile() {
		//TODO:
		return false;
	}


	/**
	 * Check for updates and apply them directy.
	 */
	private boolean checkForUpdates() {
		//TODO:
		return false;
	}



  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource().equals(viewSWZeus.getAp().getJbtnAddNewJava())) {
      setHi(new Highlight());
    }
  }



  /**
   * @return the hi
   */
  public Highlight viewHiZeus() {
    return viewHiZeus;
  }



  /**
   * @param hi the hi to set
   */
  public void setHi(Highlight hi) {
    this.viewHiZeus = hi;
  }
}
