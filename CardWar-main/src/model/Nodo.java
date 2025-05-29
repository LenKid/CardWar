package model;
public class Nodo {
    private int valor;
    private Nodo sig;
    
    public Nodo() {
        this.valor = 0;
        this.sig = null;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public Nodo getSiguiente() {
        return sig;
    }

    public void setSiguiente(Nodo sig) {
        this.sig = sig;
    }
    
}
