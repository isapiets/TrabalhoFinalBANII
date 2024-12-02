package Floricultura;

import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.Scanner;

public class ClientesController {

    private final MongoCollection<Document> collection;
    private final ClientesModel clientesModel;

    public ClientesController(MongoDatabase database) {
        this.collection = database.getCollection("clientes");
        this.clientesModel = new ClientesModel();
    }

    // Método para criar um cliente
    public void createCliente() {
        Scanner scanner = new Scanner(System.in);

        // Leitura dos dados do cliente
        System.out.print("Informe o nome: ");
        String nome = scanner.nextLine().trim();

        System.out.print("Informe o sobrenome: ");
        String sobrenome = scanner.nextLine().trim();

        System.out.print("Informe o telefone: ");
        String telefone = scanner.nextLine().trim();

        System.out.print("Informe o email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Informe o CPF: ");
        String cpf = scanner.nextLine().trim();

        // Validação de entrada
        if (nome.isEmpty() || sobrenome.isEmpty() || telefone.isEmpty() || email.isEmpty() || cpf.isEmpty()) {
            System.out.println("Erro: Nenhum campo pode estar vazio. Favor rever os seus dados inseridos!");
            return;
        }

        // Validação do CPF (você pode adicionar mais validações aqui)
        if (cpf.length() != 11 || !cpf.matches("[0-9]+")) {
            System.out.println("Erro: CPF inválido. O CPF deve conter 11 dígitos numéricos.");
            return;
        }

        // Criação do objeto ClienteBean com os dados
        ClientesBean novoCliente = new ClientesBean(nome, sobrenome, telefone, email, cpf);
        clientesModel.salvarCliente(novoCliente);
    }

    // Método para excluir um cliente
    public void deleteCliente() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Informe o CPF do cliente a ser deletado: ");
        String cpf = scanner.nextLine().trim();

        if (cpf.isEmpty()) {
            System.out.println("Erro: O CPF não pode estar vazio.");
            return;
        }

        // Verifica se o cliente com o CPF fornecido existe
        Document filtro = new Document("cpf", cpf);
        if (collection.deleteOne(filtro).getDeletedCount() > 0) {
            System.out.println("Cliente deletado com sucesso!");
        } else {
            System.out.println("Erro: Nenhum cliente encontrado com esse CPF.");
        }
    }

    // Método para atualizar um cliente
    public void updateCliente() {
        Scanner scanner = new Scanner(System.in);

        // Leitura do CPF para encontrar o cliente a ser atualizado
        System.out.print("Informe o CPF do cliente a ser atualizado: ");
        String cpfAntigo = scanner.nextLine().trim();

        if (cpfAntigo.isEmpty()) {
            System.out.println("Erro: O CPF não pode estar vazio.");
            return;
        }

        Document clienteExistente = collection.find(new Document("cpf", cpfAntigo)).first();
        if (clienteExistente == null) {
            System.out.println("Erro: Nenhum cliente encontrado com esse CPF.");
            return;
        }

        // Leitura dos novos dados do cliente
        System.out.print("Informe o novo nome: ");
        String nomeNovo = scanner.nextLine().trim();

        System.out.print("Informe o novo sobrenome: ");
        String sobrenomeNovo = scanner.nextLine().trim();

        System.out.print("Informe o novo telefone: ");
        String telefoneNovo = scanner.nextLine().trim();

        System.out.print("Informe o novo email: ");
        String emailNovo = scanner.nextLine().trim();

        // Validação para garantir que os campos não estejam vazios
        if (nomeNovo.isEmpty() || sobrenomeNovo.isEmpty() || telefoneNovo.isEmpty() || emailNovo.isEmpty()) {
            System.out.println("Erro: Nenhum campo pode estar vazio.");
            return;
        }

        // Atualiza os dados no banco
        Document filtro = new Document("cpf", cpfAntigo);
        Document atualizacao = new Document("$set", new Document("nome", nomeNovo)
                .append("sobrenome", sobrenomeNovo)
                .append("telefone", telefoneNovo)
                .append("email", emailNovo));
        collection.updateOne(filtro, atualizacao);
        System.out.println("Cliente atualizado com sucesso!");
    }

    // Método para listar todos os clientes
    public void listClientes() {
        System.out.println("Clientes disponíveis:");
        for (Document doc : collection.find()) {
            System.out.println("- " + doc.getString("nome") + " " + doc.getString("sobrenome") + " | " +
                    doc.getString("telefone") + " | " + doc.getString("email") + " | " + doc.getString("cpf"));
        }

        // Mensagem se não houver clientes
        if (collection.countDocuments() == 0) {
            System.out.println("Nenhum cliente encontrado.");
        }
    }
}
