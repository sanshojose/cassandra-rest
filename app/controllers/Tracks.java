package controllers;

import java.util.List;
import java.util.UUID;

import play.libs.Json;
import play.mvc.*;
import play.libs.F.*;
import views.html.*;
import models.Track;
import models.TrackFactory;

public class Tracks extends Controller
{
	private static TrackFactory factory = new TrackFactory();
	
	public static Promise<Result> index() {
		// we do this as the data access layer uses blocking calls.
  	  	Promise<List<Track>> findSome = Promise.promise(
      	    new Function0<List<Track>>() {
      	      public List<Track> apply() {
      	        return factory.findSome();
      	      }
      	    }
      	  );
  	  	return findSome.map(
      	    new Function<List<Track>, Result>() {
      	      public Result apply(List<Track> t) {
                  return ok(tracks.render(t));
      	      }
      	    }
      	  );        
	}

	public static Result create() {
		Track newTrack = Json.fromJson(request().body().asJson(), Track.class);
	    newTrack.save();
		return ok(track.render(newTrack));
	}

	public static Promise<Result> update(String id) {
		Track dbTrack = factory.findById(UUID.fromString(id));
		Track newTrack = Json.fromJson(request().body().asJson(), Track.class);
	    dbTrack.update(newTrack); // some model logic you would write to do a safe merge
	    dbTrack.save();
	    return show(id);
	}

	public static Result delete(String id) {
		factory.findById(UUID.fromString(id)).delete();
		return noContent();
	}

	public static Promise<Result> show(final String id)  {
		// we do this as the data access layer uses blocking calls.
	  	Promise<Track> find = Promise.promise(
	        	    new Function0<Track>() {
	        	      public Track apply() {
	        	        return factory.findById(UUID.fromString(id));
	        	      }
	        	    }
	        	  );
	  	return find.map(
	        	    new Function<Track, Result>() {
	        	      public Result apply(Track t) {
	                    return ok(track.render(t));
	        	      }
	        	    }
	        	  );        
	}
}
