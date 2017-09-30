/*
 * By Daniel Fraser and Peter Laskai
 */
package songLib;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import songUtil.Song;
import songUtil.SongList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Optional;
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
	char mode = 'n'; //mode is a global variable used to indicate whether the user is editing or adding a song
	Color deleteColor = Color.SALMON;
	Color addColor = Color.LIGHTGREEN;
	Color editColor = Color.LIGHTYELLOW;

	boolean editing = false; //edit status for controller indication
	// FXML DEFINITIONS FOR CONTROLLER
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

	/**
	 * sets properties to buttons and a few of the labels
	 * loads list from file
	 * 
	 * @param mainStage
	 */
	public void start(Stage mainStage) { // Initialization of FXapp

		//sets background color
		editSong.setBackground(new Background(new BackgroundFill(editColor, CornerRadii.EMPTY, Insets.EMPTY)));
		addSong.setBackground(new Background(new BackgroundFill(addColor, CornerRadii.EMPTY, Insets.EMPTY)));
		deleteSong.setBackground(new Background(new BackgroundFill(deleteColor, CornerRadii.EMPTY, Insets.EMPTY)));

		//sets helper label
		helpLabel.setText(String.format("  %-25s\n  %s", "Song name", "Artist"));
		helpLabel.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

		//Inputs initally not visible, not until user interacts with either add/eddit
		songInput.setVisible(false); 
		artistInput.setVisible(false);
		albumInput.setVisible(false);
		yearInput.setVisible(false);
		saveButton.setVisible(false);
		cancelButton.setVisible(false);
		//load list from file
		SongList.loadList();

		observableList = FXCollections.observableArrayList(SongList.getList());

		songView.setItems(observableList); //populates listview with Songs form txt file

		//formats each cell in listview to display songs nicely
		songView.setCellFactory(param -> new ListCell<Song>() {
			@Override
			protected void updateItem(Song item, boolean empty) {
				super.updateItem(item, empty);

				if (empty || item == null) {
					setText(null);
				} else {
					setText(String.format("%s\n%s", item.getName(), item.getArtist()));
				}
			}
		});

		//sets labels to display info for first item if there is any
		songView.getSelectionModel().select(0);
		if(observableList.size() > 0)
		{
			setAll(observableList.get(0));
		}
		//used for when user selects item on list
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
			//Input fields filled in with song data if user is Editing currently selected song
			songInput.setText(song.getName());
			artistInput.setText(song.getArtist());
			albumInput.setText(song.getAlbum());
			yearInput.setText(song.getYear());

			//Labels are filled with song data and dynamically change as user selects other songs in listview
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
		if(!editing)
		{
			Song item = songView.getSelectionModel().getSelectedItem();

			//observableList.set(index, new Song("a", "a", "a", 45)); might need it for later use
			setAll(item);
		}

	}
	/**
	 * sets a boolean to true if there are no duplicates
	 * @param index, newSong
	 */
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

	/**
	 * Event handler for save button
	 * @param ActionEvent e
	 */
	public void save(ActionEvent e) 
	{
		//get selected item
		//Song item = songView.getSelectionModel().getSelectedItem();
		int index = songView.getSelectionModel().getSelectedIndex();

		//Extrapolates user input from fields to create a new <Song>
		String name = songInput.getText(); 
		String artist = artistInput.getText();
		String album = albumInput.getText();
		String year = yearInput.getText();
		
		Comparator<Song> songComparer = Comparator.comparing(Song::getName, String.CASE_INSENSITIVE_ORDER)
				.thenComparing(Song::getArtist, String.CASE_INSENSITIVE_ORDER);

		Song newSong = new Song(name, artist, album, year); //New Song created and initialized with user vals

		//If user leaves name field blank asserts a error popup indicating such
		if(name.isEmpty())
		{
			editing = true;
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Unable to save, song name is blank");
			alert.showAndWait();
			songInput.setText("");
		}
		else if(artist.isEmpty()) //Error pop ups if user leaves artists fields blank
		{
			editing = true;  // resets bool val
			Alert alert = new Alert(AlertType.ERROR); //ERROR alert type
			alert.setTitle("Error ");
			alert.setHeaderText("Unable to save, artist name is blank");
			alert.showAndWait();
			artistInput.setText("");//Resets back to blank
		}

		if(mode == 'e' && !name.isEmpty() && !artist.isEmpty()) //user is in edit mode and all required fields are filled im
		{
			if(canAdd(index, newSong)) // if it passes all paramemeter reqs
			{
				observableList.set(index, newSong); //updates songlist and view and changes placement if necessary
				editing = false; // resets bool val
				Collections.sort(observableList, songComparer);
				songView.getSelectionModel().select(observableList.indexOf(newSong)); //autoselects updated song 
				mainScreen(); // returns to main initial screen
				saveList(); //saves it to txt file
				
				
			}
			else {
				editing = false;  // resets bool val
				Alert alert = new Alert(AlertType.ERROR); //DUPLICATE ERROR pops up if fields are the same
				alert.setTitle("Error ");
				alert.setHeaderText("There is already a song with the name and artist combination.\n"
						+ "Please change one of them in order to edit the song");
				Optional<ButtonType> result = alert.showAndWait();
				setup(); //shows fields again
				
			}

		}
		else if(mode == 'c' && !name.isEmpty() && !artist.isEmpty()) //user is in add mode and fields are filled in
		{
			if(canAdd(-1, newSong))
			{
				editing = false;  // resets bool val
				observableList.add(newSong); //adds new song to list
				Collections.sort(observableList, songComparer); //sorts again with new song added to list
				songView.getSelectionModel().select(observableList.indexOf(newSong)); //selects new song
				mainScreen(); // resets to main screen
				saveList(); //saves new list to txt file
			}
			else {
				editing = false;  // resets bool val
				Alert alert = new Alert(AlertType.ERROR); //DUPLICATE ERROR
				alert.setTitle("Error ");
				alert.setHeaderText("There is already a song with the name and artist combination.\n"
						+ "Please change one of them in order to add the song");
				Optional<ButtonType> result = alert.showAndWait();
				setup(); //shows fields again
			}
		}

		Collections.sort(observableList, songComparer); //sorts list
	}

	/**
	 * Event handler for add Button
	 * @param ActionEvent e
	 */
	public void addClick(ActionEvent e) 
	{
		mode = 'c'; //tells save to add new song
		setup(); //calls setup method
	}

	/**
	 * Event handler for edit button
	 * @param ActionEvent e
	 */
	public void editClick(ActionEvent e) 
	{
		mode = 'e'; //tells save to edit a current song
		setup(); //calls setup method
	}

	/**
	 * Event handler for cancel button
	 * @param ActionEvent e
	 */
	public void cancel(ActionEvent e) { 

		mainScreen(); //calls mainscreen method

		//get item selected
		Song item = songView.getSelectionModel().getSelectedItem();

		setAll(item);
	}

	/**
	 * Event handler for delete button
	 * @param ActionEvent e
	 */
	public void delete(ActionEvent e) 
	{
		Alert alert = new Alert(AlertType.CONFIRMATION); //Confirmation pop up
		alert.setTitle("Confirm Dialog");
		alert.setHeaderText("Are you sure you want to delete this song?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) //if user says ok
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
			saveList();
			songView.getSelectionModel().select(selectIndex);
			if(selectIndex > -1)
				setAll(observableList.get(selectIndex));
			else
				setAll(new Song("","","",""));
		}
	}
	/**
	 * setup is a method that sets up the UI display according to the users interaction with
	 * add/edit but
	 */
	public void setup () { 
		//All initial labels and buttons are set to invisible
		songNameLabel.setVisible(false);
		artistLabel.setVisible(false);
		albumLabel.setVisible(false);
		yearPublishedLabel.setVisible(false);
		addSong.setVisible(false);
		editSong.setVisible(false);
		deleteSong.setVisible(false);

		//makes input and save/cancel buttons visible
		songInput.setVisible(true);
		artistInput.setVisible(true);
		albumInput.setVisible(true);
		yearInput.setVisible(true);
		saveButton.setVisible(true);
		cancelButton.setVisible(true);


		if(mode == 'e') {	//sets colors according to users current mode
			songInput.setBackground(new Background(new BackgroundFill(editColor, CornerRadii.EMPTY, Insets.EMPTY)));
			artistInput.setBackground(new Background(new BackgroundFill(editColor, CornerRadii.EMPTY, Insets.EMPTY)));
			albumInput.setBackground(new Background(new BackgroundFill(editColor, CornerRadii.EMPTY, Insets.EMPTY)));
			yearInput.setBackground(new Background(new BackgroundFill(editColor, CornerRadii.EMPTY, Insets.EMPTY)));
		}
		else if(mode == 'c') {
			songInput.setText("");
			artistInput.setText("");
			albumInput.setText("");
			yearInput.setText("");

			songInput.setBackground(new Background(new BackgroundFill(addColor, CornerRadii.EMPTY, Insets.EMPTY)));
			artistInput.setBackground(new Background(new BackgroundFill(addColor, CornerRadii.EMPTY, Insets.EMPTY)));
			albumInput.setBackground(new Background(new BackgroundFill(addColor, CornerRadii.EMPTY, Insets.EMPTY)));
			yearInput.setBackground(new Background(new BackgroundFill(addColor, CornerRadii.EMPTY, Insets.EMPTY)));
		}
	}
	/**
	 * mainScreen sets up user to the intial screen if user cancels or saves successfully
	 */
	public void mainScreen() {

		//removes input and buttons from view
		songInput.setVisible(false);
		artistInput.setVisible(false);
		albumInput.setVisible(false);
		yearInput.setVisible(false);
		saveButton.setVisible(false);
		cancelButton.setVisible(false);

		//sets labels and buttons to view
		songNameLabel.setVisible(true);
		artistLabel.setVisible(true);
		albumLabel.setVisible(true);
		yearPublishedLabel.setVisible(true);
		addSong.setVisible(true);
		editSong.setVisible(true);
		deleteSong.setVisible(true);

	}
	/**
	 * saves song to txt file
	 */
	public void saveList()
	{
		SongList.setList(new ArrayList<>(observableList));
	}
}