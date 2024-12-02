package Floricultura;

import org.bson.types.ObjectId;

public class ItensDaVendaBean {
    private ObjectId id;           // Chave primária
    private ObjectId idVenda;      // ID da venda
    private ObjectId idProduto;    // ID do produto
    private int quantidade;        // Quantidade do item

    // Construtor sem parâmetros
    public ItensDaVendaBean() {}

    // Construtor com parâmetros
    public ItensDaVendaBean(ObjectId idVenda, ObjectId idProduto, int quantidade) {
        this.idVenda = idVenda;
        this.idProduto = idProduto;
        this.quantidade = quantidade;
    }

    // Getters e Setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(ObjectId idVenda) {
        this.idVenda = idVenda;
    }

    public ObjectId getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(ObjectId idProduto) {
        this.idProduto = idProduto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "ItensDaVendaBean [id=" + id + ", idVenda=" + idVenda + ", idProduto=" + idProduto + ", quantidade=" + quantidade + "]";
    }
}
