package modelo;

public class Producto 
{
	private int productoId;
	private String tipo;
	private String descripcion;
	private String laboratorio;
	private int codigo;
	private float precio;
	
	public Producto(int productoId, String tipo, String descripcion, String laboratorio, int codigo, float precio) {
		super();
		this.productoId = productoId;
		this.tipo = tipo;
		this.descripcion = descripcion;
		this.laboratorio = laboratorio;
		this.codigo = codigo;
		this.precio = precio;
	}

	public Producto() {
		super();
	}

	public int getProductoId() {
		return productoId;
	}

	public void setProductoId(int productoId) {
		this.productoId = productoId;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getLaboratorio() {
		return laboratorio;
	}

	public void setLaboratorio(String laboratorio) {
		this.laboratorio = laboratorio;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	@Override
	public String toString() {
		return "Producto [productoId=" + productoId + ", tipo=" + tipo + ", descripcion=" + descripcion
				+ ", laboratorio=" + laboratorio + ", codigo=" + codigo + ", precio=" + precio + "]";
	}
	
	
	
	
	
	
	
	
}
