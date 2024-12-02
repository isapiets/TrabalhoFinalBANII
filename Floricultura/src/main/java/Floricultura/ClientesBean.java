package Floricultura;

import org.bson.types.ObjectId;

public class ClientesBean {

    private ObjectId id;  
    private String nome;
    private String sobrenome;
    private String telefone;
    private String email;
    private String cpf;

    
    public ClientesBean() {}

    
    public ClientesBean(String nome, String sobrenome, String telefone, String email, String cpf) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.telefone = telefone;
        this.email = email;
        this.cpf = cpf;
    }

    
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "ClientesBean [id=" + id + ", nome=" + nome + ", sobrenome=" + sobrenome + 
                ", telefone=" + telefone + ", email=" + email + ", cpf=" + cpf + "]";
    }
}
