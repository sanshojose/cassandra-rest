package models;

import java.util.UUID;

import com.datastax.driver.core.Row;

public class Track {
	private TrackFactory factory;
	public UUID		id;
	public String	artist;
	public String	title;
	public String	mediaType;
	public String	mediaTitle;
	public Integer	mediaIndex;
	
	Track( TrackFactory factory) 
	{
		this.factory = factory;
	}

	Track( TrackFactory factory, UUID id, String artist, String title, String mediaType, String mediaTitle, Integer mediaIndex) 
	{
		this.factory = factory;
		this.id = id;
	    this.artist = artist;
	    this.title = title;
	    this.mediaType = mediaType;
	    this.mediaTitle = mediaTitle;
	    this.mediaIndex = mediaIndex;		
	}

	Track( TrackFactory factory, String artist, String title, String mediaType, String mediaTitle, Integer mediaIndex) 
	{
		this.factory = factory;
		this.id = UUID.randomUUID();
	    this.artist = artist;
	    this.title = title;
	    this.mediaType = mediaType;
	    this.mediaTitle = mediaTitle;
	    this.mediaIndex = mediaIndex;		
	}
	Track(TrackFactory factory, Row row)
	{
		this.factory = factory;
		this.id = row.getUUID("id");
	    this.artist = row.getString("artist");
	    this.title = row.getString("title");
	    this.mediaType = row.getString("mediaType");
	    this.mediaTitle = row.getString("mediaTitle");
	    this.mediaIndex = row.getInt("mediaIndex");		
	}
	public void save()
	{
		factory.execute(factory.insert.bind(
			      this.id,
			      this.artist,
			      this.title,
			      this.mediaType,
			      this.mediaTitle,
			      this.mediaIndex ) );		
	}
	
	public void delete()
	{
		
	}
	
	public void update( Track newTrack) 
	{
	}
	public String toString()
	{
		StringBuffer result = new StringBuffer();
		result.append(this.id);
		result.append(" ");
		result.append(this.artist);
		result.append(" ");
		result.append(this.title);
		result.append(" ");
		result.append(this.mediaType);
		result.append(":");
		result.append(this.mediaTitle);
		result.append(":");
		result.append(this.mediaIndex);
		return result.toString();
	}
}
