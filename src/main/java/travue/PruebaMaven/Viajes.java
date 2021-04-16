package travue.PruebaMaven;

public class Viajes {

	String id;
	String nombre;
	String fechaInicio;
	String fechaFin;
	int precio;
	String profesor;
	int plazasDisponibles;

	public Viajes(String id, String nombre, String fechaInicio, String fechaFin, int precio, String profesor,
			int plazasDisponibles) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.precio = precio;
		this.profesor = profesor;
		this.plazasDisponibles = plazasDisponibles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public int getPrecio() {
		return precio;
	}

	public void setPrecio(int precio) {
		this.precio = precio;
	}

	public String getProfesor() {
		return profesor;
	}

	public void setProfesor(String profesor) {
		this.profesor = profesor;
	}

	public int getPlazasDisponibles() {
		return plazasDisponibles;
	}

	public void setPlazasDisponibles(int plazasDisponibles) {
		this.plazasDisponibles = plazasDisponibles;
	}

	public String[] getArray() {
		String[] datos = { nombre, fechaInicio, fechaFin, String.valueOf(precio), profesor,
				String.valueOf(plazasDisponibles) };
		return datos;

	}

	@Override
	public String toString() {
		return "Viajes [id=" + id + ", nombre=" + nombre + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin
				+ ", precio=" + precio + ", profesor=" + profesor + ", plazasDisponibles=" + plazasDisponibles + "]";
	}

}
