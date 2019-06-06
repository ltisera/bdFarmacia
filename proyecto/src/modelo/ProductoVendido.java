package modelo;

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
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "ProductoVendido [producto=" + producto.getDescripcion() + ", cantidad=" + cantidad + ", precio=" + precio + ", total="
				+ total + "]";
	}

}
