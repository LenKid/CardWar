package model;

public class JugadorEstado {
    private String nombre;
    private int cartasRestantes;
    private int victorias;
    private int empates;

    public JugadorEstado(String nombre) {
        this.nombre = nombre;
        this.cartasRestantes = 0;
        this.victorias = 0;
        this.empates = 0;
    }

    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCartasRestantes() { return cartasRestantes; }
    public void setCartasRestantes(int cartasRestantes) { this.cartasRestantes = cartasRestantes; }

    public int getVictorias() { return victorias; }
    public void setVictorias(int victorias) { this.victorias = victorias; }

    public int getEmpates() { return empates; }
    public void setEmpates(int empates) { this.empates = empates; }
}
