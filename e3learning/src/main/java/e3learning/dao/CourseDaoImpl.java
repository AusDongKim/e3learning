package e3learning.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import e3learning.domain.CourseVO;
import e3learning.framework.jpa.JPAAbstractDao;

@Repository("CourseDao")
public class CourseDaoImpl extends JPAAbstractDao implements CourseDao {

	@Override
	public void saveOrUpdate(CourseVO courseVO) {
		EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		try {
			entityManager.persist(courseVO);
			close(CloseMode.COMMIT,entityManager);
		} catch (Exception e) {
			close(CloseMode.ROLLBACK,entityManager);
		}
		
	}

	@Override
	public List<CourseVO> findAllCourse() {
		return entityManager.createQuery("from course").getResultList();
	}

	@Override
	public Long findByCourseTitle(String courseTitle) {
		return (Long)entityManager.createQuery("select count(o) from course o where o.courseTitle=:courseTitle",Long.class)
		.setParameter("courseTitle", courseTitle).getSingleResult();
	}

}
