import java.util.ArrayList;
import java.util.Collections;

/**
 * Juego BlackJack, hijo de juego.
 * Simula el funcionamiento del BlackJack real. 
 * @author David Forero
 */
public class BlackJack extends Juego {

	private ArrayList<Integer> cartasTotales = new ArrayList<>();
	private ArrayList<Integer> cartasCliente = new ArrayList<>();
	private ArrayList<Integer> cartasCrupier= new ArrayList<>();
	
	/**
	 * Constructor del blackjack, envia al constructor de Juego su tipo.
	 * @param dinero Dinero que contará el juego para las apuestas.
	 */
	public BlackJack(double dinero) {
		super(dinero, "BlackJack");
	}


	/**
	 * Compara la suma de las cartas del cliente y del crupier.<br>
	 * Si el cliente pierde, pierde su apuesta.<br>
	 * Si hay un empate:<br>
	 *  - Si el empate es con un blackjack, se multiplica la apuesta por 1.5.<br>
	 *  - Si el empate es sin blackjack, se recupera la apuesta original.<br>
	 * Si gana el cliente:<br>
	 *  - Con blackjack, se multiplica la apuesta por 2.5.<br>
	 *  - Sin blackjack, se multiplica la apuesta por 1.<br>
	 */
	@Override
	public double jugar(double apuesta) {
		
		int sumaCrupier = sumarCartas(cartasCrupier);
		int sumaCliente = sumarCartas(cartasCliente);
		
		// Pierde el cliente
		if (sumaCliente < sumaCrupier) {
			return -apuesta;
		} 
		
		// Empate con blackjack o normal
		if (sumaCliente == sumaCrupier) {
	        return (sumaCliente == 21) ? apuesta * 1.5 : apuesta; 
		}
		
		// Gana con blackjack o normal
		return (sumaCliente == 21) ? apuesta * 2.5 : apuesta * 2;
	  
	}
	

	/**
	 * Baraja las cartas. Primero vacia todos los mazos de cartas, luego rellena el mazo de cartas total y finalmente las mezcla.
	 */
	public void barajarCartas() {		

		cartasTotales.clear();
		cartasCliente.clear();
		cartasCrupier.clear();
		
		for (int i = 1; i < 14; i++) {
            for (int j = 0; j < 4; j++) {
            	cartasTotales.add(i);
            }
        }   
        
        Collections.shuffle(cartasTotales);
	}
	

	/**
	 * Reparte un numero de cartas al jugador indicado, eliminando las cartas del mazo total.
	 * @param numCartas Número de cartas a repartir.
	 * @param jugador Jugador al que se le repartirá.
	 */
	public void repartirCartas(int numCartas, String jugador) {
	
		
		if (jugador.equalsIgnoreCase("cliente")) {
			for (int i = 0; i < numCartas; i++) {
				cartasCliente.add(cartasTotales.getFirst());
				cartasTotales.removeFirst();
			}
			
		} else if (jugador.equalsIgnoreCase("crupier")) {
			for (int i = 0; i < numCartas; i++) {
				cartasCrupier.add(cartasTotales.getFirst());
				cartasTotales.removeFirst();
			}
		}	
	}


	/**
	 * Muestra las cartas de cada jugador.
	 * @param ocultarCarta Ocultar la 2º carta del crupier o no.
	 * @return Cartas de cada jugador.
	 */
	public String mostrarCartas(boolean ocultarCarta) {
		
		String cartas = "Cartas crupier:\n";
		
		for (Integer carta : cartasCrupier) {
		    if (ocultarCarta && cartasCrupier.indexOf(carta) == 1) {
		        cartas += "[?] ";
		        
		    } else {
		        cartas += "[" + leerCarta(carta) + "] ";
		    }
		}

		cartas += "\n\nCartas jugador: \n";

		for (Integer carta : cartasCliente) {
		    cartas += "[" + leerCarta(carta) + "] ";        
		}
		
		return cartas;
	}
	
	
	/**
	 * Lee una carta para traducirla a la baraja francesa.
	 * @param carta Carta a leer.
	 * @return Carta traducida.
	 */
	private String leerCarta(Integer carta) {
		
		switch (carta) {
		case 1:
			return "A";
		
		case 11:
			return "J";
			
		case 12:
			return "Q";
			
		case 13:
			return "K";
			
		default :
			return String.valueOf(carta);			
		}
	}


	/**
	 * Comprueba si el jugador se ha pasado de 21.
	 * @param jugador Jugador a comprobar.
	 * @return Se ha pasado de 21 o no.
	 */
	public boolean jugadorPierde(String jugador) {

	    ArrayList<Integer> listaCartas = jugador.equalsIgnoreCase("cliente") ? cartasCliente : cartasCrupier;
	    
	    return sumarCartas(listaCartas) > 21;
	}
	
	
	/**
	 * Suma el valor de las cartas de cada jugador.
	 * @param listaCartas Cartas del jugador.
	 * @return La suma total, teniendo en cuenta los As.
	 */
	public int sumarCartas(ArrayList<Integer> listaCartas) {
		
		int suma = 0;
		int numAses = 0;
		
		for (int carta : listaCartas) {
			// Cuenta si hay algun As.
            if (carta == 1) {  
                numAses++;
                suma += 11;
                
            // J, Q y K los considero como 10.
            } else if (carta > 10) {  
                suma += 10;
                
            } else {
            	suma += carta;
            }
		}
		 
		// Convierte un As de 11 a 1 si el jugador se pasa de 21.
		while (suma > 21 && numAses > 0) {
	        suma -= 10;  
	        numAses--;
		}
		
		return suma;
	}
	

	/**
	 * Indica si el crupier debería pedir carta en situaciones diferentes.
	 * @return Pide carta o no.
	 */
	public boolean crupierDebePedir() {
		
		int sumaCrupier = sumarCartas(cartasCrupier);
		int sumaCliente = sumarCartas(cartasCliente);
		
		// Si sus cartas suman menos de 16, debe pedir sí o sí.
		if (sumaCrupier < 16) {
			return true;
		
		// Si tiene mas puntos que el cliente y no se pasa de 21, se planta.
		} else if (sumaCrupier > sumaCliente && sumaCrupier < 22) {
			return false;
		
		// Tiene menos puntos que el cliente y mas de 16, debe pedir para ganarle.
		} else {
			return true;
		}
	}


	public ArrayList<Integer> getCartasCliente() {
		return cartasCliente;
	}
}

