package controle;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import beans.Escola;
import util.GerarEntityManager;
import util.TrataException;

@ManagedBean(name="escola")
@SessionScoped
public class EscolaCrud {
	private List<Escola> lista;
	private Escola objeto;
	
	public void inicializarLista(){
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		lista = em.createQuery("from Escola").getResultList();
		em.close();
	}
	
	public String incluir(){
		objeto = new Escola();
		return "EscolaForm?faces-redirect=true";
	}
	
	public String alterar(Integer id_escola) { 
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		objeto = em.find(Escola.class, id_escola);
		em.close();
		return "EscolaForm?faces-redirect=true";
	}
	
	public String excluir(Integer id_escola) { 
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		try {
			objeto = em.find(Escola.class, id_escola);
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
		return "EscolaList?faces-redirect=true";
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
        return "EscolaList?faces-redirect=true";
	}

	public String cancelar() {
		return "EscolaList?faces-redirect=true";
	}

	public List<Escola> getLista() {
		return lista;
	}

	public void setLista(List<Escola> lista) {
		this.lista = lista;
	}

	public Escola getObjeto() {
		return objeto;
	}

	public void setObjeto(Escola objeto) {
		this.objeto = objeto;
	}
	

}
