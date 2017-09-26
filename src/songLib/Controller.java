package songLib;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import songUtil.SongList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView; 

public class Controller 
{
	Stage mainStage;
	
	@FXML
	ListView<String> songList;
	
	@FXML
	Button editSong;
	
	@FXML
	Button deleteSong;
	
	@FXML
	Button addSong;
	
	@FXML
	Label songNameLabel;
	
	@FXML
	Label artistLabel;
	
	@FXML
	Label albumLabel;
	
	@FXML
	Label yearPublishedLabel;
	
	private ObservableList<String> observableList;
	
	public void onLaunch(Stage mainStage) {
		observableList = FXCollections.<String>observableArrayList(SongList.getShrtList());
		songList.setItems(observableList);
		songList.getSelectionModel().select(0);
	}
	
	public void addClick(ActionEvent e) {
		
	}
	
}
