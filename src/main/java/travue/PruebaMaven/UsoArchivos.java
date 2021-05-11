package travue.PruebaMaven;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class UsoArchivos {

	private String rutaViajes = "..\\PruebaMaven\\src\\main\\java\\archivos\\Viajes.csv";
	private String rutaUsuarios = "..\\PruebaMaven\\src\\main\\java\\archivos\\Usuarios.csv";
	private String rutaComentarios = "..\\PruebaMaven\\src\\main\\java\\archivos\\Comentarios.csv";
	private String separadorParaLeer = "·";

	/*
	 * Este metodo lee el excel de Viajes y lo convierte en un ArrayList de Viajes
	 * eliminando la cabecera
	 */
	public ArrayList<Viajes> listarViajes() {
		String[][] cabeceras = null;
		String[][] body = null;
		ArrayList viajes = new ArrayList();
		ArrayList<Viajes> datosViajes = new ArrayList<Viajes>();

		try (FileInputStream file = new FileInputStream(new File(rutaViajes))) {

			XSSFWorkbook libro = new XSSFWorkbook(file);// Lee archivo
			XSSFSheet sheet = libro.getSheetAt(0);// Obtener la hoja a leer
			Iterator<Row> rowIterator = sheet.iterator();// Obtener todas las filas de la hoja

			while (rowIterator.hasNext()) {// Recorre cada fila una a una
				Iterator<Cell> cellIterator = rowIterator.next().cellIterator();// Se obtienen las celdas por fila

				while (cellIterator.hasNext()) {// Se recorre cada celda
					// Se obtiene la celda en especifico con cellIterator.next.get...

					String todasLasCeldas = String.valueOf(cellIterator.next().toString());// coje lo que haya en la
																							// celda del excel como
																							// string
					double celdasNumericas;
					try {
						celdasNumericas = Double.parseDouble(todasLasCeldas);// convertimos el string a double ya que
																				// tiene decimales
						int celdasNumericasCasteadas = (int) celdasNumericas;// el double lo casteamos a int para quitar
																				// decimales
						todasLasCeldas = String.valueOf(celdasNumericasCasteadas);// volvemos a pasarlo a string
					} catch (Exception e) {

					}

					// System.out.print(todasLasCeldas + " - ");
					viajes.add(todasLasCeldas);// añadimos al array la celda del excel
				}
				// System.out.println("");
			}

			/*
			 * Con esto vamos a pasar de un arrayList normal a un ArrayList de Viajes
			 * Creamos un string convertidorArrayListViajes para ir almacenando cada objeto
			 * del arraylist anadiendole un separador ..o.., con el cual luego hacemos un
			 * bucle de cada 7 objetos que son los atributos de nuestros viajes y asi poder
			 * recogerlos e ir añadiendolos a nuestro arrayList de viajes de Viaje en Viaje.
			 */
			String convertidorArrayListViajes = "";
			for (int i = 0; i < viajes.size(); i++) {
				convertidorArrayListViajes += separadorParaLeer + viajes.get(i); // Guardamos los objetos con un
																					// separador
				if ((i + 1) % 7 == 0 && i != 0 && i != 6 && i != 7) { // Dividimos entre 7 ya que son los atributos de
																		// nuestros viajes, pero hay que eliminar los 7
																		// primeros ya que son la cabecera y no nos
																		// aportan nada
					String dat[] = convertidorArrayListViajes.split(separadorParaLeer); // hacemos un split para guardar
																						// cada
					// objeto en su sitio
					String id = dat[1];
					String nombre = dat[2];
					String fechaInicio = dat[3];
					String fechaFin = dat[4];
					double precioDouble = Double.parseDouble(dat[5]); // al cojer los datos los numeros vienen con
																		// decimales por lo que hacemos un casteo a int
																		// para que coincidan con nuestros atributos y
																		// para eliminar los decimales
					int precio = (int) precioDouble;
					String profesor = dat[6];
					double plazasDisponiblesDouble = Double.parseDouble(dat[7]);
					int plazasDisponibles = (int) plazasDisponiblesDouble;
					// con esto vamos a meter en nuestro arrayList de viajes uno a uno todos los
					// viajes
					datosViajes.add(new Viajes(id, nombre, fechaInicio, fechaFin, precio, profesor, plazasDisponibles));

					convertidorArrayListViajes = "";
				} else if (i == 6) {
					convertidorArrayListViajes = "";
				}

			}

		} catch (Exception e) {
			e.getMessage();
		}
		return datosViajes;

	}

	/*
	 * Este metodo mete el ArrayList de viajes en excel
	 */
	public void anadirViajeaExcel(ArrayList<Viajes> viaje) {

		XSSFWorkbook libro = new XSSFWorkbook();
		XSSFSheet hoja1 = libro.createSheet("hoja1");

		for (int k = 0; k <= viaje.size(); k++) {
			// cabeceras solo se añaden la primera iteracion
			String[][] cuerpo = new String[][] {
					{ "ID", "NOMBRE", "FECHA INICIO", "FECHA FIN", "PRECIO", "PROFESOR", "PLAZAS DISPONIBLES" } };

			int kmenos1 = k - 1; // esta variable nos sirve ya que la primera vez metemos la cabecera y entonces
									// tenemos que ir viendo todos los viajes por eso hay q restarle 1
			if (k != 0) {
				cuerpo = new String[][] { { viaje.get(kmenos1).getId(), viaje.get(kmenos1).getNombre(),
						viaje.get(kmenos1).getFechaInicio(), viaje.get(kmenos1).getFechaFin(),
						String.valueOf(viaje.get(kmenos1).getPrecio()), viaje.get(kmenos1).getProfesor(),
						String.valueOf(viaje.get(kmenos1).getPlazasDisponibles()) } };
			}
			XSSFRow row = hoja1.createRow(k); // Se crea la fila
			for (int j = 0; j < 7; j++) {
				XSSFCell cell = row.createCell(j); // Se crean las celdas para el contenido
				cell.setCellValue(cuerpo[0][j]); // Se añade el contenido, se pone 0 ya que nosotros vamos eliminando y
													// creando la variable cuerpo por lo que solo esta en la posicion 0

			}

		}
		// Crear el archivo
		try (OutputStream fileOut = new FileOutputStream(rutaViajes)) {
			// System.out.println("SE CREO EL EXCEL");
			libro.write(fileOut);
		} catch (IOException e) {
			System.out.println("ERROR EN EXCEL");
			e.printStackTrace();
		}
	}

	/*
	 * Este metodo pinta un ArrayList de Viajes en consola
	 */
	public void pintarViajes(ArrayList<Viajes> viajes) {
		String separador = " | ";
		System.out.println("ID" + separador + "NOMBRE" + separador + "FECHA INICIO" + separador + "FECHA FIN"
				+ separador + "PRECIO" + separador + "PROFESOR" + separador + "PLAZAS DISPONIBLES");
		// System.out.println("");
		for (Viajes v : viajes) {
			System.out.println(v.getId() + separador + v.getNombre() + separador + v.getFechaInicio() + separador
					+ v.getFechaFin() + separador + v.getPrecio() + separador + v.getProfesor() + separador
					+ v.getPlazasDisponibles());
		}
	}

	/*
	 * Este metodo pinta un ArrayList de Users en consola
	 */
	public void pintarUsuarios(ArrayList<Users> users) {
		String separador = " | ";
		System.out.println("USER" + separador + "PASSWORD" + separador + "NOMBRE COMPLETO" + separador + "ADMIN"
				+ separador + "ESTUDIOS" + separador + "EMAIL");
		// System.out.println("");
		for (Users u : users) {
			System.out.println(u.getUser() + separador + u.getPassword() + separador + u.getNombreCompleto() + separador
					+ u.getAdmin() + separador + u.getEstudios() + separador + u.getEmail());
		}
	}

	/*
	 * Este metodo pinta un ArrayList de Comentarios en consola
	 */
	public void pintarComentarios(ArrayList<Comentarios> comentarios) {
		String separador = " | ";
		System.out.println("USER" + separador + "COMENTARIOS");
		// System.out.println("");
		for (Comentarios c : comentarios) {
			System.out.println(c.getUsuario() + separador + c.getComentarioUser());
		}
	}

	/*
	 * Este metodo pide un nuevo User y lo añade al arrayList de Users para
	 * seguidamente guardar todos los cambios en el excel
	 */
	public void crearCuenta(ArrayList<Users> todosUsuarios) {
		XSSFWorkbook libro = new XSSFWorkbook();
		XSSFSheet hoja1 = libro.createSheet("hoja1");

		for (int k = 0; k <= todosUsuarios.size(); k++) {
			// cabeceras solo se añaden la primera iteracion
			String[][] cuerpo = new String[][] {
					{ "USER", "PASSWORD", "NOMBRE COMPLETO", "ADMIN", "ESTUDIOS", "EMAIL" } };

			int kmenos1 = k - 1; // esta variable nos sirve ya que la primera vez metemos la cabecera y entonces
									// tenemos que ir viendo todos los viajes por eso hay q restarle 1
			if (k != 0) {
				// String user, String password, String nombreCompleto, int admin, String
				// estudios, String email
				cuerpo = new String[][] { { todosUsuarios.get(kmenos1).getUser(),
						todosUsuarios.get(kmenos1).getPassword(), todosUsuarios.get(kmenos1).getNombreCompleto(),
						String.valueOf(todosUsuarios.get(kmenos1).getAdmin()), todosUsuarios.get(kmenos1).getEstudios(),
						todosUsuarios.get(kmenos1).getEmail() } };
			}
			XSSFRow row = hoja1.createRow(k); // Se crea la fila
			for (int j = 0; j < 6; j++) {
				XSSFCell cell = row.createCell(j); // Se crean las celdas para el contenido
				cell.setCellValue(cuerpo[0][j]); // Se añade el contenido, se pone 0 ya que nosotros vamos eliminando y
													// creando la variable cuerpo por lo que solo esta en la posicion 0

			}

		}

		// Crear el archivo
		try (OutputStream fileOut = new FileOutputStream(rutaUsuarios)) {
			// System.out.println("SE CREO EL EXCEL");
			libro.write(fileOut);
		} catch (IOException e) {
			System.out.println("ERROR EN EXCEL");
			e.printStackTrace();
		}

	}

	/*
	 * Este metodo lee el excel de Usuarios y lo convierte en un ArrayList de
	 * Usuarios eliminando la cabecera
	 */
	public ArrayList<Users> listarUsuarios() {
		ArrayList usuarios = new ArrayList();
		ArrayList<Users> todosUsuarios = new ArrayList<Users>();

		try (FileInputStream file = new FileInputStream(new File(rutaUsuarios))) {

			XSSFWorkbook libro = new XSSFWorkbook(file);// Lee archivo
			XSSFSheet sheet = libro.getSheetAt(0);// Obtener la hoja a leer
			Iterator<Row> rowIterator = sheet.iterator();// Obtener todas las filas de la hoja

			while (rowIterator.hasNext()) {// Recorre cada fila una a una
				Iterator<Cell> cellIterator = rowIterator.next().cellIterator();// Se obtienen las celdas por fila

				while (cellIterator.hasNext()) {// Se recorre cada celda
					// Se obtiene la celda en especifico con cellIterator.next.get...

					String todasLasCeldas = String.valueOf(cellIterator.next().toString());// coje lo que haya en la
																							// celda del excel como
																							// string
					double celdasNumericas;
					try {
						celdasNumericas = Double.parseDouble(todasLasCeldas);// convertimos el string a double ya que
																				// tiene decimales
						int celdasNumericasCasteadas = (int) celdasNumericas;// el double lo casteamos a int para quitar
																				// decimales
						todasLasCeldas = String.valueOf(celdasNumericasCasteadas);// volvemos a pasarlo a string
					} catch (Exception e) {

					}

					// System.out.print(todasLasCeldas + " - ");
					usuarios.add(todasLasCeldas);// añadimos al arrayList la celda del excel
				}
				// System.out.println("");
			}

			/*
			 * Con esto vamos a pasar de un arrayList normal a un ArrayList de Viajes
			 * Creamos un string convertidorArrayListViajes para ir almacenando cada objeto
			 * del arraylist anadiendole un separador ..o.., con el cual luego hacemos un
			 * bucle de cada 7 objetos que son los atributos de nuestros viajes y asi poder
			 * recogerlos e ir añadiendolos a nuestro arrayList de viajes de Viaje en Viaje.
			 */
			String convertidorArrayListUsuarios = "";
			for (int i = 0; i < usuarios.size(); i++) {
				convertidorArrayListUsuarios += separadorParaLeer + usuarios.get(i); // Guardamos los objetos con un
																						// separador
				if ((i + 1) % 6 == 0 && i != 0 && i != 5 && i != 6) { // Dividimos entre 7 ya que son los atributos de
																		// nuestros viajes, pero hay que eliminar los 7
																		// primeros ya que son la cabecera y no nos
																		// aportan nada
					String dat[] = convertidorArrayListUsuarios.split(separadorParaLeer); // hacemos un split para
																							// guardar cada
					// objeto en su sitio

					String user = dat[1];
					String password = dat[2];
					String nombreCompleto = dat[3];
					double adminDouble = Double.parseDouble(dat[4]); // al cojer los datos los numeros vienen con
																		// decimales por lo que hacemos un casteo a int
																		// para que coincidan con nuestros atributos y
																		// para eliminar los decimales
					int admin = (int) adminDouble;
					String estudios = dat[5];
					String email = dat[6];
					// con esto vamos a meter en nuestro arrayList de viajes uno a uno todos los
					// viajes
					todosUsuarios.add(new Users(user, password, nombreCompleto, admin, estudios, email));

					convertidorArrayListUsuarios = "";
				} else if (i == 5) {
					convertidorArrayListUsuarios = "";
				}

			}

		} catch (Exception e) {
			e.getMessage();
		}

		return todosUsuarios;
	}

	/*
	 * Este metodo verifica que el usuario y la contraseña introducidos al iniciar
	 * sesion sean iguales a algun usuario y contraseña de dentro del excel con los
	 * usuarios. Si existe devuelve el usuario completo y en caso contrario null
	 */
	public Users comprobarUsuario(String userI, String passwordI, ArrayList<Users> todosUsuarios) {
		Users usuarioExistente = null;
		for (Users u : todosUsuarios) {
			if (userI.equals(u.getUser()) && passwordI.equals(u.getPassword())) {
				usuarioExistente = new Users(u.getUser(), u.getPassword(), u.getNombreCompleto(), u.getAdmin(),
						u.getEstudios(), u.getEmail());
			}
		}
		return usuarioExistente;
	}

	/*
	 * Este metodo necesita que antes se hayan leido los comentarios y nos pasen un
	 * arraylist de Comentarios para añadirle el nuevo y asi meterlo en nuestro
	 * excel
	 */
	public void escribirComentario(ArrayList<Comentarios> todosComentarios, String userUser, String comentario) {

		XSSFWorkbook libro = new XSSFWorkbook();
		XSSFSheet hoja1 = libro.createSheet("hoja1");
		todosComentarios.add(new Comentarios(userUser, comentario));

		for (int k = 0; k <= todosComentarios.size(); k++) {
			// cabeceras solo se añaden la primera iteracion
			String[][] cuerpo = new String[][] { { "USERS", "COMENTARIOS" } };

			int kmenos1 = k - 1; // esta variable nos sirve ya que la primera vez metemos la cabecera y entonces
									// tenemos que ir viendo todos los viajes por eso hay q restarle 1
			if (k != 0) {
				cuerpo = new String[][] { { todosComentarios.get(kmenos1).getUsuario(),
						todosComentarios.get(kmenos1).getComentarioUser() } };
			}
			XSSFRow row = hoja1.createRow(k); // Se crea la fila
			for (int j = 0; j < 2; j++) {
				XSSFCell cell = row.createCell(j); // Se crean las celdas para el contenido
				cell.setCellValue(cuerpo[0][j]); // Se añade el contenido, se pone 0 ya que nosotros vamos eliminando y
													// creando la variable cuerpo por lo que solo esta en la posicion 0

			}

		}
		// Crear el archivo
		try (OutputStream fileOut = new FileOutputStream(rutaComentarios)) {
			// System.out.println("SE CREO EL EXCEL");
			libro.write(fileOut);
		} catch (IOException e) {
			System.out.println("ERROR EN EXCEL");
			e.printStackTrace();
		}
	}

	/*
	 * este metodo lee del excel Comentarios todos los comentarios
	 */
	public ArrayList<Comentarios> leerComentarios() {

		String[][] cabeceras = null;
		String[][] body = null;
		ArrayList coment = new ArrayList();
		ArrayList<Comentarios> todosComentarios = new ArrayList<Comentarios>();

		try (FileInputStream file = new FileInputStream(new File(rutaComentarios))) {

			XSSFWorkbook libro = new XSSFWorkbook(file);// Lee archivo
			XSSFSheet sheet = libro.getSheetAt(0);// Obtener la hoja a leer
			Iterator<Row> rowIterator = sheet.iterator();// Obtener todas las filas de la hoja

			while (rowIterator.hasNext()) {// Recorre cada fila una a una
				Iterator<Cell> cellIterator = rowIterator.next().cellIterator();// Se obtienen las celdas por fila

				while (cellIterator.hasNext()) {// Se recorre cada celda
					// Se obtiene la celda en especifico con cellIterator.next.get...

					String todasLasCeldas = String.valueOf(cellIterator.next().toString());// coje lo que haya en la
																							// celda del excel como
																							// string

					// System.out.print(todasLasCeldas + " - ");
					coment.add(todasLasCeldas);// añadimos al array la celda del excel
				}
				// System.out.println("");
			}

			/*
			 * Con esto vamos a pasar de un arrayList normal a un ArrayList de Viajes
			 * Creamos un string convertidorArrayListViajes para ir almacenando cada objeto
			 * del arraylist anadiendole un separador ..o.., con el cual luego hacemos un
			 * bucle de cada 7 objetos que son los atributos de nuestros viajes y asi poder
			 * recogerlos e ir añadiendolos a nuestro arrayList de viajes de Viaje en Viaje.
			 */
			String convertidorArrayListComentarios = "";
			for (int i = 0; i < coment.size(); i++) {
				convertidorArrayListComentarios += separadorParaLeer + coment.get(i); // Guardamos los objetos con un
																						// separador
				if ((i + 1) % 2 == 0 && i != 0 && i != 1 && i != 2) { // Dividimos entre 7 ya que son los atributos de
																		// nuestros viajes, pero hay que eliminar los 7
																		// primeros ya que son la cabecera y no nos
																		// aportan nada
					String dat[] = convertidorArrayListComentarios.split(separadorParaLeer); // hacemos un split para
																								// guardar cada
					// objeto en su sitio
					String user = dat[1];
					String comentUser = dat[2];

					// con esto vamos a meter en nuestro arrayList de viajes uno a uno todos los
					// viajes
					todosComentarios.add(new Comentarios(user, comentUser));

					convertidorArrayListComentarios = "";
				} else if (i == 1) {
					convertidorArrayListComentarios = "";
				}

			}

		} catch (Exception e) {
			e.getMessage();
		}
		return todosComentarios;
	}

}
