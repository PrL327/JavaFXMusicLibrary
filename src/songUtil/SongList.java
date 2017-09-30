/*
 * By Daniel Fraser and Peter Laskai
 */
package songUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SongList
{
	/**
	 * main list of songs
	 */
	private static ArrayList<Song> songList = new ArrayList<>();
	
	/**
	 * save list to file
	 */
	public static void saveList()
	{
		SongFile.writeFile(songList);
	}
	
	/**
	 * load list from file
	 */
	public static void loadList()
	{
		songList = SongFile.readFile();
	}
	
	/**
	 * prints arraylist (used for debug purposes)
	 */
	public static void printList()
	{
		System.out.println("------------------");
		for(Song s : songList)
			System.out.print(s);
	}
	/**
	 * gets list of song objects
	 */
	public static ArrayList<Song> getList()
	{
		return songList;
	}
	/**
	 * sets list of array
	 * @param arr
	 */
	public static void setList(ArrayList<Song> arr)
	{
		songList = arr;
		saveList();
	}
}
