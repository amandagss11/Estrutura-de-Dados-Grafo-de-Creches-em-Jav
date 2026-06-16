
public class ListaEncadeada {
    private No cabeca;
    private int tamanho;

    public ListaEncadeada() {
        this.cabeca = null;
        this.tamanho = 0;
    }

   
    public void inserir(int indiceCreche, double distancia) {
        No novo = new No(indiceCreche, distancia);
        if (cabeca == null) {
            cabeca = novo;
        } else {
            No atual = cabeca;
            while (atual.proximo != null) {
                atual = atual.proximo;
            }
            atual.proximo = novo;
        }
        tamanho++;
    }

   
    public boolean remover(int indiceCreche) {
        if (cabeca == null) return false;

        if (cabeca.indiceCreche == indiceCreche) {
            cabeca = cabeca.proximo;
            tamanho--;
            return true;
        }

        No atual = cabeca;
        while (atual.proximo != null) {
            if (atual.proximo.indiceCreche == indiceCreche) {
                atual.proximo = atual.proximo.proximo;
                tamanho--;
                return true;
            }
            atual = atual.proximo;
        }
        return false;
    }

    public double buscarDistancia(int indiceCreche) {
        No atual = cabeca;
        while (atual != null) {
            if (atual.indiceCreche == indiceCreche) {
                return atual.distancia;
            }
            atual = atual.proximo;
        }
        return -1;
    }

   
    public boolean contem(int indiceCreche) {
        return buscarDistancia(indiceCreche) >= 0;
    }

   
    public No[] ordenarPorDistancia() {
        if (tamanho == 0) return new No[0];

       
        No[] arr = new No[tamanho];
        No atual = cabeca;
        for (int i = 0; i < tamanho; i++) {
            arr[i] = atual;
            atual = atual.proximo;
        }

        
        for (int i = 1; i < arr.length; i++) {
            No chave = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j].distancia > chave.distancia) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = chave;
        }
        return arr;
    }

    public No getCabeca() {
        return cabeca;
    }

    public int getTamanho() {
        return tamanho;
    }
}