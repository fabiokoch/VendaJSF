package controle;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;


import beans.Cursos;
import util.GerarEntityManager;
import util.TrataException;

@ManagedBean(name="cursos")
@SessionScoped
public class CursoCrud {
	private List<Cursos> lista;
	private Cursos objeto;
	
	public void inicializarLista(){
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		lista = em.createQuery("from Cursos").getResultList();
		em.close();
	}
	
	public String incluir(){
		objeto = new Cursos();
		return "CursosForm?faces-redirect=true";
	}
	
	public String alterar(Integer id_cursos) { 
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		objeto = em.find(Cursos.class, id_cursos);
		em.close();
		return "CursosForm?faces-redirect=true";
	}
	
	public String excluir(Integer id_cursos) { 
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		try {
			objeto = em.find(Cursos.class, id_cursos);
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
		return "CursosList?faces-redirect=true";
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
        return "CursosList?faces-redirect=true";
	}

	public String cancelar() {
		return "CursosList?faces-redirect=true";
	}

	public List<Cursos> getLista() {
		return lista;
	}

	public void setLista(List<Cursos> lista) {
		this.lista = lista;
	}

	public Cursos getObjeto() {
		return objeto;
	}

	public void setObjeto(Cursos objeto) {
		this.objeto = objeto;
	}
	
	

}
