package e3learning.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import e3learning.domain.TrainingVO;
import e3learning.framework.jpa.JPAAbstractDao;

@Repository("TrainingDao")
public class TrainingDaoImpl extends JPAAbstractDao implements TrainingDao {

	@Override
	public void saveOrUpdate(TrainingVO trainingVO) {
		EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		try {
			entityManager.persist(trainingVO);
			close(CloseMode.COMMIT,entityManager);
		} catch (Exception e) {
			close(CloseMode.ROLLBACK,entityManager);
		}
	}

	@Override
	public TrainingVO merge(TrainingVO trainingVO) {
		EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		try {
			trainingVO = entityManager.merge(trainingVO);
			close(CloseMode.COMMIT,entityManager);
		} catch (Exception e) {
			close(CloseMode.ROLLBACK,entityManager);
		}
		return trainingVO;
		
	}

	@Override
	public TrainingVO findOne(TrainingVO training2vo) {
		return entityManager.find(TrainingVO.class, training2vo.getIdTraining());
	}

	@Override
	public boolean isAlreadyTakenTraining(int accountId, int courseId) {
		Long count = (Long)entityManager.createQuery("select count(o) from training o join o.account p join o.course q where p.idAccount=:idAccount and q.idCourse=:idCourse")
		.setParameter("idAccount", accountId)
		.setParameter("idCourse", courseId)
		.getSingleResult();
		
		return count > 0;
	}

	@Override
	public TrainingVO findTrainingDetail(int accountId, int courseId) {
		// TODO Auto-generated method stub
		Object obj =  entityManager.createQuery("select o from training o join o.account p join o.course q where p.idAccount=:idAccount and q.idCourse=:idCourse")
				.setParameter("idAccount", accountId)
				.setParameter("idCourse", courseId)
				.getSingleResult();
		return obj==null?null:(TrainingVO) obj;
	}

	@Override
	public List<TrainingVO> getTrainingsbyAccount(int idAccount) {
		// TODO Auto-generated method stub
		return entityManager.createQuery("select o from training o join o.account p join o.course q where p.idAccount=:idAccount")
				.setParameter("idAccount",idAccount)
				.getResultList();
	}

}
