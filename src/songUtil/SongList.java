package songUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SongList
{
	//main list of songs
	private static ArrayList<Song> songList = new ArrayList<>();
	//Comparator
	Comparator<Song> songComparer = Comparator.comparing(Song::getName, String.CASE_INSENSITIVE_ORDER)
	          .thenComparing(Song::getArtist, String.CASE_INSENSITIVE_ORDER);
	
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
	
	public static void setList(ArrayList<Song> arr)
	{
		songList = arr;
		saveList();
	}
}
