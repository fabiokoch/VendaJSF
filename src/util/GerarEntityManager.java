package util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Session;
import org.hibernate.internal.SessionFactoryImpl;

public class GerarEntityManager {
		
	private EntityManagerFactory factory;
	
	public GerarEntityManager() {
		factory = Persistence.createEntityManagerFactory("VendaJPA");
	}

	public static GerarEntityManager getInstance(){
		 return GerarEntityManagerInstance.INSTANCE;
	}
	
	public EntityManager getEntityManager(){
		return factory.createEntityManager();
	}
	
	private static class GerarEntityManagerInstance{
		public static final GerarEntityManager INSTANCE = new GerarEntityManager(); 
	}
	
	
	 public Connection getEntityManagerJDBCConnection() throws SQLException { 
	      EntityManager em = factory.createEntityManager(); 
	      Session ses = (Session) em.getDelegate(); 
	      SessionFactoryImpl sessionFactory = (SessionFactoryImpl) ses.getSessionFactory(); 
	      Connection conexao = sessionFactory.getConnectionProvider().getConnection();   
	      em.close();  
	       return conexao; 
	    }  
	
}
