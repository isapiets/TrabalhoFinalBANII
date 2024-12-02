package Floricultura;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.client.MongoDatabase;
import java.util.Arrays;

public class Relatorio2 {

    private MongoDatabase database;

    public Relatorio2(MongoDatabase database) {
        this.database = database;
    }

    public void gerarRelatorio2() {
        try {
            // Acessa a coleção de vendas
            MongoCollection<Document> vendasCollection = database.getCollection("vendas");

            // Realiza uma junção entre as coleções 'vendas' e 'clientes' para pegar os dados do cliente
            MongoCursor<Document> cursor = vendasCollection.aggregate(
                Arrays.asList(
                    new Document("$lookup", new Document("from", "clientes") // Junção com a coleção de clientes
                        .append("localField", "id_cliente") // Campo em vendas
                        .append("foreignField", "_id")    // Campo em clientes
                        .append("as", "cliente_info")),   // Alias para a junção
                    new Document("$unwind", "$cliente_info")  // "Desmonta" o array 'cliente_info'
                )
            ).iterator();

            // Exibe os dados de vendas e clientes
            while (cursor.hasNext()) {
                Document venda = cursor.next();
                Document clienteInfo = (Document) venda.get("cliente_info"); // Acessa o cliente_info

                if (clienteInfo != null) {
                    // Pega o ID da venda
                    Object vendaId = venda.get("_id"); // Pode ser Integer ou ObjectId
                    String clienteNome = clienteInfo.getString("nome"); // Nome do cliente
                    Integer totalVenda = venda.getInteger("total"); // Total da venda (int32)

                    // Exibe o nome do cliente
                    System.out.println("Cliente: " + clienteNome);

                    // Exibe o ID da venda, tratando o tipo
                    if (vendaId instanceof ObjectId) {
                        System.out.println("ID da Venda: " + ((ObjectId) vendaId).toString()); // Se for ObjectId
                    } else {
                        System.out.println("ID da Venda: " + vendaId); // Se for Integer
                    }

                    // Exibe o total da venda
                    System.out.println("Total da Venda: " + totalVenda);
                    System.out.println("-----------------------------------");
                } else {
                    System.out.println("Cliente não encontrado para a venda.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
