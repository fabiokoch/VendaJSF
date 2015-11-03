package controle;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;

import beans.Carteirinha;
import beans.Movimento;
import beans.Pessoa;
import beans.Usuario;
import util.GerarEntityManager;



@ManagedBean(name="venda")
@SessionScoped
public class VendaCrudOld {
	private List<Movimento> lista;
	private Movimento objeto;
	
	public List<Usuario> completeUsuario(String query) { 
		   EntityManager em = GerarEntityManager.getInstance().getEntityManager();
	  	   List<Usuario> results = em.createQuery(
	                           "from Usuario where upper(nome) like "+
	                           "'"+query.trim().toUpperCase()+"%' "+
	                           "order by nome").getResultList();   
	  	  em.close();
	      return results;  
	   }
	
	public List<Carteirinha> completeCarteirinha(Integer query) { 
		   EntityManager em = GerarEntityManager.getInstance().getEntityManager();
	  	   List<Carteirinha> results = em.createQuery(
	                           "from Carteirinha where id_carteirinha like "+
	                           "'"+query+"%' "+
	                           "order by id_carteirinha").getResultList();   
	  	  em.close();
	      return results;  
	   }
	
	public List<Pessoa> completePessoa(String query) { 
		   EntityManager em = GerarEntityManager.getInstance().getEntityManager();
	  	   List<Pessoa> results = em.createQuery(
	                           "from Pessoa where upper(nome) like "+
	                           "'"+query.trim().toUpperCase()+"%' "+
	                           "order by nome").getResultList();   
	  	  em.close();
	      return results;  
	   }

	public List<Movimento> getLista() {
		return lista;
	}

	public void setLista(List<Movimento> lista) {
		this.lista = lista;
	}

	public Movimento getObjeto() {
		return objeto;
	}

	public void setObjeto(Movimento objeto) {
		this.objeto = objeto;
	}
	
	

}
