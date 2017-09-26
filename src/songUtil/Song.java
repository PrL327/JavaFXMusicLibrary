package songUtil;

public class Song
{
	private String name, artist, album; 
	private int year;
	
	//name, artist, album, and year
	/**
	 * @param newName
	 * @param newArtist
	 * @param newAlbum
	 * @param newYear
	 */
	public Song(String newName, String newArtist, String newAlbum, int newYear) 
	{
		name = newName;
		year = newYear;
		artist = newArtist;
		album = newAlbum;
	}
	/**
	 * @return
	 */
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	/**
	 * Song's equal method
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj == null || obj instanceof Song)
			return false;
		else
		{
			Song newSong = (Song) obj;
			if(this.getName().equalsIgnoreCase(newSong.getName()) && this.getArtist().equalsIgnoreCase(newSong.getArtist()))
				return true;
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "Song name: " + this.name +
				"\nArtist: "+ this.artist +
				"\nAlbum: "+ this.album +
				"\nYear: "+ this.year + "\n------------------\n";
	}
}
