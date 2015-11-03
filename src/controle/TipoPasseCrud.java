package controle;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import beans.Cidade;
import beans.TipoPasse;
import util.GerarEntityManager;
import util.TrataException;;

@ManagedBean(name="tipo")
@SessionScoped
public class TipoPasseCrud {
	private List<TipoPasse> lista;
	private TipoPasse objeto;
	
	
	public void inicializarLista(){
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		lista = em.createQuery("from TipoPasse").getResultList();
		em.close();
	}
	
	public String incluir(){
		objeto = new TipoPasse();
		return "TipoPasseForm?faces-redirect=true";
	}
	
	public String alterar(Integer id_tipo_passe) { 
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		objeto = em.find(TipoPasse.class, id_tipo_passe);
		em.close();
		return "TipoPasseForm?faces-redirect=true";
	}
	
	public String excluir(Integer id_tipo_passe) { 
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		try {
			objeto = em.find(TipoPasse.class, id_tipo_passe);
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
		return "TipoPasseList?faces-redirect=true";
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
        return "TipoPasseList?faces-redirect=true";
	}

	public String cancelar() {
		return "TipoPasseList?faces-redirect=true";
	}

	public List<TipoPasse> getLista() {
		return lista;
	}

	public void setLista(List<TipoPasse> lista) {
		this.lista = lista;
	}

	public TipoPasse getObjeto() {
		return objeto;
	}

	public void setObjeto(TipoPasse objeto) {
		this.objeto = objeto;
	}

	
	

}
