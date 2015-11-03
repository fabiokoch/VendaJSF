package controle;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.hibernate.Hibernate;

import beans.Caixa;
import beans.Carteirinha;
import beans.Pessoa;
import beans.TipoPasse;
import beans.Usuario;
import beans.Venda;
import beans.VendaItem;
import util.GerarEntityManager;
import util.TrataException;

@ManagedBean
@SessionScoped
public class VendaCrud {

	private List<Venda> lista;
	private Venda objeto;
	private List<Pessoa> listaPessoa;

	private VendaItem item; 
	private Integer rowIndex = null; 
	public void incluirItem() {
		rowIndex = null;
		item = new VendaItem();
	}

	public void alterarItem(Integer rowIndex) {
		this.rowIndex = rowIndex;
		item = objeto.getItens().get(rowIndex); // pega item da cole��o
	}

	public void excluirItem(Integer rowIndex) {
		objeto.getItens().remove(rowIndex.intValue()); // exclui item da cole��o
		calcularTotais();
	}

	public void gravarItem() {
		if (this.rowIndex == null) {
			item.setVenda(objeto);
			objeto.getItens().add(item); // adiciona item na cole��o
		} else {
			objeto.getItens().set(rowIndex, item); // altera na cole��o
		}
		calcularTotais();
		rowIndex = null;
		item = null;
	}

	public void cancelarItem() {
		rowIndex = null;
		item = null;
	}

	/**
	 * M�todo chamado por ajax para o c�lculo do total do item ao digitar no
	 * formul�rio
	 */
	public void calcularTotalItem() {
		if (item.getCustoUnitario() == null || item.getQuantidade() == null)
			return;
		item.setTotal(item.getCustoUnitario() * item.getQuantidade());
	}
	
	/**
	 * M�todo que calcula o total do recebimento ap�s as opera��es sobre itens
	 */
	public void calcularTotais() {
		Double valorProdutos = 0.0;
		for (VendaItem it : objeto.getItens())
			valorProdutos += it.getTotal();
		objeto.setValorProdutos(valorProdutos);
	}

	public List<Pessoa> completePessoa(String query) {
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		List<Pessoa> results = em.createQuery(
				"from Pessoa where upper(nome) like " + "'" + query.trim().toUpperCase() + "%' " + "order by nome")
				.getResultList();
		em.close();
		return results;
	}
	
	public List<TipoPasse> completeTipoPasse(String query) {
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		List<TipoPasse> results = em.createQuery(
				"from TipoPasse where upper(descricao) like " + "'" + query.trim().toUpperCase() + "%' " + "order by descricao")
				.getResultList();
		em.close();
		return results;
	}

	public List<Usuario> completeUsuario(String query) {
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		List<Usuario> results = em.createQuery(
				"from Usuario where upper(nome) like " + "'" + query.trim().toUpperCase() + "%' " + "order by nome")
				.getResultList();
		em.close();
		return results;
	}

	public List<Carteirinha> completeCarteirinha(String query) {
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		List<Carteirinha> results = em
				.createQuery(
						"from Carteirinha where id_carteirinha like " + "'" + query + "%' " + "order by id_carteirinha")
				.getResultList();
		em.close();
		return results;
	}
	
	public List<Caixa> completeCaixa(String query) {
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		List<Caixa> results = em
				.createQuery(
						"from Caixa where id_caixa like " + "'" + query + "%' " + "order by id_caixa")
				.getResultList();
		em.close();
		return results;
	}

	public void inicializarLista() {
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		lista = em.createQuery("from Venda").getResultList();
		em.close();
	}

	public String incluir() {
		objeto = new Venda();
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		listaPessoa = em.createQuery("from Pessoa order by nome").getResultList();
		em.close();
		return "VendaForm?faces-redirect=true";
	}

	public String alterar(Integer id) {
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		objeto = em.find(Venda.class, id);
		Hibernate.initialize(objeto.getItens());

		listaPessoa = em.createQuery("from Pessoa order by nome").getResultList();
		em.close();
		return "VendaForm?faces-redirect=true";
	}

	public String excluir(Integer id) {
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		try {
			objeto = em.find(Venda.class, id);
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
		return "VendaList?faces-redirect=true";
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
		return "VendaList?faces-redirect=true";
	}

	public String cancelar() {
		return "VendaList?faces-redirect=true";
	}

	public VendaCrud() {
	}

	public List<Venda> getLista() {
		return lista;
	}

	public void setLista(List<Venda> Vendas) {
		this.lista = Vendas;
	}

	public Venda getObjeto() {
		return objeto;
	}

	public void setObjeto(Venda objeto) {
		this.objeto = objeto;
	}

	public List<Pessoa> getListaCidades() {
		return listaPessoa;
	}

	public void setListaCidades(List<Pessoa> listaCidades) {
		this.listaPessoa = listaCidades;
	}

	public VendaItem getItem() {
		return item;
	}

	public void setItem(VendaItem item) {
		this.item = item;
	}
	

}
