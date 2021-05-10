package travue.PruebaMaven;

public class Users {
	String user;
	String password;
	String nombreCompleto;
	int admin; // 0 no es, 1 es admin
	String estudios;
	String email;

	public Users(String user, String password, String nombreCompleto, int admin, String estudios, String email) {
		super();
		this.user = user;
		this.password = password;
		this.nombreCompleto = nombreCompleto;
		this.admin = admin;
		this.estudios = estudios;
		this.email = email;
	}

	public Users() {
		super();
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}

	public String getEstudios() {
		return estudios;
	}

	public void setEstudios(String estudios) {
		this.estudios = estudios;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Users [user=" + user + ", password=" + password + ", nombreCompleto=" + nombreCompleto + ", admin="
				+ admin + ", estudios=" + estudios + ", email=" + email + "]";
	}

}
