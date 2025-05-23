import java.util.Scanner;
import model.*;

public class CardWar {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1. Crear baraja y barajar manualmente (Fisher-Yates)
        int[] cartas = new int[52];
        for (int i = 0; i < 52; i++) cartas[i] = i + 1;

        // Barajar (Fisher-Yates)
        for (int i = 51; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            int temp = cartas[i];
            cartas[i] = cartas[j];
            cartas[j] = temp;
        }

        // Insertar cartas en la lista enlazada
        ListaEnlazada baraja = new ListaEnlazada();
        for (int i = 0; i < 52; i++) {
            baraja.insertarNodoFinal(cartas[i]);
        }

        // Repartir a dos colas (mazos)
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

        // 2. Tabla Hash para estado de jugadores
        TablaHash estado = new TablaHash(2);
        estado.insertar(0, mazoJugador.getInicio() != null ? 26 : 0); // Cartas restantes jugador
        estado.insertar(1, mazoCPU.getInicio() != null ? 26 : 0);     // Cartas restantes CPU

        // 3. Pila para historial
        Pila historial = new Pila();

        // 4. Árbol binario para decisión de guerra
        ArbolBinario arbol = new ArbolBinario();

        // 5. Nombres
        System.out.print("Ingrese nombre del Jugador 1: ");
        String nombreJugador = sc.nextLine();
        String nombreCPU = "CPU";

        int rondas = 1, ganadasJugador = 0, ganadasCPU = 0, empates = 0;

        System.out.println("\nBienvenido a Guerra de Cartas 🛡️");

        while (!mazoJugador.esVacia() && !mazoCPU.esVacia()) {
            System.out.println("\n--- Ronda " + rondas + " ---");
            int cartaJ = mazoJugador.desenColar();
            int cartaC = mazoCPU.desenColar();

            historial.apilar(cartaJ);
            historial.apilar(cartaC);

            System.out.println(nombreJugador + " juega: " + cartaToString(cartaJ));
            System.out.println(nombreCPU + " juega: " + cartaToString(cartaC));

            if (valorCarta(cartaJ) > valorCarta(cartaC)) {
                System.out.println("Ganador: " + nombreJugador);
                mazoJugador.enColar(cartaJ);
                mazoJugador.enColar(cartaC);
                ganadasJugador++;
            } else if (valorCarta(cartaJ) < valorCarta(cartaC)) {
                System.out.println("Ganador: " + nombreCPU);
                mazoCPU.enColar(cartaJ);
                mazoCPU.enColar(cartaC);
                ganadasCPU++;
            } else {
                System.out.println("Empate! Se inicia la guerra...");
                empates++;
                // Árbol de decisión para guerra (simbólico)
                arbol.insertar(1);

                // Verificar si ambos pueden continuar la guerra
                boolean jugadorPuede = contarCartas(mazoJugador) >= 2;
                boolean cpuPuede = contarCartas(mazoCPU) >= 2;
                if (!jugadorPuede || !cpuPuede) {
                    System.out.println("Algún jugador no tiene suficientes cartas para la guerra.");
                    if (!jugadorPuede) System.out.println(nombreJugador + " pierde la guerra.");
                    if (!cpuPuede) System.out.println(nombreCPU + " pierde la guerra.");
                    break;
                }

                // Ambos colocan una carta boca abajo y otra boca arriba
                int bocaAbajoJ = mazoJugador.desenColar();
                int bocaAbajoC = mazoCPU.desenColar();
                int guerraJ = mazoJugador.desenColar();
                int guerraC = mazoCPU.desenColar();

                System.out.println(nombreJugador + " coloca: " + cartaToString(guerraJ) + " (boca arriba)");
                System.out.println(nombreCPU + " coloca: " + cartaToString(guerraC) + " (boca arriba)");

                // El ganador se lleva todas las cartas jugadas en la guerra
                if (valorCarta(guerraJ) > valorCarta(guerraC)) {
                    System.out.println("Ganador de la guerra: " + nombreJugador);
                    mazoJugador.enColar(cartaJ); mazoJugador.enColar(cartaC);
                    mazoJugador.enColar(bocaAbajoJ); mazoJugador.enColar(bocaAbajoC);
                    mazoJugador.enColar(guerraJ); mazoJugador.enColar(guerraC);
                    ganadasJugador++;
                } else if (valorCarta(guerraJ) < valorCarta(guerraC)) {
                    System.out.println("Ganador de la guerra: " + nombreCPU);
                    mazoCPU.enColar(cartaJ); mazoCPU.enColar(cartaC);
                    mazoCPU.enColar(bocaAbajoJ); mazoCPU.enColar(bocaAbajoC);
                    mazoCPU.enColar(guerraJ); mazoCPU.enColar(guerraC);
                    ganadasCPU++;
                } else {
                    // Si vuelve a haber empate, puedes repetir la guerra o decidir el empate según tus reglas
                    System.out.println("¡Empate en la guerra! Puedes implementar una guerra recursiva aquí.");
                }
            }

            // Actualizar estado en tabla hash
            estado.insertar(0, contarCartas(mazoJugador));
            estado.insertar(1, contarCartas(mazoCPU));

            // Mostrar estado
            System.out.println("\n--- Estado actual ---");
            System.out.println(nombreJugador + " tiene " + contarCartas(mazoJugador) + " cartas");
            System.out.println(nombreCPU + " tiene " + contarCartas(mazoCPU) + " cartas");

            System.out.println("\nPresione Enter para continuar...");
            sc.nextLine();
            rondas++;
        }

        System.out.println("\nJuego terminado.");
        System.out.println(nombreJugador + " ganó " + ganadasJugador + " rondas.");
        System.out.println(nombreCPU + " ganó " + ganadasCPU + " rondas.");
        System.out.println("Empates: " + empates);
    }

    // Métodos auxiliares
    public static int valorCarta(int carta) {
        // 1-13: Corazones, 14-26: Picas, 27-39: Tréboles, 40-52: Diamantes
        return ((carta - 1) % 13) + 2; // 2-14 (donde 11=J, 12=Q, 13=K, 14=A)
    }

    public static String cartaToString(int carta) {
        String[] palos = {"Corazones", "Picas", "Tréboles", "Diamantes"};
        String[] valores = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        int palo = (carta - 1) / 13;
        int valor = (carta - 1) % 13;
        return valores[valor] + " de " + palos[palo];
    }

    public static int contarCartas(Cola mazo) {
        int count = 0;
        Nodo actual = mazo.getInicio();
        while (actual != null) {
            count++;
            actual = actual.getSiguiente();
        }
        return count;
    }
}
