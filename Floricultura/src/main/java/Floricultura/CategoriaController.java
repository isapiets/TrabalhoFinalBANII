package Floricultura;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Scanner;

public class CategoriaController {

    private final MongoCollection<Document> collection;

    public CategoriaController(MongoDatabase database) {
        this.collection = database.getCollection("categoria");
    }

    public void createCategoria() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Informe o nome da Categoria: ");
        String nome = scanner.nextLine().trim();

        if (nome.isEmpty()) {
            System.out.println("Erro: O nome da categoria não pode estar vazio. Favor digitar o nome da categoria");
            return;
        }

        if (collection.find(new Document("nome", nome)).first() != null) {
            System.out.println("Erro: Já existe uma categoria com esse nome.");
            return;
        }

        Document categoria = new Document("nome", nome);
        collection.insertOne(categoria);
        System.out.println("Categoria inserida com sucesso!");
    }

    public void deleteCategoria() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Informe o nome da Categoria a ser deletada: ");
        String nome = scanner.nextLine().trim();

        if (nome.isEmpty()) {
            System.out.println("Erro: O nome da categoria não pode estar vazio.");
            return;
        }

        Document filtro = new Document("nome", nome);
        if (collection.deleteOne(filtro).getDeletedCount() > 0) {
            System.out.println("Categoria deletada com sucesso!");
        } else {
            System.out.println("Erro: Nenhuma categoria encontrada com esse nome.");
        }
    }

    public void updateCategoria() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Informe o nome da Categoria a ser atualizada: ");
        String nomeAntigo = scanner.nextLine().trim();

        if (nomeAntigo.isEmpty()) {
            System.out.println("Erro: O nome da categoria não pode estar vazio.");
            return;
        }

        if (collection.find(new Document("nome", nomeAntigo)).first() == null) {
            System.out.println("Erro: Nenhuma categoria encontrada com esse nome.");
            return;
        }

        System.out.print("Informe o novo nome da Categoria: ");
        String nomeNovo = scanner.nextLine().trim();

        if (nomeNovo.isEmpty()) {
            System.out.println("Erro: O novo nome da categoria não pode estar vazio.");
            return;
        }

        Document filtro = new Document("nome", nomeAntigo);
        Document atualizacao = new Document("$set", new Document("nome", nomeNovo));
        collection.updateOne(filtro, atualizacao);
        System.out.println("Categoria atualizada com sucesso!");
    }

    public void listCategorias() {
        System.out.println("Categorias disponíveis:");
        for (Document doc : collection.find()) {
            System.out.println("- ID: " + doc.get("_id") + ", Nome: " + doc.getString("nome"));
        }

        if (collection.countDocuments() == 0) {
            System.out.println("Nenhuma categoria encontrada.");
        }
    }
}
