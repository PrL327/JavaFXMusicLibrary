package songLib;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.util.Callback;
import songUtil.Song;
import songUtil.SongList;

import java.util.Optional;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField; 

public class Controller 
{
	@FXML
	ListView<Song> songView;

	@FXML
	Button editSong;

	@FXML
	Button deleteSong;

	@FXML
	Button addSong;

	@FXML
	Button saveButton;

	@FXML
	Button cancelButton;

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

	@FXML
	TextField songInput;

	@FXML
	TextField artistInput;

	@FXML
	TextField albumInput;

	@FXML
	TextField yearInput;
	public void start(Stage mainStage) {
		songInput.setVisible(false);
		artistInput.setVisible(false);
		albumInput.setVisible(false);
		yearInput.setVisible(false);
		saveButton.setVisible(false);
		cancelButton.setVisible(false);

		SongList.loadList();
		//SongList.printList();

		observableList = FXCollections.observableArrayList(SongList.getList());

		songView.setItems(observableList);

		songView.setCellFactory(param -> new ListCell<Song>() {
			@Override
			protected void updateItem(Song item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
				} else {
					setText(item.getName() + "-" + item.getArtist());
				}
			}
		});

		songView.getSelectionModel().select(0);
		if(observableList.size() > 0)
		{
			setAll(observableList.get(0));
		}
		songView
		.getSelectionModel()
		.selectedIndexProperty()
		.addListener(
				(obs, oldVal, newVal) -> 
				showSongInfo(mainStage));
	}

	/**
	 * sets all labels and textboxes to the selected song
	 * @param song
	 */
	private void setAll(Song song)
	{
		songInput.setText(song.getName());
		artistInput.setText(song.getArtist());
		albumInput.setText(song.getAlbum());
		yearInput.setText(song.getYear());

		songNameLabel.setText(song.getName());
		artistLabel.setText(song.getArtist());
		albumLabel.setText(song.getAlbum());
		yearPublishedLabel.setText(song.getYear());
	}

	/**
	 * changes info based on selection
	 * @param mainStage
	 */
	private void showSongInfo(Stage mainStage) 
	{  
		//get item selected
		Song item = songView.getSelectionModel().getSelectedItem();

		//observableList.set(index, new Song("a", "a", "a", 45)); might need it for later use
		setAll(item);
	}

	public void save(ActionEvent e) 
	{
		//TODO save old and new values then let songList
		//take care of most of the work
		
		String name = songInput.getText();
		String artist = artistInput.getText();
		String album = albumInput.getText();
		String year = yearInput.getText();
	}

	public void addClick(ActionEvent e) {

		songInput.setVisible(true);
		artistInput.setVisible(true);
		albumInput.setVisible(true);
		yearInput.setVisible(true);
		saveButton.setVisible(true);
		cancelButton.setVisible(true);

		songNameLabel.setVisible(false);
		artistLabel.setVisible(false);
		albumLabel.setVisible(false);
		yearPublishedLabel.setVisible(false);

	}

	public void editClick(ActionEvent e) {

		songInput.setVisible(true);
		artistInput.setVisible(true);
		albumInput.setVisible(true);
		yearInput.setVisible(true);
		saveButton.setVisible(true);
		cancelButton.setVisible(true);

		songNameLabel.setVisible(false);
		artistLabel.setVisible(false);
		albumLabel.setVisible(false);
		yearPublishedLabel.setVisible(false);
	}

	public void cancel(ActionEvent e) {

		songInput.setVisible(false);
		artistInput.setVisible(false);
		albumInput.setVisible(false);
		yearInput.setVisible(false);
		saveButton.setVisible(false);
		cancelButton.setVisible(false);

		songNameLabel.setVisible(true);
		artistLabel.setVisible(true);
		albumLabel.setVisible(true);
		yearPublishedLabel.setVisible(true);

	}

}
