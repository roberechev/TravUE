package travue.PruebaMaven;

import java.util.ArrayList;
import java.util.Scanner;

public class Login {

	public static void main(String[] args) {
		UsoArchivos usA = new UsoArchivos();
		Users usuario;
		Scanner sc = new Scanner(System.in);
		String seleccionLogin = "0";
		int opcionLogin = 1;
		ArrayList<Users> todosUsuarios;
		todosUsuarios = usA.listarUsuarios();
		// Colores para el texto en la consola
		// String ANSI_BLUE = "\u001B[34m";// azul oscuro
		// String ANSI_BLUE_Claro = "\033[36m";// azul claro

		// String ANSI_RESET = "\u001B[0m";// para cerrar la etiqueta de color y que no
		// se pinte toda la consola
		System.out.println(
				"-⎽__⎽-⎻⎺⎺⎻-⎽__⎽--⎻⎺⎺⎻--⎽__⎽-⎻⎺⎺⎻-⎽__⎽--⎻⎺⎺⎻--⎽__⎽-⎻⎺⎺⎻-⎽__⎽--⎻⎺⎺⎻--⎽__⎽-⎻⎺⎺⎻-⎽__⎽--⎻⎺⎺⎻-⎽__⎽--⎻⎺⎺⎻-\n-⎽__⎽-⎻⎺⎺⎻-⎽__⎽--⎻⎺⎺⎻--⎽__⎽-⎻⎺⎺⎻-⎽__⎽--⎻⎺⎺⎻-TravUE-⎽__⎽-⎻⎺⎺⎻-⎽__⎽--⎻⎺⎺⎻--⎽__⎽-⎻⎺⎺⎻-⎽__⎽--⎻⎺⎺⎻-\n-⎽__⎽-⎻⎺⎺⎻-⎽__⎽--⎻⎺⎺⎻--⎽__⎽-⎻⎺⎺⎻-⎽__⎽--⎻⎺⎺⎻--⎽__⎽-⎻⎺⎺⎻-⎽__⎽--⎻⎺⎺⎻--⎽__⎽-⎻⎺⎺⎻-⎽__⎽--⎻⎺⎺⎻-⎽__⎽--⎻⎺⎺⎻-");
		while (opcionLogin != 0) {
			System.out.println("1 - Iniciar Sesion\n2 - Crear Cuenta\n3 - Salir");
			seleccionLogin = sc.nextLine();
			System.out.println(seleccionLogin);

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
						System.out.println("Bienvenid@ " + verificaUser.getUser());
						menu.menuAdmin(verificaUser);
					} else {
						System.out.println("Bienvenid@ " + verificaUser.getUser());
						menu.menuUser(verificaUser);
					}
				} else {
					System.out.println("Ese usuario no existe");
				}
				break;
			case "2": // CREAR CUENTA
				System.out.println("Usuario: ");
				String user = "";
				boolean usuarioExistente = true;// si existe el usuario en el csv seguira preguntando por otro user
				while (usuarioExistente) {
					boolean usuarioNoExistente = true;// comprueba si entra en el if de la igualacion
					user = sc.nextLine();
					ArrayList<Users> todosUsers = usA.listarUsuarios();
					for (Users u : todosUsers) {
						if (user.equals(u.getUser())) {
							System.out.println("Este usuario no esta disponible. Introduce otro: ");
							usuarioNoExistente = false;
						}
					}
					if (usuarioNoExistente) {
						usuarioExistente = false;
					}
				}

				System.out.println("Contraseña: ");
				String password = sc.nextLine();
				System.out.println("Nombre Completo: ");
				String nombreCompleto = sc.nextLine();
				System.out.println("¿Eres admin?\n1 - Si\n2 - No");
				String adm = sc.nextLine();
				int admin = 0;
				admin = comprobarAdmin(adm, admin);// Comprobamos que es admin realmente

				System.out.println("Introduce la carrera que impartes o estudias: ");
				String estudios = sc.nextLine();
				System.out.println("Introduce tu gmail: ");
				String email = sc.nextLine();
				usuario = new Users(user, password, nombreCompleto, admin, estudios, email);
				todosUsuarios.add(usuario);
				usA.crearCuenta(todosUsuarios);
				break;
			case "3":
				System.out.println("Gracias por usar TravUE. Hasta la proxima.");
				opcionLogin = 0;
				break;
			}
		}
	}

	private static int comprobarAdmin(String adm, int admin) {
		Scanner scanner = new Scanner(System.in);
		String passAdmin = "ContrasenaAdmin123";
		if (adm.equals("1")) {
			System.out.println("Introduce la contraseña especifica para ser Admin");
			String comprobacion = scanner.nextLine();
			if (comprobacion.equals(passAdmin)) {
				admin = 1;
			}
		} else if (adm.equals("2")) {

		} else {
			System.out.println("Lo siento, la contraseña no es correcta.");
		}
		return admin;
	}

}
