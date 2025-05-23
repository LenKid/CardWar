package model;

public class ArbolBinario {
    private NodoDoble raiz;

    public ArbolBinario() {
        this.raiz = null;
    }

    // Insertar un valor en el árbol
    public void insertar(int valor) {
        raiz = insertarRec(raiz, valor);
    }

    private NodoDoble insertarRec(NodoDoble nodo, int valor) {
        if (nodo == null) {
            NodoDoble nuevo = new NodoDoble();
            nuevo.setValor(valor);
            return nuevo;
        }
        if (valor < nodo.getValor()) {
            nodo.setAnterior(insertarRec(nodo.getAnterior(), valor)); // Izquierdo
        } else if (valor > nodo.getValor()) {
            nodo.setSiguiente(insertarRec(nodo.getSiguiente(), valor)); // Derecho
        }
        return nodo;
    }

    // Buscar un valor en el árbol
    public boolean buscar(int valor) {
        return buscarRec(raiz, valor);
    }

    private boolean buscarRec(NodoDoble nodo, int valor) {
        if (nodo == null) {
            return false;
        }
        if (valor == nodo.getValor()) {
            return true;
        }
        return valor < nodo.getValor()
            ? buscarRec(nodo.getAnterior(), valor)
            : buscarRec(nodo.getSiguiente(), valor);
    }
}
