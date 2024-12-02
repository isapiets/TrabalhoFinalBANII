package Floricultura;
import org.bson.types.ObjectId;
public class CategoriaBean {
   private ObjectId id;  
   private String descricao;  
  
   public CategoriaBean() {}
 
   public CategoriaBean(String descricao) {
       this.descricao = descricao;
   }
 
   public ObjectId getId() {
       return id;
   }
   public void setId(ObjectId id) {
       this.id = id;
   }
   
   public String getDescricao() {
       return descricao;
   }
   public void setDescricao(String descricao) {
       this.descricao = descricao;
   }
   
   @Override
   public String toString() {
       return "CategoriaBean [id=" + id + ", descricao=" + descricao + "]";
   }
	public Object getNome() {
		
		return null;
	}
}
