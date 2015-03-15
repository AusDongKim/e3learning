package e3learning.service;

import java.util.List;

import javax.transaction.Transactional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import e3learning.dao.AccountDao;
import e3learning.domain.AccountVO;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountDao accountDao;

	@Override
	@Transactional
	public boolean addAcount(AccountVO accountVO) throws Exception {
		AccountVO dupCheckedAccountVO = accountDao.findByEmail(accountVO.getEmail());
		if(dupCheckedAccountVO!=null) {
			return false;
		} else {
			accountDao.saveOrUpdate(accountVO);
		}
		return true;
	}
	
	@Override
	public AccountVO getAccountDetail(AccountVO accountVO) throws Exception {
		return accountDao.findOne(accountVO);
	}

	@Override
	public AccountVO updateUserDetail(AccountVO detachedAccountVo) {
		return accountDao.merge(detachedAccountVo);
	}

	@Override
	public List<AccountVO> getAccountList() throws Exception {
		return accountDao.findAllAccount();
	}
	
}
