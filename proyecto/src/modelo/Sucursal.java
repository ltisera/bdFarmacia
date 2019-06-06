package modelo;

import java.util.List;

public class Sucursal 
{
	private Domicilio domicilio;
	private List<Empleado> empleados;
	private Empleado encargado;
	private int numeroTicket;

	public Sucursal() {
		super();
	}

	public Sucursal(Domicilio domicilio, List<Empleado> empleados, Empleado encargado,
			int numeroTicket) {
		super();
		this.domicilio = domicilio;
		this.empleados = empleados;
		this.encargado = encargado;
		this.numeroTicket = numeroTicket;
	}

	public Domicilio getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}

	public List<Empleado> getEmpleados() {
		return empleados;
	}

	public void setEmpleados(List<Empleado> empleados) {
		this.empleados = empleados;
	}

	public Empleado getEncargado() {
		return encargado;
	}

	public void setEncargado(Empleado encargado) {
		this.encargado = encargado;
	}

	public int getNumeroTicket() {
		return numeroTicket;
	}

	public void setNumeroTicket(int numeroTicket) {
		this.numeroTicket = numeroTicket;
	}

	@Override
	public String toString() {
		return "Sucursal [domicilio=" + domicilio + ", empleados=" + empleados
				+ ", encargado=" + encargado + ", numeroTicket=" + numeroTicket + "]";
	}
	
	
	
	

}
