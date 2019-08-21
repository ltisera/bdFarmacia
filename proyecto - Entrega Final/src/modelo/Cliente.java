package modelo;

public class Cliente extends Persona
{

	public Cliente() {
		super();
	}

	public Cliente(String apellido, String nombre, int dni, Domicilio domicilio, String obraSocial,
			int numeroAfiliado) {
		super(apellido, nombre, dni, domicilio, obraSocial, numeroAfiliado);
	}

	@Override
	public String toString() {
		return "Cliente [" + super.toString() + "]";
	}
	
	
	
}
