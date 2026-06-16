import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Grafo {

    private static final int MAX = 50; 

    private String[] nomes;          
    private int[][] adjacencia;      
    private ListaEncadeada[] listas; 
    private int numCreches;

    public Grafo() {
        nomes      = new String[MAX];
        adjacencia = new int[MAX][MAX];
        listas     = new ListaEncadeada[MAX];
        numCreches = 0;

        for (int i = 0; i < MAX; i++) {
            listas[i] = new ListaEncadeada();
            for (int j = 0; j < MAX; j++) {
                adjacencia[i][j] = 0;
            }
        }
    }

   
    public void lerArquivo(String caminho) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(caminho));
        String linha;

        
        linha = br.readLine().trim();
        numCreches = Integer.parseInt(linha);

       
        for (int i = 0; i < numCreches; i++) {
            nomes[i] = br.readLine().trim();
        }

      
        while ((linha = br.readLine()) != null) {
            linha = linha.trim();
            if (linha.isEmpty()) continue;

            String[] partes = linha.split("\\s+");
            if (partes.length < 3) continue;

            String nomeA    = partes[0];
            String nomeB    = partes[1];
            double distancia = Double.parseDouble(partes[2]);

            int idxA = buscarIndice(nomeA);
            int idxB = buscarIndice(nomeB);

            if (idxA == -1 || idxB == -1) {
                System.out.println("Aviso: creche nao encontrada na aresta '" + linha + "'");
                continue;
            }

            adicionarConexao(idxA, idxB, distancia);
        }
        br.close();
    }

   
    private void adicionarConexao(int a, int b, double distancia) {
        adjacencia[a][b] = 1;
        adjacencia[b][a] = 1;

        if (!listas[a].contem(b)) listas[a].inserir(b, distancia);
        if (!listas[b].contem(a)) listas[b].inserir(a, distancia);
    }

   
    public String adicionarConexaoPorNome(String nomeA, String nomeB, double distancia) {
        int idxA = buscarIndice(nomeA);
        int idxB = buscarIndice(nomeB);

        if (idxA == -1) return "Creche '" + nomeA + "' nao encontrada.";
        if (idxB == -1) return "Creche '" + nomeB + "' nao encontrada.";
        if (idxA == idxB) return "As creches informadas sao a mesma.";

        if (adjacencia[idxA][idxB] == 1) {
            return "Conexao entre '" + nomeA + "' e '" + nomeB + "' ja existe ("
                    + listas[idxA].buscarDistancia(idxB) + " km).";
        }

        adicionarConexao(idxA, idxB, distancia);
        return "Conexao adicionada: " + nomeA + " <-> " + nomeB + " (" + distancia + " km).";
    }

   
    public void exibirConexoesPorCreche() {
        System.out.println("\n=== Numero de conexoes por creche ===");
        for (int i = 0; i < numCreches; i++) {
            int contagem = 0;
            for (int j = 0; j < numCreches; j++) {
                contagem += adjacencia[i][j];
            }
            System.out.printf("  %-35s -> %d conexao(oes)%n", nomes[i], contagem);
        }
    }

    
    public void listarVizinhosOrdenados(String nomeCreche) {
        int idx = buscarIndice(nomeCreche);
        if (idx == -1) {
            System.out.println("Creche '" + nomeCreche + "' nao encontrada.");
            return;
        }

        System.out.println("\n=== Creches conectadas a '" + nomeCreche + "' (ordem crescente de distancia) ===");

        No[] ordenados = listas[idx].ordenarPorDistancia();
        if (ordenados.length == 0) {
            System.out.println("  Nenhuma conexao encontrada.");
            return;
        }

        for (No no : ordenados) {
            System.out.printf("  %-35s %.1f km%n", nomes[no.indiceCreche], no.distancia);
        }
    }
 
    public void informarDistancia(String nomeA, String nomeB) {
        int idxA = buscarIndice(nomeA);
        int idxB = buscarIndice(nomeB);

        System.out.println("\n=== Distancia entre creches ===");

        if (idxA == -1) { System.out.println("Creche '" + nomeA + "' nao encontrada."); return; }
        if (idxB == -1) { System.out.println("Creche '" + nomeB + "' nao encontrada."); return; }

        if (adjacencia[idxA][idxB] == 1) {
            double dist = listas[idxA].buscarDistancia(idxB);
            System.out.printf("  %s <-> %s : %.1f km%n", nomeA, nomeB, dist);
        } else {
            System.out.println("  Nao existe conexao direta entre '" + nomeA + "' e '" + nomeB + "'.");
        }
    }

    
    public void exibirMatrizAdjacencias() {
        System.out.println("\n=== Matriz de Adjacencias ===");

       
        System.out.printf("%-5s", "");
        for (int j = 0; j < numCreches; j++) {
            System.out.printf(" %3d", j);
        }
        System.out.println();

        for (int i = 0; i < numCreches; i++) {
            System.out.printf("[%2d] ", i);
            for (int j = 0; j < numCreches; j++) {
                System.out.printf(" %3d", adjacencia[i][j]);
            }
            System.out.println("  <- " + nomes[i]);
        }
    }


    public int buscarIndice(String nome) {
        for (int i = 0; i < numCreches; i++) {
            if (nomes[i].equalsIgnoreCase(nome)) return i;
        }
        return -1;
    }

    public int getNumCreches() { return numCreches; }
    public String getNome(int i) { return nomes[i]; }
}