import play.*;
import play.libs.*;
import utils.CassandraClient;

import com.avaje.ebean.Ebean;

import models.*;

import java.util.*;

public class Global extends GlobalSettings {
    @Override
    public void onStart(Application app) {
    	CassandraClient client = CassandraClient.get();
		client.connect("127.0.0.1");
    }
}