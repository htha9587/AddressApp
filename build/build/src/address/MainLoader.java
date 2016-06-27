package address;

import javafx.application.Preloader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * AddressApp Preloader.
 * @author htha9587
 *
 */
public class MainLoader extends Preloader {
	
	private Stage stage;
	
	private Scene createPreloaderScene()
	{
		String url = getClass().getResource("loader.GIF").toExternalForm();
		ImageView progress = new ImageView(url);
		
		Label title = new Label("AddressApp");
		title.setStyle("-fx-font-size: 2.3em; -fx-text-fill: whitesmoke;");
		
		Label footer = new Label("Author: Harrison Thacker");
		footer.setStyle("-fx-font-size: 0.5em; -fx-text-fill: whitesmoke; -fx-font-style: oblique;");
		
		VBox root = new VBox();
		root.setSpacing(10.0);
		root.setAlignment(Pos.CENTER);
		root.getChildren().addAll(title, progress);
		
		BorderPane pane = new BorderPane(root);
		pane.setBottom(footer);
		pane.setStyle("-fx-background-color: #2b579a");
		
		return new Scene(pane, 480, 320, Color.TRANSPARENT);
	}
	
	
	@Override
	public void start(Stage stage) throws Exception 
	{
		this.stage = stage;
		
		
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setScene(createPreloaderScene());
		stage.show();
	}
	
	@Override
	public void handleStateChangeNotification(StateChangeNotification scn)
	{
		if(scn.getType() == StateChangeNotification.Type.BEFORE_START)
		{
			stage.hide();
		}
	}
}
