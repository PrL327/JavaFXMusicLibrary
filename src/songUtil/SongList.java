package songUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class SongList
{
	static ArrayList<Song> songList = new ArrayList<>();
	Comparator<Song> songComparer = Comparator.comparing(Song::getName, String.CASE_INSENSITIVE_ORDER)
	          .thenComparing(Song::getArtist, String.CASE_INSENSITIVE_ORDER);
	private static ArrayList<String> shrtList;
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
	
	public static ArrayList<String> getShrtList()
	{
		shrtList = new ArrayList<>();
		for(Song s: songList)
		{
			shrtList.add(s.getName() + " " + s.getArtist());
		}
		return shrtList;
	}
}
