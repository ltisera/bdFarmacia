package test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import modelo.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bson.Document;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CrearDatos {
	public static void main(String[] args) {
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			
			MongoClient mongoClient = MongoClients.create();
			MongoDatabase db = mongoClient.getDatabase("test");

			MongoCollection<Document> collectionCliente = db.getCollection("Clientes");
			MongoCollection<Document> collectionSucursal = db.getCollection("Sucursales");
			MongoCollection<Document> collectionEmpleado = db.getCollection("Empleados");
			
			List<String> nombres = new ArrayList<>(Arrays.asList("Alejandra", "Alex", "Candela", "Cristian", "Delia", "Diego", "Eugenia", "Fabio", "Federico", "Gabriel", "Juan", "Juana", "Jose", "Leandro", "Luis", "Marcos", "Oscar", "Pedro", "Rita", "Sandra", "Tomas", "Valeria"));
			List<String> apellidos = new ArrayList<>(Arrays.asList("Alonso", "Alvarez", "Blanco", "Diaz", "Fernandez", "Garcia", "Gomez", "Gonzales", "Gutierrez", "Hernandez", "Jimenez", "Lopez", "Martinez", "Moreno", "Muñoz", "Perez", "Rodriguez", "Ruiz", "Sanchez", "Santos", "Torres", "Vazquez"));
			
			
			int clientes = 10;
			int sucursales = 3;
			int empleadosXsucursal = 3;
			
			Random rand = new Random(); 
			
 
			// ----- Clientes -----
			System.out.print("Cargando clientes... ");
			for(int i = 0; i < clientes; i ++) {
				String apellido = apellidos.remove(rand.nextInt(apellidos.size()));
				String nombre = nombres.remove(rand.nextInt(nombres.size()));
				int dni = 40805000 + (i * 10) + i;
				Domicilio dom = new Domicilio("calle" + i, 110 + i, "Localidad", "Buenos Aires");
				String obraSocial = "Osde";
				int nroAfiliado = i;
				Cliente cliente = new Cliente(apellido, nombre, dni, dom, obraSocial, nroAfiliado);
				Document doc = Document.parse(mapper.writeValueAsString(cliente));
				collectionCliente.insertOne(doc);
			}
			System.out.print("completado.\n");
			
			
			// ----- Sucursales y Empleados-----
			System.out.print("Cargando sucursales y empleados... ");
			for(int j = 0; j < sucursales; j ++) {
				Domicilio domic = new Domicilio("Av " + j, 40 + j, "Localidad", "Buenos Aires");
				List<Empleado> empleados = new ArrayList();
				Empleado encargado = null;
				int nroTicket = j;
				for(int i = 0; i < empleadosXsucursal; i ++) {
					String apellido = apellidos.remove(rand.nextInt(apellidos.size()));
					String nombre = nombres.remove(rand.nextInt(nombres.size()));
					int dni = 56784000 + (i * 10) + i + j;
					long cuil = 270000000000L + (dni * 100L);
					Domicilio dom = new Domicilio("calle falsa " + i, 230 + i, "Localidad", "Buenos Aires");
					String obraSocial = "Pami";
					int nroAfiliado = clientes + i + (j * 10);
					
					Empleado empleado = new Empleado(apellido, nombre, dni, dom, obraSocial, nroAfiliado, cuil);
					Document doc = Document.parse(mapper.writeValueAsString(empleado));
					collectionEmpleado.insertOne(doc);
					
					if (i==0) {
						encargado = empleado;
					}
					else {
						empleados.add(empleado);
					}
				}
				Sucursal sucursal = new Sucursal(domic, empleados, encargado, nroTicket);
				Document doc = Document.parse(mapper.writeValueAsString(sucursal));
				collectionSucursal.insertOne(doc);
			}
			System.out.print("completado.\n");
		}
		catch (JsonParseException e) { e.printStackTrace();}
	    catch (JsonMappingException e) { e.printStackTrace(); }
	    catch (Exception e) { e.printStackTrace(); }
	}
}
