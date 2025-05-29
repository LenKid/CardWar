package model;

public class JugadorEstado {
    private String nombre;    // Nombre del jugador
    private int cartasRestantes;    // Cantidad de cartas que le quedan al jugador
    private int victorias;   // Cantidad de victorias del jugador
    private int empates;    // Cantidad de empates del jugador
    // Constructor que inicializa el nombre y pone todo lo demas en cero
    public JugadorEstado(String nombre) {
        this.nombre = nombre;
        this.cartasRestantes = 0;
        this.victorias = 0;
        this.empates = 0;
    }

    // Getters y setters para acceder y modificar los datos del jugador
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCartasRestantes() {
        return cartasRestantes;
    }

    public void setCartasRestantes(int cartasRestantes) {
        this.cartasRestantes = cartasRestantes;
    }

    public int getVictorias() {
        return victorias;
    }

    public void setVictorias(int victorias) {
        this.victorias = victorias;
    }

    public int getEmpates() {
        return empates;
    }

    public void setEmpates(int empates) {
        this.empates = empates;
    }
}
