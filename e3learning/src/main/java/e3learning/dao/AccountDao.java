package e3learning.dao;

import java.util.List;

import e3learning.domain.AccountVO;

public interface AccountDao {

	void saveOrUpdate(AccountVO accountVo) throws Exception;

	AccountVO findOne(AccountVO accountVO)  throws Exception;

	AccountVO findByEmail(String string)  throws Exception;

	List<AccountVO> findAllAccount()  throws Exception;

	void remove(AccountVO accountVO);

	AccountVO merge(AccountVO detachedAccountVO);


}
