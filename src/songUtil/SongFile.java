/*
 * By Daniel Fraser and Peter Laskai
 */
package songUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SongFile 
{
	/**
	 * writes array of Song objects to the text file
	 * @param songList
	 */
	public static void writeFile(ArrayList<Song> songList)
	{
		try {
			Files.write(Paths.get("songFile.txt"), "".getBytes());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		for(Song s : songList)
		{
			String newEntry =  "Song Name: " + s.getName() + " \n"
					+ "Artist: " + s.getArtist() + " \n"
					+ "Album: " + s.getAlbum() + " \n"
					+ "Year: " + s.getYear() + " \n"
					+ "----------------------------------" + " \n";
			try {
				Files.write(Paths.get("songFile.txt"), newEntry.getBytes(), StandardOpenOption.APPEND);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * reads in from text file and converts it to arraylist of Song objects
	 */
	public static ArrayList<Song> readFile()
	{
		String fileName = "songFile.txt";
		ArrayList<String> list = new ArrayList<>();
		ArrayList<Song> songList = new ArrayList<>();
		
		try (BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {

			list = (ArrayList<String>) br.lines().collect(Collectors.toList());

		} catch (IOException e) {
			e.printStackTrace();
		}
		String songInfo = "";
		Pattern r;
		Matcher m;
		String pattern = "Name: (\\w+) Artist: (\\w+) Album: (\\w*) Year: (\\d*)";
		for(int i = 0; i < list.size(); i += 5)
		{
			songInfo = list.get(i) + list.get(i+1) + list.get(i+2) + list.get(i+3);
			r = Pattern.compile(pattern);
			m = r.matcher(songInfo);
			
			if (m.find())
			{
				songList.add(new Song(m.group(1),m.group(2),m.group(3),m.group(4)));
			}
		}
		return songList;
	}
}
