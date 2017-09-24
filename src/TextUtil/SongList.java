package TextUtil;

import java.util.ArrayList;

public class SongList
{
	static ArrayList<Song> songList = new ArrayList<>();
	
	public void addSong(String newName, String newArtist, String newAlbum, int newYear)
	{
		Song newSong = new Song(newName, newArtist, newAlbum, newYear);
		if(!isDuplicate(newSong))
			songList.add(newSong);
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
	
}
