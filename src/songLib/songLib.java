package songLib;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import songLib.Controller;

public class songLib extends Application
{
	public static void main(String[] args) 
	{
		launch(args);
	}

	@Override
	public void start(Stage Primestage) throws Exception 
	{
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/songLib/SongLibraryFX.fxml"));
		AnchorPane root = (AnchorPane) loader.load();
		
		Controller controller = loader.getController();
		controller.start(Primestage);
		
		Scene scene = new Scene(root);
		Primestage.setScene(scene);
		Primestage.setTitle("Song Library");
		Primestage.setResizable(false);
		Primestage.show();
	}
}


