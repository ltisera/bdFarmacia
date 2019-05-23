package modelo;

//import org.bson.Document;

public class Domicilio 
{
	private int domicilioId;
	private String calle;
	private String numero;
	private String localidad;
	private String provincia;
	
	public Domicilio(int domicilioId, String calle, String numero, String localidad, String provincia) {
		super();
		this.domicilioId = domicilioId;
		this.calle = calle;
		this.numero = numero;
		this.localidad = localidad;
		this.provincia = provincia;
	}

	public Domicilio() {
		super();
	}

	public int getDomicilioId() {
		return domicilioId;
	}

	public void setDomicilioId(int domicilioId) {
		this.domicilioId = domicilioId;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	@Override
	public String toString() {
		return "Domicilio [domicilioId=" + domicilioId + ", calle=" + calle + ", numero=" + numero + ", localidad="
				+ localidad + ", provincia=" + provincia + "]";
	}
	
	
	
	/*
	public String toJson() 
	{
		Document doc = new Document();
		return doc.append("calle", calle).append("numero", (numero);
	}*/
	
	
	
}
