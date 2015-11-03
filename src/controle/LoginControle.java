package controle;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import beans.Usuario;
import util.GerarEntityManager;

@ManagedBean(name="controle")
@SessionScoped
public class LoginControle {
	private String login;
	private String senha;
	private Usuario usuarioLogado = null;

	public LoginControle() {
	}

	
	public String entrar() {
		EntityManager em = GerarEntityManager.getInstance().getEntityManager();
		Query qry = em.createQuery("from Usuario where login = :login and senha = :senha");
		qry.setParameter("login", login);
		qry.setParameter("senha", senha);
		List<Usuario> list = qry.getResultList();
		em.close();
		if (list.size() <= 0) {
			usuarioLogado = null;
			FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login ou senha inválida!", "");
			FacesContext.getCurrentInstance().addMessage(null, mensagem);
			return "";
		} else {
			usuarioLogado = list.get(0);
			return "/faces/Sistema/Home/Home.xhtml";
		}
	}

	public String sair() {
		usuarioLogado = null;
		FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuário Desconectado!", "");
		FacesContext.getCurrentInstance().addMessage(null, mensagem);
		return "/faces/Sistema/Login/LoginForm.xhtml";
	}

	public String getNome() {
		return login;
	}

	public void setNome(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
	

}
