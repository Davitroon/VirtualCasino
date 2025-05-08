
/**
 * Juego tragaperras, hijo de clase Juego.
 * Simula el funcionamiento real de una máquina tragaperras.
 * @author David Forero
 */
public class Tragaperras extends Juego {

	int [] numeros = new int [3];
	
	
	/**
	 * Constructor de la tragaperras, envia al constructor de Juego su tipo.
	 * @param dinero Dinero que contará el juego para las apuestas.
	 */
	public Tragaperras(double dinero) {
		super(dinero, "Tragaperras");
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
	    	System.out.println("¡Jackpot!");
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
	 * Rellena las 3 casillas con un número al azar del 1 al 9.
	 */
	public void generarNumeros() {	
	
		for (int i = 0; i < numeros.length; i++) {
				numeros[i] = (int) Math.round(Math.random() * 8) + 1;		
		}
	}


	@Override
	public String toString() {
		
		return "["+numeros[0]+"]" + "["+numeros[1]+"]" + "["+numeros[2]+"]";
	}
}