package controle;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;


import beans.Usuario;
import util.GerarEntityManager;
import util.TrataException;

@ManagedBean(name="usuario")
@SessionScoped
public class UsuarioCrud {
	private List<Usuario> lista;
	private Usuario objeto;
	
	
	public void inicializarLista(){
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		lista = em.createQuery("from Usuario").getResultList();
		em.close();
	}
	
	public String incluir(){
		objeto = new Usuario();
		return "UsuarioForm?faces-redirect=true";
	}
	
	public String alterar(Integer id) { 
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		objeto = em.find(Usuario.class, id);
		em.close();
		return "UsuarioForm?faces-redirect=true";
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
        return "UsuarioList?faces-redirect=true";
	}
	

	public UsuarioCrud() {
		super();
	}

	public UsuarioCrud(Usuario objeto) {
		super();
		this.objeto = objeto;
	}

	public List<Usuario> getLista() {
		return lista;
	}

	public void setLista(List<Usuario> lista) {
		this.lista = lista;
	}

	public Usuario getObjeto() {
		return objeto;
	}

	public void setObjeto(Usuario objeto) {
		this.objeto = objeto;
	}
	
	

}
