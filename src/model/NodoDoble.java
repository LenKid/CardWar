package model;

public class NodoDoble {
    private int clave;
    private JugadorEstado estado;
    private int cantidadCartas; // Nuevo campo para cantidad de cartas
    private NodoDoble siguiente;
    private NodoDoble anterior;

    public NodoDoble() {
        this.clave = 0;
        this.estado = null;
        this.cantidadCartas = 0;
        this.siguiente = null;
        this.anterior = null;
    }

    public NodoDoble(int clave, JugadorEstado estado) {
        this.clave = clave;
        this.estado = estado;
        this.cantidadCartas = 0;
        this.siguiente = null;
        this.anterior = null;
    }

    // Constructor para clave y cantidadCartas
    public NodoDoble(int clave, int cantidadCartas) {
        this.clave = clave;
        this.cantidadCartas = cantidadCartas;
        this.estado = null;
        this.siguiente = null;
        this.anterior = null;
    }

    public int getClave() {
        return clave;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }

    public JugadorEstado getEstado() {
        return estado;
    }

    public void setEstado(JugadorEstado estado) {
        this.estado = estado;
    }

    public NodoDoble getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoDoble siguiente) {
        this.siguiente = siguiente;
    }

    public NodoDoble getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoDoble anterior) {
        this.anterior = anterior;
    }

    public int getCantidadCartas() {
        return cantidadCartas;
    }

    public void setCantidadCartas(int cantidadCartas) {
        this.cantidadCartas = cantidadCartas;
    }
}
