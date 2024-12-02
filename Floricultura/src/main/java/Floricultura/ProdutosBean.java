package Floricultura;

public class ProdutosBean {
    private int idProduto; // Agora o ID é tratado como um número inteiro
    private String descricao; // Descrição do produto
    private double precoUnitario; // Preço unitário do produto
    private int qtdEstoque; // Quantidade em estoque
    private int idCategoria; // Categoria como um número inteiro

    // Construtor sem parâmetros
    public ProdutosBean() {}

    // Construtor com parâmetros
    public ProdutosBean(int idProduto, String descricao, double precoUnitario, int qtdEstoque, int idCategoria) {
        this.idProduto = idProduto;
        this.descricao = descricao;
        this.precoUnitario = precoUnitario;
        this.qtdEstoque = qtdEstoque;
        this.idCategoria = idCategoria;
    }

    // Getters e Setters
    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    @Override
    public String toString() {
        return "ProdutosBean [idProduto=" + idProduto + ", descricao=" + descricao +
                ", precoUnitario=" + precoUnitario + ", qtdEstoque=" + qtdEstoque +
                ", idCategoria=" + idCategoria + "]";
    }
}
