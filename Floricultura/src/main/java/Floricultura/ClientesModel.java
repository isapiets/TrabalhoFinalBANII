package Floricultura;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class ClientesModel {

    private MongoCollection<Document> collection;

    // Construtor
    public ClientesModel() {
        // Conectar ao MongoDB e obter a coleção de clientes
        collection = Conecta.getDatabase().getCollection("clientes");
    }

    // Método para salvar um cliente
    public void salvarCliente(ClientesBean cliente) {
        // Validação dos campos do cliente
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            System.out.println("Erro: O nome do cliente não pode estar vazio.");
            return;
        }

        if (cliente.getSobrenome() == null || cliente.getSobrenome().trim().isEmpty()) {
            System.out.println("Erro: O sobrenome do cliente não pode estar vazio.");
            return;
        }

        if (cliente.getTelefone() == null || cliente.getTelefone().trim().isEmpty()) {
            System.out.println("Erro: O telefone do cliente não pode estar vazio.");
            return;
        }

        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            System.out.println("Erro: O email do cliente não pode estar vazio.");
            return;
        }

        if (cliente.getCpf() == null || cliente.getCpf().trim().isEmpty()) {
            System.out.println("Erro: O CPF do cliente não pode estar vazio.");
            return;
        }

        // Verificar se já existe um cliente com o mesmo CPF
        if (collection.find(new Document("cpf", cliente.getCpf())).first() != null) {
            System.out.println("Erro: Já existe um cliente com esse CPF.");
            return;
        }

        // Inserir o cliente na coleção do MongoDB
        Document doc = new Document("nome", cliente.getNome())
                .append("sobrenome", cliente.getSobrenome())
                .append("telefone", cliente.getTelefone())
                .append("email", cliente.getEmail())
                .append("cpf", cliente.getCpf());

        collection.insertOne(doc);
        System.out.println("Cliente salvo com sucesso: " + cliente);
    }

    // Método para listar todos os clientes
    public List<Document> listarClientes() {
        return collection.find().into(new ArrayList<>());
    }

    // Método para encontrar um cliente pelo ID
    public Document encontrarClientePorId(String id) {
        if (id == null || id.trim().isEmpty()) {
            System.out.println("Erro: O ID não pode estar vazio.");
            return null;
        }

        Document cliente = collection.find(new Document("_id", id)).first();

        if (cliente == null) {
            System.out.println("Erro: Nenhum cliente encontrado com o ID fornecido.");
        }

        return cliente;
    }

    // Método para atualizar um cliente
    public void atualizarCliente(ClientesBean cliente) {
        // Validação dos campos do cliente
        if (cliente.getId() == null) {
            System.out.println("Erro: O ID do cliente não pode estar vazio.");
            return;
        }

        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            System.out.println("Erro: O nome do cliente não pode estar vazio.");
            return;
        }

        if (cliente.getSobrenome() == null || cliente.getSobrenome().trim().isEmpty()) {
            System.out.println("Erro: O sobrenome do cliente não pode estar vazio.");
            return;
        }

        if (cliente.getTelefone() == null || cliente.getTelefone().trim().isEmpty()) {
            System.out.println("Erro: O telefone do cliente não pode estar vazio.");
            return;
        }

        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            System.out.println("Erro: O email do cliente não pode estar vazio.");
            return;
        }

        if (cliente.getCpf() == null || cliente.getCpf().trim().isEmpty()) {
            System.out.println("Erro: O CPF do cliente não pode estar vazio.");
            return;
        }

        // Atualizar o cliente na coleção
        Document updatedDoc = new Document("nome", cliente.getNome())
                .append("sobrenome", cliente.getSobrenome())
                .append("telefone", cliente.getTelefone())
                .append("email", cliente.getEmail())
                .append("cpf", cliente.getCpf());

        long modifiedCount = collection.replaceOne(new Document("_id", cliente.getId()), updatedDoc).getModifiedCount();

        if (modifiedCount > 0) {
            System.out.println("Cliente atualizado com sucesso: " + cliente);
        } else {
            System.out.println("Erro: Não foi possível atualizar o cliente. Verifique o ID fornecido.");
        }
    }

    // Método para excluir um cliente
    public void excluirCliente(String id) {
        if (id == null || id.trim().isEmpty()) {
            System.out.println("Erro: O ID não pode estar vazio.");
            return;
        }

        long deletedCount = collection.deleteOne(new Document("_id", id)).getDeletedCount();

        if (deletedCount > 0) {
            System.out.println("Cliente excluído com o ID: " + id);
        } else {
            System.out.println("Erro: Nenhum cliente encontrado com o ID fornecido.");
        }
    }
}
