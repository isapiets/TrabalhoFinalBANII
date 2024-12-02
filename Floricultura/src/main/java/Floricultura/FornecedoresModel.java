package Floricultura;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class FornecedoresModel {

    private final MongoCollection<Document> collection;

    public FornecedoresModel(MongoCollection<Document> collection) {
        this.collection = collection;
    }

    public String createFornecedor(String nome, String rua, int numero, String bairro, String cidade, String estado, String email, String telefone) {
        // Validação de entradas
        if (nome.isEmpty()) {
            return "Erro: O nome do fornecedor não pode estar vazio.";
        }

        if (rua.isEmpty()) {
            return "Erro: O campo rua não pode estar vazio.";
        }

        if (bairro.isEmpty()) {
            return "Erro: O campo bairro não pode estar vazio.";
        }

        if (cidade.isEmpty()) {
            return "Erro: O campo cidade não pode estar vazio.";
        }

        if (estado.isEmpty()) {
            return "Erro: O campo estado não pode estar vazio.";
        }

        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            return "Erro: E-mail inválido.";
        }

        if (telefone.isEmpty() || telefone.length() < 10) {
            return "Erro: O telefone informado é inválido.";
        }

        // Criando o documento a ser inserido no banco
        Document fornecedor = new Document("nome", nome)
                .append("rua", rua)
                .append("numero", numero)
                .append("bairro", bairro)
                .append("cidade", cidade)
                .append("estado", estado)
                .append("email", email)
                .append("telefone", telefone);

        try {
            collection.insertOne(fornecedor);
            return "Fornecedor inserido com sucesso!";
        } catch (MongoException e) {
            return "Erro ao inserir fornecedor no banco de dados. Tente novamente.";
        }
    }

    public String deleteFornecedor(String nome) {
        if (nome.isEmpty()) {
            return "Erro: O nome do fornecedor não pode estar vazio.";
        }

        Document filtro = new Document("nome", nome);

        try {
            long deletedCount = collection.deleteOne(filtro).getDeletedCount();
            if (deletedCount > 0) {
                return "Fornecedor deletado com sucesso!";
            } else {
                return "Erro: Fornecedor não encontrado.";
            }
        } catch (MongoException e) {
            return "Erro ao excluir fornecedor. Tente novamente.";
        }
    }

    public String updateFornecedor(String nomeAntigo, String nomeNovo, String rua, int numero, String bairro, String cidade, String estado, String email, String telefone) {
        if (nomeAntigo.isEmpty() || nomeNovo.isEmpty()) {
            return "Erro: O nome do fornecedor não pode estar vazio.";
        }

        // Validações de outros campos, se necessário
        if (rua.isEmpty()) {
            return "Erro: O campo rua não pode estar vazio.";
        }

        if (bairro.isEmpty()) {
            return "Erro: O campo bairro não pode estar vazio.";
        }

        if (cidade.isEmpty()) {
            return "Erro: O campo cidade não pode estar vazio.";
        }

        if (estado.isEmpty()) {
            return "Erro: O campo estado não pode estar vazio.";
        }

        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            return "Erro: E-mail inválido.";
        }

        if (telefone.isEmpty() || telefone.length() < 10) {
            return "Erro: O telefone informado é inválido.";
        }

        Document filtro = new Document("nome", nomeAntigo);
        Document atualizacao = new Document("$set", new Document("nome", nomeNovo)
                .append("rua", rua)
                .append("numero", numero)
                .append("bairro", bairro)
                .append("cidade", cidade)
                .append("estado", estado)
                .append("email", email)
                .append("telefone", telefone));

        try {
            collection.updateOne(filtro, atualizacao);
            return "Fornecedor atualizado com sucesso!";
        } catch (MongoException e) {
            return "Erro ao atualizar fornecedor. Tente novamente.";
        }
    }

    public String listFornecedores() {
        try {
            StringBuilder fornecedoresList = new StringBuilder("Fornecedores disponíveis:\n");
            for (Document doc : collection.find()) {
                fornecedoresList.append("- Nome: ").append(doc.getString("nome")).append("\n")
                        .append("  Endereço: ").append(doc.getString("rua")).append(", ")
                        .append(doc.getInteger("numero")).append(" - ")
                        .append(doc.getString("bairro")).append(", ")
                        .append(doc.getString("cidade")).append(" - ")
                        .append(doc.getString("estado")).append("\n")
                        .append("  E-mail: ").append(doc.getString("email")).append("\n")
                        .append("  Telefone: ").append(doc.getString("telefone")).append("\n\n");
            }
            return fornecedoresList.toString();
        } catch (MongoException e) {
            return "Erro ao listar fornecedores. Tente novamente.";
        }
    }
}