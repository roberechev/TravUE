package travue.PruebaMaven;

import java.util.ArrayList;

public class App {

	public static void main(String[] args) {
		/*
		 * UsoArchivos us = new UsoArchivos(); Viajes vi5 = new Viajes("7", "Prueba2",
		 * "1", "3", 1000, "jesus", 50);
		 * 
		 * ArrayList<Viajes> viajes = us.listarViajes(); viajes.add(vi5);
		 * us.anadirViaje(viajes);
		 * 
		 * 
		 * ArrayList<Users> todosUsuarios = us.listarUsuarios(); for (Users u :
		 * todosUsuarios) { System.out.println(u.toString()); // Vemos que funcione
		 * pintando uno a uno todos los viajes anadidos }
		 */
		UsoArchivos us = new UsoArchivos();
		
		ArrayList<Users> todosUsuarios = us.listarUsuarios();
		us.pintarUsuarios(todosUsuarios);
		

	}

}
