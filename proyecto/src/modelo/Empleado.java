package modelo;

public class Empleado extends Persona
{
	private long cuil;

	public Empleado() {
		super();
	}

	public Empleado(String apellido, String nombre, int dni, Domicilio domicilio, String obraSocial, int numeroAfiliado,
			long cuil) {
		super(apellido, nombre, dni, domicilio, obraSocial, numeroAfiliado);
		this.cuil = cuil;
	}

	public long getCuil() {
		return cuil;
	}

	public void setCuil(long cuil) {
		this.cuil = cuil;
	}

	@Override
	public String toString() {
		return "Empleado [cuil=" + cuil + super.toString() + "]";
	}
	
	
	
	
	

	
	
	
	
	
	
}
