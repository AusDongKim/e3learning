package e3learning.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;


import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


import e3learning.domain.AccountVO;
import e3learning.framework.jpa.JPAAbstractDao;

@Repository("AccountDao")
public class AccountDaoImpl extends JPAAbstractDao implements AccountDao {

	@Override
	public void saveOrUpdate(AccountVO accountVo) throws Exception {
		EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		try {
			entityManager.persist(accountVo);
			close(CloseMode.COMMIT,entityManager);
		} catch (Exception e) {
			close(CloseMode.ROLLBACK,entityManager);
			throw new Exception(e);
		} 
	}
	
	@Override
	public AccountVO findOne(AccountVO accountVO) {
		return entityManager.find(AccountVO.class, accountVO.getIdAccount());
	}

	@Override
	public AccountVO findByEmail(String email) {
		List<AccountVO> accountList =  entityManager.createQuery("from account where email = :email")
		.setParameter("email", email).getResultList();
		return accountList.size()>0?accountList.get(0):null;
	}

	@Override
	public List<AccountVO> findAllAccount() {
		return entityManager.createQuery("from account").getResultList();
	}

	@Override
	public void remove(AccountVO accountVO) {
		EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		try {
			entityManager.remove(accountVO);	
			close(CloseMode.COMMIT,entityManager);
		} catch (Exception e) {
			close(CloseMode.ROLLBACK,entityManager);
		} 
		
	}

	@Override
	public AccountVO merge(AccountVO detachedAccountVO) {
		EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		try {
			entityManager.merge(detachedAccountVO);
			close(CloseMode.COMMIT,entityManager);
		} catch (Exception e) {
			close(CloseMode.ROLLBACK,entityManager);
		}
		return detachedAccountVO;
	}
	

}
