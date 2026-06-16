import java.io.IOException;
import java.util.Scanner;


public class Main {

    private static final String ARQUIVO_GRAFO = "grafo.txt";

    public static void main(String[] args) {
        Grafo grafo = new Grafo();
        Scanner sc  = new Scanner(System.in);

       
        try {
            grafo.lerArquivo(ARQUIVO_GRAFO);
            System.out.println("Grafo carregado com sucesso! ("
                    + grafo.getNumCreches() + " creches)");
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo '" + ARQUIVO_GRAFO + "': " + e.getMessage());
            System.out.println("Verifique se o arquivo existe no diretório de execução.");
            sc.close();
            return;
        }

       
        int opcao = -1;
        do {
            exibirMenu();
            System.out.print("Opcao: ");
            try {
                opcao = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    grafo.exibirConexoesPorCreche();
                    break;

                case 2:
                    System.out.print("Nome da creche: ");
                    String creche = sc.nextLine().trim();
                    grafo.listarVizinhosOrdenados(creche);
                    break;

                case 3:
                    System.out.print("Nome da primeira creche: ");
                    String crecheA = sc.nextLine().trim();
                    System.out.print("Nome da segunda creche:  ");
                    String crecheB = sc.nextLine().trim();
                    grafo.informarDistancia(crecheA, crecheB);
                    break;

                case 4:
                    System.out.print("Nome da primeira creche: ");
                    String novaA = sc.nextLine().trim();
                    System.out.print("Nome da segunda creche:  ");
                    String novaB = sc.nextLine().trim();
                    System.out.print("Distancia (km):          ");
                    double dist;
                    try {
                        dist = Double.parseDouble(sc.nextLine().trim().replace(",", "."));
                    } catch (NumberFormatException e) {
                        System.out.println("Distancia invalida.");
                        break;
                    }
                    System.out.println(grafo.adicionarConexaoPorNome(novaA, novaB, dist));
                    break;

                case 5:
                    grafo.exibirMatrizAdjacencias();
                    break;

                case 6:
                    System.out.println("\nCreches cadastradas:");
                    for (int i = 0; i < grafo.getNumCreches(); i++) {
                        System.out.printf("  [%2d] %s%n", i, grafo.getNome(i));
                    }
                    break;

                case 0:
                    System.out.println("\nEncerrando o sistema. Ate logo!");
                    break;

                default:
                    System.out.println("Opcao invalida. Tente novamente.");
            }

        } while (opcao != 0);

        sc.close();
    }

    private static void exibirMenu() {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.println("║   Sistema de Rotas de Merenda - Creches      ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.println("║  1. Numero de conexoes por creche             ║");
        System.out.println("║  2. Vizinhos de uma creche (ordem distancia)  ║");
        System.out.println("║  3. Distancia entre duas creches              ║");
        System.out.println("║  4. Incluir nova conexao                      ║");
        System.out.println("║  5. Exibir matriz de adjacencias              ║");
        System.out.println("║  6. Listar todas as creches                   ║");
        System.out.println("║  0. Sair                                      ║");
        System.out.println("╚══════════════════════════════════════════════╝");
    }
}