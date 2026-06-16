/**
 * Nó da lista simplesmente encadeada.
 * Armazena o índice da creche vizinha e a distância até ela.
 */
public class No {
    public int indiceCreche;
    public double distancia;
    public No proximo;

    public No(int indiceCreche, double distancia) {
        this.indiceCreche = indiceCreche;
        this.distancia = distancia;
        this.proximo = null;
    }
}