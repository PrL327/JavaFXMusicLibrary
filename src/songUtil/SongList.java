package songUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SongList
{
	//main list of songs
	public static ArrayList<Song> songList = new ArrayList<>();
	//Comparator
	Comparator<Song> songComparer = Comparator.comparing(Song::getName, String.CASE_INSENSITIVE_ORDER)
	          .thenComparing(Song::getArtist, String.CASE_INSENSITIVE_ORDER);
	
	public void addSong(String newName, String newArtist, String newAlbum, int newYear)
	{
		Song newSong = new Song(newName, newArtist, newAlbum, newYear);
		if(!isDuplicate(newSong))
		{
			songList.add(newSong);
			Collections.sort(songList, songComparer);
		}
		//else
			//tell user cannot be done
	}
	public void addSong(Song newSong)
	{
		if(!isDuplicate(newSong))
			songList.add(newSong);
		//else
			//tell user cannot be done
	}
	public boolean isDuplicate(Song newSong)
	{
		boolean isDuplicate = false;
		for(Song s: songList)
		{
			if(s.equals(newSong))
				isDuplicate = true;
		}
		System.out.println(isDuplicate);
		return isDuplicate;
	}
	
	public static void saveList()
	{
		SongFile.writeFile(songList);
	}
	
	public static void loadList()
	{
		songList = SongFile.readFile();
	}
	
	public static void printList()
	{
		System.out.println("------------------");
		for(Song s : songList)
			System.out.print(s);
	}
	
	public static ArrayList<Song> getList()
	{
		return songList;
	}
}
