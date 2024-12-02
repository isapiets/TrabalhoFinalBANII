package Floricultura;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.List;

public class VendasModel {
    private final MongoCollection<Document> collection;
    private final MongoCollection<Document> clientesCollection;

    public VendasModel() {
        this.collection = Conecta.getDatabase().getCollection("vendas");
        this.clientesCollection = Conecta.getDatabase().getCollection("clientes");
    }

    // Método para salvar uma venda
    public void salvarVenda(VendasBean venda) {
        // Validações
        if (venda.getTotal() < 0) {
            System.out.println("Erro: O total da venda não pode ser menor que 0.");
            return;
        }

        if (venda.getData() == null || venda.getData().isEmpty()) {
            System.out.println("Erro: A data da venda é obrigatória.");
            return;
        }

        if (venda.getIdCliente() == null || clientesCollection.find(new Document("_id", venda.getIdCliente())).first() == null) {
            System.out.println("Erro: O cliente informado não existe.");
            return;
        }

        // Inserir venda
        Document doc = new Document("data", venda.getData())
                .append("total", venda.getTotal())
                .append("id_cliente", venda.getIdCliente());
        collection.insertOne(doc);
        System.out.println("Venda salva com sucesso!");
    }

    // Método para listar todas as vendas
    public List<Document> listarVendas() {
        return collection.find().into(new ArrayList<Document>());
    }

    // Método para atualizar uma venda
    public void atualizarVenda(VendasBean venda) {
        // Validações
        if (venda.getTotal() < 0) {
            System.out.println("Erro: O total da venda não pode ser menor que 0.");
            return;
        }

        if (venda.getData() == null || venda.getData().isEmpty()) {
            System.out.println("Erro: A data da venda é obrigatória.");
            return;
        }

        if (venda.getIdCliente() == null || clientesCollection.find(new Document("_id", venda.getIdCliente())).first() == null) {
            System.out.println("Erro: O cliente informado não existe.");
            return;
        }

        // Verifica se a venda existe
        Document vendaExistente = collection.find(new Document("_id", venda.getIdVenda())).first();
        if (vendaExistente == null) {
            System.out.println("Erro: A venda informada não foi encontrada.");
            return;
        }

        // Atualiza a venda
        Document updatedDoc = new Document("data", venda.getData())
                .append("total", venda.getTotal())
                .append("id_cliente", venda.getIdCliente());

        // Atualiza o documento da venda baseado no _id da venda
        collection.replaceOne(new Document("_id", venda.getIdVenda()), updatedDoc);
        System.out.println("Venda atualizada com sucesso!");
    }

    // Método para excluir uma venda
    public boolean excluirVenda(ObjectId id) {
        // Verifique se a venda existe antes de tentar excluir
        Document venda = collection.find(new Document("_id", id)).first();
        if (venda != null) {
            collection.deleteOne(new Document("_id", id));
            return true;
        } else {
            return false; // Venda não encontrada
        }
    }
}
