package modelo;

import java.util.List;

public class Sucursal 
{
	private int sucursalId;
	private Domicilio domicilio;
	private List<Empleado> empleados;
	private Empleado encargado;
	
	public Sucursal(int sucursalId, Domicilio domicilio, List<Empleado> empleados, Empleado encargado) {
		super();
		this.sucursalId = sucursalId;
		this.domicilio = domicilio;
		this.empleados = empleados;
		this.encargado = encargado;
	}

	public Sucursal() {
		super();
	}

	public int getSucursalId() {
		return sucursalId;
	}

	public void setSucursalId(int sucursalId) {
		this.sucursalId = sucursalId;
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

	@Override
	public String toString() {
		return "Sucursal [sucursalId=" + sucursalId + ", domicilio=" + domicilio + ", empleados=" + empleados
				+ ", encargado=" + encargado + "]";
	}
	
	
	
	

}
