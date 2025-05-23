package model;

public class Cola {
    private Nodo inicio, fin;

    public Cola() {
        this.inicio = null;
        this.fin = null;
    }

    public boolean esVacia() {
        return inicio == null;
    }

    public void enColar(int valor) {
        Nodo nuevo = new Nodo();
        nuevo.setValor(valor);
        if (esVacia()) {
            inicio = nuevo;
            fin = nuevo;
        } else {
            fin.setSiguiente(nuevo);
            fin = nuevo;
        }
    }

    public int desenColar() {
        if (!esVacia()) {
            int dato = inicio.getValor();
            if (inicio == fin) {
                inicio = null;
                fin = null;
            } else {
                inicio = inicio.getSiguiente();
            }
            return dato;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    public void mostrarCola() {
        Nodo auxiliar = inicio;
        while (auxiliar != null) {
            System.out.println("|\t|" + auxiliar.getValor() + "|\t|");
            auxiliar = auxiliar.getSiguiente();
        }
    }

    public Nodo getInicio() {
        return inicio;
    }

    public void setInicio(Nodo inicio) {
        this.inicio = inicio;
    }

    public Nodo getFin() {
        return fin;
    }

    public void setFin(Nodo fin) {
        this.fin = fin;
    }
    
    
}
