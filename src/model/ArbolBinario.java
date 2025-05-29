package model;

public class ArbolBinario {
    private NodoDoble raiz;

    public ArbolBinario() {
        this.raiz = null;
    }

    // Insertar un valor en el árbol binario
    public void insertar(int valor) {
        raiz = insertarRec(raiz, valor);
    }

    private NodoDoble insertarRec(NodoDoble nodo, int valor) {
        if (nodo == null) {
            NodoDoble nuevo = new NodoDoble();
            nuevo.setClave(valor);
            return nuevo;
        }
        if (valor < nodo.getClave()) {
            nodo.setAnterior(insertarRec(nodo.getAnterior(), valor)); // Izquierdo
        } else if (valor > nodo.getClave()) {
            nodo.setSiguiente(insertarRec(nodo.getSiguiente(), valor)); // Derecho
        }
        return nodo;
    }

      // Buscar un valor dentro del árbol
    public boolean buscar(int valor) {
        return buscarRec(raiz, valor);
    }

    private boolean buscarRec(NodoDoble nodo, int valor) {
        if (nodo == null) {
            return false; // Árbol vacío o no encontrado
        }
        if (valor == nodo.getClave()) {
            return true;// Valor encontrado
        }
        // Buscar en subArbol izquierdo o derecho dependiendo del valor
        return valor < nodo.getClave()
            ? buscarRec(nodo.getAnterior(), valor)
            : buscarRec(nodo.getSiguiente(), valor);
    }
}
