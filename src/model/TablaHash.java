package model;

public class TablaHash {
    private NodoDoble[] tabla;
    private int capacidad;

    public TablaHash(int capacidad) {
        this.capacidad = capacidad;
        this.tabla = new NodoDoble[capacidad];
    }

    // Función hash simple
    private int hash(int clave) {
        return Math.abs(clave) % capacidad;
    }

    // Insertar un valor en la tabla hash
    public void insertar(int clave, int valor) {
        int indice = hash(clave);
        NodoDoble nuevo = new NodoDoble();
        nuevo.setValor(valor);

        if (tabla[indice] == null) {
            tabla[indice] = nuevo;
        } else {
            NodoDoble actual = tabla[indice];
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
            nuevo.setAnterior(actual);
        }
    }

    // Buscar un valor por clave
    public Integer buscar(int clave) {
        int indice = hash(clave);
        NodoDoble actual = tabla[indice];
        while (actual != null) {
            if (actual.getValor() == clave) {
                return actual.getValor();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    // Puedes agregar métodos para eliminar, actualizar, etc.
}
