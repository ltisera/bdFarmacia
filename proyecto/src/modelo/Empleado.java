package modelo;

public class Empleado 
{
	private int emepladoId;
	private String apellido;
	private String nombre;
	private int dni;
	private Domicilio domicilio;
	private String obraSocial;
	private int numeroAfiliado;
	
	public Empleado(int emepladoId, String apellido, String nombre, int dni, Domicilio domicilio, String obraSocial,
			int numeroAfiliado) {
		super();
		this.emepladoId = emepladoId;
		this.apellido = apellido;
		this.nombre = nombre;
		this.dni = dni;
		this.domicilio = domicilio;
		this.obraSocial = obraSocial;
		this.numeroAfiliado = numeroAfiliado;
	}

	public Empleado() {
		super();
	}

	public int getEmepladoId() {
		return emepladoId;
	}

	public void setEmepladoId(int emepladoId) {
		this.emepladoId = emepladoId;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public Domicilio getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}

	public String getObraSocial() {
		return obraSocial;
	}

	public void setObraSocial(String obraSocial) {
		this.obraSocial = obraSocial;
	}

	public int getNumeroAfiliado() {
		return numeroAfiliado;
	}

	public void setNumeroAfiliado(int numeroAfiliado) {
		this.numeroAfiliado = numeroAfiliado;
	}

	@Override
	public String toString() {
		return "Empleado [emepladoId=" + emepladoId + ", apellido=" + apellido + ", nombre=" + nombre + ", dni=" + dni
				+ ", domicilio=" + domicilio + ", obraSocial=" + obraSocial + ", numeroAfiliado=" + numeroAfiliado
				+ "]";
	}
	
	

	
	
	
	
	
	
}
