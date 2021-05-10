package travue.PruebaMaven;

public class Comentarios {
	String usuario;
	String comentarioUser;

	public Comentarios(String usuario, String comentarioUser) {
		super();
		this.usuario = usuario;
		this.comentarioUser = comentarioUser;
	}

	public Comentarios() {
		super();
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getComentarioUser() {
		return comentarioUser;
	}

	public void setComentarioUser(String comentarioUser) {
		this.comentarioUser = comentarioUser;
	}

}
