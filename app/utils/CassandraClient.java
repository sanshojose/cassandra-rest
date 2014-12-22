package utils;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

public class CassandraClient {
	// singleton
	private static CassandraClient _instance = null;

   private Cluster cluster;
   private Session session;

   private CassandraClient()
   {
   }

   // singleton accessor
   public static CassandraClient get()
   {
	   if (_instance == null) 
	   {
		   _instance = new CassandraClient();
	   }
	   return _instance;
   }
   
   public void connect(String node) 
   {
      cluster = Cluster.builder()
            .addContactPoint(node).build();
      session = cluster.connect();
   }

   public void close() {
      cluster.shutdown();
   }
   
   public Session getSession() 
   {
	   return this.session;
   }
   
   public void diagnostics() 
   {
      Metadata metadata = cluster.getMetadata();
      System.out.printf("Connected to cluster: %s\n", 
            metadata.getClusterName());
      for ( Host host : metadata.getAllHosts() ) 
      {
         System.out.printf("Datatacenter: %s; Host: %s; Rack: %s\n",
               host.getDatacenter(), host.getAddress(), host.getRack());
      }
   }

   public static void main(String[] args) {

      CassandraClient client = CassandraClient.get();
      client.connect("127.0.0.1");
      client.diagnostics();
      client.close();
   }
}