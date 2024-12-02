package Floricultura;

import com.mongodb.client.MongoDatabase;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {
        try {
            // Conectar ao banco de dados
            MongoDatabase database = Conecta.getDatabase();

            int op;
            do {
                // Exibe o menu para o usuário escolher uma opção
                op = menu();

                // Processa a opção escolhida pelo usuário
                switch (op) {
                    case 1:
                        new CategoriaController(database).createCategoria();
                        break;
                    case 2:
                        new CategoriaController(database).deleteCategoria();
                        break;
                    case 3:
                        new CategoriaController(database).updateCategoria();
                        break;
                    case 4:
                        new CategoriaController(database).listCategorias();
                        break;
                    case 5:
                        new ProdutosController(database).createProduto();
                        break;
                    case 6:
                        new ProdutosController(database).deleteProduto();
                        break;
                    case 7:
                        new ProdutosController(database).updateProduto();
                        break;
                    case 8:
                        new ProdutosController(database).listProdutos();
                        break;
                    case 9:
                        new FornecedoresController(database).createFornecedor();
                        break;
                    case 10:
                        new FornecedoresController(database).deleteFornecedor();
                        break;
                    case 11:
                        new FornecedoresController(database).updateFornecedor();
                        break;
                    case 12:
                        new FornecedoresController(database).listFornecedores();
                        break;
                    case 13:
                        new VendasController(database).createVenda();
                        break;
                    case 14:
                        new VendasController(database).deleteVenda();
                        break;
                    case 15:
                        new VendasController(database).updateVenda();
                        break;
                    case 16:
                        new VendasController(database).listarVendas();
                        break;
                    case 17:
                        new ItensDaVendaController(database).createItem();
                        break;
                    case 18:
                        new ItensDaVendaController(database).deleteItem();
                        break;
                    case 19:
                        new ItensDaVendaController(database).updateItem();
                        break;
                    case 20:
                        new ItensDaVendaController(database).listItens();
                        break;
                    case 21:
                        new FornecedoresProdutosController(database).listarIdsAssociativos();
                        break;
                    case 22:
                        new Relatorio1(database).gerarRelatorio1();
                        break;
                    case 23:
                        new Relatorio2(database).gerarRelatorio2();  // Relatório de Vendas por Cliente
                        break;
                    case 24:
                        new Relatorio3(database).gerarRelatorio3();  // Relatório 3
                        break;
                    case 25:
                        new ClientesController(database).createCliente();
                        break;
                    case 26:
                        new ClientesController(database).deleteCliente();
                        break;
                    case 27:
                        new ClientesController(database).updateCliente();
                        break;
                    case 28:
                        new ClientesController(database).listClientes();
                        break;
                    case 29:
                        exibirRelatorios(database);  // Exibir relatórios
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida! Por favor, tente novamente.");
                        break;
                }
            } while (op != 0);

            // Fechar a conexão ao final
            Conecta.fecharConexao();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Exibe o menu para o usuário e retorna a opção escolhida.
     */
    private static int menu() {
        Scanner input = new Scanner(System.in);
        System.out.println("\n================= MENU =================");
        System.out.println("1. Adicionar Categoria");
        System.out.println("2. Remover Categoria");
        System.out.println("3. Atualizar Categoria");
        System.out.println("4. Listar Categorias");
        System.out.println("5. Adicionar Produto");
        System.out.println("6. Remover Produto");
        System.out.println("7. Atualizar Produto");
        System.out.println("8. Listar Produtos");
        System.out.println("9. Adicionar Fornecedor");
        System.out.println("10. Remover Fornecedor");
        System.out.println("11. Atualizar Fornecedor");
        System.out.println("12. Listar Fornecedores");
        System.out.println("13. Registrar Venda");
        System.out.println("14. Remover Venda");
        System.out.println("15. Atualizar Venda");
        System.out.println("16. Listar Vendas");
        System.out.println("17. Adicionar Item na Venda");
        System.out.println("18. Remover Item da Venda");
        System.out.println("19. Atualizar Item da Venda");
        System.out.println("20. Listar Itens da Venda");
        System.out.println("21. Listar FornecedoresProdutos");
        System.out.println("22. Gerar Relatório 1");
        System.out.println("23. Gerar Relatório 2");
        System.out.println("24. Gerar Relatório 3");
        System.out.println("25. Adicionar Cliente");
        System.out.println("26. Remover Cliente");
        System.out.println("27. Atualizar Cliente");
        System.out.println("28. Listar Clientes");
        System.out.println("29. Exibir Relatórios");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");

        while (!input.hasNextInt()) {
            System.out.print("Entrada inválida! Por favor, insira um número: ");
            input.next();
        }
        return input.nextInt();
    }

    /**
     * Exibe os relatórios disponíveis.
     */
    private static void exibirRelatorios(MongoDatabase database) {
        // Exibir Relatório 1 - Produtos por Fornecedor
        System.out.println("\nRelatório 1:");
        new Relatorio1(database).gerarRelatorio1();  // Gera e exibe o relatório de produtos por fornecedor

        // Exibir Relatório 2 - Vendas por Cliente
        System.out.println("\nRelatório 2:");
        new Relatorio2(database).gerarRelatorio2();  // Gera e exibe o relatório de vendas por cliente

        // Exibir Relatório 3 - (ou qualquer outro relatório que você tenha implementado)
        System.out.println("\nRelatório 3:");
        new Relatorio3(database).gerarRelatorio3();  // Gera e exibe o relatório 3
    }
}
