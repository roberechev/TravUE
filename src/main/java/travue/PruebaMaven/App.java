package travue.PruebaMaven;

import java.util.ArrayList;

public class App {

	public static void main(String[] args) {
		UsoArchivos us = new UsoArchivos();
		Viajes vi = new Viajes("2", "Prueba2", "1", "3", 1000, "jesus", 50);
		Viajes vi2 = new Viajes("3", "Prueba2", "1", "3", 1000, "jesus", 50);
		Viajes vi3 = new Viajes("4", "Prueba2", "1", "3", 1000, "jesus", 50);
		Viajes vi4 = new Viajes("5", "Prueba2", "1", "3", 1000, "jesus", 50);

		ArrayList<Viajes> viajes = us.listarViajes();
		viajes.add(vi);
		viajes.add(vi2);
		viajes.add(vi3);
		viajes.add(vi4);
		us.anadirViaje(viajes);

	}

}
