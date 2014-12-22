
import models.Track;
import models.TrackFactory;
import utils.CassandraClient;

public class Import 
{
	private CassandraClient client;
	private TrackFactory factory;

	public static void main(String[] args) 
	{
		Import importer = new Import();
		importer.init();
		importer.diagnostics();
		importer.createSchema();
        importer.setUpTrackFactory();
		importer.load_singles();
		importer.load_albums();
	    for (Track track : importer.factory.findSome())
	    {
			System.out.printf("Track %s\n", track ); 
	    }
		importer.done();
	}

	private void init()
	{
		this.client = CassandraClient.get();
		this.client.connect("127.0.0.1");
	}

    private void setUpTrackFactory() {
        this.factory = new TrackFactory();
    }
	private void done()
	{
		this.client.close();
		this.client = null;
	}

	private void createSchema() 
	{
		String keyspace = "play_cassandra";

		client.getSession().execute("DROP KEYSPACE " + keyspace);
			   System.out.println("Finished dropping " + keyspace + " keyspace.");

		client.getSession().execute("CREATE KEYSPACE " + keyspace + " WITH replication " + 
			      "= {'class':'SimpleStrategy', 'replication_factor':3};");

		client.getSession().execute(
			      "CREATE TABLE " + keyspace + ".tracks (" +
			            "id UUID PRIMARY KEY," + 
			            "artist text," + 
			            "title text," + 
			            "mediaType text," + 
			            "mediaTitle text," + 
			            "mediaIndex int," + 
			            ");");
	}

	private static String unquote(String s)
	{
		if (s != null
			   && ((s.startsWith("\"") && s.endsWith("\""))
			   || (s.startsWith("'") && s.endsWith("'")))) 
		{
			  s = s.substring(1, s.length() - 1);
		}
		return s;		
	}

    private void load_singles()
    {
        int count = 0;
        try{
        //String artist, String title, String mediaType, String mediaTitle, Integer mediaIndex
            for(count = 1;count <= 10;count++ ) {
                factory.create("Artist"+count, "Album"+count, "single", "Single"+count, 1).save();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.printf("Loaded %s singles\n", count-1 );
    }

	private void load_albums()
	{
		
	}
	
	private void diagnostics() 
	{
		this.client.diagnostics();
	}

}
