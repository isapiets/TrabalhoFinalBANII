package Floricultura;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Scanner;

public class VendasController {
    private final MongoDatabase database;

    public VendasController(MongoDatabase database) {
        this.database = database;
    }

    // Método para criar uma venda
    public void createVenda() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Informe a data da venda (formato: YYYY-MM-DD):");
        String data = scanner.nextLine();

        System.out.println("Informe o total da venda:");
        int total = scanner.nextInt();
        scanner.nextLine(); // Consumir a linha restante

        System.out.println("Informe o ID do cliente:");
        String idClienteStr = scanner.nextLine();

        // Verificar se o ID do cliente existe
        ObjectId idCliente;
        try {
            idCliente = new ObjectId(idClienteStr); // Converter para ObjectId
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: O ID do cliente informado não é válido.");
            return;
        }

        MongoCollection<Document> clientesCollection = database.getCollection("clientes");
        Document clienteExistente = clientesCollection.find(new Document("_id", idCliente)).first();
        if (clienteExistente == null) {
            System.out.println("Erro: O cliente informado não existe. Por favor, insira um cliente válido.");
            return;
        }

        // Criar a venda com os dados informados
        VendasBean venda = new VendasBean();
        venda.setData(data);
        venda.setTotal(total);
        venda.setIdCliente(idCliente);

        // Salvar a venda no banco de dados
        VendasModel vendasModel = new VendasModel();
        vendasModel.salvarVenda(venda);
    }

    // Método para listar todas as vendas
    public void listarVendas() {
        VendasModel vendasModel = new VendasModel();
        for (var venda : vendasModel.listarVendas()) {
            System.out.println(venda.toJson());
        }
    }

    // Método para excluir uma venda
    public void deleteVenda() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Informe o ID da venda a ser excluída:");
        String idVendaStr = scanner.nextLine();
        ObjectId idVenda = new ObjectId(idVendaStr); // Converter para ObjectId

        VendasModel vendasModel = new VendasModel();
        boolean excluida = vendasModel.excluirVenda(idVenda);

        if (excluida) {
            System.out.println("Venda excluída com sucesso!");
        } else {
            System.out.println("Venda não encontrada.");
        }
    }

    // Método para atualizar uma venda
    public void updateVenda() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Informe o ID da venda a ser atualizada:");
        String idVendaStr = scanner.nextLine();
        ObjectId idVenda = new ObjectId(idVendaStr); // Converter para ObjectId

        System.out.println("Informe a nova data da venda (formato: YYYY-MM-DD):");
        String data = scanner.nextLine();

        System.out.println("Informe o novo total da venda:");
        int total = scanner.nextInt();
        scanner.nextLine(); // Consumir a linha restante

        System.out.println("Informe o ID do cliente:");
        String idClienteStr = scanner.nextLine();
        ObjectId idCliente = new ObjectId(idClienteStr); // Converter para ObjectId

        // Criar a venda com os dados informados
        VendasBean venda = new VendasBean();
        venda.setIdVenda(idVenda);
        venda.setData(data);
        venda.setTotal(total);
        venda.setIdCliente(idCliente);

        // Atualizar a venda no banco de dados
        VendasModel vendasModel = new VendasModel();
        vendasModel.atualizarVenda(venda);
    }
}
