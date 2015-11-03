package controle;

import java.util.List;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import beans.Cidade;
import beans.Pessoa;
import beans.PessoaFisica;
import util.GerarEntityManager;
import util.TrataException;

@ManagedBean(name="fisica")
@SessionScoped
public class PessoaFisicaCrud extends Pessoa{

	private List<PessoaFisica> lista;
	private PessoaFisica objeto;
	private List<Cidade> listaCidades;

	public PessoaFisicaCrud() {
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

	public void inicializarLista() {
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		lista = em.createQuery("from PessoaFisica").getResultList();
		em.close();
	}

	public String incluir() {
		objeto = new PessoaFisica();
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		listaCidades = em.createQuery("from Cidade order by nome").getResultList();
		em.close();
		return "PessoaFisicaForm?faces-redirect=true";
	}

	public String alterar(Integer id) {
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		objeto = em.find(PessoaFisica.class, id);
		listaCidades = em.createQuery("from Cidade order by nome").getResultList();
		em.close();
		return "PessoaFisicaForm?faces-redirect=true";
	}

	public String excluir(Integer id) {
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		try {
			objeto = em.find(PessoaFisica.class, id);
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
		return "PessoaFisicaList?faces-redirect=true";
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
		return "PessoaFisicaList?faces-redirect=true";
	}

	public String cancelar() {
		return "PessoaFisicaList?faces-redirect=true";
	}

	public List<PessoaFisica> getLista() {
		return lista;
	}

	public void setLista(List<PessoaFisica> PessoaFisicas) {
		this.lista = PessoaFisicas;
	}

	public PessoaFisica getObjeto() {
		return objeto;
	}

	public void setObjeto(PessoaFisica objeto) {
		this.objeto = objeto;
	}

	public List<Cidade> getListaCidades() {
		return listaCidades;
	}

	public void setListaCidades(List<Cidade> listaCidades) {
		this.listaCidades = listaCidades;
	}

}
