package controle;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import beans.FormaPagamento;
import util.GerarEntityManager;
import util.TrataException;

@ManagedBean(name="formaPagamento")
@SessionScoped
public class FormaPagamentoCrud {
	private List<FormaPagamento> lista;
	private FormaPagamento objeto;
	
	public void inicializarLista(){
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		lista = em.createQuery("from FormaPagamento").getResultList();
		em.close();
	}
	
	public String incluir(){
		objeto = new FormaPagamento();
		return "FormaPagamentoForm?faces-redirect=true";
	}
	
	public String alterar(Integer id_forma_pagto) { 
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		objeto = em.find(FormaPagamento.class, id_forma_pagto);
		em.close();
		return "FormaPagamentoForm?faces-redirect=true";
	}
	
	public String excluir(Integer id_forma_pagto) { 
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		try {
			objeto = em.find(FormaPagamento.class, id_forma_pagto);
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
		return "FormaPagamentoList?faces-redirect=true";
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
        return "FormaPagamentoList?faces-redirect=true";
	}

	public String cancelar() {
		return "FormaPagamentoList?faces-redirect=true";
	}

	public List<FormaPagamento> getLista() {
		return lista;
	}

	public void setLista(List<FormaPagamento> lista) {
		this.lista = lista;
	}

	public FormaPagamento getObjeto() {
		return objeto;
	}

	public void setObjeto(FormaPagamento objeto) {
		this.objeto = objeto;
	}
	

}
