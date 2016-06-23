package address.view;

import java.io.File;

import address.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

/**
 * View Controller for the root layout and the JavaFX menu.
 * @author htha9587
 *
 */
public class RootLayoutController 
{
	//Main class reference.
	private MainApp mainApp;
	
	/**
	 * Called by main application to give a reference
	 * back to itself.
	 */
	public void setMainApp(MainApp mainApp)
	{
		this.mainApp = mainApp;
	}
	
	/**
	 * Creates an empty address book.
	 */
	@FXML
	private void handleNew()
	{
		mainApp.getPersonData().clear();
		mainApp.setPersonFilePath(null);
	}
	
	/**
	 * Opens a filechooser for the user to open up an address book file.
	 */
	@FXML
	private void handleOpen()
	{
		FileChooser fileChooser = new FileChooser();
		
		//Extension filter.
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML Files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		
		//Save file dialog.
		File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
		
		if(file != null)
		{
			mainApp.loadPersonDataFromFile(file);
		}
	}
	
	/**
	 * Saves file to currently open person file. Otherwise, save as dialog is shown.
	 */
	@FXML
	private void handleSave()
	{
		File personFile = mainApp.getPersonFilePath();
		if(personFile != null)
		{
			mainApp.savePersonDataToFile(personFile);
		}
		else
		{
			handleSaveAs();
		}
	}
	
	/**
	 * Opens file chooser for user to save to.
	 */
	@FXML
	private void handleSaveAs()
	{
		FileChooser fileChooser = new FileChooser();
		
		//Set extension filter.
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files(*.xml", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		
		//Show save file dialog.
		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
		
		if(file != null)
		{
			//Ensures that it has the correct extension.
			if(!file.getPath().endsWith("xml"))
			{
				file = new File(file.getPath() + ".xml");
			}
			mainApp.savePersonDataToFile(file);
		}
			
	}
	
	/**
	 * Opens an about dialog.
	 */
	@FXML
	private void handleAbout()
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("AddressApp");
		alert.setHeaderText("About");
		alert.setContentText("Author: htha9587\nWebsite: https://github.com/htha9587");
		
		alert.showAndWait();
	}
	
	/**
	 * Closes application.
	 */
	@FXML
	private void handleExit()
	{
		System.exit(0);
	}
	
}
