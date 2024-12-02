package Floricultura;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Projections;
import org.bson.Document;

import java.util.Arrays;

public class Relatorio1 {
    private final MongoCollection<Document> fornecedoresCollection;

    public Relatorio1(MongoDatabase database) {
        this.fornecedoresCollection = database.getCollection("fornecedores");
    }

    public void gerarRelatorio1() {
        System.out.println("Gerando Relatório 1 - Produtos por Fornecedor...");

        var pipeline = Arrays.asList(
            Aggregates.lookup("fornecedoresProdutos", "_id", "id_fornecedor", "produtosAssociados"),
            Aggregates.unwind("$produtosAssociados"),
            Aggregates.lookup("produtos", "produtosAssociados.id_produto", "_id", "detalhesProdutos"),
            Aggregates.unwind("$detalhesProdutos"),
            Aggregates.project(Projections.fields(
                Projections.include("nome"),
                Projections.computed("descricao", "$detalhesProdutos.descricao")
            ))
        );

        fornecedoresCollection.aggregate(pipeline).forEach(doc -> {
            String nomeFornecedor = doc.getString("nome");
            String descricaoProduto = doc.getString("descricao");

            System.out.println("Fornecedor: " + nomeFornecedor);
            System.out.println("Produto: " + (descricaoProduto != null ? descricaoProduto : "Descrição não disponível"));
            System.out.println("-----------------------------------------");
        });
    }
}
