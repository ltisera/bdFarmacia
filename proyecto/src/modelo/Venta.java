package modelo;

import java.util.GregorianCalendar;
import java.util.List;

public class Venta 
{
	private GregorianCalendar fecha;
	private String numeroTicket;
	private float total;
	private String formaDePago;
	private List<ProductoVendido> productos;
	private Empleado empleadoAtendio;
	private Empleado empleadoCobro;
	private Cliente cliente;

	public Venta() {
		super();
	}

	public Venta(GregorianCalendar fecha,String numeroTicket, float total, String formaDePago,
			List<ProductoVendido> productos, Empleado empleadoAtendio, Empleado empleadoCobro, Cliente cliente) {
		super();
		this.fecha = fecha;
		this.numeroTicket = numeroTicket;
		this.total = total;
		this.formaDePago = formaDePago;
		this.productos = productos;
		this.empleadoAtendio = empleadoAtendio;
		this.empleadoCobro = empleadoCobro;
		this.cliente = cliente;
	}

	public Venta(GregorianCalendar fecha, int numeroTicket, int numeroSucursal, String formaDePago,
			List<ProductoVendido> productos, Empleado empleadoAtendio, Empleado empleadoCobro, Cliente cliente) {
		super();
		this.fecha = fecha;
		this.setNumeroTicket(numeroTicket, numeroSucursal);
		this.formaDePago = formaDePago;
		this.productos = productos;
		this.empleadoAtendio = empleadoAtendio;
		this.empleadoCobro = empleadoCobro;
		this.cliente = cliente;
		for (ProductoVendido p : productos) {
			this.total += p.getTotal();
		}
	}
	
	public GregorianCalendar getFecha() {
		return fecha;
	}

	public void setFecha(GregorianCalendar fecha) {
		this.fecha = fecha;
	}

	public String getNumeroTicket() {
		return numeroTicket;
	}

	public void setNumeroTicket(int numeroTicket, int numeroSucursal) {
		this.numeroTicket = String.format("%04d" , numeroSucursal)+ "-" + String.format("%08d" , numeroTicket);
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public String getFormaDePago() {
		return formaDePago;
	}

	public void setFormaDePago(String formaDePago) {
		this.formaDePago = formaDePago;
	}

	public List<ProductoVendido> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductoVendido> productos) {
		this.productos = productos;
	}

	public Empleado getEmpleadoAtendio() {
		return empleadoAtendio;
	}

	public void setEmpleadoAtendio(Empleado empleadoAtendio) {
		this.empleadoAtendio = empleadoAtendio;
	}

	public Empleado getEmpleadoCobro() {
		return empleadoCobro;
	}

	public void setEmpleadoCobro(Empleado empleadoCobro) {
		this.empleadoCobro = empleadoCobro;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "Venta [fecha=" + fecha + ", numeroTicket=" + numeroTicket + ", total=" + total + ", formaDePago="
				+ formaDePago + ", productos=" + productos + ", empleadoAtendio=" + empleadoAtendio + ", empleadoCobro="
				+ empleadoCobro + "]";
	}
}
