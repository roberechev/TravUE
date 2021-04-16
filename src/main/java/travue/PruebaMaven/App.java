package travue.PruebaMaven;

public class App {

	public static void main(String[] args) {
		UsoArchivos us = new UsoArchivos();
		Viajes vi = new Viajes("1", "Prueba2", "1", "3", 1000, "jesus", 50);
		Viajes vi2 = new Viajes("1", "Prueba2", "1", "3", 1000, "jesus", 50);
		Viajes vi3 = new Viajes("1", "Prueba2", "1", "3", 1000, "jesus", 50);
		Viajes vi4 = new Viajes("1", "Prueba2", "1", "3", 1000, "jesus", 50);

		//us.anadirViaje(vi);
		//us.anadirViaje(vi2);
		//us.anadirViaje(vi3);
		//us.anadirViaje(vi4);
		us.listarViajes();

	}

}
