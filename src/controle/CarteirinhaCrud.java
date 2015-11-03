package controle;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import beans.Carteirinha;
import beans.Cursos;
import beans.Escola;
import beans.PessoaFisica;
import util.GerarEntityManager;
import util.TrataException;

@ManagedBean(name = "carteirinha")
@SessionScoped
public class CarteirinhaCrud {

	private List<Carteirinha> lista;
	private Carteirinha objeto;
	
	public List<Cursos> completeCurso(String query) { 
		   EntityManager em = GerarEntityManager.getInstance().getEntityManager();
	  	   List<Cursos> results = em.createQuery(
	                           "from Cursos where upper(descricao) like "+
	                           "'"+query.trim().toUpperCase()+"%' "+
	                           "order by descricao").getResultList();   
	  	  em.close();
	      return results;  
	   }
	
	public List<Escola> completeEscola(String query) { 
		   EntityManager em = GerarEntityManager.getInstance().getEntityManager();
	  	   List<Escola> results = em.createQuery(
	                           "from Escola where upper(nome) like "+
	                           "'"+query.trim().toUpperCase()+"%' "+
	                           "order by nome").getResultList();   
	  	  em.close();
	      return results;  
	   }
	
	public List<PessoaFisica> completeAluno(String query) { 
		   EntityManager em = GerarEntityManager.getInstance().getEntityManager();
	  	   List<PessoaFisica> results = em.createQuery(
	                           "from PessoaFisica where upper(nome) like "+
	                           "'"+query.trim().toUpperCase()+"%' "+
	                           "order by nome").getResultList();   
	  	  em.close();
	      return results;  
	   }

	public void inicializarLista() {
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		lista = em.createQuery("from Carteirinha").getResultList();
		em.close();
	}

	public String incluir() {
		objeto = new Carteirinha();
		return "CarteirinhaForm?faces-redirect=true";
	}

	public String alterar(Integer id_carteirinha) {
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		objeto = em.find(Carteirinha.class, id_carteirinha);
		em.close();
		return "CarteirinhaForm?faces-redirect=true";
	}

	public String excluir(Integer id_carteirinha) {
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		try {
			objeto = em.find(Carteirinha.class, id_carteirinha);
			em.getTransaction().begin();
			em.remove(objeto);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			TrataException.pegaMensagem(e, FacesContext.getCurrentInstance());
			return "";
		} finally {
			em.close();
		}
		return "CarteirinhaList?faces-redirect=true";
	}

	public String gravar() {
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(objeto);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			TrataException.pegaMensagem(e, FacesContext.getCurrentInstance());
			return "";
		} finally {
			em.close();
		}
		return "CarteirinhaList?faces-redirect=true";
	}

	public String cancelar() {
		return "CarteirinhaList?faces-redirect=true";
	}

	public List<Carteirinha> getLista() {
		return lista;
	}

	public void setLista(List<Carteirinha> lista) {
		this.lista = lista;
	}

	public Carteirinha getObjeto() {
		return objeto;
	}

	public void setObjeto(Carteirinha objeto) {
		this.objeto = objeto;
	}

}
