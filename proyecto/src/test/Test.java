package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import modelo.*;

public class Test {

	public static void main(String[] args) {
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase db = mongoClient.getDatabase("test");
		
		System.out.println("conectado");
		
		MongoCollection<Document> collection = db.getCollection("Clientes");
		
		List<Cliente> clientes = new ArrayList();
		
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    
		try{
			MongoCursor<Document> cursor = collection.find().iterator();
			try {
			    while (cursor.hasNext()) {
			    	clientes.add(mapper.readValue(cursor.next().toJson(), Cliente.class));
			    }
			} finally {
			    cursor.close();
			}
			for(Cliente c : clientes) {
				System.out.println(c);
			}
	    }
		catch (JsonParseException e) { e.printStackTrace();}
	    catch (JsonMappingException e) { e.printStackTrace(); }
	    catch (Exception e) { e.printStackTrace(); }
	  
		

	}
}
