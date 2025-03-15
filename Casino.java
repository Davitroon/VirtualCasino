import java.util.Arrays;
import java.util.Scanner;

public class CasinoOld {
	
	// Programa que simula la gestión de un casino.
	// El usuario podra crear, consultar, gestionar y eliminar máquinas tragaperras / clientes.
	// El programa cuenta con menús para orientar al usuario con las diferentes opciones
	public static void main (String[] args) {
		
		Scanner scn = new Scanner (System.in);
		
		// - ASIGNACIÓN DE VARAIBLES -
		int menuOpcion = 0;
		int usuariosCreados = 0;
		int maquinasCreadas = 0;
		int posicionNombreBuscado = 0;
		final int EDAD_MAXIMA = 100;
		
		boolean terminarPrograma = false;
		boolean salirMenu = false;
		
		final double SALDO_MAXIMO = 10000;
		
		String nombreBuscado = null;
		
		System.out.print("Número máximo de usuarios: ");
		int usuariosMaximo = validarNumeroEntero(scn, 0, 10000);
		
		System.out.print("Número máximo de máquinas: ");
		int maquinasMaxima = validarNumeroEntero(scn, 0, 10000);
		
		// Arrays que servirán como las tablas del programa.
		String [] nombresUsuarios = new String [usuariosMaximo];
		double [] dineroUsuarios = new double [usuariosMaximo];
		int [] edadUsuarios = new int [usuariosMaximo];
		char [] generoUsuarios = new char [usuariosMaximo];
		boolean [] bajaUsuarios = new boolean [usuariosMaximo];
		
		String [] nombresMaquinas = new String [maquinasMaxima];
		
		System.out.println("Bienvenido al casino de David. ¿Que te gustaria hacer?");
		
		do {
			
			salirMenu = false;
			
			// Menú principal
			System.out.println("1 - Jugar");
			System.out.println("2 - Administrar usuarios");
			System.out.println("3 - Administrar tragaperras");
			System.out.println("0 - Salir");
			
			menuOpcion = validarOpcionMenu(0, 3, scn);
			
			switch (menuOpcion) {
			
			// Menú principal -> Menú juegos
			case 1:
				System.out.println("¡Por ahora solo puedes gestionar el casino, no jugar!");
				System.out.println("--------------");
				break;
			
			// Menú principal -> Menú administracion de usuarios
			case 2:
				System.out.println("Administración de usuarios.");
				
				do {
					System.out.println("--------------");
					
					System.out.println("1 - Agregar usuario");
					System.out.println("2 - Consultar usuarios");
					System.out.println("3 - Modificar usuario");
					System.out.println("4 - Eliminar usuario");
					System.out.println("5 - Alternar baja");			
					System.out.println("6 - Modificar aforo");
					System.out.println("0 - Volver");
					
					menuOpcion = validarOpcionMenu(0, 6, scn);
					
					switch (menuOpcion) {
					
					// Menú administración usuarios -> Agregar usuario
					case 1:
						
						if (usuariosCreados >= nombresUsuarios.length) {
							System.out.println("ERROR: Aforo máximo alcanzado.");
							
						} else {
							nombresUsuarios = agregarNombre(scn, nombresUsuarios, usuariosCreados, 0);
							edadUsuarios = agregarEdad(scn, edadUsuarios, EDAD_MAXIMA);
							generoUsuarios = agregarGenero(scn, generoUsuarios);
							dineroUsuarios = agregarSaldo(scn, dineroUsuarios, nombresUsuarios, SALDO_MAXIMO);
							System.out.println("Usuario agregado \n--------------");
							usuariosCreados++;
						}
						
						break;
						
					// Menú administración usuarios -> Consultar usuarios
					case 2:
						
						if (usuariosCreados == 0) {
							System.out.println("ERROR: No hay usuarios registrados.");
						
						} else {
							consultarUsuarios(nombresUsuarios, edadUsuarios, generoUsuarios, bajaUsuarios, dineroUsuarios, usuariosCreados);
						}
						
						break;
						
					// Menú administración usuarios -> Modificar usuario		
					case 3:

						if (usuariosCreados == 0) {
							System.out.println("ERROR: No hay usuarios registrados.");
						
						} else {
							System.out.print("Escriba el nombre y apellido del usuario a modificar: ");
							
							nombreBuscado = validarNombrePersona(scn, false);
							posicionNombreBuscado = buscarNombre (nombreBuscado, nombresUsuarios);
							
							if (posicionNombreBuscado == -1) {
								System.out.println("ERROR: El usuario \"" + nombreBuscado + "\" no está registrado.");
							
							} else {
								nombresUsuarios = modificarUsuario(scn, posicionNombreBuscado, nombresUsuarios);
								dineroUsuarios = modificarSaldo(scn, posicionNombreBuscado, dineroUsuarios, SALDO_MAXIMO);
								edadUsuarios = modificarEdad(scn, posicionNombreBuscado, edadUsuarios, EDAD_MAXIMA);
								scn.nextLine();
								generoUsuarios = modificarGenero(scn, posicionNombreBuscado, generoUsuarios);
								
								System.out.println("Usuario modificado.");
							}
						}
						
						break;
						
					// Menú administración usuarios -> Eliminar usuario	
					case 4:
						
						if (usuariosCreados == 0) {
							System.out.println("ERROR: No hay usuarios registrados.");
						
						} else {
							System.out.print("Escriba el nombre y apellido del usuario a eliminar (0 para cancelar): ");
							nombreBuscado = validarNombrePersona(scn, true);
							
							if (nombreBuscado.matches("0") ) {
								System.out.println("Cancelando...");
								break;
								
							} else {
								posicionNombreBuscado = buscarNombre (nombreBuscado, nombresUsuarios);
								
								if (posicionNombreBuscado == -1) {
									System.out.println("ERROR: El usuario \"" + nombreBuscado + "\" no está registrado.");
								
								} else {
									System.out.println("Usuario \"" + nombresUsuarios[posicionNombreBuscado] + "\" eliminado.");
									
									// El programa moverá los siguientes usuarios que se hayan creado despues del usuario eliminado.
									// Si es el último usuario del array, no moverá ningun dato.
									if (posicionNombreBuscado != nombresUsuarios.length - 1) {
										for (int i = posicionNombreBuscado; i < usuariosCreados; i++) {
											
											if (i == nombresUsuarios.length - 1) {
												nombresUsuarios[i] = null;
												dineroUsuarios[i] = 0;
												edadUsuarios[i] = 0;
												generoUsuarios[i] = 0;
												bajaUsuarios[i] = false;
												
											} else {
												nombresUsuarios[i] = nombresUsuarios[i + 1];
												nombresUsuarios[i + 1] = null;
												
												dineroUsuarios[i] = dineroUsuarios[i + 1];
												dineroUsuarios[i + 1] = 0;
												
												edadUsuarios[i] = edadUsuarios[i + 1];
												edadUsuarios[i + 1] = 0;
												
												generoUsuarios[i] = generoUsuarios[i + 1];
												generoUsuarios[i + 1] = 0;
												
												bajaUsuarios[i] = bajaUsuarios[i + 1];
												bajaUsuarios[i + 1] = false;
											}
										}
									} else {
										nombresUsuarios[posicionNombreBuscado] = null;
										dineroUsuarios[posicionNombreBuscado] = 0;
										edadUsuarios[posicionNombreBuscado] = 0;
										generoUsuarios[posicionNombreBuscado] = 0;
										bajaUsuarios[posicionNombreBuscado] = false;
									}						

									usuariosCreados--;
									break;
								}
							}							
						}
						
						break;
						
					// Menú administración usuarios -> Alternar baja de los usuarios	
					case 5:
						
						if (usuariosCreados == 0) {
							System.out.println("ERROR: No hay usuarios registrados.");
						
						} else {
							System.out.print("Escriba el nombre y apellido del usuario a alternar la baja: ");
							
							nombreBuscado = validarNombrePersona(scn, false);
							posicionNombreBuscado = buscarNombre (nombreBuscado, nombresUsuarios);
							
							if (posicionNombreBuscado == -1) {
								System.out.println("ERROR: El usuario \"" + nombreBuscado + "\" no está registrado.");
							
							} else {
								System.out.println(bajaUsuarios[posicionNombreBuscado] == false ? "Usuario " + nombreBuscado + " dado de baja.": "Usuario " + nombreBuscado + " dado de alta.");
								
								if (bajaUsuarios[posicionNombreBuscado] == false) {
									bajaUsuarios[posicionNombreBuscado] = true;
									
								} else {
									bajaUsuarios[posicionNombreBuscado] = false;
								}
							}
						}
						
						break;
						
					// Menú administración usuarios -> Modificar aforo máximo
					case 6:
						System.out.print("Ingrese el nuevo aforo máximo de usuarios (Si es un número menor al aforo actual, es posible que algunos usuarios se pierdan): ");
						usuariosMaximo = validarNumeroEntero(scn, 0, 10000);
						
						if (usuariosMaximo == nombresUsuarios.length) {
							System.out.println("ERROR: El aforo máximo ya es de " + usuariosMaximo + ".");
						
						} else {
							// Sobreescribo el array con una copia del mismo pero con la longitud que haya escrito el usuario.
							nombresUsuarios = Arrays.copyOf(nombresUsuarios, usuariosMaximo);
							dineroUsuarios = Arrays.copyOf(dineroUsuarios, usuariosMaximo);
							edadUsuarios = Arrays.copyOf(edadUsuarios, usuariosMaximo);
							generoUsuarios = Arrays.copyOf(generoUsuarios, usuariosMaximo);
							bajaUsuarios = Arrays.copyOf(bajaUsuarios, usuariosMaximo);
							
							System.out.println("Aforo redimensionado a " + usuariosMaximo + ".");
						}						

						break;
						
					// Menú administración usuarios -> Volver menú principal
					case 0:
						System.out.println("Volviendo al menú principal...");
						System.out.println("--------------");
						salirMenu = true;
						break;
					
					}
				} while (!salirMenu);				
				break;
			
			// Menú principal -> Menú administracion de máquinas
			case 3:
				System.out.println("Administración de máquinas tragaperras");
				
				do {
					System.out.println("--------------");
									
					System.out.println("1 - Agregar máquina");
					System.out.println("2 - Consultar máquinas");
					System.out.println("3 - Eliminar máquina");
					System.out.println("4 - Modificar aforo de máquinas");	
					System.out.println("0 - Volver");	
					
					menuOpcion = validarOpcionMenu(0, 5, scn);
					
					switch (menuOpcion) {
					
					// Menú administración máquinas -> Agregar máquina
					case 1:
						
						if (maquinasCreadas >= nombresMaquinas.length) {
							System.out.println("ERROR: Aforo máximo alcanzado.");
							
						} else {
							nombresMaquinas = agregarNombre(scn, nombresMaquinas, maquinasCreadas, 1);
							System.out.println("Máquina agregada.");
							maquinasCreadas++;
						}
						
						break;
						
					// Menú administración máquinas -> Consultar maquinas
					case 2:
						
						if (maquinasCreadas == 0) {
							System.out.println("ERROR: No hay máquinas registradas.");
						
						} else {
							
							System.out.printf("%-5s|%-24s %n", "Id", "Nombre");
							System.out.println("------------------------------");
							
							for (int i = 0; i < maquinasCreadas; i++) {
								if (nombresMaquinas[i] != null) {
									System.out.printf("%-5s|%-24s%n", (i + 1), nombresMaquinas[i]);
								}
							}
							
							System.out.println();
						}
						
						break;			
						
					// Menú administración máquinas -> Eliminar maquina	
					case 3:
						
						if (maquinasCreadas == 0) {
							System.out.println("ERROR: No hay máquinas registradas.");
						
						} else {
							System.out.print("Escriba el nombre de la máquina a eliminar (0 para cancelar): ");
							nombreBuscado = validarNombreMaquina(scn);
							
							if (nombreBuscado.matches("0") ) {
								System.out.println("Cancelando...");
								break;
								
							} else {
								posicionNombreBuscado = buscarNombre (nombreBuscado, nombresMaquinas);
								
								if (posicionNombreBuscado == -1) {
									System.out.println("ERROR: La máquina \"" + nombreBuscado + "\" no está registrada.");
								
								} else {
									System.out.println("Máquina \"" + nombresMaquinas[posicionNombreBuscado] + "\" eliminada.");

									if (posicionNombreBuscado != nombresMaquinas.length - 1) {
										for (int i = posicionNombreBuscado; i < maquinasCreadas; i++) {
											
											if (i == nombresMaquinas.length - 1) {
												nombresMaquinas[i] = null;
												
											} else {
												nombresMaquinas[i] = nombresMaquinas[i + 1];
												nombresMaquinas[i + 1] = null;
											}
										}
										
									} else {
										nombresMaquinas[posicionNombreBuscado] = null;
									}						

									maquinasCreadas--;
									break;
								}
							}							
						}
						
						break;
						
						
					// Menú administración máquinas -> Modificar aforo máximo
					case 4:
						System.out.print("Ingrese el nuevo aforo máximo de máquinas (Si es un número menor al aforo actual, es posible que algunos usuarios se pierdan): ");
						maquinasMaxima = validarNumeroEntero(scn, 0, 10000);
						
						if (maquinasMaxima == nombresMaquinas.length) {
							System.out.println("ERROR: El aforo máximo ya es de " + maquinasMaxima + ".");
						
						} else {
							nombresMaquinas = Arrays.copyOf(nombresMaquinas, maquinasMaxima);
							if (maquinasCreadas > maquinasMaxima) {
								maquinasCreadas = maquinasMaxima;
							}
							
							System.out.println("Aforo redimensionado a " + maquinasMaxima + ".");
						}						

						break;
						
					// Menú administración máquinas -> Volver menú principal
					case 0:
						System.out.println("Volviendo al menú principal...");
						System.out.println("--------------");
						salirMenu = true;
						break;
					
					}
					
				} while (!salirMenu);
				break;
				
				
			// Menú principal -> Cerrar programa
			case 0:
				System.out.println("Saliendo del programa...");
				terminarPrograma = true;
				break;
			}
			
			
		} while (!terminarPrograma);
	}
	
	
	// ----------------- METODOS -----------------


	// Agregar el nombre a un dato, este metodo servirá para varios arrays.
	// Tipo 0 = Usuario. Tipo 1 = Máquina.
	private static String[] agregarNombre(Scanner scn, String[] listaDatos, int datosCreados, int tipo) {	
		
		String nombre = null;
		boolean nombreRepetido = false;

		for (int i = 0; i < listaDatos.length; i++) {
			if (listaDatos[i] == null) {
				
				if (tipo == 0) {
					System.out.print("Escriba el nombre y apellido del usuario: ");
					
				} else if (tipo == 1) {
					System.out.print("Escriba el nombre de la máquina tragaperras: ");
				}		
				
				do {
					
					if (tipo == 0) {
						nombre = validarNombrePersona(scn, false);
						
					} else if (tipo == 1) {
						nombre = validarNombreMaquina(scn);
					}				
					
					if (datosCreados != 0) {
						for (int j = 0; j < datosCreados; j++) {				
							if (listaDatos[j].equalsIgnoreCase(nombre)) {
								System.out.print(nombre + " ya está registrado, ingrese otro nombre. ");
								nombreRepetido = true;
								break;
								
							} else {
								nombreRepetido = false;
							}
						}
						
					} else {
						listaDatos[i] = nombre;
						return listaDatos;
					}
					
				} while (nombreRepetido);
				
				listaDatos[i] = nombre;
				
				break;
			}
		}
	
		return listaDatos;
	}	
	
	
	// Agrega la edad de un usuario.
	private static int[] agregarEdad(Scanner scn, int[] edadUsuarios, int EDAD_MAXIMA) {
		
		for (int i = 0; i < edadUsuarios.length; i++) {
			if (edadUsuarios[i] == 0) {
				System.out.print("Escriba la edad del usuario: ");
				edadUsuarios[i] = validarNumeroEntero(scn, 0, EDAD_MAXIMA);
				
				break;
			}
		}
		
		return edadUsuarios;
	}
	
	
	// Agrega el genero de un usuario.
	private static char[] agregarGenero(Scanner scn, char[] generoUsuarios) {
		
		for (int i = 0; i < generoUsuarios.length; i++) {
			if (generoUsuarios[i] == 0) {
				System.out.print("Escriba el género del usuario [H / M / O]: ");
				generoUsuarios[i] = validarGenero(scn, false);
				
				break;
			}
		}
		
		
		return generoUsuarios;
	}


	// Agrega el saldo a un usuario.
	private static double[] agregarSaldo(Scanner scn, double[] dineroUsuarios, String[] nombresUsuarios, double SALDO_MAXIMO) {
		
		for (int i = 0; i < dineroUsuarios.length; i++) {
			if (dineroUsuarios[i] == 0) {
				System.out.print("Escriba el saldo del usuario: ");
				dineroUsuarios[i] = validarNumeroDecimal(scn, 0, SALDO_MAXIMO);

				break;
			}
		}
	
		return dineroUsuarios;
	}
		

	// Muestra todos los usuarios registrados en formato tabla, ignorando los que tengan valores nulos.
	private static void consultarUsuarios(String[] nombresUsuarios, int[] edadUsuarios, char[] generoUsuarios, boolean[] bajaUsuarios, double[] dineroUsuarios, int usuariosCreados) {
		
		System.out.printf("%-5s|%-24s|%-6s|%-7s|%-6s|%10s %n", "Id", "Nombre", "Edad", "Genero", "Baja", "Saldo");
		System.out.println("-----------------------------------------------------------------");
		
		for (int i = 0; i < usuariosCreados; i++) {
			if (nombresUsuarios[i] != null) {
				System.out.printf("%-5s|%-24s|%-6d|%-7c|%-6b|%10.2f € %n", (i + 1), nombresUsuarios[i], edadUsuarios[i], generoUsuarios[i], bajaUsuarios[i], dineroUsuarios[i]);
			}
		}
		
		System.out.println();
	}
	
	
	// Busca un usuario y obtiene la posicion en el que se encuentra. Devuelve -1 si no está registrado.
	private static int buscarNombre(String nombreBuscado, String[] listaDatos) {

		for (int i = 0; i < listaDatos.length; i++) {
			
			if (listaDatos[i] != null && listaDatos[i].equalsIgnoreCase(nombreBuscado)) {
			    return i;
			}
		}	
		return -1;
	}
	
	
	// Modifica el nombre de un usuario.
	private static String[] modificarUsuario(Scanner scn, int posicionUsuarioBuscado, String[] nombresUsuarios) {
		
		System.out.print("Ingrese un nuevo nombre ('0' para no cambiar): ");
		
		String nuevoNombre = validarNombrePersona(scn, true);
		
		if (!nuevoNombre.matches("0") ) {
			nombresUsuarios[posicionUsuarioBuscado] = nuevoNombre;		
		}
		
		return nombresUsuarios;
		
	}
	
	
	// Modifica la edad de un usuario.
	private static int[] modificarEdad(Scanner scn, int posicionUsuarioBuscado, int[] edadUsuarios, int EDAD_MAXIMA) {
		System.out.print("Escriba una nueva edad ('0' para no cambiar): ");
		
		int nuevaEdad = validarNumeroEntero(scn, 0, EDAD_MAXIMA);
		
		if (nuevaEdad != 0 ) {
			edadUsuarios[posicionUsuarioBuscado] = nuevaEdad;
		}
		
		return edadUsuarios;
	}
	
	
	// Modifica el genero de un usuario.
	private static char[] modificarGenero(Scanner scn, int posicionUsuarioBuscado, char[] generoUsuarios) {
		System.out.print("Escriba un nuevo genero ('0' para no cambiar): ");
		
		char nuevoGenero = validarGenero(scn, true);
		
		if (nuevoGenero != 0 ) {
			generoUsuarios[posicionUsuarioBuscado] = nuevoGenero;
		}
		
		return generoUsuarios;
	}
	

	// Modifica el saldo de un usuario.
	private static double[] modificarSaldo(Scanner scn, int posicionUsuarioBuscado, double[] dineroUsuarios, double SALDO_MAXIMO) {
		System.out.print("Escriba un nuevo saldo ('0' para no cambiar): ");
		
		double nuevoSaldo = validarNumeroDecimal(scn, 0, SALDO_MAXIMO);
		
		if (nuevoSaldo != 0 ) {
			dineroUsuarios[posicionUsuarioBuscado] = nuevoSaldo;		
		}
		
		return dineroUsuarios;
		
	}
	
	
	// Valida que la opcion escrita sea un número y esté en la lista de opciones que el usuario indique.
	private static int validarOpcionMenu(int opcionMinima, int opcionMaxima, Scanner scn) {

		boolean errorDato = true;
		int  numero = 0;
	
		do {
			if (scn.hasNextInt() ) {
				numero = scn.nextInt();
				
				if (numero < opcionMinima || numero > opcionMaxima) {
					System.out.print("ERROR: Opción no válida. ");
					
				} else {
					errorDato = false;
				}
				
			} else {
				System.out.print("ERROR: Dato no válido, ingrese un número entero. ");
			}
			
			scn.nextLine();
			
		} while (errorDato);
		
		return numero;
	}	
	
	
	// Valida el nombre de una máquina tragaperras.
	private static String validarNombreMaquina(Scanner scn) {
		String texto;
		boolean errorDato = false;
		
		do {			
			texto = scn.nextLine();
			
			if (texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñ]+")) {
					if (texto.length() > 24 ) {
						System.out.print("ERROR: Nombre demasiado largo [Máximo 24 caracteres]. ");
						errorDato = true;
					
					} else {
						errorDato = false;
					}
					
				} else {
					System.out.print("ERROR: Formato de nombre no válido. Escriba todo junto. ");
					errorDato = true;
				}
			
		} while (errorDato);
		
		return texto;
	}
	
	
	// Valida el nombre de una persona. Si cancelar es verdadero, al escribir 0 no cambiará el nombre.
		private static String validarNombrePersona(Scanner scn, boolean cancelar) {

			String texto;
			boolean errorDato = false;
			
			do {
				
				texto = scn.nextLine();
				
				if (cancelar) {
					
					if (texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñ]+ [a-zA-ZáéíóúÁÉÍÓÚñ]+") || texto.matches("0") ) {
						if (texto.length() > 24 ) {
							System.out.print("ERROR: Nombre demasiado largo [Máximo 24 caracteres]. ");
							errorDato = true;
						
						} else {
							
							return texto;
						}
						
					} else {
						System.out.print("ERROR: Formato de nombre no válido [Nombre Apellido]. ");
						errorDato = true;
					}
					
				} else {
					if (texto.matches("[a-zA-ZáéíóúÁÉÍÓÚñ]+ [a-zA-ZáéíóúÁÉÍÓÚñ]+")) {
						if (texto.length() > 24 ) {
							System.out.print("ERROR: Nombre demasiado largo [Máximo 24 caracteres]. ");
							errorDato = true;
						
						} else {
							return texto;
						}
						
					} else {
						System.out.print("ERROR: Formato de nombre no válido [Nombre Apellido]. ");
						errorDato = true;
					}
				}
			
			} while (errorDato);
				
			return null;
		}
		
	
		// Valida numeros decimales entre el rango que ingrese el usuario.
		private static double validarNumeroDecimal(Scanner scn, double valorMinimo, double valorMaximo) {
		
		boolean errorDato = true;
		double numero = 0;
		
		do {
			if (scn.hasNextDouble() ) {
				numero = scn.nextDouble();
				
				if (numero <= valorMinimo || numero >= valorMaximo) {
					System.out.print("ERROR: Número no válido [debe ser mayor a " + valorMinimo + " y menor que " + valorMaximo + "]. ");
				
				} else {
					errorDato = false;
				}
				
			} else {
				System.out.print("ERROR: Dato no válido, ingrese un número entero o decimal. ");
			}
		
			scn.nextLine();
			
		} while (errorDato);

		return numero;
	}
	
		
	// Valida números enteros entre el rango que ingrese el usuario.
	private static int validarNumeroEntero(Scanner scn, int valorMinimo, int valorMaximo) {
		
		boolean errorDato = true;
		int numero = 0;
		
		do {
			if (scn.hasNextInt() ) {
				numero = scn.nextInt();
				
				if (numero <= valorMinimo || numero >= valorMaximo) {
					System.out.print("ERROR: Número no válido [debe ser mayor a " + valorMinimo + " y menor que " + valorMaximo + "]. ");
				
				} else {
					errorDato = false;
				}
				
			} else {
				System.out.print("ERROR: Dato no válido, ingrese un número entero. ");
			}
		
			scn.nextLine();
			
		} while (errorDato);

		return numero;
	}
	
	
	// Validar opciones de genero. Permito Mujer, Hombre, Otro y Cancelar si es verdadero.
	private static char validarGenero(Scanner scn, boolean cancelar) {
		
		boolean errorDato = true;
		String letraString = null;
		char letra = 0;
		
		do {
			letraString = scn.nextLine();
			
			if (cancelar) {			
				if (letraString.matches("H") || letraString.equals("M") || letraString.equals("O") || letraString.equals("0")) {

					errorDato = false;	
					
					letra = letraString.charAt(0);
					
				} else {
					System.out.print("ERROR: Dato no válido, escriba una de las opciones. ");
				}
				
			} else {
				if (letraString.matches("H") || letraString.equals("M") || letraString.equals("O")) {

					errorDato = false;	
					
					letra = letraString.charAt(0);
					
				} else {
					System.out.print("ERROR: Dato no válido, escriba una de las opciones. ");
				}
			}		 
		} while (errorDato);

		return letra;	
	} 	
}