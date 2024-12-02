package Floricultura;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProdutosModel {

    private MongoCollection<Document> collection;
    private MongoCollection<Document> associativaCollection;

    public ProdutosModel() {
        collection = Conecta.getDatabase().getCollection("produtos");
        associativaCollection = Conecta.getDatabase().getCollection("fornecedoresProdutos");
    }

    public void salvarProduto(ProdutosBean produto) throws IllegalArgumentException {
        // Verifica se o ID já existe
        if (isProdutoExistenteById(produto.getIdProduto())) {
            throw new IllegalArgumentException("Erro: Já existe um produto com o ID informado.");
        }

        // Verifica se a descrição já existe
        if (isProdutoExistenteByDescricao(produto.getDescricao())) {
            throw new IllegalArgumentException("Erro: Já existe um produto com a descrição informada.");
        }

        // Valida preço unitário
        validarPrecoUnitario(produto.getPrecoUnitario());

        // Valida quantidade em estoque
        validarQtdEstoque(produto.getQtdEstoque());

        // Valida o ID da Categoria
        validarIdCategoria(produto.getIdCategoria());

        // Cria e insere o produto no banco
        Document doc = new Document("_id", produto.getIdProduto())
                .append("descricao", produto.getDescricao())
                .append("preco_unitario", produto.getPrecoUnitario())
                .append("qtd_estoque", produto.getQtdEstoque())
                .append("id_categoria", produto.getIdCategoria());
        collection.insertOne(doc);
        System.out.println("Produto salvo com sucesso!");
    }

    public List<Document> listarProdutos() {
        return collection.find().into(new ArrayList<>());
    }

    public Document encontrarProdutoPorId(int id) {
        return collection.find(new Document("_id", id)).first();
    }

    public void atualizarProduto(ProdutosBean produto) throws IllegalArgumentException {
        // Verifica se existe um produto com o ID informado
        Document produtoExistente = collection.find(new Document("_id", produto.getIdProduto())).first();
        if (produtoExistente == null) {
            throw new IllegalArgumentException("Erro: Produto com o ID informado não encontrado.");
        }

        // Verifica a unicidade da descrição
        if (isProdutoExistenteByDescricao(produto.getDescricao()) &&
                !produtoExistente.getString("descricao").equals(produto.getDescricao())) {
            throw new IllegalArgumentException("Erro: Já existe um produto com a descrição informada.");
        }

        // Valida preço unitário
        validarPrecoUnitario(produto.getPrecoUnitario());

        // Valida quantidade em estoque
        validarQtdEstoque(produto.getQtdEstoque());

        // Valida o ID da Categoria
        validarIdCategoria(produto.getIdCategoria());

        // Atualiza o produto no banco
        Document updatedDoc = new Document("descricao", produto.getDescricao())
                .append("preco_unitario", produto.getPrecoUnitario())
                .append("qtd_estoque", produto.getQtdEstoque())
                .append("id_categoria", produto.getIdCategoria());
        collection.replaceOne(new Document("_id", produto.getIdProduto()), updatedDoc);
        System.out.println("Produto atualizado com sucesso!");
    }

    public void excluirProduto(int id) {
        // Exclui o produto da coleção "produtos"
        collection.deleteOne(new Document("_id", id));
        System.out.println("Produto excluído com sucesso!");

        // Exclui as referências na coleção associativa "fornecedoresProdutos"
        associativaCollection.deleteMany(new Document("id_produto", id));
        System.out.println("Referências ao produto na tabela associativa 'fornecedoresProdutos' removidas.");
    }

    // Métodos privados para validações
    private void validarPrecoUnitario(double precoUnitario) {
        if (precoUnitario < 0) {
            throw new IllegalArgumentException("Erro: O preço unitário não pode ser menor que 0.");
        }
    }

    private void validarQtdEstoque(int qtdEstoque) {
        if (qtdEstoque < 0) {
            throw new IllegalArgumentException("Erro: A quantidade em estoque não pode ser menor que 0.");
        }
    }

    private void validarIdCategoria(int idCategoria) {
        if (idCategoria < 1 || idCategoria > 6) {
            throw new IllegalArgumentException("Erro: O ID da Categoria deve ser um número entre 1 e 6.");
        }
    }

    private boolean isProdutoExistenteById(int id) {
        return Optional.ofNullable(collection.find(new Document("_id", id)).first()).isPresent();
    }

    private boolean isProdutoExistenteByDescricao(String descricao) {
        return Optional.ofNullable(collection.find(new Document("descricao", descricao)).first()).isPresent();
    }
}
