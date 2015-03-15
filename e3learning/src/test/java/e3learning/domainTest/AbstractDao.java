package e3learning.domainTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations={"file:WebContent/WEB-INF/spring/spring-servlet.xml","file:WebContent/WEB-INF/spring/spring-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AbstractDao {

	@Autowired
	private EntityManagerFactory entityManagerFactory;
	protected EntityManager entityManager;

	public AbstractDao() {
		super();
	}

	@Before
	public void init() {
		entityManager = entityManagerFactory.createEntityManager();
		Assert.assertNotNull(entityManager);
		entityManager.getTransaction().begin();
	}

	@After
	public void close() {
		entityManager.getTransaction().commit();
		entityManager.close();
	}

}