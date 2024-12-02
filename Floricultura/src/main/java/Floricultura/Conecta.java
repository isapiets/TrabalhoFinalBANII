package Floricultura;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Conecta {

    private static MongoClient mongoClient;
    private static MongoDatabase database;

    // Método estático para conectar ao MongoDB
    public static void conectar() {
        try {
            // Criar uma conexão com o MongoDB
            mongoClient = MongoClients.create("mongodb://localhost:27017");

            // Conectar ao banco de dados "Floricultura"
            database = mongoClient.getDatabase("Floricultura");

            // Mensagem de sucesso
            System.out.println("Conexão bem-sucedida ao banco de dados: " + database.getName());

        } catch (Exception e) {
            // Tratamento de erros
            System.err.println("Erro ao conectar ao MongoDB: " + e.getMessage());
        }
    }

    // Método para obter o banco de dados
    public static MongoDatabase getDatabase() {
        if (database == null) {
            conectar();  // Caso o banco de dados ainda não tenha sido conectado
        }
        return database;
    }

    // Método para fechar a conexão
    public static void fecharConexao() {
        if (mongoClient != null) {
            mongoClient.close();
            System.out.println("Conexão com o MongoDB fechada.");
        }
    }
}
