package models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import utils.CassandraClient;

import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;

public class TrackFactory 
{
	private Session session;
	PreparedStatement insert;
	PreparedStatement delete;
	PreparedStatement update;
	PreparedStatement select;
	PreparedStatement selectn;

	public TrackFactory()
	{
		this.session = CassandraClient.get().getSession();
		this.insert = this.session.prepare(
			    "INSERT INTO play_cassandra.tracks " +
			    "(id, artist, title, mediaType, mediaTitle, mediaIndex) " +
				"VALUES (?, ?, ?, ?, ?, ?);");
		this.update = this.session.prepare(
			    "UPDATE play_cassandra.tracks " +
			    "SET artist=?, title=?, mediaType=?, mediaTitle=?, mediaIndex=? " +
	    		"WHERE id = ?;");
		this.delete = this.session.prepare(
			    "DELETE FROM play_cassandra.tracks " + 
			    "WHERE id = ?;");
		this.select = this.session.prepare(
			    "SELECT * FROM play_cassandra.tracks " + 
			    "WHERE id = ?;");
		this.selectn = this.session.prepare(
			    "SELECT * FROM play_cassandra.tracks " + 
			    "LIMIT 100;");
	}
	
	public Track create(String artist, String title, String mediaType, String mediaTitle, Integer mediaIndex )
	{
		return new Track(this, artist, title, mediaType, mediaTitle, mediaIndex);
	}

	public ResultSet execute(Statement statement)
	{
		return this.session.execute(statement);
	}
	
	public Track findById(UUID id)
	{
		ResultSet rows = execute(select.bind(id) );		
		return new Track( this, rows.one() );
	}

	public List<Track> findSome()
	{
		List<Track> results = new ArrayList<Track>();
	    ResultSet rows = this.execute(this.selectn.bind());
	    for (Row row : rows) 
	    {
	    	results.add( new Track( this, row ) ); 
	    }
		return results;
	}
}
