import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class ArbolB {
    private Nodo raiz;
    private int grado;

    public ArbolB(int grado) {
        this.grado = grado;
        this.raiz = new Nodo();
    }

    private class Nodo {
        List<Integer> claves;
        List<Nodo> hijos;

        Nodo() {

            this.claves = new ArrayList<>(grado - 1);
            this.hijos = new ArrayList<>(grado);
        }
    }


    public void insertar(int clave) {
        insertarRecur(raiz, clave);
    }

    private void insertarRecur(Nodo nodo, int clave) {
        int i = 0;
        while (i < nodo.claves.size() && clave > nodo.claves.get(i)) {
            i++;
        }

        if (nodo.hijos.isEmpty()) {
            nodo.claves.add(i, clave);
        } else {
            insertarRecur(nodo.hijos.get(i), clave);
        }

        if (nodo.claves.size() == grado - 1) {
            dividirNodo(nodo);
        }
    }

    private void dividirNodo(Nodo nodo) {
        Nodo nuevoNodo = new Nodo();
        int mediana = nodo.claves.get(grado / 2);
        for (int i = grado / 2 + 1; i < grado - 1 && !nodo.claves.isEmpty(); i++) {
            nuevoNodo.claves.add(nodo.claves.remove(grado / 2 + 1));
        }
        for (int i = grado / 2 + 1; i <= grado && !nodo.hijos.isEmpty(); i++) {
            nuevoNodo.hijos.add(nodo.hijos.remove(grado / 2 + 1));
        }
        if (nodo == raiz) {
            raiz = new Nodo();
            raiz.claves.add(mediana);
            raiz.hijos.add(nodo);
            raiz.hijos.add(nuevoNodo);
        } else {
            Nodo padre = encontrarPadre(raiz, nodo);
            insertarEnPadre(padre, mediana, nuevoNodo);
        }
    }

    private Nodo encontrarPadre(Nodo actual, Nodo hijo) {
        for (Nodo nodo : actual.hijos) {
            if (nodo == hijo) {
                return actual;
            } else {
                Nodo encontrado = encontrarPadre(nodo, hijo);
                if (encontrado != null) {
                    return encontrado;
                }
            }
        }
        return null;
    }

    private void insertarEnPadre(Nodo padre, int clave, Nodo nuevoHijo) {
        int i = 0;
        while (i < padre.claves.size() && clave > padre.claves.get(i)) {
            i++;
        }
        padre.claves.add(i, clave);
        padre.hijos.add(i + 1, nuevoHijo);
        if (padre.claves.size() == grado - 1) {
            dividirNodo(padre);
        }
    }


    public boolean buscar(int clave) {
        return buscarRecursivo(raiz, clave);
    }

    private boolean buscarRecursivo(Nodo nodo, int clave) {
        int i = 0;
        while (i < nodo.claves.size() && clave > nodo.claves.get(i)) {
            i++;
        }

        if (i < nodo.claves.size() && clave == nodo.claves.get(i)) {
            return true;
        }

        if (nodo.hijos.isEmpty()) {
            return false;
        }

        return buscarRecursivo(nodo.hijos.get(i), clave);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese el grado del árbol B: ");
        int grado = scanner.nextInt();
        ArbolB arbolB = new ArbolB(grado);
        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1.Insertar");
            System.out.println("2.Eliminar");
            System.out.println("3.Buscar");
            System.out.println("4.Salir");
            System.out.print("Opción:  ");
            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    System.out.print("Ingrese numero insertar: ");
                    int claveInsertar = scanner.nextInt();
                    arbolB.insertar(claveInsertar);
                    System.out.println("numero insertado correctamente.");
                    break;
                case 2:
                    System.out.print("Ingrese el numero a eliminar:");
                    int claveEliminar = scanner.nextInt();
                    System.out.println("El numero ha sido eliminado.");
                    break;
                case 3:
                    System.out.print("Ingrese el numero a buscar: ");
                    int claveBuscar = scanner.nextInt();
                    if (arbolB.buscar(claveBuscar)) {
                        System.out.println("el numero se encuentra el el arbol.");
                    } else {
                        System.out.println("No esta en el arbol ingrese otro num.");
                    }
                    break;
                case 4:
                    System.out.println("Saliendo... :D");
                    System.exit(0);
                default:
                    System.out.println("Opción inválida, intente de nuevo.");
            }
        }
    }
}
