package Floricultura;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;

public class Relatorio3 {

    private final MongoCollection<Document> itensCollection;

    public Relatorio3(MongoDatabase database) {
        this.itensCollection = database.getCollection("itensDaVenda");
    }

    public void gerarRelatorio3() {
        try {
            System.out.println("Relatório de Itens da Venda:");

            // Realiza a junção entre 'itensDaVenda', 'produtos' e 'vendas'
            MongoCursor<Document> cursor = itensCollection.aggregate(
                Arrays.asList(
                    new Document("$lookup", new Document("from", "produtos")
                        .append("localField", "id_produto")
                        .append("foreignField", "_id")
                        .append("as", "produto_info")),
                    new Document("$unwind", "$produto_info"),
                    new Document("$lookup", new Document("from", "vendas")
                        .append("localField", "id_venda")
                        .append("foreignField", "_id")
                        .append("as", "venda_info")),
                    new Document("$unwind", "$venda_info")
                )
            ).iterator();

            // Itera pelos resultados e exibe os dados relevantes
            while (cursor.hasNext()) {
                Document item = cursor.next();
                Document produtoInfo = (Document) item.get("produto_info");
                Document vendaInfo = (Document) item.get("venda_info");

                // Extrai os dados necessários
                Object idVenda = vendaInfo.get("_id");
                String descricaoProduto = produtoInfo.getString("descricao");
                Integer quantidade = item.getInteger("quantidade");

                // Exibe apenas os campos selecionados
                System.out.println("ID da Venda: " + idVenda);
                System.out.println("Produto: " + descricaoProduto);
                System.out.println("Quantidade: " + quantidade);
                System.out.println("-----------------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
