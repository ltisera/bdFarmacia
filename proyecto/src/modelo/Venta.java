package modelo;

import java.time.LocalDateTime;
import java.util.List;

public class Venta 
{
	private LocalDateTime fecha;
	private int numeroTicket;
	private float total;
	private String formaDePago;
	private List<ProductoVendido> productos;
	private Empleado empleadoAtendio;
	private Empleado empleadoCobro;

	public Venta() {
		super();
	}

	public Venta(LocalDateTime fecha, int numeroTicket, float total, String formaDePago,
			List<ProductoVendido> productos, Empleado empleadoAtendio, Empleado empleadoCobro) {
		super();
		this.fecha = fecha;
		this.numeroTicket = numeroTicket;
		this.total = total;
		this.formaDePago = formaDePago;
		this.productos = productos;
		this.empleadoAtendio = empleadoAtendio;
		this.empleadoCobro = empleadoCobro;
	}



	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public int getNumeroTicket() {
		return numeroTicket;
	}

	public void setNumeroTicket(int numeroTicket) {
		this.numeroTicket = numeroTicket;
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

	@Override
	public String toString() {
		return "Venta [fecha=" + fecha + ", numeroTicket=" + numeroTicket + ", total=" + total + ", formaDePago="
				+ formaDePago + ", productos=" + productos + ", empleadoAtendio=" + empleadoAtendio + ", empleadoCobro="
				+ empleadoCobro + "]";
	}
}
