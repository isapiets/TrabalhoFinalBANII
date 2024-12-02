package Floricultura;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.Scanner;

public class ItensDaVendaController {
    private final MongoCollection<Document> collection;
    private final MongoCollection<Document> produtosCollection;

    public ItensDaVendaController(MongoDatabase database) {
        this.collection = database.getCollection("itensDaVenda");
        this.produtosCollection = database.getCollection("produtos");
    }

    public void createItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Informe o ID da Venda: ");
        ObjectId idVenda = new ObjectId(scanner.nextLine());  // ID da venda (ObjectId)

        System.out.print("Informe o ID do Produto (pode ser numérico ou ObjectId): ");
        String idProdutoInput = scanner.nextLine();  // ID do produto inserido pelo usuário

        // Verifica se o ID do produto é numérico ou ObjectId
        Object idProduto = null;
        try {
            // Tenta converter para Integer (caso o ID seja numérico)
            idProduto = Integer.parseInt(idProdutoInput);
        } catch (NumberFormatException e) {
            // Se não for numérico, tenta converter para ObjectId
            if (idProdutoInput.length() == 24 && idProdutoInput.matches("[0-9a-fA-F]+")) {
                idProduto = new ObjectId(idProdutoInput);  // Converte para ObjectId
            } else {
                System.out.println("Erro: O ID do produto informado não é válido.");
                return;
            }
        }

        // Verifica se o produto existe no banco (para Integer e ObjectId)
        Document produtoExistente = null;
        if (idProduto instanceof Integer) {
            produtoExistente = produtosCollection.find(new Document("_id", idProduto)).first();
        } else if (idProduto instanceof ObjectId) {
            produtoExistente = produtosCollection.find(new Document("_id", idProduto)).first();
        }

        // Se o produto não existir, exibe erro
        if (produtoExistente == null) {
            System.out.println("Erro: O ID do produto informado não existe.");
            return;
        }

        // Solicita a quantidade
        System.out.print("Informe a quantidade: ");
        int quantidade = scanner.nextInt();

        // Valida a quantidade
        if (quantidade < 0) {
            System.out.println("Erro: A quantidade não pode ser menor que 0.");
            return;
        }

        // Cria o item da venda e insere no banco
        Document item = new Document("id_venda", idVenda)
                .append("id_produto", idProduto)
                .append("quantidade", quantidade);
        collection.insertOne(item);
        System.out.println("Item da venda inserido com sucesso!");
    }

    // Método para deletar item da venda
    public void deleteItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Informe o ID do Item a ser deletado: ");
        ObjectId id = new ObjectId(scanner.nextLine());  // ID do item a ser deletado
        collection.deleteOne(new Document("_id", id));
        System.out.println("Item da venda deletado com sucesso!");
    }

    // Método para atualizar item da venda
    public void updateItem() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Informe o ID do Item a ser atualizado: ");
        ObjectId id = new ObjectId(scanner.nextLine());  // ID do item a ser atualizado
        System.out.print("Informe a nova quantidade: ");
        int quantidade = scanner.nextInt();

        // Valida a quantidade
        if (quantidade < 0) {
            System.out.println("Erro: A quantidade não pode ser menor que 0.");
            return;
        }

        // Cria o documento de atualização e aplica no banco
        Document atualizacao = new Document("$set", new Document("quantidade", quantidade));
        collection.updateOne(new Document("_id", id), atualizacao);
        System.out.println("Item da venda atualizado com sucesso!");
    }

    // Método para listar os itens da venda
    public void listItens() {
        System.out.println("Itens da venda disponíveis:");
        for (Document doc : collection.find()) {
            System.out.println(doc.toJson());
        }
    }
}
