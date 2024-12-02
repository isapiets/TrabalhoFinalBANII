package Floricultura;

import org.bson.types.ObjectId;

public class VendasBean {
    private ObjectId idVenda; // ID da venda
    private String data; // Data da venda
    private int total; // Total da venda
    private ObjectId idCliente; // ID do cliente

    // Getters e Setters
    public ObjectId getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(ObjectId idVenda) {
        this.idVenda = idVenda;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ObjectId getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(ObjectId idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        return "VendasBean [idVenda=" + idVenda + ", data=" + data + ", total=" + total + ", idCliente=" + idCliente + "]";
    }
}
