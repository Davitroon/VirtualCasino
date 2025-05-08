 import java.util.ArrayList;
import java.util.Scanner;

/**
 * Programa que simula la gestión de un casino.
 * El usuario podra jugar, crear, consultar, gestionar y eliminar juegos / clientes.
 * El programa cuenta con menús para orientar al usuario con las diferentes opciones.
 * @author David Forero
*/
public class Casino {
	
	/**
	 * Rango de edad válida de los clientes.
	 */
	final static int EDAD_MINIMA = 18;
	final static int EDAD_MAXIMA = 100;
	
	/**
	 * Rango de dinero válido para los juegos.
	 */
	final static int DINERO_MINIMO = 1000;
	final static int DINERO_MAXIMO = 1000000;
	
	/**
	 * Rango de saldo válido para los clientes.
	 */
	final static double SALDO_MINIMO = 10;
	final static double SALDO_MAXIMO = 10000;
	
	/**
	 * Rango de apuesta máximo para las partidas.
	 */
	final static double APUESTA_MINIMA = 2;
	final static double APUESTA_MAXIMA = 1000;
	
	/**
	 * Guías de los juegos.
	 */
	final static String guiaBlackjack = "Guía BlackJack\n-------\nTras marcar la apuesta, se repartirán 2 cartas a ambos jugadores (cliente y crupier), una carta del crupier estará oculta.\nEl jugador tendrá que asegurarse de que la suma del valor de sus cartas nunca se pase de 21. El As puede contar como 1 u 11, y la J, Q y K cuentan como 10.\nEl jugador ahora tiene la opción de pedir una carta más, plantarse o retirarse.\nSi el jugador se retira, pierde la mitad de la apuesta, pero si lo hace tras pedir carta, pierde tres cuartos de la apuesta.\nTras plantarse, el crupier revelará sus cartas. Si la suma de sus cartas es menor a 16, se verá obligado a pedir carta.\nEl juego termina cuando uno de los dos gane al rival o se pase de 21.\n - Empatar con el crupier mantendrá la apuesta igual.\n - Ganar con mano normal o empatar teniendo blackjack te multiplica la apuesta por 1.50x.\n - Ganar con blackjack multiplica la apuesta por 2x.";
	final static String guiaTragaperras = "Guía TragaPerras\n-------\nLa máquina generará 3 números al azar. \nSi 2 de estos números coinciden, se multiplicará la apuesta por 1.50x. \nSi coinciden 3 números, será por 2x. \nSi no coincide ningún número, el jugador perderá toda su apuesta.";
	
	/**
	 * Metodo principal.
	 */
	public static void main (String[] args) {
		
		Scanner scn = new Scanner (System.in);
		
		// - ASIGNACIÓN DE VARAIBLES -
		int menuOpcion = 0;
		int posicionBuscado = 0;
		
		ArrayList<Juego> juegos = new ArrayList<>();
		ArrayList<Cliente> clientes = new ArrayList<>();
		
		clientes.add(new Cliente("prueba", 19, 'H', false, 300));
		juegos.add(new BlackJack(2000));
		juegos.add(new Tragaperras(2000));
				
		boolean terminarJuego = false;
		boolean terminarPrograma = false;
		boolean salirMenu = false;
		
		limpiarPantalla();
		System.out.println("Bienvenido al casino de David. ¿Que te gustaria hacer?");
		
		do {		
			salirMenu = false;
			
			// Menú principal
			System.out.println("1 - Jugar");
			System.out.println("2 - Administrar usuarios");
			System.out.println("3 - Administrar juegos");
			System.out.println("4 - Administración rápida");
			System.out.println("0 - Salir");
			
			menuOpcion = validarNumeroEntero(scn, 0, 4, false);
			
			switch (menuOpcion) {
			
			// Menú principal -> Menú juegos
			case 1:
				limpiarPantalla();
				
				if (clientes.isEmpty())  {
					System.out.println("ERROR: No hay clientes registrados.");
					
				} else if (juegos.isEmpty()) {
					System.out.println("ERROR: No hay juegos registrados.");
					
				} else {
					System.out.print("Escribe el ID del juego y el ID cliente [IdJuego IdCliente] [0 - cancelar]: ");
					boolean errorDato = true;
				
					// Validación ID juego y cliente
					do {					
						String texto = scn.nextLine();
						
						if (texto.equals("0")) {
							limpiarPantalla();
							System.out.println("Cancelando...");
							errorDato = false;
							
						} else if (texto.matches("[1-9]+[0-9]* [1-9]+[0-9]*")) {
							String[] textoDividido = texto.split(" ");
							
							int idJuego = Integer.parseInt(textoDividido[0]) - 1;
							int idCliente = Integer.parseInt(textoDividido[1]) - 1;
	
							// Identificar juego y cliente a usar
							if (partidaPosible(idJuego, idCliente, juegos, clientes, 0)) {
								limpiarPantalla();
								System.out.println("Juego: " + juegos.get(idJuego).getTipo());
								System.out.println("Saldo del cliente " + clientes.get(idCliente).getNombre() + ": " + clientes.get(idCliente).getSaldo() + "$");
								System.out.println("Dinero de la máquina " + idJuego + ": " + juegos.get(idJuego).getDinero() + "$");
								
								// Comenzar el juego
								do {
									System.out.print("Ingrese su apuesta (minimo 2$) [0 - cancelar / 1 - guia]: ");
									double apuesta = validarNumeroDecimal(scn, APUESTA_MINIMA, APUESTA_MAXIMA, true, true);
												
										// Cancelar partida
										if (apuesta == 0) {
											System.out.println("Cancelando...");
											terminarJuego = true;
										
										// Ver guia
										} else if (apuesta == 1)  {
											limpiarPantalla();
											if (juegos.get(idJuego) instanceof Tragaperras) {
												System.out.println(guiaTragaperras);
												
											} else if (juegos.get(idJuego) instanceof BlackJack) {
												System.out.println(guiaBlackjack);
											}
											
										// Jugar
										} else {
											if (partidaPosible(idJuego, idCliente, juegos, clientes, apuesta)) {		
												limpiarPantalla();
												
												// Jugar a la tragaperras
												if (juegos.get(idJuego) instanceof Tragaperras) {
													Tragaperras juego = (Tragaperras) juegos.get(idJuego);
													
													juego.generarNumeros();
													System.out.println(juego + "\n");
													apuesta = juego.jugar(apuesta);	
												}	
												
												// Jugar al blackjack
												if (juegos.get(idJuego) instanceof BlackJack) {
													BlackJack juego = (BlackJack) juegos.get(idJuego);
													boolean clientePide = true;
													boolean clienteSeRinde = false;
													String masDe21 = "";
													
													juego.barajarCartas();
													juego.repartirCartas(2, "cliente");
													juego.repartirCartas(2, "crupier");
													
													// El cliente pide cartas
													do {
														limpiarPantalla();
														System.out.println(juego.mostrarCartas(true));
														if (juego.jugadorPierde("cliente")) {
															clientePide = false;
															masDe21 = "cliente";	
														
														} else {
															System.out.println("¿Que deseas hacer? [1 - Pedir / 2 - Plantarse / 0 - Rendirse");	
															menuOpcion = validarNumeroEntero(scn, 0, 2, false);
															
															switch (menuOpcion) {
																// Pedir
																case 1:
																	juego.repartirCartas(1, "cliente");
																	break;
																
																// Plantarse
																case 2:
																	clientePide = false;
																	break;
																	
																// Rendirse
																case 0:
																	clientePide = false;
																	clienteSeRinde = true;
																	break;
															}
														}
													} while (clientePide);
													limpiarPantalla();
													
													// Si el jugador no ha perdido ya
													if (!masDe21.equalsIgnoreCase("cliente") && !clienteSeRinde) {
														// Crupier pide
														while (true) {
															if (!juego.jugadorPierde("crupier")) {					
																if (juego.crupierDebePedir()) {
																	juego.repartirCartas(1, "crupier");
																	
																} else {
																	break;
																}
																
															// El  crupier se pasa de 21
															} else {
																masDe21 = "crupier";
																break;
															}
														}
														System.out.println(juego.mostrarCartas(false));
													}
													
													if (masDe21.equalsIgnoreCase("crupier")) {
														System.out.println("¡El crupier se ha pasado de 21!");
														apuesta *= 2;
													
													} else if (masDe21.equalsIgnoreCase("cliente")) {
														System.out.println("¡Te has pasado de 21!");	
														apuesta *= -1;
														
													} else {
														// El cliente se rindió despues de pedir cartas
														if (clienteSeRinde && juego.getCartasCliente().size() > 2) {
															System.out.println("Te rendiste tras pedir cartas.");
															apuesta = (apuesta * -1) * 0.75;
															
														// El cliente se rindió al principio
														} else if (clienteSeRinde) {
															System.out.println("Te rendiste al principio.");
															apuesta = (apuesta * -1) * 0.50;
														
														// 
														} else {
															apuesta = juego.jugar(apuesta);
														}	
													}
												}
												
												// Si la apuesta es mayor que el dinero de la maquina
												if (apuesta > juegos.get(idJuego).getDinero()) {
													apuesta = juegos.get(idJuego).getDinero();
													juegos.get(idJuego).setDinero(0);
												
												} else {
													juegos.get(idJuego).setDinero( juegos.get(idJuego).getDinero() - apuesta);
												}
												
												// Si la perdida es mayor que el saldo del cliente
												if ((clientes.get(idCliente).getSaldo() + apuesta) < 0) {
													clientes.get(idCliente).setSaldo(0);
												
												} else {
													clientes.get(idCliente).setSaldo( clientes.get(idCliente).getSaldo() + apuesta);
												}

												System.out.printf("Has " + (apuesta > 0 ? "ganado " : "perdido ") + "%.2f$\n", (apuesta * 1));
												System.out.printf("Saldo actual: %.2f$\n", clientes.get(idCliente).getSaldo());	
												System.out.printf("Dinero en el juego: %.2f$\n", juegos.get(idJuego).getDinero());	
											}
										}
								} while (!terminarJuego);
								terminarJuego = false;
								errorDato = false;
							}	
							
						} else {
							System.out.print("Error: Entrada no válida. ");
						}
					} while (errorDato);				
				}
				
				break;
			
			// Menú principal -> Menú administracion de usuarios
			case 2:
				limpiarPantalla();
				System.out.println("Administración de usuarios.");
				
				do {
					System.out.println("--------------");
					
					System.out.println("1 - Agregar usuario");
					System.out.println("2 - Consultar usuarios");
					System.out.println("3 - Modificar usuario");
					System.out.println("4 - Alternar baja");
					System.out.println("5 - Eliminar usuario");
					System.out.println("0 - Volver");
					
					menuOpcion = validarNumeroEntero(scn, 0, 5, false);
					
					switch (menuOpcion) {
					
					// Menú administración usuarios -> Agregar usuario
					case 1:
						limpiarPantalla();
						System.out.print("Ingrese el nombre y apellido del cliente: ");
						String nombreCliente = validarNombrePersona(scn, false);
						
						System.out.print("Ingrese la edad del cliente: ");
						int edadCliente = validarNumeroEntero(scn, EDAD_MINIMA, EDAD_MAXIMA, false);
						
						System.out.print("Ingrese el genero del cliente [H/M/O]: ");
						char generoCliente = validarGenero(scn, false);
						
						System.out.print("Ingrese el saldo del cliente: ");
						double saldoCliente = validarNumeroDecimal(scn, SALDO_MINIMO, SALDO_MAXIMO, false, false);
						
						limpiarPantalla();
						clientes.add(new Cliente(nombreCliente, edadCliente, generoCliente, false, saldoCliente));
						System.out.println("Cliente " + clientes.getLast().getNombre() + " con ID " + clientes.size() + " agregado.");
						
						break;
						
					// Menú administración usuarios -> Consultar usuarios
					case 2:
						
						limpiarPantalla();
						if (clientes.isEmpty()) {
							System.out.println("ERROR: No hay usuarios registrados.");
						
						} else {
							consultarClientes(clientes);
						}
						
						break;
						
					// Menú administración usuarios -> Modificar usuario		
					case 3:
						
						limpiarPantalla();
						if (clientes.isEmpty()) {
							System.out.println("ERROR: No hay usuarios registrados.");
						
						} else {
							System.out.print("Escriba el ID del usuario a modificar: ");						
							
							posicionBuscado = validarNumeroEntero(scn, 0, clientes.size(), true);
							
							if (posicionBuscado == 0) {
								System.out.println("Cancelando...");
								
							} else {
								posicionBuscado--;
								clientes = modificarCliente(scn, posicionBuscado, clientes);
								
								System.out.println("Usuario " + clientes.get(posicionBuscado).getNombre() + " modificado.");
							}																
						}
						break;
						
					// Menú administración usuarios -> Alternar baja de los usuarios	
					case 4:
						
						limpiarPantalla();
						if (clientes.isEmpty()) {
							System.out.println("ERROR: No hay usuarios registrados.");
						
						} else {
							System.out.print("Escriba el ID del usuario a alternar la baja (0 para cancelar): ");
							
							posicionBuscado = validarNumeroEntero(scn, 0, clientes.size(), true);
							
							
							if (posicionBuscado == 0) {
								System.out.println("Cancelando...");
									
							} else {
								posicionBuscado--;
								System.out.println("Usuario " + clientes.get(posicionBuscado).getNombre() + " dado de " + (clientes.get(posicionBuscado).isBaja() == false ? "baja." : "alta."));
								if (clientes.get(posicionBuscado).isBaja() == false) {
									clientes.get(posicionBuscado).setBaja(true);
									
								} else {
									clientes.get(posicionBuscado).setBaja(false);
								}
							}	
						}
						break;
						
					// Menú administración usuarios -> Eliminar usuario	
					case 5:
						limpiarPantalla();
						if (clientes.isEmpty()) {
							System.out.println("ERROR: No hay usuarios registrados.");
						
						} else {
							System.out.print("Escriba el ID del usuario a eliminar (0 para cancelar): ");
							posicionBuscado = validarNumeroEntero(scn, 0, clientes.size(), true);
							
							if (posicionBuscado == 0) {
								System.out.println("Cancelando...");
								
							} else {
								posicionBuscado--;			
								System.out.println("Usuario " + clientes.get(posicionBuscado).getNombre() + " eliminado.");
								clientes.remove(posicionBuscado);
							}				
						}
						
						break;
						
					
						
					// Menú administración usuarios -> Volver menú principal
					case 0:
						limpiarPantalla();
						System.out.println("Volviendo al menú principal...");
						System.out.println("--------------");
						salirMenu = true;
						break;
					
					}
				} while (!salirMenu);				
				break;
			
			// Menú principal -> Menú administracion de juegos
			case 3:
				limpiarPantalla();
				System.out.println("Administración de juegos");
				
				do {
					System.out.println("--------------");
									
					System.out.println("1 - Agregar juego");
					System.out.println("2 - Consultar juegos");
					System.out.println("3 - Alternar baja");
					System.out.println("4 - Eliminar juego");
					System.out.println("0 - Volver");	
					
					menuOpcion = validarNumeroEntero(scn, 0, 4, false);
					
					switch (menuOpcion) {
					
					// Menú administración máquinas -> Agregar máquina
					case 1:
						
						limpiarPantalla();
						System.out.print("Escribe el tipo de juego a agregar [0 - BlackJack / 1 - Tragaperras]: ");
						int tipoJuego = validarNumeroEntero(scn, 0, 1, false);
						System.out.print("Ingresa la cantidad de dinero que dispondrá el juego: ");
						double dinero = validarNumeroDecimal(scn, DINERO_MINIMO, DINERO_MAXIMO, false, false);
						
						if (tipoJuego == 0) {
							juegos.add(new BlackJack(dinero));
						
						} else if (tipoJuego == 1) {
							juegos.add(new Tragaperras(dinero));
						}
						
						limpiarPantalla();
						System.out.println("Juego con ID " + juegos.size()  + " agregado.");						
						
						break;
						
					// Menú administración juegos -> Consultar juegos
					case 2:
						
						limpiarPantalla();
						if (juegos.isEmpty()) {
							System.out.println("ERROR: No hay juegos registrados.");
						
						} else {				
							consultarJuegos(juegos);
						}
						
						break;	
						
					// Menú administración juegos -> Alternar baja de los juegos	
					case 3:
						
						limpiarPantalla();
						if (juegos.isEmpty()) {
							System.out.println("ERROR: No hay juegos registrados.");
						
						} else {
							System.out.print("Escriba el ID del juego a alternar la baja (0 para cancelar): ");
							
							posicionBuscado = validarNumeroEntero(scn, 0, juegos.size(), true);
							
							if (posicionBuscado == 0) {
								System.out.println("Cancelando...");
									
							} else {
								posicionBuscado--;
								System.out.println("Juego con ID " + (posicionBuscado + 1) + " dado de " + (juegos.get(posicionBuscado).isActivo() == false ? "baja." : "alta."));
								
								if (juegos.get(posicionBuscado).isActivo() == false) {
									juegos.get(posicionBuscado).setActivo(true);
									
								} else {
									juegos.get(posicionBuscado).setActivo(false);
								}
							}
						}
						break;
						
					// Menú administración máquinas -> Eliminar juego	
					case 4:
						
						limpiarPantalla();
						if (juegos.isEmpty()) {
							System.out.println("ERROR: No hay juegos registrados.");
						
						} else {
							System.out.print("Escriba el ID del juego a eliminar (0 para cancelar): ");
							posicionBuscado = validarNumeroEntero(scn, 0, juegos.size(), true);
							
							if (posicionBuscado == 0) {
								System.out.println("Cancelando...");
								break;
								
							} else {
								posicionBuscado--;
								System.out.println("Juego con ID " + (posicionBuscado + 1) + " eliminada.");
								juegos.remove(posicionBuscado);
							}										
						}					
						break;	
	
					// Menú administración máquinas -> Volver menú principal
					case 0:
						
						limpiarPantalla();
						System.out.println("Volviendo al menú principal...");
						System.out.println("--------------");
						salirMenu = true;
						break;
					
					}
					
				} while (!salirMenu);
				break;
			
			// Menú principal -> Menú administracion rápida
			case 4:
				
				limpiarPantalla();
				System.out.println("Administración rapida");
				
				do {
					System.out.println("--------------");
					System.out.println("1 - Agregar clientes");
					System.out.println("2 - Consultar clientes");
					System.out.println("3 - Vaciar clientes");
					System.out.println("---");
					System.out.println("4 - Agregar juegos");
					System.out.println("5 - Consultar Juegos");
					System.out.println("6 - Vaciar juegos");
					System.out.println("---");
					System.out.println("0 - Volver");
					
					menuOpcion = validarNumeroEntero(scn, 0, 6, false);
					
					switch (menuOpcion) {
					
						// Menú principal -> Menú administracion rápida -> Agregar clientes
						case 1:
							limpiarPantalla();
							clientes = agregarMuchosClientes(clientes, scn);
							break;
						
						// Menú principal -> Menú administracion rápida -> Ver clientes
						case 2:
							limpiarPantalla();
							if (clientes.isEmpty()) {
								System.out.println("ERROR: No hay clientes registrados.");
								
							} else {
								consultarClientes(clientes);
							}								
							break;
							
						// Menú principal -> Menú administracion rápida -> Vaciar clientes	
						case 3:
							
							limpiarPantalla();
							if (clientes.isEmpty()) {
								System.out.println("ERROR: No hay clientes registrados.");
								
							} else {
								System.out.println("Eliminados todos los clientes (" + clientes.size() +").");
								clientes.clear();
							}
							break;
						
						// Menú principal -> Menú administracion rápida -> Agregar juegos
						case 4:
							limpiarPantalla();
							juegos = agregarMuchosJuegos(juegos, scn);
							break;
						
						// Menú principal -> Menú administracion rápida -> Ver juegos
						case 5:
							limpiarPantalla();
							if (juegos.isEmpty()) {
								System.out.println("ERROR: No hay juegos registrados.");
								
							} else {
								consultarJuegos(juegos);
							}				
							break;
							
						// Menú principal -> Menú administracion rápida -> Vaciar juegos	
						case 6:
							limpiarPantalla();
							if (juegos.isEmpty()) {
								System.out.println("ERROR: No hay juegos registrados.");
								
							} else {
								System.out.println("Eliminados todos los juegos (" + juegos.size() +").");
								juegos.clear();
							}
							break;
						
						// Menú principal -> Menú administracion rápida -> Salir
						case 0:
							limpiarPantalla();
							System.out.println("Volviendo al menú principal...");
							salirMenu = true;
							break;
					}
				} while (!salirMenu);	
				break;
				
			// Menú principal -> Cerrar programa
			case 0:
				limpiarPantalla();
				System.out.println("Saliendo del programa...");
				terminarPrograma = true;
				break;
			}			
			
		} while (!terminarPrograma);
	}


	/**
	 * Comprueba diferentes condiciones que se deben cumplir para que sea posible jugar la partida con un usuario, cliente y apuesta dada.
	 * @param idJuego ID del juego.
	 * @param idCliente ID del cliente.
	 * @param juegos Lista de los juegos.
	 * @param clientes Lista de los clientes.
	 * @param apuesta Apuesta ingresada.
	 * @return La partida sería posible o no.
	 */
	private static boolean partidaPosible(int idJuego, int idCliente, ArrayList<Juego> juegos, ArrayList<Cliente> clientes, double apuesta) {
		
		// Juego no existente
		if (idJuego > juegos.size()) {
			System.out.print("Error: Juego con ID " + (idJuego + 1) + " no encontrado. ");
			return false;
			
		// Cliente no existente
		} else if (idCliente > clientes.size()) {
			System.out.print("Error: Cliente con ID " + (idCliente + 1) + " no encontrado. ");
			return false;	
		} 
		
		Cliente cliente = clientes.get(idCliente);
		Juego juego = juegos.get(idJuego);
		
		// Juego no activo
		if (!juego.isActivo()) {
			System.out.print("Error: Juego con ID " + (idJuego + 1) + " no está activo. ");
			return false;
			
		// Cliente de baja
		} else if (cliente.isBaja()) {
			System.out.print("Error: Cliente con ID " + (idCliente + 1) + " está dado de baja. ");
			return false;
			
		// Juego sin dinero
		} else if (juego.getDinero() == 0) {
			System.out.print("Error: Juego con ID " + (idJuego + 1) + " sin dinero. ");
			return false;
			
		// Cliente sin saldo
		} else if (cliente.getSaldo() < 2) {
			System.out.print("Error: Cliente con ID " + (idCliente + 1) + " sin suficiente saldo. ");
			return false;
			
		// Apuesta mayor que saldo cliente
		} else if (apuesta > cliente.getSaldo()) {
			System.out.print("Error: Apuesta mayor al saldo actual del cliente con ID " + (idCliente + 1) + ". ");
			return false;
		
		// Apuesta mayor con dinero maquina
		} else if (apuesta > juego.getDinero()) {
			System.out.print("Error: Apuesta mayor al dinero actual del juego con ID " + (idJuego + 1) + ". ");
			return false;
		
		} else {
			return true;
		}
	}


	/**
	 * Agrega una cantidad indicada de juegos con valores por defecto.
	 * @param Lista de juegos a modificar.
	 * @param scn Scanner para recibir texto.
	 * @return Lista de juegos modificada.
	 */
	private static ArrayList<Juego> agregarMuchosJuegos(ArrayList<Juego> juegos, Scanner scn) {
		int numClientes = 0;
		
		System.out.print("Ingresa el número de juegos a agregar (0 - Cancelar): ");
		
		numClientes = validarNumeroEntero(scn, 0, 200, true);
		
		if (numClientes != 0) {
			for (int i = 0; i < numClientes; i++) {
				double tipoJuego = Math.random();
				if (tipoJuego > 0.5) {
					juegos.add(new Tragaperras (2000));
				
				} else {
					juegos.add(new BlackJack (2000));
				}
				
			}
			System.out.println("Agregados " + numClientes + " juegos.");
			
		} else {
			System.out.println("Cancelando...");
		}
		
		return juegos;
	}


	/**
	 * Agrega una cantidad indicada de clientes con valores por defecto.
	 * @param Lista de clientes a modificar.
	 * @param scn Scanner para recibir texto.
	 * @return Lista de clientes modificada.
	 */
	private static ArrayList<Cliente> agregarMuchosClientes(ArrayList<Cliente> clientes, Scanner scn) {
		
		int numClientes = 0;
		
		System.out.print("Ingresa el número de clientes a agregar [0 - Cancelar]: ");
		
		numClientes = validarNumeroEntero(scn, 0, 200, true);
		
		if (numClientes != 0) {
			for (int i = 0; i < numClientes; i++) {
				clientes.add(new Cliente ("Cliente-" + (clientes.size() + 1), 20, 'O', false, 50));
			}
			System.out.println("Agregados " + numClientes + " clientes.");
		} else {
			System.out.println("Cancelando...");
		}
		
		return clientes;
	}

	
	/**
	 * Consulta todos los clientes registrados.
	 * @param lista Lista de clientes.
	 */
	private static void consultarClientes(ArrayList<Cliente> lista) {
		System.out.printf("%-5s|%-24s|%-6s|%-7s|%-6s|%10s %n", "Id", "Nombre", "Edad", "Genero", "Baja", "Saldo");
		System.out.println("-----------------------------------------------------------------");
		
		for (int i = 0; i < lista.size(); i++) {
			Cliente clienteActual = (Cliente) lista.get(i);
			System.out.println(clienteActual.imprimirCliente(i));
		}
	}
	
	
	/**
	 * Consultar todos los juegos registrados.
	 * @param lista Lista de juegos.
	 */
	private static void consultarJuegos(ArrayList<Juego> lista) {
		System.out.printf("%-5s|%-15s|%-7s|%10s %n", "Id", "Tipo", "Activo", "Dinero");
		System.out.println("-----------------------------------------------------------------");
		
		for (int i = 0; i < lista.size(); i++) {
			Juego juegoActual = (Juego) lista.get(i);

			System.out.println(juegoActual.imprimirJuego(i));
		}
	}
	 
	/**
	 * Modifica los datos de un usuario.
	 * @param scn Scanner para recibir texto.
	 * @param posicionUsuarioBuscado ID del cliente a modificar.
	 * @param clientes Lista de clientes.
	 * @return Lista de clientes con el usuario indicado modificado.
	 */
	private static ArrayList<Cliente> modificarCliente(Scanner scn, int posicionUsuarioBuscado, ArrayList<Cliente> clientes) {
		
		System.out.print("Ingrese un nuevo nombre [0 - no cambiar]: ");		
		String nuevoNombre = validarNombrePersona(scn, true);
		
		if (!nuevoNombre.matches("0") ) {
			clientes.get(posicionUsuarioBuscado).setNombre(nuevoNombre);
		}
		
		System.out.print("Escriba una nueva edad [0 - no cambiar]: ");		
		int nuevaEdad = validarNumeroEntero(scn, EDAD_MINIMA, EDAD_MAXIMA, true);
		
		if (nuevaEdad != 0 ) {
			clientes.get(posicionUsuarioBuscado).setEdad(nuevaEdad);
		}	
		
		System.out.print("Escriba un nuevo genero [0 - no cambiar]: ");		
		char nuevoGenero = validarGenero(scn, true);
		
		if (nuevoGenero != 0 ) {
			clientes.get(posicionUsuarioBuscado).setGenero(nuevoGenero);
		}
		
		System.out.print("Escriba un nuevo saldo [0 - no cambiar]: ");		
		double nuevoSaldo = validarNumeroDecimal(scn, SALDO_MINIMO, SALDO_MAXIMO, true, false);
		
		if (nuevoSaldo != 0 ) {
			clientes.get(posicionUsuarioBuscado).setSaldo(nuevoSaldo);		
		}
		
		return clientes;
		
	}	
	

	/**
	 * Valida el nombre de una persona.
	 * @param scn Scanner para recibir texto.
	 * @param cancelar Permite ingresar 0 para que no se realice ningún cambio en el main.
	 * @return El nombre completo de la persona validado.
	 */
	private static String validarNombrePersona(Scanner scn, boolean cancelar) {
	    String texto;
	    
	    do {
	        texto = scn.nextLine();
	        
	        if (texto.length() > 24) {
	            System.out.print("ERROR: Nombre demasiado largo (Máximo 24 caracteres). ");
	            
	        } else if (texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñ]+ [a-zA-ZáéíóúÁÉÍÓÚñ]+") || (cancelar && texto.equals("0"))) {
	            return texto;
	            
	        } else {
	            System.out.print("ERROR: Formato de nombre no válido [Nombre Apellido]. ");
	        }
	        
	    } while (true);
	}
		
	
	/**
	 * Valida un número decimal.
	 * @param scn Scanner para recibir texto.
	 * @param valorMinimo Valor mínimo del rango válido de números.
	 * @param valorMaximo Valor máximo del rango válido de números.
	 * @param cancelar Poder ingresar 0 para que no se realice ningún cambio en el main.
	 * @param guia Poder ingresar 1 para que muestre una guía (si se trata de un juego) en el main.
	 * @return Número decimal validado.
	 */
	private static double validarNumeroDecimal(Scanner scn, double valorMinimo, double valorMaximo, boolean cancelar, boolean guia ) {
	
	    double numero;
	    
	    while (true) {
	        if (scn.hasNextDouble()) {
	            numero = scn.nextDouble();

	            if ((numero >= valorMinimo && numero <= valorMaximo) || (cancelar && numero == 0) || (guia && numero == 1)) {
	                return numero; 
	            }
	            
	            System.out.print("ERROR: Número no válido [debe ser mayor a " + valorMinimo + " y menor que " + valorMaximo + "]. ");
	            
	        } else {
	            System.out.print("ERROR: Dato no válido, ingrese un número entero o decimal. ");
	        }
            scn.nextLine();
	    }
	}
	
		
	/**
	 * Valida numeros enteros entre un rango de numeros.
	 * @param scn Scanner para recibir texto.
	 * @param valorMinimo Valor mínimo del rango válido de números.
	 * @param valorMaximo Valor máximo del rango válido de números.
	 * @param cancelar Poder ingresar 0 para que no se realice ningún cambio en el main.
	 * @return Número entero validado.
	 */
	private static int validarNumeroEntero(Scanner scn, int valorMinimo, int valorMaximo, boolean cancelar) {
	    int numero;
	    
	    while (true) {
	        if (scn.hasNextInt()) {
	            numero = scn.nextInt();
	            
	            if ((numero >= valorMinimo && numero <= valorMaximo) || (cancelar && numero == 0)) {
	            	scn.nextLine();
	                return numero;
	            } else {
	                System.out.print("ERROR: Número no válido [debe ser mayor a " + valorMinimo + " y menor que " + valorMaximo + "]. ");
	            }
	        } else {
	            System.out.print("ERROR: Dato no válido, ingrese un número entero. ");
	        }
            scn.nextLine();
	    }
	}
	

	/**
	 * Validar opciones de genero. Permite Mujer, Hombre, Otro.
	 * @param scn Scanner para recibir texto.
	 * @param cancelar Poder ingresar 0 para que no se realice ningún cambio en el main.
	 * @return Opción de género validada.
	 */
	private static char validarGenero(Scanner scn, boolean cancelar) {
	    String letraString;
	    
	    while (true) {
	        letraString = scn.nextLine();
	        
	        if (letraString.matches("[HMO]") || (cancelar && letraString.equals("0"))) {
	            return letraString.charAt(0);
	            
	        } else {
	            System.out.print("ERROR: Dato no válido, escriba una de las opciones. ");
	        }
	    }
	}
	
	
	/**
	 * Limpia la pantalla si se está usando el programa en una cmd.
	 * Uso exclusivo para teminal.
	 */
	private static void limpiarPantalla() {
		System.out.print("\033[H\033[2J");
        System.out.flush();
	}
}