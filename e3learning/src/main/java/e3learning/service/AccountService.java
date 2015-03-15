package e3learning.service;


import java.util.List;
import e3learning.domain.AccountVO;

public interface AccountService {

	boolean addAcount(AccountVO accountVo) throws Exception;

	AccountVO getAccountDetail(AccountVO accountVo) throws Exception;
	
	AccountVO updateUserDetail(AccountVO detachedAccountVo);

	List<AccountVO> getAccountList() throws Exception;



}
