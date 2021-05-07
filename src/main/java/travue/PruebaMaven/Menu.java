package travue.PruebaMaven;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Menu {
	UsoArchivos usA = new UsoArchivos();
	Users usuario = new Users();
	ArrayList<Viajes> todosViajes = usA.listarViajes();

	public void menuAdmin(Users user) {
		usuario = user;
		System.out.println("Admin");

	}

	public void menuUser(Users user) {
		usuario = user;
		System.out.println("User");

	}

	public void inscribirseViaje(String idViaje, ArrayList<Viajes> todosViajes) {
		for (Viajes v : todosViajes) {
			if (idViaje.equals(v.getId())) {
				v.setPlazasDisponibles(v.getPlazasDisponibles() - 1);// disminuimos las plazas disponibles
				//mandarEmail();
			} else {
				System.out.println("Ese viaje no existe");
			}

		}
	}

	private void mandarEmail(Users user) {

		// Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el
		// remitente también.
		String miCorreoSinArroba = "proyingenieriagrupo"; // Para la dirección nomcuenta@gmail.com
		String miPassword = "proyingenieriagrupo123";

		String asunto = "asunto";
		String cuerpo = "cuerpo";

		Properties prop = System.getProperties();
		prop.put("mail.smtp.host", "smtp.gmail.com"); // El servidor SMTP de Google
		prop.put("mail.smtp.user", miCorreoSinArroba);
		prop.put("mail.smtp.clave", miPassword); // La clave de la cuenta
		prop.put("mail.smtp.auth", "true"); // Usar autenticación mediante usuario y clave
		prop.put("mail.smtp.starttls.enable", "true"); // Para conectar de manera segura al servidor SMTP
		prop.put("mail.smtp.port", "587"); // El puerto SMTP seguro de Google

		Session session = Session.getDefaultInstance(prop);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(miCorreoSinArroba));
			message.addRecipients(Message.RecipientType.TO, user.getEmail()); // Se podrían añadir varios de la misma
																				// manera
			message.setSubject(asunto);
			message.setText(cuerpo);
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", miCorreoSinArroba, miPassword);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("Email enviado correctamente a: " + user.getEmail());
		} catch (MessagingException e) {
			System.out.println("No ha sido posible");
			e.printStackTrace();
		}

	}
}
