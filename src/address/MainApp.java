package address;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import address.model.Person;
import address.model.PersonListWrapper;
import address.view.PersonEditDialogController;
import address.view.PersonOverviewController;
import address.view.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application 
{
	
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	/**
	 * Data as an ObservableList of Persons.
	 */
	private ObservableList<Person> personData = FXCollections.observableArrayList();
	
	/**
	 * Constructor.
	 */
	public MainApp()
	{
		//Sample Data.
		personData.add(new Person("Cody", "Henrichsen"));
		personData.add(new Person("Clayton", "Anderson"));
		personData.add(new Person("Jonathan", "Humphries"));
		personData.add(new Person("Harrison", "Thacker"));
		personData.add(new Person("Walter", "Savitch"));
		personData.add(new Person("Emmanuel", "Kepas"));
		personData.add(new Person("Forrest", "Thacker"));
		personData.add(new Person("Alec", "Thacker"));
		personData.add(new Person("Chandler", "Thacker"));
		
	}
	
	/**
     * Returns the data as an observable list of Persons. 
     * @return
     */
    public ObservableList<Person> getPersonData() {
        return personData;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        
        //Application Icon
        this.primaryStage.getIcons().add(new Image("file:resources/images/Icon.png"));
        initRootLayout();

        showPersonOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            
            //Give controller access to main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);
            
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //try to open last opened person file.
        File file = getPersonFilePath();
        if(file != null)
        		{
        			loadPersonDataFromFile(file);
        		}
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     * 
     * @param person the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     */
    public boolean showPersonEditDialog(Person person) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Returns person file preference, file that was last opened.
     * Read from OS specific registry. Null is returned if no preference is found.
     */
    public File getPersonFilePath()
    {
    	Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
    	String filePath = prefs.get("filePath", null);
    	if(filePath != null)
    	{
    		return new File(filePath);
    	}
    	else
    	{
    		return null;
    	}
    }
    
    /**
     * Sets file path from OS registry(Currently loaded file).
     */
    public void setPersonFilePath(File file)
    {
    	Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
    	if(file != null)
    	{
    		prefs.put("filePath", file.getPath());
    		//Update Stage title.
    		primaryStage.setTitle("AddressApp - " + file.getName());
    	}
    	else
    	{
    		prefs.remove("filePath");
    		//Update Stage title.
    		primaryStage.setTitle("AddressApp");
    	}
    }
    		
    /**
     * Loads person data from specified file. Current data will be replaced.
     */
    public void loadPersonDataFromFile(File file)
    {
    	try
    	{
    		JAXBContext context = JAXBContext
    				.newInstance(PersonListWrapper.class);
    		Unmarshaller um = context.createUnmarshaller();
    		
    		//Reading xml from file and unmarshalling.
    		PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);
    		
    		personData.clear();
    		personData.addAll(wrapper.getPersons());
    		//Save file path to registry.
    		setPersonFilePath(file);
    	} 
    	catch (Exception e) //ANY exception.
    	{
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error");
    		alert.setHeaderText("Couldn't load data");
    		alert.setContentText("Couldn't load data from file:\n" + file.getPath());
    		
    		alert.showAndWait();
    	}
    }

    /**
     * Saves person data to a specified file.
     */
    public void savePersonDataToFile(File file)
    {
    	try
    	{
    		JAXBContext context = JAXBContext
    				.newInstance(PersonListWrapper.class);
    		Marshaller m = context.createMarshaller();
    		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    		//Wrapping person data.
    		PersonListWrapper wrapper = new PersonListWrapper();
    		wrapper.setPersons(personData);
    		//Marshalling and saving xml to file.
    		m.marshal(wrapper, file);
    		//Save file path to registry.
    		setPersonFilePath(file);
    	}
    	catch(Exception e) //Grabs any exception.
    	{
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Error");
    		alert.setHeaderText("Couldn't save data");
    		alert.setContentText("Couldn't save data to file:\n" + file.getPath());
    		
    		alert.showAndWait();
    	}
    }
    
    
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
