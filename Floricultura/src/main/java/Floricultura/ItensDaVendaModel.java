package Floricultura;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class ItensDaVendaModel {
    private MongoCollection<Document> collection;
    private MongoCollection<Document> produtosCollection;

    // Construtor
    public ItensDaVendaModel() {
        this.collection = Conecta.getDatabase().getCollection("itensDaVenda");
        this.produtosCollection = Conecta.getDatabase().getCollection("produtos");
    }

    // Método para verificar e converter ID do produto
    private Object verificarIdProduto(Object idProduto) {
        // Se o ID do produto for um número inteiro
        if (idProduto instanceof Integer) {
            return idProduto;
        } else if (idProduto instanceof String) {
            // Se for uma string, tentamos convertê-la para ObjectId (caso seja um ID gerado pelo MongoDB)
            try {
                if (((String) idProduto).length() == 24 && ((String) idProduto).matches("[0-9a-fA-F]+")) {
                    return new ObjectId((String) idProduto);
                }
            } catch (Exception e) {
                System.out.println("Erro: O ID do produto informado não é válido.");
                return null;
            }
        }
        return null;
    }

    // Método para salvar um item da venda
    public void salvarItem(ItensDaVendaBean item) {
        // Verificar se o ID do produto existe no banco
        Object idProdutoVerificado = verificarIdProduto(item.getIdProduto());

        if (idProdutoVerificado == null) {
            System.out.println("Erro: O ID do produto informado não é válido.");
            return;
        }

        Document produtoExistente = produtosCollection.find(new Document("_id", idProdutoVerificado)).first();
        
        if (produtoExistente == null) {
            System.out.println("Erro: O ID do produto informado não existe.");
            return;
        }

        if (item.getQuantidade() < 0) {
            System.out.println("Erro: A quantidade não pode ser menor que 0.");
            return;
        }

        Document doc = new Document("id_venda", item.getIdVenda())
                .append("id_produto", idProdutoVerificado)
                .append("quantidade", item.getQuantidade());
        collection.insertOne(doc);
        System.out.println("Item da venda salvo: " + item);
    }

    // Método para listar todos os itens da venda
    public List<Document> listarItens() {
        return collection.find().into(new ArrayList<>());
    }

    // Método para encontrar um item pelo ID
    public Document encontrarItemPorId(ObjectId id) {
        return collection.find(new Document("_id", id)).first();
    }

    // Método para atualizar um item da venda
    public void atualizarItem(ItensDaVendaBean item) {
        // Verificar se o ID do produto existe no banco
        Object idProdutoVerificado = verificarIdProduto(item.getIdProduto());

        if (idProdutoVerificado == null) {
            System.out.println("Erro: O ID do produto informado não é válido.");
            return;
        }

        Document produtoExistente = produtosCollection.find(new Document("_id", idProdutoVerificado)).first();
        
        if (produtoExistente == null) {
            System.out.println("Erro: O ID do produto informado não existe.");
            return;
        }

        if (item.getQuantidade() < 0) {
            System.out.println("Erro: A quantidade não pode ser menor que 0.");
            return;
        }

        Document updatedDoc = new Document("id_venda", item.getIdVenda())
                .append("id_produto", idProdutoVerificado)
                .append("quantidade", item.getQuantidade());
        collection.replaceOne(new Document("_id", item.getId()), updatedDoc);
        System.out.println("Item da venda atualizado: " + item);
    }

    // Método para excluir um item da venda
    public void excluirItem(ObjectId id) {
        collection.deleteOne(new Document("_id", id));
        System.out.println("Item da venda excluído com o ID: " + id);
    }
}
