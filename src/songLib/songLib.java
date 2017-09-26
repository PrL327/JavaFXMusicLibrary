package songLib;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import songUtil.*;

public class songLib extends Application
{
	public static void main(String[] args) 
	{
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Create the Lists for the ListViews
        ObservableList<String> seasonList = FXCollections.<String>observableArrayList(SongList.getShrtList());
        
}
