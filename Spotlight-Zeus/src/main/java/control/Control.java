package control;

import spotlight.delphi.view.Overlay;

public class Control {

	
	public Control() {
		
		//
		// check for updates
		boolean updatesFound = checkForUpdates();
		
		//
		// Launch the program in case updates have been found.
		if (!updatesFound) {
			
			
			// launch c - part of the program
			boolean success = launchCFile();
			
			if (success) {
				System.out.println("overlay..");
				new Overlay();
			}
		}
	}
	
	
	
	/**
	 * Launch the c - part of the project.
	 * @return if this function was successfully executed.
	 */
	private boolean launchCFile() {
		//TODO:
		return true;
	}


	/**
	 * Check for updates and apply them directy.
	 */
	private boolean checkForUpdates() {
		//TODO:
		return false;
	}
}
