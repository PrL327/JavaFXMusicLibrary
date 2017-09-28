package songLib;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.util.Callback;
import songUtil.Song;
import songUtil.SongList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.control.Alert.AlertType; 

public class Controller 
{
	//e = editing a song
	//c = creating a new song
	//n = nothing
	//d = deletion mode
	char mode = 'n';
	Color deleteColor = Color.SALMON;
	Color addColor = Color.LIGHTGREEN;
	Color editColor = Color.LIGHTYELLOW;
	
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
	
	@FXML
	Label helpLabel;
	
	public void start(Stage mainStage) {
		System.out.printf("%-25s%s\n", "aaaaaaaaaaaaaaaaaa", "b");
		System.out.printf("%-25s%s\n", "a", "b");
		
		editSong.setBackground(new Background(new BackgroundFill(editColor, CornerRadii.EMPTY, Insets.EMPTY)));
		addSong.setBackground(new Background(new BackgroundFill(addColor, CornerRadii.EMPTY, Insets.EMPTY)));
		deleteSong.setBackground(new Background(new BackgroundFill(deleteColor, CornerRadii.EMPTY, Insets.EMPTY)));
		
		helpLabel.setText(String.format("%-25s%s", "Song name", "Artist"));
		helpLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		
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
		//songView.set
		songView.setCellFactory(param -> new ListCell<Song>() {
			@Override
			protected void updateItem(Song item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
				} else {
					setText(String.format("%-35s%s", item.getName(), item.getArtist()));
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
		if(song != null)
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

	private boolean canAdd(int index, Song newSong)
	{
		Song s;
		boolean canAdd = true;
		if(newSong.getArtist().isEmpty() || newSong.getName().isEmpty())
			canAdd = false;
		for(int i = 0; i < observableList.size() && canAdd; i++)
		{
			s = observableList.get(i);
			if(i != index)
			{
				if(s.equals(newSong))
				{
					canAdd = false;
				}
			}
		}
		
		return canAdd;
	}
	
	public void save(ActionEvent e) 
	{
		//get selected item
		//Song item = songView.getSelectionModel().getSelectedItem();
		int index = songView.getSelectionModel().getSelectedIndex();

		String name = songInput.getText();
		String artist = artistInput.getText();
		String album = albumInput.getText();
		String year = yearInput.getText();

		Comparator<Song> songComparer = Comparator.comparing(Song::getName, String.CASE_INSENSITIVE_ORDER)
				.thenComparing(Song::getArtist, String.CASE_INSENSITIVE_ORDER);

		Song newSong = new Song(name, artist, album, year);
		
		if(mode == 'e')
		{
			if(canAdd(index, newSong))
			{
				observableList.set(index, newSong);
				Collections.sort(observableList, songComparer);
				songView.getSelectionModel().select(observableList.indexOf(new Song(name, artist, album, year)));
				System.out.println(observableList.indexOf(newSong));
			}
			else
				System.out.println("error e");
		}
		else if(mode == 'c')
		{
			if(canAdd(-1, newSong))
			{
				observableList.add(newSong);
				Collections.sort(observableList, songComparer);
				songView.getSelectionModel().select(observableList.indexOf(newSong));
				System.out.println("c: " + observableList.indexOf(newSong));
			}
			else
				System.out.println("error c");
		}
		//Collections.sort(observableList, songComparer);
		//songView.getSelectionModel().select(observableList.indexOf(new Song(name, artist, album, year)));

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

	public void addClick(ActionEvent e)
	{
		
		songInput.setVisible(true);
		artistInput.setVisible(true);
		albumInput.setVisible(true);
		yearInput.setVisible(true);
		saveButton.setVisible(true);
		cancelButton.setVisible(true);

		songInput.setText("");
		artistInput.setText("");
		albumInput.setText("");
		yearInput.setText("");
		
		songInput.setBackground(new Background(new BackgroundFill(addColor, CornerRadii.EMPTY, Insets.EMPTY)));
		artistInput.setBackground(new Background(new BackgroundFill(addColor, CornerRadii.EMPTY, Insets.EMPTY)));
		albumInput.setBackground(new Background(new BackgroundFill(addColor, CornerRadii.EMPTY, Insets.EMPTY)));
		yearInput.setBackground(new Background(new BackgroundFill(addColor, CornerRadii.EMPTY, Insets.EMPTY)));
		
		songNameLabel.setVisible(false);
		artistLabel.setVisible(false);
		albumLabel.setVisible(false);
		yearPublishedLabel.setVisible(false);

		mode = 'c'; //tells save to add new song
	}

	public void editClick(ActionEvent e) 
	{
		
		songInput.setVisible(true);
		artistInput.setVisible(true);
		albumInput.setVisible(true);
		yearInput.setVisible(true);
		saveButton.setVisible(true);
		cancelButton.setVisible(true);
		
		songInput.setBackground(new Background(new BackgroundFill(editColor, CornerRadii.EMPTY, Insets.EMPTY)));
		artistInput.setBackground(new Background(new BackgroundFill(editColor, CornerRadii.EMPTY, Insets.EMPTY)));
		albumInput.setBackground(new Background(new BackgroundFill(editColor, CornerRadii.EMPTY, Insets.EMPTY)));
		yearInput.setBackground(new Background(new BackgroundFill(editColor, CornerRadii.EMPTY, Insets.EMPTY)));

		songNameLabel.setVisible(false);
		artistLabel.setVisible(false);
		albumLabel.setVisible(false);
		yearPublishedLabel.setVisible(false);

		mode = 'e'; //tells save to edit a current song
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
	
	public void delete(ActionEvent e)
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		   alert.setTitle("Confirm Dialog");
		   alert.setHeaderText("Are you sure you want to delete this song?");
		   
		 Optional<ButtonType> result = alert.showAndWait();
		 if (result.get() == ButtonType.OK)
		 {
			 int index = songView.getSelectionModel().getSelectedIndex();
			 int selectIndex = 0;
			 if(index == 0 && observableList.size() == 1)
			 {
				 selectIndex = -1;
			 }
			 else if(index == 0 && observableList.size() > 1)
			 {
				 selectIndex = 0;
			 }
			 else if(index == observableList.size() - 1)
			 {
				 selectIndex = observableList.size() - 2;
			 }
			 else
				 selectIndex = index;
			 observableList.remove(index);
			 songView.getSelectionModel().select(selectIndex);
		 }
	}
	
	public void saveList()
	{
		SongList.setList(new ArrayList<>(observableList));
	}
}
