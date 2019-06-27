package test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.*;

import modelo.Cliente;
import modelo.ProductoVendido;
import modelo.Sucursal;
import modelo.Venta;

public class Consultas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Instant instant = Instant.parse("2019-05-27T00:00:00.001Z");
		Date desde = Date.from(instant);
		instant = Instant.parse("2019-06-27T00:00:00.001Z");
		Date hasta = Date.from(instant);
		  
		try {
			consulta1(desde, hasta);
			System.in.read();
			consulta2("OSDE", desde, hasta);
			System.in.read();
			consulta3("Tarjeta", desde, hasta);
			System.in.read();
			consulta4(desde, hasta);
			System.in.read();
			consulta5(desde, hasta);
			System.in.read();
			consulta6(desde, hasta);
			System.in.read();
			consulta7(desde, hasta);
			System.in.read();
			consulta8(desde, hasta);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	//1. Detalle y totales de ventas para la cadena completa y por sucursal, entre fechas.
	private static void consulta1(Date desde, Date hasta) {
		System.out.println("\n1. Detalle y totales de ventas para la cadena completa y por sucursal, entre fechas.\n");
		List<Bson> filtros = new ArrayList();
		filtros.add(match(gte("fecha", desde)));
		filtros.add(match(lte("fecha", hasta)));
		ventasxSucursal(filtros, null);
	}
	
	//2. Detalle y totales de ventas para la cadena completa y por sucursal, por obra social o privados entre fechas.
	private static void consulta2(String obraSocial, Date desde, Date hasta) {
		System.out.println("\n2. Detalle y totales de ventas para la cadena completa y por sucursal, por obra social o privados entre fechas.\n");
		List<Bson> filtros = new ArrayList();
		filtros.add(match(gte("fecha", desde)));
		filtros.add(match(lte("fecha", hasta)));
		filtros.add(match(eq("cliente.obraSocial", obraSocial)));
		ventasxSucursal(filtros, "tipo obra social: " + obraSocial);
	}
	
	//3. Detalle y totales de cobranza para la cadena completa y por sucursal, por medio de pago y entre fechas.
	private static void consulta3(String medioPago, Date desde, Date hasta) {
		System.out.println("\n3. Detalle y totales de cobranza para la cadena completa y por sucursal, por medio de pago y entre fechas.\n");
		List<Bson> filtros = new ArrayList();
		filtros.add(match(gte("fecha", desde)));
		filtros.add(match(lte("fecha", hasta)));
		filtros.add(match(eq("formaDePago", medioPago)));
		ventasxSucursal(filtros, "forma de pago: " + medioPago);
	}
	
	//4. Detalle y totales de ventas de productos, total de la cadena y por sucursal, entre fechas, diferenciados entre farmacia y perfumería.
	private static void consulta4(Date desde, Date hasta) {
		System.out.println("\n4. Detalle y totales de ventas de productos, total de la cadena y por sucursal, entre fechas, diferenciados entre farmacia y perfumería.\n");
		List<Bson> filtros = new ArrayList();
		filtros.add(match(gte("fecha", desde)));
		filtros.add(match(lte("fecha", hasta)));
		ventaxProducto(filtros, "Medicamento");
		System.out.println("\n");
		filtros = new ArrayList();
		filtros.add(match(gte("fecha", desde)));
		filtros.add(match(lte("fecha", hasta)));
		ventaxProducto(filtros, "Perfumeria");
	}

	//5. Ranking de ventas de productos, total de la cadena y por sucursal, entre fechas, por monto.
	private static void consulta5(Date desde, Date hasta) {
		System.out.println("\n5. Ranking de ventas de productos, total de la cadena y por sucursal, entre fechas, por monto.\n");
		List<Bson> filtros = new ArrayList();
		filtros.add(match(gte("fecha", desde)));
		filtros.add(match(lte("fecha", hasta)));
		rankingProducto(filtros, "total");
	}
	
	//6. Ranking de ventas de productos, total de la cadena y por sucursal, entre fechas, por cantidad vendida.
	private static void consulta6(Date desde, Date hasta) {
		System.out.println("\n6. Ranking de ventas de productos, total de la cadena y por sucursal, entre fechas, por cantidad vendida.\n");
		List<Bson> filtros = new ArrayList();
		filtros.add(match(gte("fecha", desde)));
		filtros.add(match(lte("fecha", hasta)));
		rankingProducto(filtros, "cantidad");
	}
	
	
	//7. Ranking de clientes por compras, total de la cadena y por sucursal, entre fechas, por monto.
	private static void consulta7(Date desde, Date hasta) {
		System.out.println("\n7. Ranking de clientes por compras, total de la cadena y por sucursal, entre fechas, por monto.\n");
		List<Bson> filtros = new ArrayList();
		filtros.add(match(gte("fecha", desde)));
		filtros.add(match(lte("fecha", hasta)));
		filtros.add(group("$cliente.dni", sum("total","$total"), first("Cliente", "$cliente")));
		rankingCliente(filtros, "monto total");
	}
	
	//8. Ranking de clientes por compras, total de la cadena y por sucursal, entre fechas, por cantidad vendida.
	private static void consulta8(Date desde, Date hasta) {
		System.out.println("\n8. Ranking de clientes por compras, total de la cadena y por sucursal, entre fechas, por cantidad vendida.\n");
		List<Bson> filtros = new ArrayList();
		filtros.add(match(gte("fecha", desde)));
		filtros.add(match(lte("fecha", hasta)));
		filtros.add(group("$cliente.dni", sum("total", 1), first("Cliente", "$cliente")));
		rankingCliente(filtros, "cantidad");
	}
	
	private static void ventaxProducto(List<Bson> filtros, String tipo) {
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase db = mongoClient.getDatabase("test");
		
		MongoCollection<Document> collVentas = db.getCollection("Ventas");
		
		List<Sucursal> sucursales = getSucursales(db);
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		
	    filtros.add(new BasicDBObject("$unwind","$productos"));
		filtros.add(match(eq("productos.producto.tipo", tipo)));
		filtros.add(group("$productos.producto.codigo", first("eproducto","$productos.producto"), push("ecantidad", "$productos.cantidad"), first("eprecio","$productos.precio"), push("etotal", "$productos.total")));
		filtros.add(project(fields(Arrays.asList(new BasicDBObject("cantidad", new BasicDBObject("$sum", "$ecantidad")),new BasicDBObject("precio","$eprecio"), new BasicDBObject("total", new BasicDBObject("$sum", "$etotal")), new BasicDBObject("producto","$eproducto")))));
		float totalCadena = 0;
		for(Sucursal s : sucursales) {
			float totalSucursal = 0;
			try{
				filtros.add(0,match(regex("numeroTicket", String.format("%04d-.*", s.getNumeroTicket()))));
				MongoCursor<Document> curVentas = collVentas.aggregate(filtros).iterator();
				filtros.remove(0);
				System.out.println("Detalle de ventas de productos del tipo " + tipo.toLowerCase() + " en la sucursal " + s.getNumeroTicket());
				try {
					while (curVentas.hasNext()) {
				    	ProductoVendido producto = mapper.readValue(curVentas.next().toJson(), ProductoVendido.class);
				    	System.out.println(producto);
				    	totalSucursal += producto.getTotal();
				    }
				} finally {
					curVentas.close();
					totalCadena += totalSucursal;
					System.out.println("Ventas totales de " + tipo + " en la sucursal " + s.getNumeroTicket() + ": " + totalSucursal + "\n");
				}
		    }
			catch (JsonParseException e) { e.printStackTrace();}
		    catch (JsonMappingException e) { e.printStackTrace(); }
		    catch (Exception e) { e.printStackTrace(); }
		}
		try{
			MongoCursor<Document> curVentas = collVentas.aggregate(filtros).iterator();
			System.out.println("Detalle de ventas de productos del tipo " + tipo.toLowerCase() + " en la cadena");
			try {
			    while (curVentas.hasNext()) {
			    	ProductoVendido producto = mapper.readValue(curVentas.next().toJson(), ProductoVendido.class);
			    	System.out.println(producto);
			    }
			} finally {
				curVentas.close();
				System.out.println("Ventas totales de " + tipo + " en la cadena: " + totalCadena);
			}
		}
		catch (JsonParseException e) { e.printStackTrace();}
	    catch (JsonMappingException e) { e.printStackTrace(); }
	    catch (Exception e) { e.printStackTrace(); }
		
	}
	
	private static void rankingProducto(List<Bson> filtros, String motivo) {
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase db = mongoClient.getDatabase("test");
		
		List<Sucursal> sucursales = getSucursales(db);
		
		filtros.add(new BasicDBObject("$unwind","$productos"));
		filtros.add(group("$productos.producto.codigo", first("eproducto","$productos.producto"), push("ecantidad", "$productos.cantidad"), first("eprecio","$productos.precio"), push("etotal", "$productos.total")));
		filtros.add(project(fields(Arrays.asList(new BasicDBObject("cantidad", new BasicDBObject("$sum", "$ecantidad")),new BasicDBObject("precio","$eprecio"), new BasicDBObject("total", new BasicDBObject("$sum", "$etotal")), new BasicDBObject("producto","$eproducto")))));
		filtros.add(sort(new Document(motivo, -1)));
		
		for(Sucursal s : sucursales) {
			System.out.println("Ranking de productos por " + motivo + " de compras en la sucursal " + s.getNumeroTicket());
			filtros.add(0, match(regex("numeroTicket", String.format("%04d-.*", s.getNumeroTicket()))));
			imprimirRankingProducto(db, filtros);
			filtros.remove(0);
			System.out.println("");
		}
		System.out.println("Ranking de productos por " + motivo + " de compras en la cadena");
		imprimirRankingProducto(db, filtros);
	}
	
	private static void imprimirRankingProducto(MongoDatabase db, List<Bson> filtros){
		MongoCollection<Document> collVentas = db.getCollection("Ventas");
			
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    
	    try{
			MongoCursor<Document> curVentas = collVentas.aggregate(filtros).iterator();
			try {
			    while (curVentas.hasNext()) {
			    	ProductoVendido producto = mapper.readValue(curVentas.next().toJson(), ProductoVendido.class);
			    	System.out.println(producto);
			    }
			} finally {
				curVentas.close();
			}
	    }
		catch (JsonParseException e) { e.printStackTrace();}
	    catch (JsonMappingException e) { e.printStackTrace(); }
	    catch (Exception e) { e.printStackTrace(); }
	}
	
	private static void rankingCliente(List<Bson> filtros, String motivo) {
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase db = mongoClient.getDatabase("test");
		
		List<Sucursal> sucursales = getSucursales(db);
		
		for(Sucursal s : sucursales) {
			System.out.println("Ranking de clientes por " + motivo + " de compras de la sucursal " + s.getNumeroTicket());
			filtros.add(0, match(regex("numeroTicket", String.format("%04d-.*", s.getNumeroTicket()))));
			imprimirRankingCliente(db, filtros);
			filtros.remove(0);
			System.out.println("");
		}
		System.out.println("Ranking de clientes por " + motivo + " de compras de la cadena");
		imprimirRankingCliente(db, filtros);
	}
	
	private static void imprimirRankingCliente(MongoDatabase db, List<Bson> filtros){
		MongoCollection<Document> collVentas = db.getCollection("Ventas");
			
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    
	    try{
	    	filtros.add(sort(new Document("total", -1)));
			filtros.add(project(fields(excludeId())));
			MongoCursor<Document> curVentas = collVentas.aggregate(filtros).iterator();
			try {
			    while (curVentas.hasNext()) {
			    	Document aux = curVentas.next();
			    	Cliente cliente = mapper.readValue(((Document) aux.get("Cliente")).toJson(), Cliente.class);
			    	System.out.println("Total: " + round(Float.parseFloat(aux.get("total").toString()), 2) + " " + cliente);
			    }
			} finally {
				curVentas.close();
			}
	    }
		catch (JsonParseException e) { e.printStackTrace();}
	    catch (JsonMappingException e) { e.printStackTrace(); }
	    catch (Exception e) { e.printStackTrace(); }
	}
	
	private static List<Sucursal> getSucursales(MongoDatabase db){
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
	    }
		catch (JsonParseException e) { e.printStackTrace();}
	    catch (JsonMappingException e) { e.printStackTrace(); }
	    catch (Exception e) { e.printStackTrace(); }
		return sucursales;
	}

	private static void ventasxSucursal(List<Bson> filtros, String motivo) {
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase db = mongoClient.getDatabase("test");
		
		MongoCollection<Document> collVentas = db.getCollection("Ventas");
		
		List<Venta> ventas = new ArrayList();
		List<Sucursal> sucursales = getSucursales(db);
		
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    
	    filtros.add(project(fields(excludeId())));
	    float totalCadena = 0;
		for(Sucursal s : sucursales) {
			float totalSucursal = 0;
			filtros.add(match(regex("numeroTicket", String.format("%04d-.*", s.getNumeroTicket()))));
			System.out.println("Detalle de ventas de la sucursal " + s.getNumeroTicket() + ((motivo!=null) ? (" por " + motivo) : ("")));
			try{
				MongoCursor<Document> curVentas = collVentas.aggregate(filtros).iterator();
				filtros.remove(filtros.size()-1);
				try {
				    while (curVentas.hasNext()) {
				    	Venta venta = mapper.readValue(curVentas.next().toJson(), Venta.class);
				    	System.out.println(venta);
				    	totalSucursal += venta.getTotal();
				    	ventas.add(venta);
				    }
				} finally {
					curVentas.close();
					System.out.println("Ventas totales de la sucursal " + s.getNumeroTicket() + ": " + totalSucursal + "\n");
					totalCadena += totalSucursal;
				}
			} 
			catch (JsonParseException e) { e.printStackTrace();}
		    catch (JsonMappingException e) { e.printStackTrace(); }
		    catch (Exception e) { e.printStackTrace(); }
		}
		System.out.println("Detalle de ventas de la cadena" + ((motivo!=null) ? (" por " + motivo) : ("")));
		for(Venta v: ventas) {
			System.out.println(v);
		}
		System.out.println("Ventas totales de la cadena: " + totalCadena);
	}
	
	public static Object round(float d, int decimalPlace) {
		if(d%1 > 0) {
			BigDecimal bd = new BigDecimal(Float.toString(d));
		    bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		    return bd.floatValue();
		}
	    return (int)d;
	}
}


