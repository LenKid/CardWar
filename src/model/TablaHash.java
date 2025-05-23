package model;

public class TablaHash {
    private NodoDoble[] tabla;
    private int capacidad;

    public TablaHash(int capacidad) {
        this.capacidad = capacidad;
        this.tabla = new NodoDoble[capacidad];
    }

    private int hash(int clave) {
        return Math.abs(clave) % capacidad;
    }

    // Insertar o actualizar estado de jugador
    public void insertar(int clave, JugadorEstado estado) {
        int indice = hash(clave);
        NodoDoble actual = tabla[indice];

        // Si ya existe, actualiza
        while (actual != null) {
            if (actual.getClave() == clave) {
                actual.setEstado(estado);
                return;
            }
            actual = actual.getSiguiente();
        }

        // Si no existe, inserta al inicio
        NodoDoble nuevo = new NodoDoble(clave, estado);
        nuevo.setSiguiente(tabla[indice]);
        if (tabla[indice] != null) {
            tabla[indice].setAnterior(nuevo);
        }
        tabla[indice] = nuevo;
    }

    // Buscar estado de jugador por clave
    public JugadorEstado buscar(int clave) {
        int indice = hash(clave);
        NodoDoble actual = tabla[indice];
        while (actual != null) {
            if (actual.getClave() == clave) {
                return actual.getEstado();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }
}
