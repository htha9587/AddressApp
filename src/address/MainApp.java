package address;

import java.io.IOException;

import address.model.Person;
import address.view.PersonOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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
	 * Returns data as ObservableList of Persons.
	 */
	public ObservableList<Person> getPersonData()
	{
		return personData;
	}
	@Override
	public void start(Stage primaryStage) 
	{
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("AddressApp");
		
		initRootLayout();
		
		showPersonOverview();
	}

	/**
	 * Initializes rootLayout.
	 */
	public void initRootLayout()
	{
		//Load root Layout from FXML file.
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
		try {
			rootLayout = (BorderPane) loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Show Scene containing root layout.
		Scene scene = new Scene(rootLayout);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * Sets person overview into the center of the root layout.
	 * @param args
	 */
	public void showPersonOverview()
	{
		try 
		{
			//Load PersonOverview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();
			
			//Set Person overview into the center of root layout.
			rootLayout.setCenter(personOverview);
			//Give Controller access to MainApp.
			PersonOverviewController controller = loader.getController();
			controller.setMainApp(this);
			 
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns main stage.
	 * @param args
	 */
	public Stage getPrimaryStage()
	{
		return primaryStage;
	}
	
	public static void main(String[] args) 
	{
		launch(args);
	}
}
