package modelo;

public class Empleado extends Persona
{
	private String cuil;

	public Empleado() {
		super();
	}

	public Empleado(String apellido, String nombre, int dni, Domicilio domicilio, String obraSocial, int numeroAfiliado,
			String cuil) {
		super(apellido, nombre, dni, domicilio, obraSocial, numeroAfiliado);
		this.cuil = cuil;
	}

	public String getCuil() {
		return cuil;
	}

	public void setCuil(String cuil) {
		this.cuil = cuil;
	}

	@Override
	public String toString() {
		return "Empleado [cuil=" + cuil + super.toString() + "]";
	}
	
	
	
	
	

	
	
	
	
	
	
}
