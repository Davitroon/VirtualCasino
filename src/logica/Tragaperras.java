package logica;

/**
 * Juego tragaperras, hijo de clase Juego.
 * Simula el funcionamiento real de una máquina tragaperras.
 * @author David Forero
 * @since 2.0
 */
public class Tragaperras extends Juego {

	int [] numeros = new int [3];
	
	
	/**
	 * Constructor de la tragaperras, envia al constructor de Juego su tipo.
	 * @param dinero Dinero que contará el juego para las apuestas.
	 * @since 2.0
	 */
	public Tragaperras(double dinero) {
		super(dinero, "Tragaperras");
	}

	
	/**
	 * Constructor completo (para ser editado) de la tragaperras, envia al constructor de Juego los parámetros.
	 * @param id Id del juego
	 * @param tipo Tipo del juego
	 * @param activo Estado del juego
	 * @param dinero Dinero del juego
	 * @since 2.0
	 */
	public Tragaperras(int id, String tipo, boolean activo, double dinero) {
		super(id, tipo, activo, dinero);
	}
	
	
	/**
	 * Constructor de tragaperras de datos necesarios (para guardar en el juego).
	 * @param id Id del juego
	 * @param dinero Dinero del juego
	 * @since 3.0
	 */
	public Tragaperras(int id, double dinero) {
		super(id, dinero, "Tragaperras");
	}

	
	/**
	 * Comprueba el número de casillas que esten repetidas.<br>
	 * - Si no hay ninguna, se pierde la apuesta.<br>
	 * - Si hay 2, se multiplica por 1.9.<br>
	 * - Si hay 3, se multiplica por 3.5.<br>
	 * - Si hay 3 sietes, se multiplica por 6.5.<br>
	 */
	@Override
	public double jugar(double apuesta) {
	    int n1 = numeros[0], n2 = numeros[1], n3 = numeros[2];
	    
	    if (n1 == 7 && n2 == 7 && n3 == 7) {
	        return apuesta * 6.50;
	    }
	    
	    if (n1 == n2 && n1 == n3) {
	        return apuesta * 3.5;
	        
	    }
	    
	    if (n1 == n2 || n1 == n3 || n2 == n3) {
	        return apuesta * 1.9;
	        
	    }
	    
	    return -apuesta;    
	}
	
	
	/**
	 * Método que rellena las 3 casillas con un número al azar del 1 al 9.
	 * @since 2.0
	 */
	public void generarNumeros() {	
	
		for (int i = 0; i < numeros.length; i++) {
				numeros[i] = (int) Math.round(Math.random() * 8) + 1;		
		}
	}


	public int[] getNumeros() {
		return numeros;
	}


	
}