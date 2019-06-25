package test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.*;

import modelo.Cliente;
import modelo.Producto;
import modelo.Sucursal;
import modelo.Venta;

public class Consultas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Consulta1(new GregorianCalendar(), new GregorianCalendar());
		//Consulta2("OSDE", new GregorianCalendar(), new GregorianCalendar());
		//Consulta3("Tarjeta", new GregorianCalendar(), new GregorianCalendar());
		//Consulta4(new GregorianCalendar(), new GregorianCalendar());
		Consulta7(); 
		Consulta8();
	}
	
	
	//1. Detalle y totales de ventas para la cadena completa y por sucursal, entre fechas.
	private static void Consulta1(GregorianCalendar desde, GregorianCalendar hasta) {
		List<Bson> filtros = new ArrayList();
		//filtros.add(Filters.gte("fecha", desde));
		//filtros.add(Filters.lte("fecha", hasta));
		VentasxSucursal(filtros);
	}
	
	//2. Detalle y totales de ventas para la cadena completa y por sucursal, por obra social o privados entre fechas.
	private static void Consulta2(String obraSocial, GregorianCalendar desde, GregorianCalendar hasta) {
		List<Bson> filtros = new ArrayList();
		//filtros.add(Filters.gte("fecha", desde));
		//filtros.add(Filters.lte("fecha", hasta));
		filtros.add(Filters.eq("cliente.obraSocial", obraSocial));
		VentasxSucursal(filtros);
	}
	
	//3. Detalle y totales de cobranza para la cadena completa y por sucursal, por medio de pago y entre fechas.
	private static void Consulta3(String medioPago, GregorianCalendar desde, GregorianCalendar hasta) {
		List<Bson> filtros = new ArrayList();
		//filtros.add(Filters.gte("fecha", desde));
		//filtros.add(Filters.lte("fecha", hasta));
		filtros.add(Filters.eq("formaDePago", medioPago));
		VentasxSucursal(filtros);
	}
	
	//4. Detalle y totales de ventas de productos, total de la cadena y por sucursal, entre fechas, diferenciados entre farmacia y perfumería.	
	//5. Ranking de ventas de productos, total de la cadena y por sucursal, entre fechas, por monto.
	//6. Ranking de ventas de productos, total de la cadena y por sucursal, entre fechas, por cantidad vendida.
	
	//7. Ranking de clientes por compras, total de la cadena y por sucursal, entre fechas, por monto.
	private static void Consulta7() {
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase db = mongoClient.getDatabase("test");
		
		MongoCollection<Document> collSucursales = db.getCollection("Sucursales");
		
		List<Sucursal> sucursales = new ArrayList();
		
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    
	    try{
			MongoCursor<Document> curSucursales = collSucursales.find().iterator();
			try {
			    while (curSucursales.hasNext()) {
			    	sucursales.add(mapper.readValue(curSucursales.next().toJson(), Sucursal.class));
			    }
			} finally {
			    curSucursales.close();
			}
			Bson group = group("$cliente.dni", sum("total","$total"), first("Cliente", "$cliente"));
			for(Sucursal s : sucursales) {
				System.out.println("Sucursal " + s.getNumeroTicket());
				Bson match = match(Filters.regex("numeroTicket", String.format("%04d-.*", s.getNumeroTicket())));
				RankingCliente(db, match, group);
				System.out.println("");
			}
			System.out.println("Por cadena");
			Bson match = match(Filters.gte("total", 0));
			RankingCliente(db, match, group);
				
			
	    }
		catch (JsonParseException e) { e.printStackTrace();}
	    catch (JsonMappingException e) { e.printStackTrace(); }
	    catch (Exception e) { e.printStackTrace(); }
	}
	
	//8. Ranking de clientes por compras, total de la cadena y por sucursal, entre fechas, por cantidad vendida.
	private static void Consulta8() {
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase db = mongoClient.getDatabase("test");
		
		MongoCollection<Document> collSucursales = db.getCollection("Sucursales");
		
		List<Sucursal> sucursales = new ArrayList();
		
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    
	    try{
			MongoCursor<Document> curSucursales = collSucursales.find().iterator();
			try {
			    while (curSucursales.hasNext()) {
			    	sucursales.add(mapper.readValue(curSucursales.next().toJson(), Sucursal.class));
			    }
			} finally {
			    curSucursales.close();
			}
			Bson group = group("$cliente.dni", sum("total", 1), first("Cliente", "$cliente"));
			for(Sucursal s : sucursales) {
				System.out.println("Sucursal " + s.getNumeroTicket());
				Bson match = match(Filters.regex("numeroTicket", String.format("%04d-.*", s.getNumeroTicket())));
				RankingCliente(db, match, group);
				System.out.println("");
			}
			System.out.println("Por cadena");
			Bson match = match(Filters.gte("total", 0));
			RankingCliente(db, match, group);
				
			
	    }
		catch (JsonParseException e) { e.printStackTrace();}
	    catch (JsonMappingException e) { e.printStackTrace(); }
	    catch (Exception e) { e.printStackTrace(); }
	}
	
	private static void RankingCliente(MongoDatabase db, Bson match, Bson group){
		MongoCollection<Document> collVentas = db.getCollection("Ventas");
		Bson sort = sort(new Document("total", -1));
		Bson projection = project(Projections.fields(Projections.excludeId()));
		MongoCursor<Document> curVentas = collVentas.aggregate(Arrays.asList(match, group, sort, projection)).iterator();
		try {
		    while (curVentas.hasNext()) {
				System.out.println(curVentas.next().toJson());
		    }
		} finally {
			curVentas.close();
		}
	}
	
	private static void VentasxSucursal(List<Bson> filtros) {
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase db = mongoClient.getDatabase("test");
		
		MongoCollection<Document> collSucursales = db.getCollection("Sucursales");
		MongoCollection<Document> collVentas = db.getCollection("Ventas");
		
		
		List<Sucursal> sucursales = new ArrayList();
		
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    
	    try{
			MongoCursor<Document> curSucursales = collSucursales.find().iterator();
			try {
			    while (curSucursales.hasNext()) {
			    	sucursales.add(mapper.readValue(curSucursales.next().toJson(), Sucursal.class));
			    }
			} finally {
			    curSucursales.close();
			}
			
			for(Sucursal s : sucursales) {
				List<Venta> ventas = new ArrayList();
				filtros.add(Filters.regex("numeroTicket", String.format("%04d-.*", s.getNumeroTicket())));
				MongoCursor<Document> curVentas = collVentas.find(Filters.and(filtros)).projection(Projections.exclude("fecha")).iterator();
				filtros.remove(filtros.size()-1);
				try {
				    while (curVentas.hasNext()) {
				    	ventas.add(mapper.readValue(curVentas.next().toJson(), Venta.class));
				    }
				} finally {
					curVentas.close();
					s.setVentas(ventas);
				}
			}
			float totalCadena = 0;
			for(Sucursal s : sucursales) {
				float totalSucursal = 0;
				System.out.println("Detalle de ventas de la sucursal " + s.getNumeroTicket());
				for(Venta v : s.getVentas()) {
					System.out.println(v);
					totalSucursal += v.getTotal();
				}
				System.out.println("Ventas totales de la sucursal " + s.getNumeroTicket() + ": " + totalSucursal + "\n");
				totalCadena += totalSucursal;
			}
			System.out.println("Ventas totales de la cadena: " + totalCadena);
			
	    }
		catch (JsonParseException e) { e.printStackTrace();}
	    catch (JsonMappingException e) { e.printStackTrace(); }
	    catch (Exception e) { e.printStackTrace(); }
	}
	
	
}
