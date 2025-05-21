package logica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Juego BlackJack, hijo de juego.
 * Simula el funcionamiento del BlackJack real. 
 * @author David Forero
 */
public class Blackjack extends Juego {

	private LinkedList<Integer> cartasTotales = new LinkedList<>();
	private ArrayList<Integer> cartasCliente = new ArrayList<>();
	private ArrayList<Integer> cartasCrupier= new ArrayList<>();
	
	/**
	 * Constructor del blackjack, envia al constructor de Juego su tipo.
	 * @param dinero Dinero que contará el juego para las apuestas.
	 * @since 2.0
	 */
	public Blackjack(double dinero) {
		super(dinero, "BlackJack");
	}
	
	
	/**
	 * Constructor completo (para ser editado) del blackjack, envia al constructor de Juego los parámetros.
	 * @param id Id del juego
	 * @param tipo Tipo del juego
	 * @param activo Estado del juego
	 * @param dinero Dinero del juego
	 * @since 3.0
	 */
	public Blackjack(int id, String tipo, boolean activo, double dinero) {
		super(id, tipo, activo, dinero);
	}

	
	/**
	 * Constructor de blackjack de datos necesarios (para guardar en el juego).
	 * @param id Id del juego
	 * @param dinero Dinero del juego
	 * @since 3.0
	 */
	public Blackjack(int id, double dinero) {
		super(id, dinero);
	}

	/**
	 * Compara la suma de las cartas del cliente y del crupier.<br>
	 * - Si gana el cliente con blackjack, se multiplica la apuesta por 2.5.<br>
	 * - Si hay empate con un blackjack o el cliente gana con mano normal, se multiplica la apuesta por 1.5.<br>
	 * - Si hay empate sin blackjack, se recupera la apuesta original.<br>
	 * - Si el cliente pierde, pierde su apuesta.<br>
	 */
	@Override
	public double jugar(double apuesta) {
		
		int sumaCrupier = sumarCartas(cartasCrupier);
		int sumaCliente = sumarCartas(cartasCliente);
		
	    // Cliente pierde
	    if (sumaCliente > 21) {
	        return -apuesta;
	    }

	    // Crupier se pasa y el cliente no
	    if (sumaCrupier > 21) {
	    	return apuesta * 1.75;
	    }

	    // Empate
	    if (sumaCliente == sumaCrupier) {
	        if (sumaCliente == 21) {
	            return apuesta * 1.5;  // Empate con blackjack
	            
	        } else {
	            return apuesta;        // Empate normal
	        }
	    }

	    // Cliente gana (Blackjack o normal)
	    if (sumaCliente > sumaCrupier) {
	        return (sumaCliente == 21 && cartasCliente.size() == 2) ? apuesta * 2.5 : apuesta * 1.5;
	    }

	    // Cliente pierde
	    return -apuesta;
	  
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
	 * @since 2.0
	 */
	public String mostrarCartas(boolean ocultarCarta, String jugador) {
		
		String cartas = "";
		
		if (jugador.equals("cliente")) {
			for (Integer carta : cartasCliente) {
			    cartas += "[" + leerCarta(carta) + "] ";        
			}
		}
		
		if (jugador.equals("crupier")) {		
			for (int i = 0; i < cartasCrupier.size(); i++) {
			    if (ocultarCarta && i == 1) {
			        cartas += "[?] ";
			    } else {
			        cartas += "[" + leerCarta(cartasCrupier.get(i)) + "] ";
			    }
			}
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
	
	public ArrayList<Integer> getCartasCrupier() {
		return cartasCrupier;
	}
}

