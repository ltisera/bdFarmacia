package test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import modelo.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
			MongoCollection<Document> collectionProducto = db.getCollection("Productos");
			MongoCollection<Document> collectionVenta = db.getCollection("Ventas");
			
			List<String> nombres = new ArrayList<>(Arrays.asList("Alejandra", "Alex", "Candela", "Cristian", "Delia", "Diego", "Eugenia", "Fabio", "Federico", "Gabriel", "Juan", "Juana", "Jose", "Leandro", "Luis", "Marcos", "Oscar", "Pedro", "Rita", "Sandra", "Tomas", "Valeria"));
			List<String> apellidos = new ArrayList<>(Arrays.asList("Alonso", "Alvarez", "Blanco", "Diaz", "Fernandez", "Garcia", "Gomez", "Gonzales", "Gutierrez", "Hernandez", "Jimenez", "Lopez", "Martinez", "Moreno", "Muñoz", "Perez", "Rodriguez", "Ruiz", "Sanchez", "Santos", "Torres", "Vazquez"));
			
			List<Sucursal> lstSucursales = new ArrayList();
			List<Producto> lstProductos = new ArrayList();
			
			int clientes = 10;
			int sucursales = 3;
			int empleadosXsucursal = 3;
			int medicamentos = 7;
			int perfumeria = 3;
			int ventasXsucursal = 35;
			int variacion = 20; //En porcentaje
			variacion = (int) ((ventasXsucursal * variacion) / 100);
			int productosXventa = 2;
			
			Random rand = new Random();
			
 
			// --------------- Clientes ---------------
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
			
			
			// --------------- Sucursales y Empleados ---------------
			System.out.print("Cargando sucursales y empleados... ");
			for(int j = 0; j < sucursales; j ++) {
				Domicilio domic = new Domicilio("Av " + j, 40 + j, "Localidad", "Buenos Aires");
				List<Empleado> lstEmpleados = new ArrayList();
				Empleado encargado = null;
				int nroTicket = j + 1;
				for(int i = 0; i < empleadosXsucursal; i ++) {
					String apellido = apellidos.remove(rand.nextInt(apellidos.size()));
					String nombre = nombres.remove(rand.nextInt(nombres.size()));
					int dni = 56784000 + (i * 10) + i + j;
					String cuil = "20-" + (dni * 100L) + "-0";
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
						lstEmpleados.add(empleado);
					}
				}
				Sucursal sucursal = new Sucursal(domic, lstEmpleados, encargado, nroTicket);
				lstSucursales.add(sucursal);
				Document doc = Document.parse(mapper.writeValueAsString(sucursal));
				collectionSucursal.insertOne(doc);
			}
			System.out.print("completado.\n");
			
			
			// --------------- Medicamentos y Perfumeria --------------------
			System.out.print("Cargando medicamentos y perfumeria... ");
			for(int i = 0; i < medicamentos + perfumeria; i ++) {
				int codigo = i;
				String desc = "Perfume" + i;
				String tipo = "Perfumeria";
				if (i < medicamentos) {
					desc = "Medicamento" + i;
					tipo = "Medicamento";
				}
				String laboratorio = "Lab";
				float precio = round((float) (rand.nextInt(150) + rand.nextDouble() + 50), 2);
				Producto producto = new Producto(tipo, desc, laboratorio, codigo, precio);
				lstProductos.add(producto);
				Document doc = Document.parse(mapper.writeValueAsString(producto));
				collectionProducto.insertOne(doc);
			}
			System.out.print("completado.\n");
			
			
			// --------------- Ventas ---------------
			
			for(Sucursal s : lstSucursales) {
				System.out.print("Cargando ventas susursal "+ s.getNumeroTicket() +"... ");
				for(int i = 0; i < (ventasXsucursal - variacion + rand.nextInt((variacion * 2) + 1)); i++) {
					Empleado empleadoAtendio = s.getEmpleados().get(rand.nextInt(s.getEmpleados().size()));
					Empleado empleadoCobro = s.getEmpleados().get(rand.nextInt(s.getEmpleados().size()));
					LocalDateTime fecha = LocalDateTime.now();
					String formaDePago = "";
					int opc = rand.nextInt(3);
					if(opc == 0) {
						formaDePago = "Efectivo";
					}else if (opc == 1) {
						formaDePago = "Tarjeta";
					}else {
						formaDePago = "Débito";
					}
					int nroTicket = i + 1;
					
					List<ProductoVendido> lstProductoVendido = new ArrayList();
					for(int j = 0; j < rand.nextInt(productosXventa + 3) + 1; j++) {
						Producto producto = lstProductos.get(rand.nextInt(s.getEmpleados().size()));
						boolean estaEnLista = false;
						int n = 0;
						while(j>0 && n < lstProductoVendido.size() && !estaEnLista) {
							if (producto.equals(lstProductoVendido.get(n).getProducto())) {
								lstProductoVendido.get(n).setCantidad(lstProductoVendido.get(n).getCantidad()+1);
								estaEnLista = true;
							}
							n++;
						}
						if (!estaEnLista) {
							int cant = 1;
							float precio = producto.getPrecio();
							lstProductoVendido.add(new ProductoVendido(producto, cant, precio));
						}
					}
				
					Venta venta = new Venta(fecha, nroTicket, s.getNumeroTicket(), formaDePago, lstProductoVendido, empleadoAtendio, empleadoCobro);
					Document doc = Document.parse(mapper.writeValueAsString(venta));
					collectionVenta.insertOne(doc);
				}
				System.out.print("completado.\n");
			}
						
		}
		catch (JsonParseException e) { e.printStackTrace();}
	    catch (JsonMappingException e) { e.printStackTrace(); }
	    catch (Exception e) { e.printStackTrace(); }
	}
	
	public static float round(float d, int decimalPlace) {
	    BigDecimal bd = new BigDecimal(Float.toString(d));
	    bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
	    return bd.floatValue();
	}
}
