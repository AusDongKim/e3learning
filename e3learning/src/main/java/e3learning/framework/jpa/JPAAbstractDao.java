package e3learning.framework.jpa;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class JPAAbstractDao {

	@PersistenceContext
	protected EntityManager entityManager;
	
	@Autowired
	protected EntityManagerFactory entityManagerFactory;
	
	protected EntityManager getEntityManager(){
		EntityManager entityManager=  entityManagerFactory.createEntityManager();
		return entityManager;
	}
	
	public enum CloseMode {COMMIT,ROLLBACK};
	
	protected void close(CloseMode model,EntityManager entityManager){
		if(model==CloseMode.COMMIT) {
			entityManager.getTransaction().commit();
			entityManager.close();
		} else {
			try {
				entityManager.getTransaction().rollback();
				entityManager.close();
			} catch (Exception e) {
			}
		}
	}
	
}
