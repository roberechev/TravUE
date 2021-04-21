package travue.PruebaMaven;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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

public class UsoArchivos {

	private String rutaViajes = "..\\PruebaMaven\\src\\main\\java\\archivos\\Viajes.csv";
	private String rutaUsuarios = "..\\PruebaMaven\\src\\main\\java\\archivos\\Usuarios.csv";

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

					System.out.print(todasLasCeldas + " - ");
					viajes.add(todasLasCeldas);// añadimos al array la celda del excel
				}
				System.out.println("");
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
				convertidorArrayListViajes += "..o.." + viajes.get(i); // Guardamos los objetos con un separador
				if ((i + 1) % 7 == 0 && i != 0 && i != 6 && i != 7) { // Dividimos entre 7 ya que son los atributos de
																		// nuestros viajes, pero hay que eliminar los 7
																		// primeros ya que son la cabecera y no nos
																		// aportan nada
					String dat[] = convertidorArrayListViajes.split("..o.."); // hacemos un split para guardar cada
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

					datosViajes.add(new Viajes(id, nombre, fechaInicio, fechaFin, precio, profesor, plazasDisponibles));

					convertidorArrayListViajes = "";
				} else if (i == 6) {
					convertidorArrayListViajes = "";
				}

			}

			for (Viajes row : datosViajes) {
				System.out.println(row.toString()); // Vemos que funcione pintando uno a uno todos los viajes anadidos
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return datosViajes;

	}

	public void anadirViaje(ArrayList<Viajes> viaje) {

		XSSFWorkbook libro = new XSSFWorkbook();
		XSSFSheet hoja1 = libro.createSheet("hoja1");

		// Cabecera de la hoja de excel
		// String[] header = new String[] { "ID", "NOMBRE", "FECHA INICIO", "FECHA FIN",
		// "PRECIO", "PROFESOR",
		// "PLAZAS DISPONIBLES" };

		for (int k = 0; k <= viaje.size(); k++) {
			String[][] cuerpo = new String[][] {
					{ "ID", "NOMBRE", "FECHA INICIO", "FECHA FIN", "PRECIO", "PROFESOR", "PLAZAS DISPONIBLES" } };
			// Contenido de la hoja de excel
			int kmenos1 = k - 1;
			if (k != 0) {
				cuerpo = new String[][] { { viaje.get(kmenos1).getId(), viaje.get(kmenos1).getNombre(),
						viaje.get(kmenos1).getFechaInicio(), viaje.get(kmenos1).getFechaFin(),
						String.valueOf(viaje.get(kmenos1).getPrecio()), viaje.get(kmenos1).getProfesor(),
						String.valueOf(viaje.get(kmenos1).getPlazasDisponibles()) } };
			}
			XSSFRow row = hoja1.createRow(k); // Se crea la fila
			for (int j = 0; j < 7; j++) {
				System.out.println("FILA "+k);
				System.out.println("COLUMNA "+j);
				XSSFCell cell = row.createCell(j); // Se crean las celdas para el contenido
				cell.setCellValue(cuerpo[k][j]); // Se añade el contenido

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

	public void crearCuenta(String user, String password, String nombreCompleto, int admin, String estudios,
			String email) {
		System.out.println(user + " " + password + " " + nombreCompleto + " " + admin + " " + estudios + " " + email);

		XSSFWorkbook libro = new XSSFWorkbook();
		XSSFSheet hoja1 = libro.createSheet("hoja1");

		// Cabecera de la hoja de excel
		String[] header = new String[] { "ID", "USER", "PASSWORD", "NOMBRE COMPLETO", "ADMIN", "ESTUDIOS", "EMAIL" };

		// Contenido de la hoja de excel
		String[][] cuerpo = new String[][] {
				{ "1", user, password, nombreCompleto, String.valueOf(admin), estudios, email },
				{ "2", user, password, nombreCompleto, String.valueOf(admin), estudios, email },
				{ "3", user, password, nombreCompleto, String.valueOf(admin), estudios, email },
				{ "4", user, password, nombreCompleto, String.valueOf(admin), estudios, email },
				{ "5", user, password, nombreCompleto, String.valueOf(admin), estudios, email } };

		// Generar los datos para el documento
		for (int i = 0; i <= cuerpo.length; i++) {
			XSSFRow row = hoja1.createRow(i); // Se crea la fila
			for (int j = 0; j < header.length; j++) {
				if (i == 0) { // Para la cabecera
					XSSFCell cell = row.createCell(j); // Se crean las celdas pra la cabecera
					cell.setCellValue(header[j]); // Se añade el contenido
				} else {
					XSSFCell cell = row.createCell(j); // Se crean las celdas para el contenido
					cell.setCellValue(cuerpo[i - 1][j]); // Se añade el contenido
				}
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

	public void comprobarUsuario(String userI, String passwordI) {

	}

}
