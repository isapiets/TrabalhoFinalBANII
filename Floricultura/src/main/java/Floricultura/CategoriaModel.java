package Floricultura;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class CategoriaModel {

    private MongoCollection<Document> collection;

    public CategoriaModel() {
        collection = Conecta.getDatabase().getCollection("categoria");
    }

    public void salvarCategoria(CategoriaBean categoria) {
        if (categoria.getNome() == null || categoria.getNome().toString().trim().isEmpty()) {
            System.out.println("Erro: O nome da categoria não pode estar vazio.");
            return;
        }

        if (categoria.getDescricao() == null || categoria.getDescricao().trim().isEmpty()) {
            System.out.println("Erro: A descrição da categoria não pode estar vazia.");
            return;
        }

        Document doc = new Document("nome", categoria.getNome())
                .append("descricao", categoria.getDescricao());

        if (collection.find(new Document("nome", categoria.getNome())).first() != null) {
            System.out.println("Erro: Já existe uma categoria com esse nome.");
            return;
        }

        collection.insertOne(doc);
        System.out.println("Categoria salva: " + categoria);
    }

    public List<Document> listarCategorias() {
        List<Document> categorias = collection.find().into(new ArrayList<>());

        if (categorias.isEmpty()) {
            System.out.println("Nenhuma categoria encontrada.");
        } else {
            System.out.println("Categorias disponíveis:");
            for (Document categoria : categorias) {
                System.out.println("- ID: " + categoria.get("_id") + ", Nome: " + categoria.getString("nome"));
            }
        }

        return categorias;
    }

    public Document encontrarCategoriaPorId(String id) {
        if (id == null || id.trim().isEmpty()) {
            System.out.println("Erro: O ID não pode estar vazio.");
            return null;
        }

        Document categoria = collection.find(new Document("_id", id)).first();

        if (categoria == null) {
            System.out.println("Erro: Nenhuma categoria encontrada com o ID fornecido.");
        }

        return categoria;
    }

    public void atualizarCategoria(CategoriaBean categoria) {
        if (categoria.getId() == null) {
            System.out.println("Erro: O ID da categoria não pode estar vazio.");
            return;
        }

        if (categoria.getNome() == null || categoria.getNome().toString().trim().isEmpty()) {
            System.out.println("Erro: O nome da categoria não pode estar vazio.");
            return;
        }

        if (categoria.getDescricao() == null || categoria.getDescricao().trim().isEmpty()) {
            System.out.println("Erro: A descrição da categoria não pode estar vazia.");
            return;
        }

        Document updatedDoc = new Document("nome", categoria.getNome())
                .append("descricao", categoria.getDescricao());

        long modifiedCount = collection.replaceOne(new Document("_id", categoria.getId()), updatedDoc).getModifiedCount();

        if (modifiedCount > 0) {
            System.out.println("Categoria atualizada: " + categoria);
        } else {
            System.out.println("Erro: Não foi possível atualizar a categoria. Verifique o ID fornecido.");
        }
    }

    public void excluirCategoria(String id) {
        if (id == null || id.trim().isEmpty()) {
            System.out.println("Erro: O ID não pode estar vazio.");
            return;
        }

        long deletedCount = collection.deleteOne(new Document("_id", id)).getDeletedCount();

        if (deletedCount > 0) {
            System.out.println("Categoria excluída com o ID: " + id);
        } else {
            System.out.println("Erro: Nenhuma categoria encontrada com o ID fornecido.");
        }
    }
}
