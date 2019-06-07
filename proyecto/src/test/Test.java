package test;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import modelo.*;

public class Test {

	public static void main(String[] args) {
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase db = mongoClient.getDatabase("test");
		
		System.out.println("conectado");
		
		traerClientesQueContengan(db, "a");
		contVentasXSucursal(db);
		
		

	}
	
	public static void contVentasXSucursal(MongoDatabase db) {
		MongoCollection<Document> collection = db.getCollection("Sucursales");
		
		List<Sucursal> sucursales = new ArrayList();
		
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    
	    try{
			MongoCursor<Document> cursor = collection.find().iterator();
			try {
			    while (cursor.hasNext()) {
			    	sucursales.add(mapper.readValue(cursor.next().toJson(), Sucursal.class));
			    }
			} finally {
			    cursor.close();
			}
			collection = db.getCollection("Ventas");
			for(Sucursal s : sucursales) {
				System.out.print("\nSucursal " + s.getNumeroTicket() + " vendio: ");
				Bson filtro = Filters.regex("numeroTicket", String.format("%04d-.*", s.getNumeroTicket()));
				System.out.print(collection.countDocuments(filtro));
			}
	    }
		catch (JsonParseException e) { e.printStackTrace();}
	    catch (JsonMappingException e) { e.printStackTrace(); }
	    catch (Exception e) { e.printStackTrace(); }
	}
	
	public static void traerClientesQueContengan(MongoDatabase db, String str) {	
		MongoCollection<Document> collection = db.getCollection("Clientes");
		
		List<Cliente> clientes = new ArrayList();
		
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    
		try{
			MongoCursor<Document> cursor = collection.find(Filters.regex("nombre", ".*" + str + ".*")).iterator();
			try {
			    while (cursor.hasNext()) {
			    	clientes.add(mapper.readValue(cursor.next().toJson(), Cliente.class));
			    }
			} finally {
			    cursor.close();
			}
			System.out.println("");
			for(Cliente c : clientes) {
				System.out.println(c);
			}
	    }
		catch (JsonParseException e) { e.printStackTrace();}
	    catch (JsonMappingException e) { e.printStackTrace(); }
	    catch (Exception e) { e.printStackTrace(); }
	}
}
