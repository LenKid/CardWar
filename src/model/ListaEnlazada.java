package model;

public class ListaEnlazada {
    private Nodo cabecera;

    public ListaEnlazada() {
        this.cabecera = null;
    }

    public Nodo getCabecera() {
        return cabecera;
    }

    public void setCabecera(Nodo cabecera) {
        this.cabecera = cabecera;
    }

    public boolean esVacia() {
        return this.cabecera == null;
    }

    public void insertarNodoInicial(int valor) {
        Nodo nuevo = new Nodo();
        nuevo.setValor(valor);
        if (esVacia()) {
            this.cabecera = nuevo;
            nuevo.setSiguiente(cabecera);
        } else {
            Nodo auxiliar = cabecera;
            do {
                auxiliar = auxiliar.getSiguiente();
            } while (auxiliar.getSiguiente() != this.cabecera);
            {
                auxiliar.setSiguiente(nuevo);
                nuevo.setSiguiente((this.cabecera));
                this.cabecera = nuevo;
            }
            System.out.println("Nodo insertado al inicio con éxito!");
        }
    }

    public void insertarNodoFinal(int valor) {
        Nodo nuevo = new Nodo();
        nuevo.setValor(valor);
    
        if (esVacia()) {
            this.cabecera = nuevo;
            nuevo.setSiguiente(cabecera); // El siguiente del nuevo nodo apunta a sí mismo (lista circular)
        } else {
            Nodo auxiliar = this.cabecera;
            // Encuentra el último nodo
            while (auxiliar.getSiguiente() != this.cabecera) {
                auxiliar = auxiliar.getSiguiente();
            }
    
            // Inserta el nuevo nodo
            auxiliar.setSiguiente(nuevo);
            nuevo.setSiguiente(this.cabecera); // El siguiente del nuevo nodo apunta a la cabecera (lista circular)
        }
        System.out.println("Nodo insertado al final con éxito!");
    }

    public void eliminaCabecera() {
        if (esVacia()) {
            return;
        }
        Nodo auxiliar = cabecera;
        do {
            auxiliar = auxiliar.getSiguiente();
        } while (auxiliar.getSiguiente() != cabecera);
        auxiliar.setSiguiente(cabecera.getSiguiente());
        cabecera = cabecera.getSiguiente();
        if (auxiliar == cabecera) {
            cabecera = null;
        }
    }

    public void eliminarTodo() {
        cabecera = null;
        System.out.println("Lista Eliminada");
    }

    public boolean buscarValor(int valorABuscar) {
        if (esVacia()) {
            return false;
        }
        Nodo auxiliar = this.cabecera;
        do {
            if (auxiliar.getValor() == valorABuscar) {
                return true;
            } else {
                auxiliar = auxiliar.getSiguiente();
            }
        } while (auxiliar != this.cabecera);
        return false;
    }

    public void eliminarNodo(int valorABuscar) {
        if (esVacia()) {
            return;
        }
        Nodo auxiliar, anterior = cabecera;
        do {
            if (anterior.getSiguiente().getValor() == valorABuscar) {
                auxiliar = anterior.getSiguiente();
                anterior.setSiguiente(auxiliar.getSiguiente());
                if (auxiliar == cabecera) {
                    cabecera = auxiliar.getSiguiente();
                }
                if (auxiliar == cabecera) {
                    cabecera = null;
                }
            System.out.println("Elemento Eliminado!");
            } else {
                anterior = anterior.getSiguiente();
            }
        } while (anterior != cabecera);
        System.out.println("El valor a eliminar no existe!");
    }

    public void mostrarLista() {
        if (this.cabecera == null) {
            System.out.println("La lista está vacía.");
            return;
        }
    
        Nodo actual = this.cabecera; // Empieza en la cabecera
        do {
            System.out.println(actual.getValor()); // Imprime el valor del nodo actual
            actual = actual.getSiguiente(); // Avanza al siguiente nodo
        } while (actual != this.cabecera); // Continúa hasta volver a la cabecera
    }
    
}
