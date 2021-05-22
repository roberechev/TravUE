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
	ArrayList<Viajes> todosViajes; // = usA.listarViajes();
	Scanner sc = new Scanner(System.in);
	int opcionesBucle = 1;// variable para el bucle si se pone a 0 se acaba el bucle

	/*
	 * Este es el menu del admin, el cual solo les aparecera a las personas
	 * logueadas que tengan en su atributo de admin un 1, este rol tiene mas
	 * opciones que el usuario ya que puede crear viajes, eliminar viajes..., entre
	 * otras cosas.
	 */
	public void menuAdmin(Users user) {
		usuario = user;
		System.out.println("Admin");
		while (opcionesBucle != 0) {
			System.out.println(
					"1 - Ver viajes\n2 - Añadir Viaje\n3 - Eliminar Viaje\n4 - Modificar Viaje\n5 - Perfil\n6 - Ver/Escribir Comentarios de la aplicacion\n7 - Ver dinero recaudado\n8 - Inscribirte\n9 - Cancelar Viaje\n10 - Cerrar Sesion");
			String opcion = sc.nextLine();
			switch (opcion) {
			case "1":
				todosViajes = usA.listarViajes();
				usA.pintarViajes(todosViajes);
				break;
			case "2":

				todosViajes = usA.listarViajes();
				String id = String.valueOf(usA.ficheroIdViajes());
				System.out.println("Introduce el Nombre del Viaje");
				String nombre = sc.nextLine();
				System.out.println("Introduce la fecha de inicio del viaje");
				String fechaInicio = sc.nextLine();
				System.out.println("Introduce la fecha de fin del viaje");
				String fechaFin = sc.nextLine();
				System.out.println("Introduce el precio del viaje");
				int precio = Integer.parseInt(sc.nextLine());
				System.out.println("Introduce el nombre del Profesor asignado a este viaje");
				String profesor = sc.nextLine();
				System.out.println("Introduce las plazas totales de este viaje");
				int plazasTotales = Integer.parseInt(sc.nextLine());
				int plazasDisponibles = plazasTotales;
				todosViajes.add(new Viajes(id, nombre, fechaInicio, fechaFin, precio, profesor, plazasDisponibles,
						plazasTotales));

				usA.anadirViajeaExcel(todosViajes);
				System.out.println("Viaje añadido");

				break;
			case "3":
				todosViajes = usA.listarViajes();
				usA.pintarViajes(todosViajes);
				System.out.println("Introduce el ID del viaje a eliminar: ");
				String idEliminar = sc.nextLine();
				System.out.println(
						"Seguro que quieres eliminar este viaje no habrá vuelta atras\n1 - Eliminar\n2 - No eliminar");
				String decision = sc.nextLine();
				if (decision.equals("1")) {
					eliminarViaje(idEliminar, todosViajes);
				} else {
					System.out.println("Viaje no eliminado");
				}

				break;
			case "4":
				todosViajes = usA.listarViajes();
				usA.pintarViajes(todosViajes);
				System.out.println("Introduce el ID del viaje a modificar: ");
				String idModificar = sc.nextLine();
				modificarViaje(idModificar, todosViajes);

				break;
			case "5":
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
			case "6":
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
			case "7":

				break;
			case "8":
				ArrayList<Viajes> vi = usA.listarViajes();
				usA.pintarViajes(vi);
				System.out.println("Introduce el ID del viaje al que quieres inscribirte: ");
				String idViaje = sc.nextLine();
				inscribirseViaje(idViaje, vi);
				break;
			case "9":
				ArrayList<Viajes> viaj = usA.listarViajes();
				usA.pintarViajes(viaj);
				System.out.println("Introduce el ID del viaje para cancelar tu inscripcion: ");
				String idViaj = sc.nextLine();
				cancelarViaje(idViaj, viaj);
				break;
			case "10":
				System.out.println("Sesion cerrada");
				opcionesBucle = 0;
				break;

			default:
				break;
			}
		}

	}

	/*
	 * Este es el menu del usuario el cual aparecera a las personas logueadas que
	 * solo sean usuarios, es decir que en el atributo de admin tengan un 0
	 */
	public void menuUser(Users user) {
		usuario = user;
		System.out.println("User");

		while (opcionesBucle != 0) {
			System.out.println(
					"1 - Ver viajes\n2 - Ver/Escribir Comentarios de la aplicacion\n3 - Perfil\n4 - Inscribirte\n5 - Cancelar Viaje\n6 - Cerrar Sesion");
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

	/*
	 * Este metodo recibe el id a modificar y un arraylist de objetos Viajes, si el
	 * id coincide con alguno de los de los viajes nos pedira que queremos cambiar
	 * del viaje y segun la opcion que le marcaremos hara unos cambios u otros
	 */
	public void modificarViaje(String idModificar, ArrayList<Viajes> todosViajes) {
		for (Viajes v : todosViajes) {
			if (idModificar.equals(v.getId())) {
				System.out.println(
						"¿Que quieres modificar?\n1 - El viaje completo\n2 - El nombre\n3 - La fecha de Inicio\n4 - La fecha de Fin\n5 - El precio\n6 - El profesor\n7 - Las Plazas Totales\n8 - Nada");

				String opcionModificar = sc.nextLine();
				switch (opcionModificar) {
				case "1":
					// Modificamos el viaje completo
					System.out.println("Nuevo nombre del Viaje:");
					String nombre = sc.nextLine();
					v.setNombre(nombre);
					System.out.println("Nueva fecha de inicio del viaje:");
					String fechaInicio = sc.nextLine();
					v.setFechaInicio(fechaInicio);
					System.out.println("Nueva fecha de fin del viaje:");
					String fechaFin = sc.nextLine();
					v.setFechaFin(fechaFin);
					System.out.println("Nuevo precio del viaje:");
					int precio = Integer.parseInt(sc.nextLine());
					v.setPrecio(precio);
					System.out.println("Nuevo nombre del Profesor asignado a este viaje:");
					String profesor = sc.nextLine();
					v.setProfesor(profesor);
					System.out.println("Nuevas plazas totales de este viaje:");
					int plazasTotales = Integer.parseInt(sc.nextLine());
					v.setPlazasTotales(plazasTotales);
					int plazasOcupadas = v.getPlazasTotales() - v.getPlazasDisponibles();
					v.setPlazasDisponibles(plazasTotales - plazasOcupadas);
					System.out.println("Se ha modificado el viaje: " + v.toString());
					break;
				case "2":
					// Modificamos el nombre
					System.out.println("Nuevo nombre del Viaje:");
					String nuevoNombre = sc.nextLine();
					v.setNombre(nuevoNombre);
					System.out.println("Nombre modificado: " + v.toString());
					break;
				case "3":
					// Modificamos la fecha de inicio
					System.out.println("Nueva fecha de inicio del viaje:");
					String nuevaFechaInicio = sc.nextLine();
					v.setFechaInicio(nuevaFechaInicio);
					System.out.println("Fecha de Inicio modificada: " + v.toString());
					break;
				case "4":
					// Modificamos la fecha de fin
					System.out.println("Nueva fecha de fin del viaje:");
					String nuevaFechaFin = sc.nextLine();
					v.setFechaFin(nuevaFechaFin);
					System.out.println("Fecha de Fin modificada: " + v.toString());
					break;
				case "5":
					// Modificamos el precio
					System.out.println("Nuevo precio del viaje:");
					int nuevoPrecio = Integer.parseInt(sc.nextLine());
					v.setPrecio(nuevoPrecio);
					System.out.println("Precio modificado: " + v.toString());
					break;
				case "6":
					// Modificamos el nombre del profesor
					System.out.println("Nuevo nombre del Profesor asignado a este viaje:");
					String nuevoProfesor = sc.nextLine();
					v.setProfesor(nuevoProfesor);
					System.out.println("Nombre del Profesor modificado: " + v.toString());
					break;
				case "7":
					// Modificamos las plazas Totales y ademas las disponibles
					System.out.println("Nuevas plazas totales de este viaje:");
					int nuevasPlazasTotales = Integer.parseInt(sc.nextLine());
					int nuevasPlazasOcupadas = v.getPlazasTotales() - v.getPlazasDisponibles();
					v.setPlazasTotales(nuevasPlazasTotales);
					v.setPlazasDisponibles(nuevasPlazasTotales - nuevasPlazasOcupadas);
					System.out.println("Plazas totales y disponibles modificadas: " + v.toString());
					break;

				}
			}

		}
		usA.anadirViajeaExcel(todosViajes);

	}

	/*
	 * Este metodo recibe el id a eliminar y un arraylist de objetos Viajes, si el
	 * id existe entre los viajes se eliminara todo el viaje y seguidamente se
	 * reescribira el archivo csv
	 */
	public void eliminarViaje(String idEliminar, ArrayList<Viajes> todosViajes) {
		boolean eliminado = false;
		int index = 2000;

		for (Viajes v : todosViajes) {
			if (idEliminar.equals(v.getId())) {
				index = todosViajes.indexOf(v);
				eliminado = true;
			}

		}
		if (eliminado) {
			todosViajes.remove(index);
			System.out.println("El viaje a sido eliminado");
			eliminado = false;
			usA.anadirViajeaExcel(todosViajes);
		} else {
			System.out.println("El id introducido no pertenece a ningun viaje.");
		}
	}

	/*
	 * Este metodo sirve para inscribirse en un viaje, lo que hace es si el viaje
	 * existe le resta uno a las plazas disponibles y nos envia un correo
	 * electronico, gmail, al correo que tengamos puesto en nuestro usuario de la
	 * aplicacion TravUe
	 */
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

	/*
	 * Este metodo sirve para cancelar un viaje, lo que hace es si el viaje existe
	 * le suma uno a las plazas disponibles y nos envia un correo electronico,
	 * gmail, al correo que tengamos puesto en nuestro usuario de la aplicacion
	 * TravUe
	 */
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

	/*
	 * Este metodo se encarga de mandar un email desde nuestra cuenta ya creada
	 * proyingenieriagrupo@gmail.com al gmail que esta en el usuario con el que
	 * estamos logueados al inscribirnos o cancelar un viaje
	 */
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
