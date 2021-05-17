package travue.PruebaMaven;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Menu {
	UsoArchivos usA = new UsoArchivos();
	Users usuario = new Users();
	ArrayList<Viajes> todosViajes; //= usA.listarViajes();
	Scanner sc = new Scanner(System.in);
	int opcionesBucle = 1;// variable para el bucle si se pone a 0 se acaba el bucle

	public void menuAdmin(Users user) {
		usuario = user;
		System.out.println("Admin");
		while (opcionesBucle != 0) {
			System.out.println(
					"1 - Ver viajes\n2 - Ver/Escribir Comentarios\n3 - Perfil\n4 - Inscribirte\n5 - Cancelar Viaje\n6 - Cerrar Sesion");
			String opcion = sc.nextLine();
			switch (opcion) {
			case "1":

				break;
			case "2":

				break;
			case "3":

				break;
			case "4":

				break;
			case "5":

				break;
			case "6":
				System.out.println("Sesion cerrada");
				opcionesBucle = 0;
				break;

			default:
				break;
			}
		}

	}

	public void menuUser(Users user) {
		usuario = user;
		System.out.println("User");

		while (opcionesBucle != 0) {
			System.out.println(
					"1 - Ver viajes\n2 - Ver/Escribir Comentarios\n3 - Perfil\n4 - Inscribirte\n5 - Cancelar Viaje\n6 - Cerrar Sesion");
			String opcion = sc.nextLine();
			switch (opcion) {
			case "1":
				todosViajes = usA.listarViajes();
				usA.pintarViajes(todosViajes);
				break;
			case "2":
				System.out.println("1 - Ver comentarios\n2 - Escribir Comentarios");
				String verEscribir = sc.nextLine();
				ArrayList<Comentarios> todosComentarios = usA.leerComentarios();
				if (verEscribir.equals("1")) {
					usA.pintarComentarios(todosComentarios);
				} else if (verEscribir.equals("2")) {
					System.out.println("Escribe tu comentario: ");
					String nuevoComentario = sc.nextLine();
					usA.escribirComentario(todosComentarios, usuario.getUser(), nuevoComentario);
					todosComentarios = usA.leerComentarios();
					usA.pintarComentarios(todosComentarios);
				} else {
					System.out.println("Solo puedes ver o escribir comentarios");
				}
				break;
			case "3":
				String adm = "No";
				String separar = " | ";
				if (usuario.getAdmin() == 1) {
					adm = "Si";
				}
				System.out.println("USUARIO | PASSWORD | NOMBRE COMPLETO | ADMIN | ESTUDIOS | EMAIL");
				System.out.println(
						usuario.getUser() + separar + usuario.getPassword() + separar + usuario.getNombreCompleto()
								+ separar + adm + separar + usuario.getEstudios() + separar + usuario.getEmail());
				System.out.println("¿Quieres modificar tu email?\n1 - Si\n2 - No");
				String opcionModificacion = sc.nextLine();
				if (opcionModificacion.equals("1")) {
					System.out.println("Escribe el gmail que quieres utilizar: ");
					String nuevoEmail = sc.nextLine();
					usuario.setEmail(nuevoEmail);// aqui modifico el gmail en la variable temporal

					ArrayList<Users> todosUsuarios = usA.listarUsuarios();
					/*
					 * con este foreach cambio el gmail dentro de la lista de usuarios y lo añado al
					 * excel para modificarlo definitivamente
					 */
					for (Users u : todosUsuarios) {
						if (usuario.getUser().equals(u.getUser()) && usuario.getPassword().equals(u.getPassword())) {
							u.setEmail(nuevoEmail);
						}
					}

					usA.crearCuenta(todosUsuarios);

				}

				break;
			case "4":
				ArrayList<Viajes> vi = usA.listarViajes();
				usA.pintarViajes(vi);
				System.out.println("Introduce el ID del viaje al que quieres inscribirte: ");
				String idViaje = sc.nextLine();
				inscribirseViaje(idViaje, vi);
				break;
			case "5":
				ArrayList<Viajes> viaj = usA.listarViajes();
				usA.pintarViajes(viaj);
				System.out.println("Introduce el ID del viaje para cancelar tu inscripcion: ");
				String idViaj = sc.nextLine();
				cancelarViaje(idViaj, viaj);
				break;
			case "6":
				System.out.println("Sesion cerrada");
				opcionesBucle = 0;
				break;

			default:
				break;
			}
		}

	}

	public void inscribirseViaje(String idViaje, ArrayList<Viajes> todosViajes) {
		int contador = 0;
		for (Viajes v : todosViajes) {

			if (idViaje.equals(v.getId())) {
				if (v.getPlazasDisponibles() != 0) {
					v.setPlazasDisponibles(v.getPlazasDisponibles() - 1);// disminuimos las plazas disponibles
					String asunto = "INCRIPCION";
					String cuerpo = "Estimado...";
					System.out.println("Te has apuntado al viaje: " + v.toString());
					mandarEmail(usuario, asunto, cuerpo);
					usA.anadirViajeaExcel(todosViajes);
					break;
				} else {
					System.out.println("Este viaje esta completo");
					break;
				}

			}
			contador++;
			if (contador == todosViajes.size()) {

				System.out.println("Ese viaje no existe");
			}

		}
	}

	public void cancelarViaje(String idViaje, ArrayList<Viajes> todosViajes) {
		int contador = 0;
		for (Viajes v : todosViajes) {
			if (idViaje.equals(v.getId())) {
				if (v.getPlazasDisponibles() != v.getPlazasTotales()) {
				v.setPlazasDisponibles(v.getPlazasDisponibles() + 1);// disminuimos las plazas disponibles
				String asunto = "CANCELACION";
				String cuerpo = "Estimado...";
				System.out.println("Has cancelado el viaje: " + v.toString());
				mandarEmail(usuario, asunto, cuerpo);
				usA.anadirViajeaExcel(todosViajes);
				break;
			} else {
				System.out.println("No estas inscrito en ese viaje");
				break;
			}
			}
			contador++;
			if (contador == todosViajes.size()) {

				System.out.println("Ese viaje no existe");
			}
		}

	}

	public void mandarEmail(Users user, String asunto, String cuerpo) {

		// Esto es lo que va delante de @gmail.com en tu cuenta de correo. Es el
		// remitente también.
		String miCorreoSinArroba = "proyingenieriagrupo"; // Para la dirección nomcuenta@gmail.com
		String miPassword = "proyingenieriagrupo123";

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
