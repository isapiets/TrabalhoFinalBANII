package Floricultura;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.Scanner;

public class FornecedoresController {

    private final MongoCollection<Document> collection;

    public FornecedoresController(MongoDatabase database) {
        this.collection = database.getCollection("fornecedores");
    }

    public void createFornecedor() {
        Scanner scanner = new Scanner(System.in);

        // Validação de entrada
        System.out.print("Informe o nome do Fornecedor: ");
        String nome = scanner.nextLine().trim();
        if (nome.isEmpty()) {
            System.out.println("Erro: O nome do fornecedor não pode estar vazio.");
            return;
        }

        System.out.print("Informe a rua: ");
        String rua = scanner.nextLine().trim();
        if (rua.isEmpty()) {
            System.out.println("Erro: O campo rua não pode estar vazio.");
            return;
        }

        System.out.print("Informe o número: ");
        String numeroStr = scanner.nextLine().trim();
        int numero;
        try {
            numero = Integer.parseInt(numeroStr);
        } catch (NumberFormatException e) {
            System.out.println("Erro: O número informado é inválido.");
            return;
        }

        System.out.print("Informe o bairro: ");
        String bairro = scanner.nextLine().trim();
        if (bairro.isEmpty()) {
            System.out.println("Erro: O campo bairro não pode estar vazio.");
            return;
        }

        System.out.print("Informe a cidade: ");
        String cidade = scanner.nextLine().trim();
        if (cidade.isEmpty()) {
            System.out.println("Erro: O campo cidade não pode estar vazio.");
            return;
        }

        System.out.print("Informe o estado: ");
        String estado = scanner.nextLine().trim();
        if (estado.isEmpty()) {
            System.out.println("Erro: O campo estado não pode estar vazio.");
            return;
        }

        System.out.print("Informe o e-mail: ");
        String email = scanner.nextLine().trim();
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            System.out.println("Erro: E-mail inválido.");
            return;
        }

        System.out.print("Informe o telefone: ");
        String telefone = scanner.nextLine().trim();
        if (telefone.isEmpty() || telefone.length() < 10) {
            System.out.println("Erro: O telefone informado é inválido.");
            return;
        }

        // Criar o documento para inserir no banco de dados
        Document fornecedor = new Document("nome", nome)
                .append("rua", rua)
                .append("numero", numero)
                .append("bairro", bairro)
                .append("cidade", cidade)
                .append("estado", estado)
                .append("email", email)
                .append("telefone", telefone);

        // Tentar inserir o fornecedor no banco de dados
        try {
            collection.insertOne(fornecedor);
            System.out.println("Fornecedor inserido com sucesso!");
        } catch (MongoException e) {
            System.out.println("Erro ao inserir fornecedor no banco de dados. Tente novamente.");
        }
    }

    public void deleteFornecedor() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Informe o nome do Fornecedor a ser deletado: ");
        String nome = scanner.nextLine().trim();

        // Validação de entrada
        if (nome.isEmpty()) {
            System.out.println("Erro: O nome do fornecedor não pode estar vazio.");
            return;
        }

        Document filtro = new Document("nome", nome);

        // Tentar deletar o fornecedor
        try {
            long deletedCount = collection.deleteOne(filtro).getDeletedCount();
            if (deletedCount > 0) {
                System.out.println("Fornecedor deletado com sucesso!");
            } else {
                System.out.println("Erro: Fornecedor não encontrado.");
            }
        } catch (MongoException e) {
            System.out.println("Erro ao excluir fornecedor. Tente novamente.");
        }
    }

    public void updateFornecedor() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Informe o nome do Fornecedor a ser atualizado: ");
        String nomeAntigo = scanner.nextLine().trim();
        if (nomeAntigo.isEmpty()) {
            System.out.println("Erro: O nome do fornecedor não pode estar vazio.");
            return;
        }

        System.out.print("Informe o novo nome do Fornecedor: ");
        String nomeNovo = scanner.nextLine().trim();
        if (nomeNovo.isEmpty()) {
            System.out.println("Erro: O novo nome não pode estar vazio.");
            return;
        }

        // Outros campos de atualização...
        // [Adicionar as validações aqui para rua, número, bairro, etc.]

        Document filtro = new Document("nome", nomeAntigo);
        Document atualizacao = new Document("$set", new Document("nome", nomeNovo));

        try {
            collection.updateOne(filtro, atualizacao);
            System.out.println("Fornecedor atualizado com sucesso!");
        } catch (MongoException e) {
            System.out.println("Erro ao atualizar fornecedor. Tente novamente.");
        }
    }

    public void listFornecedores() {
        System.out.println("Fornecedores disponíveis:");

        // Tentar listar os fornecedores
        try {
            for (Document doc : collection.find()) {
                System.out.println("- Nome: " + doc.getString("nome"));
                System.out.println("  Endereço: " + doc.getString("rua") + ", " 
                                   + doc.getInteger("numero") + " - " 
                                   + doc.getString("bairro") + ", " 
                                   + doc.getString("cidade") + " - " 
                                   + doc.getString("estado"));
                System.out.println("  E-mail: " + doc.getString("email"));
                System.out.println("  Telefone: " + doc.getString("telefone"));
            }
        } catch (MongoException e) {
            System.out.println("Erro ao listar fornecedores. Tente novamente.");
        }
    }
}