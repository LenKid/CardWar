package model;

public class Pila {
    private Nodo inicio;

    public Pila() {
        inicio = null;
    }

    public boolean esVacia() {
        return inicio == null;
    }

    // Push Method
    public void apilar(int valor) {
        Nodo nuevo = new Nodo();
        nuevo.setValor(valor);
        if (esVacia()) {
            inicio = nuevo;
        } else {
            nuevo.setSiguiente(inicio);
            inicio = nuevo;
        }
    }

    // Pop Method
    public int desapilar() {
        if (!esVacia()) {
            int dato = inicio.getValor();
            inicio = inicio.getSiguiente();
            return dato;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    public void mostrarPila() {
        Nodo auxiliar = inicio;
        while (auxiliar != null) {
            System.out.println("|\t|" + auxiliar.getValor() + "|\t|");
            auxiliar = auxiliar.getSiguiente();
        }
    }

    
}
