package songLib;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import songUtil.Song;
import songUtil.SongList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView; 

public class Controller 
{
	@FXML
	ListView<String> songView;
	
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
	
	@FXML
	private ObservableList<Song> observableList;
	private ObservableList<String> obsList;    
	
	public void start(Stage mainStage) {
		//sngList = new ListView<>();
		//SongList.loadList();
		//SongList.printList();
		
		//observableList = FXCollections.observableArrayList(SongList.getList());
		obsList = FXCollections.observableArrayList(                               
                "Giants",                               
                "Patriots",
                "49ers",
                "Rams",
                "Packers",
                "Colts",
                "Cowboys",
                "Broncos",
                "Vikings",
                "Dolphins",
                "Titans",
                "Seahawks",
                "Steelers",
                "Jaguars");
		
		songView.setItems(obsList); 
		//System.out.println(observableList.size());
		//sngList = new ListView<>();
		
		//sngList.setItems(observableList);
		
//		sngList.setCellFactory(param -> new ListCell<Song>() {
//            @Override
//            protected void updateItem(Song item, boolean empty) {
//                super.updateItem(item, empty);
//
//                if (empty || item == null) {
//                    setText(null);
//                } else {
//                    setText(item.getName() + "-" + item.getArtist());
//                }
//            }
//        });
		
//		sngList.setCellFactory(new Callback<ListView<Song>, ListCell<Song>>(){
//			 
//            @Override
//            public ListCell<Song> call(ListView<Song> p) {
//                 
//                ListCell<Song> cell = new ListCell<Song>(){
// 
//                    @Override
//                    protected void updateItem(Song t, boolean bln) {
//                        super.updateItem(t, bln);
//                        if (t != null) {
//                            setText(t.getName() + ":" + t.getArtist());
//                        }
//                    }
// 
//                };
//                 
//                return cell;
//            }
//        });
		songView.getSelectionModel().select(0);
	}
	
	public void addClick(ActionEvent e) {
		
	}
	
}
