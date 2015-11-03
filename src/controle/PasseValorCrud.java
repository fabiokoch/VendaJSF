package controle;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import beans.Cidade;
import beans.PasseValor;
import beans.TipoPasse;
import util.GerarEntityManager;
import util.TrataException;



@ManagedBean(name="valor")
@SessionScoped
public class PasseValorCrud {
	
	private List<PasseValor> lista;
	private PasseValor objeto;
	private List<TipoPasse> listaTipo;
	
	public List<TipoPasse> completeTipo(String query) { 
		   EntityManager em = GerarEntityManager.getInstance().getEntityManager();
	  	   List<TipoPasse> results = em.createQuery(
	                           "from TipoPasse where upper(descricao) like "+
	                           "'"+query.trim().toUpperCase()+"%' "+
	                           "order by descricao").getResultList();   
	  	  em.close();
	      return results;  
	   }
	
	public void inicializarLista(){
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		lista = em.createQuery("from PasseValor").getResultList();
		em.close();
	}
	
	public String incluir(){
		objeto = new PasseValor();
		return "PasseValorForm?faces-redirect=true";
	}
	
	public String alterar(Integer id_valor) { 
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		objeto = em.find(PasseValor.class, id_valor);
		em.close();
		return "PasseValorForm?faces-redirect=true";
	}
	
	public String excluir(Integer id_valor) { 
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		try {
			objeto = em.find(PasseValor.class, id_valor);
			em.getTransaction().begin();
			em.remove(objeto);
			em.getTransaction().commit();		
		} catch (Exception e) {
			e.printStackTrace();
			TrataException.pegaMensagem(e, FacesContext.getCurrentInstance());
			return "";
		} finally{
			em.close(); 	
		}
		return "PasseValorList?faces-redirect=true";
	}
	

	
	public String gravar(){
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
       	try {
			em.getTransaction().begin();
			em.merge(objeto);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			TrataException.pegaMensagem(e, FacesContext.getCurrentInstance());
			return "";
		} finally{
			em.close(); 	
		}
        return "PasseValorList?faces-redirect=true";
	}

	public String cancelar() {
		return "PasseValorList?faces-redirect=true";
	}

	public List<PasseValor> getLista() {
		return lista;
	}

	public void setLista(List<PasseValor> lista) {
		this.lista = lista;
	}

	public PasseValor getObjeto() {
		return objeto;
	}

	public void setObjeto(PasseValor objeto) {
		this.objeto = objeto;
	}

	public List<TipoPasse> getListaTipo() {
		return listaTipo;
	}

	public void setListaTipo(List<TipoPasse> listaTipo) {
		this.listaTipo = listaTipo;
	}
	
	

}
