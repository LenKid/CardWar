package model;

public class TablaHash {
    private NodoDoble[] tabla; // Arreglo de nodos donde se guardan los datos
    private int capacidad; // Tamaño maximo del arreglo
    // Constructor que inicializa la tabla con una capacidad determinada
    public TablaHash(int capacidad) {
        this.capacidad = capacidad;
        this.tabla = new NodoDoble[capacidad];
    }
    // Funcion hash para calcular en que posicion guardar una clave
    private int hash(int clave) {
        return Math.abs(clave) % capacidad;
    }

    // Inserta o actualiza el estado de un jugador segun su clave
    public void insertar(int clave, JugadorEstado estado) {
        int indice = hash(clave);
        NodoDoble actual = tabla[indice];

        // Si ya hay un nodo con esa clave, simplemente se actualiza el estado
        while (actual != null) {
            if (actual.getClave() == clave) {
                actual.setEstado(estado);
                return;
            }
            actual = actual.getSiguiente();
        }

        // Si no se encontro, se crea un nuevo nodo y se inserta al inicio de la lista en ese indice
        NodoDoble nuevo = new NodoDoble(clave, estado);
        nuevo.setSiguiente(tabla[indice]);
        if (tabla[indice] != null) {
            tabla[indice].setAnterior(nuevo);
        }
        tabla[indice] = nuevo;
    }

    // Inserta o actualiza la cantidad de cartas restantes en el mazo, usando una clave
    public void insertarMazoCantidad(int clave, int cantidadCartas) {
        int indice = hash(clave);
        NodoDoble actual = tabla[indice];

        // Si ya existe la clave, actualiza la cantidad de cartas
        while (actual != null) {
            if (actual.getClave() == clave) {
                actual.setCantidadCartas(cantidadCartas); // Debes implementar este método en NodoDoble
                return;
            }
            actual = actual.getSiguiente();
        }

        // Si no existe, inserta al inicio (debes tener un constructor que acepte clave y cantidadCartas)
        NodoDoble nuevo = new NodoDoble(clave, cantidadCartas); // Debes implementar este constructor en NodoDoble
        nuevo.setSiguiente(tabla[indice]);
        if (tabla[indice] != null) {
            tabla[indice].setAnterior(nuevo);
        }
        tabla[indice] = nuevo;
    }

    // Busca y devuelve el estado del jugador segun su clave

    public JugadorEstado buscar(int clave) {
        int indice = hash(clave);
        NodoDoble actual = tabla[indice];
        // Recorre los nodos en esa posicion hasta encontrar la clave
        while (actual != null) {
            if (actual.getClave() == clave) {
                return actual.getEstado();
            }
            actual = actual.getSiguiente();  //devuelve al estado si lo encuentra
        }
      // Si no se encuentra, devuelve null
        return null;
    }
}
