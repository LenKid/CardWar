package model;

public class NodoDoble {
    private int clave;
    private JugadorEstado estado;
    private NodoDoble siguiente;
    private NodoDoble anterior;

    public NodoDoble(int clave, JugadorEstado estado) {
        this.clave = clave;
        this.estado = estado;
        this.siguiente = null;
        this.anterior = null;
    }

    public int getClave() { return clave; }
    public void setClave(int clave) { this.clave = clave; }

    public JugadorEstado getEstado() { return estado; }
    public void setEstado(JugadorEstado estado) { this.estado = estado; }

    public NodoDoble getSiguiente() { return siguiente; }
    public void setSiguiente(NodoDoble siguiente) { this.siguiente = siguiente; }

    public NodoDoble getAnterior() { return anterior; }
    public void setAnterior(NodoDoble anterior) { this.anterior = anterior; }
}
