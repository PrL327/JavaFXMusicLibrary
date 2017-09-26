package songLib;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class songLib extends Application
{
	public static void main(String[] args) 
	{
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception 
	{
		// Create the Lists for the ListViews
        //ObservableList<String> seasonList = FXCollections.<String>observableArrayList(SongList.getShrtList());
		Parent root = FXMLLoader.load(getClass().getResource("SongLibraryFX.fxml"));
        
        stage.setTitle("FXML Welcome");
        stage.setScene(new Scene(root, 300, 275));
        stage.show();
	}
}


