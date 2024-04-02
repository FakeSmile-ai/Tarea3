public class Nodo {
    int[] claves;
    int grado;
    Nodo[] nodosHijos;
    boolean hoja;
    int numClaves;

    Nodo(int grado, boolean hoja) {
        this.grado = grado;
        this.hoja = hoja;
        this.claves = new int[2 * grado - 1];
        this.nodosHijos = new Nodo[2 * grado];
        this.numClaves = 0;
    }
}
