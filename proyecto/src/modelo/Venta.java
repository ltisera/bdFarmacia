package modelo;

import java.time.LocalDateTime;

public class Venta 
{
	private int ventaId;
	private LocalDateTime fecha;
	private int numeroTicket;
	private float total;
	private String formaDePago;
	
	public Venta(int ventaId, LocalDateTime fecha, int numeroTicket, float total, String formaDePago) {
		super();
		this.ventaId = ventaId;
		this.fecha = fecha;
		this.numeroTicket = numeroTicket;
		this.total = total;
		this.formaDePago = formaDePago;
	}

	public Venta() {
		super();
	}

	public int getVentaId() {
		return ventaId;
	}

	public void setVentaId(int ventaId) {
		this.ventaId = ventaId;
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

	@Override
	public String toString() {
		return "Venta [ventaId=" + ventaId + ", fecha=" + fecha + ", numeroTicket=" + numeroTicket + ", total=" + total
				+ ", formaDePago=" + formaDePago + "]";
	}
	
	
	
	
	
}
