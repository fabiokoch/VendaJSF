package controle;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.view.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import beans.Cidade;
import beans.PessoaJuridica;
import util.GerarEntityManager;
import util.TrataException;

@ManagedBean
@SessionScoped
public class PessoaJuridicaCrud {

	private List<PessoaJuridica> lista;
	private PessoaJuridica objeto;
	private List<Cidade> listaCidades;
	
	public PessoaJuridicaCrud() {
	}

	public List<Cidade> completeCidade(String query) { 
		   EntityManager em = GerarEntityManager.getInstance().getEntityManager();
	  	   List<Cidade> results = em.createQuery(
	                           "from Cidade where upper(nome) like "+
	                           "'"+query.trim().toUpperCase()+"%' "+
	                           "order by nome").getResultList();   
	  	  em.close();
	      return results;  
	   }

	
	public void inicializarLista(){
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		lista = em.createQuery("from PessoaJuridica").getResultList();
		em.close();
	}
	
	public String incluir(){
		objeto = new PessoaJuridica();
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
     	listaCidades = em.createQuery("from Cidade order by nome").getResultList();
     	em.close();
		return "PessoaJuridicaForm?faces-redirect=true";
	}
	
	public String alterar(Integer id) { 
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		objeto = em.find(PessoaJuridica.class, id);
     	listaCidades = em.createQuery("from Cidade order by nome").getResultList();
		em.close();
		return "PessoaJuridicaForm?faces-redirect=true";
	}
	
	public String excluir(Integer id) { 
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		try {
			objeto = em.find(PessoaJuridica.class, id);
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
		return "PessoaJuridicaList?faces-redirect=true";
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
		} finally{
			em.close(); 	
		}
        return "PessoaJuridicaList?faces-redirect=true";
	}

	public String cancelar() {
		return "PessoaJuridicaList?faces-redirect=true";
	}


	public List<PessoaJuridica> getLista() {
		return lista;
	}

	public void setLista(List<PessoaJuridica> PessoaJuridicas) {
		this.lista = PessoaJuridicas;
	}

	public PessoaJuridica getObjeto() {
		return objeto;
	}

	public void setObjeto(PessoaJuridica objeto) {
		this.objeto = objeto;
	}


	public List<Cidade> getListaCidades() {
		return listaCidades;
	}


	public void setListaCidades(List<Cidade> listaCidades) {
		this.listaCidades = listaCidades;
	}


	
}
