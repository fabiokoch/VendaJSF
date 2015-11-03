package controle;


import java.util.List;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import beans.Cidade;
import util.GerarEntityManager;
import util.TrataException;

@ManagedBean(name="cidade")
@SessionScoped
public class CidadeCrud {

	private List<Cidade> lista;
	private Cidade objeto;
	private String[] listaUF = {"RS", "SC", "PR", "SP"};  
	
	public void inicializarLista(){
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		lista = em.createQuery("from Cidade").getResultList();
		em.close();
	}
	
	public String incluir(){
		objeto = new Cidade();
		return "CidadeForm?faces-redirect=true";
	}
	
	public String alterar(Integer id) { 
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		objeto = em.find(Cidade.class, id);
		em.close();
		return "CidadeForm?faces-redirect=true";
	}
	
	public String excluir(Integer id) { 
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		try {
			objeto = em.find(Cidade.class, id);
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
		return "CidadeList?faces-redirect=true";
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
        return "CidadeList?faces-redirect=true";
	}

	public String cancelar() {
		return "CidadeList?faces-redirect=true";
	}


	public List<Cidade> getLista() {
		return lista;
	}

	public void setLista(List<Cidade> cidades) {
		this.lista = cidades;
	}

	public Cidade getObjeto() {
		return objeto;
	}

	public void setObjeto(Cidade objeto) {
		this.objeto = objeto;
	}

	public String[] getListaUF() {
		return listaUF;
	}

	public void setListaUF(String[] listaUF) {
		this.listaUF = listaUF;
	}
		
	
}
