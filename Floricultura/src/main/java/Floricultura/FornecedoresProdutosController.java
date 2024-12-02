package Floricultura;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.FindIterable;

public class FornecedoresProdutosController {

    private final MongoCollection<Document> fornecedoresProdutosCollection;

    public FornecedoresProdutosController(MongoDatabase database) {
        // Inicializa a coleção fornecedoresProdutos
        this.fornecedoresProdutosCollection = database.getCollection("fornecedoresProdutos");
    }

    /**
     * Exibe os IDs de produtos e fornecedores a partir da tabela associativa.
     */
    public void listarIdsAssociativos() {
        System.out.println("Listando IDs de Produtos e Fornecedores:");

        // Faz a consulta para buscar apenas os campos id_produto e id_fornecedor
        FindIterable<Document> documentos = fornecedoresProdutosCollection.find()
                .projection(new Document("id_produto", 1).append("id_fornecedor", 1).append("_id", 0));

        // Itera sobre os documentos e exibe os IDs
        for (Document doc : documentos) {
            try {
                // Tenta obter os valores como Integer
                Integer idProduto = tryParseInteger(doc.get("id_produto"));
                Integer idFornecedor = tryParseInteger(doc.get("id_fornecedor"));
                
                if (idProduto != null && idFornecedor != null) {
                    System.out.println("ID Produto: " + idProduto + ", ID Fornecedor: " + idFornecedor);
                }
            } catch (Exception e) {
                // Caso ocorra um erro, simplesmente ignore e siga para o próximo documento
                // Não imprimirá mais a mensagem de erro
            }
        }
    }

    // Método auxiliar para tentar converter um valor para Integer
    private Integer tryParseInteger(Object value) {
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return null; // Retorna null se não for possível converter
            }
        }
        return null; // Retorna null para tipos não esperados
    }
}
