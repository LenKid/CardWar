package control;

import java.util.Scanner;
import model.*;

/*
 * ==========================
 *   GUERRA DE CARTAS - REGLAS
 * ==========================
 * 1. Se reparten todas las cartas entre dos jugadores.
 * 2. En cada ronda, ambos jugadores sacan la primera carta de su mazo.
 *    - El jugador con la carta de mayor valor gana ambas cartas y las coloca al final de su mazo.
 *    - Si hay empate, se inicia una "guerra":
 *        a) Cada jugador coloca una carta boca abajo (oculta) y otra boca arriba.
 *        b) El valor de la nueva carta boca arriba decide quién gana todas las cartas jugadas en ese turno.
 *        c) En caso de empate en la guerra, se puede repetir el proceso si ambos tienen suficientes cartas.
 *    - En este juego, el jugador elige qué cartas usar en la guerra.
 * 3. El juego termina cuando un jugador se queda sin cartas o el usuario decide salir.
 */
public class JuegoCartas {

    public void iniciarJuego() {
        Scanner sc = new Scanner(System.in);

        // Crear arreglo de cartas (1-52)
        int[] cartas = new int[52];
        for (int i = 0; i < 52; i++) cartas[i] = i + 1;

        //  Barajar las cartas usando el algoritmo Fisher-Yates
        //Fisher-Yates: es un algoritmo que se encarga de desordenar aleatoriamente un grupo de items.
        for (int i = 51; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            int temp = cartas[i];
            cartas[i] = cartas[j];
            cartas[j] = temp;
        }

    // Crear lista enlazada para la baraja y llenarla con las cartas
        ListaEnlazada baraja = new ListaEnlazada();
        for (int i = 0; i < 52; i++) {
            baraja.insertarNodoFinal(cartas[i]);
        }

    // (IMPLEMENTACION COLA) Repartir las cartas entre el jugador y la CPU usando dos colas
        Cola mazoJugador = new Cola();
        Cola mazoCPU = new Cola();
        Nodo actual = baraja.getCabecera();
        boolean turno = true;
        int count = 0;
        do {
            if (turno) mazoJugador.enColar(actual.getValor());
            else mazoCPU.enColar(actual.getValor());
            actual = actual.getSiguiente();
            turno = !turno;
            count++;
        } while (actual != baraja.getCabecera() && count < 52);

        
   //(IMPLEMENTACION TABLA HASH) Se Crea tabla hash para guardar estado inicial de cartas de cada jugador

        TablaHash estado = new TablaHash(2);
        estado.insertarMazoCantidad(0, mazoJugador.getInicio() != null ? 26 : 0);
        estado.insertarMazoCantidad(1, mazoCPU.getInicio() != null ? 26 : 0);

        //(IMPLEMENTACION PILA) Pila para guardar historial de cartas jugadas
        Pila historial = new Pila();

     //(IMPLEMENTACION ARBOL BINARIO) Se crea un árbol binario para manejar las guerras
        ArbolBinario arbol = new ArbolBinario();

   // Pedir nombre del jugador y crear estados de ambos jugadores
        System.out.print("Ingrese nombre del Jugador 1: ");
        String nombreJugador = sc.nextLine();
        String nombreCPU = "CPU";

        JugadorEstado estadoJugador = new JugadorEstado(nombreJugador);
        JugadorEstado estadoCPU = new JugadorEstado(nombreCPU);
        estadoJugador.setCartasRestantes(26);
        estadoCPU.setCartasRestantes(26);

        // Guardar los estados en tabla hash
        TablaHash estadoJugadorActual = new TablaHash(2);
        estadoJugadorActual.insertar(0, estadoJugador);
        estadoJugadorActual.insertar(1, estadoCPU);
// Variables para contar rondas, victorias y empates
        int rondas = 1, ganadasJugador = 0, ganadasCPU = 0, empates = 0;
// Mensajes de bienvenida e instruccione
        System.out.println("\nBienvenido a Guerra de Cartas 🛡️");
        System.out.println(
            "===================================\n" +
            "        GUERRA DE CARTAS - REGLAS  \n" +
            "===================================\n" +
            "1. Se reparten todas las cartas entre dos jugadores.\n" +
            "2. En cada ronda, ambos jugadores sacan la primera carta de su mazo.\n" +
            "   - El jugador con la carta de mayor valor gana ambas cartas y las coloca al final de su mazo.\n" +
            "   - Si hay empate, se inicia una 'guerra':\n" +
            "     a) Cada jugador coloca una carta boca abajo (oculta) y otra boca arriba.\n" +
            "     b) El valor de la nueva carta boca arriba decide quién gana todas las cartas jugadas en ese turno.\n" +
            "     c) En caso de empate en la guerra, se puede repetir el proceso si ambos tienen suficientes cartas.\n" +
            "   - En este juego, el jugador elige qué cartas usar en la guerra.\n" +
            "3. El juego termina cuando un jugador se queda sin cartas o el usuario decide salir.\n" +
            "===================================\n"
        );
        // Ciclo principal del juego
        while (!mazoJugador.esVacia() && !mazoCPU.esVacia()) {
            System.out.println("\n--- Ronda " + rondas + " ---");
            
            int cartaJ = mazoJugador.desenColar();
            int cartaC = mazoCPU.desenColar();

        // Guardar en historial
            historial.apilar(cartaJ);
            historial.apilar(cartaC);
        // Mostrar cartas jugadas
            System.out.println(nombreJugador + " juega: " + cartaToString(cartaJ));
            System.out.println(nombreCPU + " juega: " + cartaToString(cartaC));
        // Comparar valores
            if (valorCarta(cartaJ) > valorCarta(cartaC)) {
              // Gana la ronda el jugador
                System.out.println("Ganador: " + nombreJugador);
                mazoJugador.enColar(cartaJ);
                mazoJugador.enColar(cartaC);
                ganadasJugador++;
            } else if (valorCarta(cartaJ) < valorCarta(cartaC)) {
                 // Gana la ronda la CPU
                System.out.println("Ganador: " + nombreCPU);
                mazoCPU.enColar(cartaJ);
                mazoCPU.enColar(cartaC);
                ganadasCPU++;
            } else {
                // Empate, se inicia la guerra
                System.out.println("Empate! Se inicia la guerra...");
                empates++;
                arbol.insertar(1);

                // Verificar si ambos pueden continuar
                boolean jugadorPuede = contarCartas(mazoJugador) >= 2;
                boolean cpuPuede = contarCartas(mazoCPU) >= 2;
                if (!jugadorPuede || !cpuPuede) {
                     // Alguno no puede continuar
                    System.out.println("Algún jugador no tiene suficientes cartas para la guerra.");
                    if (!jugadorPuede) System.out.println(nombreJugador + " pierde la guerra.");
                    if (!cpuPuede) System.out.println(nombreCPU + " pierde la guerra.");
                    break;
                }

                 // El jugador elige cartas para la guerra
                System.out.println("\nTus cartas actuales:");
                mostrarCartasJugador(mazoJugador);

            
                int posBocaAbajo = pedirCarta(sc, mazoJugador, "Selecciona la posición de la carta que pondrás boca abajo (1 = primera carta): ");
                int cartaBocaAbajo = extraerCartaPorPosicion(mazoJugador, posBocaAbajo);
              
                System.out.println("\nTus cartas restantes:");
                mostrarCartasJugador(mazoJugador);
                int posBocaArriba = pedirCarta(sc, mazoJugador, "Selecciona la posición de la carta que pondrás boca arriba (1 = primera carta): ");
                int cartaBocaArriba = extraerCartaPorPosicion(mazoJugador, posBocaArriba);

                // CPU saca 2 cartas automaticamente
                int bocaAbajoC = mazoCPU.desenColar();
                int guerraC = mazoCPU.desenColar();
            // Mostrar cartas boca arriba
                System.out.println(nombreJugador + " coloca: " + cartaToString(cartaBocaArriba) + " (boca arriba)");
                System.out.println(nombreCPU + " coloca: " + cartaToString(guerraC) + " (boca arriba)");
                // Comparar cartas de guerra
                if (valorCarta(cartaBocaArriba) > valorCarta(guerraC)) {
                    System.out.println("Ganador de la guerra: " + nombreJugador);
                    mazoJugador.enColar(cartaJ); mazoJugador.enColar(cartaC);
                    mazoJugador.enColar(cartaBocaAbajo); mazoJugador.enColar(bocaAbajoC);
                    mazoJugador.enColar(cartaBocaArriba); mazoJugador.enColar(guerraC);
                    ganadasJugador++;
                    //gana el que tenga la carta boca arriba de mayor valor
                } else if (valorCarta(cartaBocaArriba) < valorCarta(guerraC)) {
                    System.out.println("Ganador de la guerra: " + nombreCPU);
                    mazoCPU.enColar(cartaJ); mazoCPU.enColar(cartaC);
                    mazoCPU.enColar(cartaBocaAbajo); mazoCPU.enColar(bocaAbajoC);
                    mazoCPU.enColar(cartaBocaArriba); mazoCPU.enColar(guerraC);
                    ganadasCPU++;
                } else {
                    // Otro empate
                    System.out.println("¡Empate en la guerra!");
                }
            }

            // Actualizar estado en tabla hash(Actualizar estado de ambos jugadores)
            estadoJugador.setCartasRestantes(contarCartas(mazoJugador));
            estadoCPU.setCartasRestantes(contarCartas(mazoCPU));
            estadoJugador.setVictorias(ganadasJugador);
            estadoCPU.setVictorias(ganadasCPU);
            estadoJugador.setEmpates(empates);
            estadoCPU.setEmpates(empates);
            estado.insertar(0, estadoJugador);
            estado.insertar(1, estadoCPU);

            // Mostrar estado actual de ambos jugadores
            System.out.println("\n--- Estado actual ---");
            System.out.println(estadoJugador.getNombre() + " tiene " + estadoJugador.getCartasRestantes() + " cartas | Victorias: " + estadoJugador.getVictorias() + " | Empates: " + estadoJugador.getEmpates());
            System.out.println(estadoCPU.getNombre() + " tiene " + estadoCPU.getCartasRestantes() + " cartas | Victorias: " + estadoCPU.getVictorias() + " | Empates: " + estadoCPU.getEmpates());
        // Permitir salir del juego
            System.out.println("\nPresione Enter para continuar o 'x' para salir...");
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("x")) {
                System.out.println("Saliendo del juego...");
                break;
            }
            rondas++;
        }
        // Juego terminado: mostrar resumen
        System.out.println("\nJuego terminado.");
        System.out.println(nombreJugador + " ganó " + ganadasJugador + " rondas.");
        System.out.println(nombreCPU + " ganó " + ganadasCPU + " rondas.");
        System.out.println("Empates: " + empates);
        System.err.println("---- Cartas Jugadas ----");
        historial.mostrarPila();
        sc.close();
    }

    // Métodos auxiliares / Devuelve el valor real de una carta (2 al A)
    public static int valorCarta(int carta) {
        return ((carta - 1) % 13) + 2;
    }
    // Convierte una carta de int a texto legible
    public static String cartaToString(int carta) {
        String[] palos = {"Corazones", "Picas", "Tréboles", "Diamantes"};
        String[] valores = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        int palo = (carta - 1) / 13;
        int valor = (carta - 1) % 13;
        return valores[valor] + " de " + palos[palo];
    }
// Cuenta cuantas cartas tiene un mazo
    public static int contarCartas(Cola mazo) {
        int count = 0;
        Nodo actual = mazo.getInicio();
        while (actual != null) {
            count++;
            actual = actual.getSiguiente();
        }
        return count;
    }

       // Pide al usuario que seleccione una carta valida por posicion
    public static void mostrarCartasJugador(Cola mazo) {
        Nodo actual = mazo.getInicio();
        int pos = 1;
        while (actual != null) {
            System.out.println(pos + ": " + cartaToString(actual.getValor()));
            actual = actual.getSiguiente();
            pos++;
        }
    }
    // Pide al usuario que seleccione una carta valida por posicion
    public static int pedirCarta(Scanner sc, Cola mazo, String mensaje) {
        int total = contarCartas(mazo);
        int pos;
        do {
            System.out.print(mensaje);
            while (!sc.hasNextInt()) {
                System.out.print("Ingrese un número válido: ");
                sc.next();
            }
            pos = sc.nextInt();
        } while (pos < 1 || pos > total);
        sc.nextLine(); // limpiar buffer
        return pos;
    }

    // Extrae y retorna la carta en la posición indicada (1 = primera carta)
    public static int extraerCartaPorPosicion(Cola mazo, int posicion) {
        Cola temp = new Cola();
        int valor = -1;
        int count = 1;
        while (!mazo.esVacia()) {
            int carta = mazo.desenColar();
            if (count == posicion) {
                valor = carta;
            } else {
                temp.enColar(carta);
            }
            count++;
        }
        // Restaurar las cartas al mazo original
        while (!temp.esVacia()) {
            mazo.enColar(temp.desenColar());
        }
        return valor;
    }
}
