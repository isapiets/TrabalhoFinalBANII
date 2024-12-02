package Floricultura;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.Scanner;

public class ProdutosController {

    private final MongoCollection<Document> collection;
    private final MongoCollection<Document> associativaCollection;

    public ProdutosController(MongoDatabase database) {
        this.collection = database.getCollection("produtos");
        this.associativaCollection = database.getCollection("fornecedoresProdutos");
    }

    public void createProduto() {
        Scanner scanner = new Scanner(System.in);

        // Validar ID único
        System.out.print("Informe o ID do Produto: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha
        if (collection.find(new Document("_id", id)).first() != null) {
            System.out.println("Erro: Já existe um produto com o ID informado. Escolha um ID único.");
            return;
        }

        // Validar descrição única
        System.out.print("Informe a descrição do Produto: ");
        String descricao = scanner.nextLine();
        if (collection.find(new Document("descricao", descricao)).first() != null) {
            System.out.println("Erro: Já existe um produto com a descrição informada. Escolha uma descrição única.");
            return;
        }

        // Validar preço unitário
        System.out.print("Informe o preço unitário: ");
        double precoUnitario = scanner.nextDouble();
        if (precoUnitario < 0) {
            System.out.println("Erro: O preço unitário não pode ser menor que 0.");
            return;
        }

        // Validar quantidade em estoque
        System.out.print("Informe a quantidade em estoque: ");
        int qtdEstoque = scanner.nextInt();
        if (qtdEstoque < 0) {
            System.out.println("Erro: A quantidade em estoque não pode ser menor que 0.");
            return;
        }

        // Validar ID da Categoria
        System.out.print("Informe o ID da Categoria (número de 1 a 6): ");
        int idCategoria = scanner.nextInt();
        if (idCategoria < 1 || idCategoria > 6) {
            System.out.println("Erro: O ID da Categoria deve ser um número entre 1 e 6.");
            return;
        }

        // Criar o documento
        Document produto = new Document("_id", id)
                .append("descricao", descricao)
                .append("preco_unitario", precoUnitario)
                .append("qtd_estoque", qtdEstoque)
                .append("id_categoria", idCategoria);

        // Inserir no banco de dados
        collection.insertOne(produto);
        System.out.println("Produto inserido com sucesso!");

        // Associar o produto com um fornecedor (caso necessário)
        System.out.print("Informe o ID do Fornecedor para associar ao produto: ");
        int idFornecedor = scanner.nextInt();

        // Criar o documento para a tabela associativa
        Document associativa = new Document("id_produto", id)
                .append("id_fornecedor", idFornecedor);

        // Inserir na tabela associativa
        associativaCollection.insertOne(associativa);
        System.out.println("Associação com fornecedor registrada com sucesso!");
    }

    public void deleteProduto() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Informe o ID do Produto a ser deletado: ");
        int id = scanner.nextInt();
        Document filtroProduto = new Document("_id", id);

        // Deletar o produto da coleção 'produtos'
        if (collection.deleteOne(filtroProduto).getDeletedCount() > 0) {
            System.out.println("Produto deletado com sucesso!");

            // Deletar os registros na tabela associativa 'fornecedoresProdutos'
            associativaCollection.deleteMany(new Document("id_produto", id));
            System.out.println("Referências ao produto na tabela associativa 'fornecedoresProdutos' removidas.");
        } else {
            System.out.println("Erro: Produto com o ID especificado não encontrado.");
        }
    }

    public void updateProduto() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Informe o ID do Produto a ser atualizado: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consumir a quebra de linha

        Document produtoExistente = collection.find(new Document("_id", id)).first();
        if (produtoExistente == null) {
            System.out.println("Erro: Produto com o ID informado não encontrado.");
            return;
        }

        System.out.print("Informe a nova descrição do Produto: ");
        String descricao = scanner.nextLine();
        if (collection.find(new Document("descricao", descricao)).first() != null &&
                !produtoExistente.getString("descricao").equals(descricao)) {
            System.out.println("Erro: Já existe um produto com a descrição informada.");
            return;
        }

        System.out.print("Informe o novo preço unitário: ");
        double precoUnitario = scanner.nextDouble();
        if (precoUnitario < 0) {
            System.out.println("Erro: O preço unitário não pode ser menor que 0.");
            return;
        }

        System.out.print("Informe a nova quantidade em estoque: ");
        int qtdEstoque = scanner.nextInt();
        if (qtdEstoque < 0) {
            System.out.println("Erro: A quantidade em estoque não pode ser menor que 0.");
            return;
        }

        System.out.print("Informe o novo ID da Categoria (número de 1 a 6): ");
        int idCategoria = scanner.nextInt();
        if (idCategoria < 1 || idCategoria > 6) {
            System.out.println("Erro: O ID da Categoria deve ser um número entre 1 e 6.");
            return;
        }

        Document atualizacao = new Document("$set", new Document("descricao", descricao)
                .append("preco_unitario", precoUnitario)
                .append("qtd_estoque", qtdEstoque)
                .append("id_categoria", idCategoria));
        collection.updateOne(new Document("_id", id), atualizacao);
        System.out.println("Produto atualizado com sucesso!");

        // Caso você precise atualizar a tabela associativa para refletir alterações, faça o necessário aqui.
    }

    public void listProdutos() {
        System.out.println("Produtos disponíveis:");
        for (Document doc : collection.find()) {
            System.out.println("- " + doc.toJson());
        }
    }
}
