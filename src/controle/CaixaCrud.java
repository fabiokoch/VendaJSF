package controle;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import beans.Caixa;
import beans.Usuario;
import util.GerarEntityManager;
import util.TrataException;

@ManagedBean(name="caixas")
@SessionScoped
public class CaixaCrud {
	private List<Caixa> lista;
	private Caixa objeto;
	private Usuario usuario;
	
	public List<Usuario> completeUsuario(String query) { 
		   EntityManager em = GerarEntityManager.getInstance().getEntityManager();
	  	   List<Usuario> results = em.createQuery(
	                           "from Usuario where upper(nome) like "+
	                           "'"+query.trim().toUpperCase()+"%' "+
	                           "order by nome").getResultList();   
	  	  em.close();
	      return results;  
	   }
	
	public void inicializarLista(){
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		lista = em.createQuery("from Caixa").getResultList();
		em.close();
	}
	
	public String incluir(){
		usuario = new Usuario();
		objeto = new Caixa();
		return "CaixaForm?faces-redirect=true";
	}
	
	public String fechar(){
		usuario = new Usuario();
		objeto = new Caixa();
		return "FechaCaixaForm?faces-redirect=true";
	}
	
	public String alterar(Integer id_caixa) { 
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		objeto = em.find(Caixa.class, id_caixa);
		em.close();
		return "FechaCaixaForm?faces-redirect=true";
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
        return "CaixaList?faces-redirect=true";
	}
	
	public String cancelar() {
		return "CaixaList?faces-redirect=true";
	}
	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Caixa> getLista() {
		return lista;
	}

	public void setLista(List<Caixa> lista) {
		this.lista = lista;
	}

	public Caixa getObjeto() {
		return objeto;
	}

	public void setObjeto(Caixa objeto) {
		this.objeto = objeto;
	}
	

}
