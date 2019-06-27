package modelo;

import java.math.BigDecimal;

public class ProductoVendido {
	private Producto producto;
	private int cantidad;
	private float precio;
	private float total;
	
	public ProductoVendido() {
		super();
	}

	public ProductoVendido(Producto producto, int cantidad, float precio, float total) {
		super();
		this.producto = producto;
		this.cantidad = cantidad;
		this.precio = precio;
		this.total = total;
	}
	
	public ProductoVendido(Producto producto, int cantidad, float precio) {
		super();
		this.producto = producto;
		this.cantidad = cantidad;
		this.precio = precio;
		this.total = precio * cantidad;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
		this.total = round(this.precio * this.cantidad, 2);
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
		this.total = this.precio * this.cantidad;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "producto=" + producto.getDescripcion() + ", cantidad=" + cantidad + ", precio=" + precio + ", total="
				+ total;
	}
	
	public float round(float d, int decimalPlace) {
	    BigDecimal bd = new BigDecimal(Float.toString(d));
	    bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
	    return bd.floatValue();
	}
}
