package e3learning.domainTest;


import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import e3learning.dao.AccountDao;
import e3learning.dao.CourseDao;
import e3learning.dao.TrainingDao;
import e3learning.domain.AccountVO;
import e3learning.domain.CourseVO;
import e3learning.domain.TrainingVO;



@ContextConfiguration(locations={"file:WebContent/WEB-INF/spring/spring-servlet.xml","file:WebContent/WEB-INF/spring/spring-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DaoUnitTest  {

	@Autowired
	private AccountDao accountDao;
	@Autowired
	private CourseDao courseDao;
	@Autowired
	private TrainingDao trainingDao;

	/*
	 * [v] Save Test 
	 * [v] Find Test 
	 * [v] getList Test 
	 * [v] Update Test 
	 * [v] remove Test
	 */
	@Test
	public void setAccount() throws Exception{
		
		String email =System.currentTimeMillis()+"@test2.net";
		AccountVO accountVO = new AccountVO("David Miller","Clack",email,"5 Waterways Street","Wentworth Point","NSW","2127","Australia");
		accountDao.saveOrUpdate(accountVO);
		AccountVO account2VO = new AccountVO("David Miller","Clack",email,"5 Waterways Street","Wentworth Point","NSW","2127","Australia");
		accountDao.saveOrUpdate(account2VO);
		AccountVO findAccountVO = accountDao.findOne(accountVO);
		Assert.assertTrue(findAccountVO.getIdAccount()>0);
		
		AccountVO findAccountByEmailVO = accountDao.findByEmail("nonnnn@test.net");
		Assert.assertNull(findAccountByEmailVO);
		
		findAccountByEmailVO = accountDao.findByEmail(email);
		Assert.assertNotNull(findAccountByEmailVO);
		
		AccountVO detachedAccountVO = new AccountVO("David Miller","Clack",email,"5 Waterways Street","Wentworth Point","NSW","2127","Australia");
		detachedAccountVO.setIdAccount(1);
		detachedAccountVO.setAddressPostCode("2321");
		
		AccountVO afterattachedAccountVO=  accountDao.merge(detachedAccountVO);
		Assert.assertEquals(detachedAccountVO.getAddressPostCode(), afterattachedAccountVO.getAddressPostCode());

		//List<AccountVO> findAllAccount =  accountDao.findAllAccount();
		//accountDao.remove(findAllAccount.get(findAllAccount.size()-1));
	}
	
	@Test
	public void setCourse() throws Exception {
		String courseTitle = System.currentTimeMillis()+"";
		CourseVO courseVO = new CourseVO(courseTitle+"");
		courseDao.saveOrUpdate(courseVO);
		Assert.assertTrue("Not Inserted", courseVO.getIdCourse()>0);
		
		Long count = courseDao.findByCourseTitle(courseTitle);
		Assert.assertTrue(count > 0);
		
		List<CourseVO> findAllCourse= courseDao.findAllCourse();
		Assert.assertTrue(findAllCourse.size()>0);
	}
	
	@Test
	public void setTraining() throws Exception {
		String email = System.currentTimeMillis()+"@test2.net";
		String courseTitle = System.currentTimeMillis()+"";
		
		AccountVO accountVO = new AccountVO("David Miller","Clack",email,"5 Waterways Street","Wentworth Point","NSW","2127","Australia");
		accountDao.saveOrUpdate(accountVO);

		CourseVO courseVO = new CourseVO(courseTitle);
		courseDao.saveOrUpdate(courseVO);

		TrainingVO trainingVO = new TrainingVO(accountVO,courseVO,"I","10032015");
		trainingDao.saveOrUpdate(trainingVO);

		TrainingVO training2VO = new TrainingVO(accountVO,courseVO,"I","10302015");
		training2VO.setIdTraining(trainingVO.getIdTraining());
		training2VO.setGrade("C");
		training2VO.setEndDate("31042015");
		TrainingVO trainingVOAfterMerge = trainingDao.merge(training2VO);
		Assert.assertEquals(trainingVOAfterMerge.getEndDate(), training2VO.getEndDate());
		
		TrainingVO  oneTrainingVO= trainingDao.findOne(training2VO);
		Assert.assertEquals("C", oneTrainingVO.getGrade());
		Assert.assertEquals("31042015", oneTrainingVO.getEndDate());
		
		int accountId = accountVO.getIdAccount();
		int courseId = courseVO.getIdCourse();
		
		boolean isTaken = trainingDao.isAlreadyTakenTraining(accountId, courseId);
		Assert.assertTrue("Already taken course",isTaken);
		
		if(isTaken==true) {
			TrainingVO oneAccountTrainingDetail =  trainingDao.findTrainingDetail(accountId,courseId);
			Assert.assertNotNull(oneAccountTrainingDetail.getCourse().getCourseTitle());
		}		
		int idAccount=accountVO.getIdAccount();
		List<TrainingVO> trainingList  = trainingDao.getTrainingsbyAccount(idAccount);
		
		TrainingVO tVo = trainingList.get(0);
		System.out.println(tVo.getCourse().getCourseTitle());
		
		
		
		
		
		
	}

}
