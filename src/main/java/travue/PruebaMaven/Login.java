package travue.PruebaMaven;

import java.util.ArrayList;
import java.util.Scanner;

public class Login {

	public static void main(String[] args) {
		UsoArchivos usA = new UsoArchivos();
		Users usuario;
		Scanner sc = new Scanner(System.in);
		String seleccionLogin = "0";
		ArrayList<Users> todosUsuarios;
		todosUsuarios = usA.listarUsuarios();

		while (!seleccionLogin.equals("1") && !seleccionLogin.equals("2") && !seleccionLogin.equals("3")) {// Bucle
			// infinito
			// hasta que
			// elija una
			// opcion
			System.out.println("1 - Iniciar Sesion\n2 - Crear Cuenta\n3 - Salir");
			seleccionLogin = sc.nextLine();
			System.out.println(seleccionLogin);
		}

		switch (seleccionLogin) {
		case "1": // INICIAR SESION
			System.out.println("user");
			String userI = sc.nextLine();
			System.out.println("password");
			String passwordI = sc.nextLine();
			Users verificaUser = usA.comprobarUsuario(userI, passwordI, todosUsuarios);
			if (verificaUser != null) {
				Menu menu = new Menu();
				if (verificaUser.getAdmin() == 1) {
					System.out.println("Hola admin " + verificaUser.getUser());
					menu.menuAdmin(verificaUser);
				} else {
					System.out.println("Hola user " + verificaUser.getUser());
					menu.menuUser(verificaUser);
				}
			} else {
				System.out.println("Ese usuario no existe");
			}
			break;
		case "2": // CREAR CUENTA
			System.out.println("User");
			String user = sc.nextLine();
			System.out.println("password");
			String password = sc.nextLine();
			System.out.println("Nombre Completo");
			String nombreCompleto = sc.nextLine();
			System.out.println("¿Eres admin?\n1 - Si\n2 - No");
			String adm = sc.nextLine();
			int admin = 0;
			admin = comprobarAdmin(adm, admin);// Comprobamos que es admin realmente

			System.out.println("¿Que estudias?");
			String estudios = sc.nextLine();
			System.out.println("email");
			String email = sc.nextLine();
			usuario = new Users(55, user, password, nombreCompleto, admin, estudios, email);
			usA.crearCuenta(usuario, todosUsuarios);
			break;
		}

	}

	private static int comprobarAdmin(String adm, int admin) {
		Scanner scanner = new Scanner(System.in);
		String passAdmin = "Pruebas123";
		if (adm.equals("1")) {
			System.out.println("Introduce la password de Admin proporcionada por un programador");
			String comprobacion = scanner.nextLine();
			if (comprobacion.equals(passAdmin)) {
				admin = 1;
			}
		} else {
			System.out.println("No eres ADMIN");
		}
		return admin;
	}

}
