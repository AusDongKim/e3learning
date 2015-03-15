package e3learning.serviceTest;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import e3learning.dao.TrainingDao;
import e3learning.domain.AccountVO;
import e3learning.domain.CourseVO;
import e3learning.domain.TrainingVO;
import e3learning.service.AccountService;
import e3learning.service.CourseService;
import e3learning.service.TrainingService;

@ContextConfiguration(locations={"file:WebContent/WEB-INF/spring/spring-config.xml","file:WebContent/WEB-INF/spring/spring-servlet.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountServiceTest {
	
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private CourseService courseService;
	@Autowired
	private TrainingService trainingService;
	
	
	@Test
	public void addAccount() throws Exception{
		String email = System.currentTimeMillis()+"@test2.net";
		
		
		AccountVO accountVo = new AccountVO("David Miller","Clack",email,"5 Waterways Street","Wentworth Point","NSW","2127","Australia");
		boolean addResult = accountService.addAcount(accountVo);
		Assert.assertTrue(addResult);
		
		addResult = accountService.addAcount(accountVo);
		Assert.assertFalse(addResult);
		
		email = System.currentTimeMillis()+"@test2.net";
		accountVo = new AccountVO("David Miller","Clack",email,"5 Waterways Street","Wentworth Point","NSW","2127","Australia");
		addResult = accountService.addAcount(accountVo);
		Assert.assertTrue(addResult);
		
		AccountVO davidDetail = accountService.getAccountDetail(accountVo);
		Assert.assertEquals("Not found detail", davidDetail.getFirstName(), "David Miller");
		
		AccountVO detachedAccountVo = new AccountVO("David Miller","Clack",email,"5 Waterways Street","Wentworth Point","NSW","2127","Australia");
		detachedAccountVo.setIdAccount(1);
		detachedAccountVo.setAddressPostCode("2128");
		
		AccountVO afterAttachedAccountVO =  accountService.updateUserDetail(detachedAccountVo);
		Assert.assertEquals(detachedAccountVo.getAddressPostCode(), afterAttachedAccountVO.getAddressPostCode());
		
		List<AccountVO> accountList = accountService.getAccountList();
		Assert.assertNotNull("Null-failed to load all Accounts",accountList);
	}
	
	@Test
	public void addCourse() throws Exception{
		String courseTitle = System.currentTimeMillis()+"";
		CourseVO courseVO = new CourseVO(courseTitle);
		boolean addResult = courseService.addCourse(courseVO);
		Assert.assertTrue("Insert Failed",addResult);

		List<CourseVO> courseList = courseService.getCourseList();
		Assert.assertTrue(courseList.size()>0);
	}
	
	@Test
	public void addTraining() throws Exception{
		
		String email = System.currentTimeMillis()+"@test2.net";
		String courseTitle = System.currentTimeMillis()+"";
		
		AccountVO accountVO = new AccountVO("David Miller","Clack",email,"5 Waterways Street","Wentworth Point","NSW","2127","Australia");
		accountService.addAcount(accountVO);

		CourseVO courseVO = new CourseVO(courseTitle);
		courseService.addCourse(courseVO);
		boolean dupCheck = trainingService.isCourseAlreadyTaken(accountVO.getIdAccount(),courseVO.getIdCourse());
		Assert.assertFalse("Already Registered",dupCheck);
		
		TrainingVO training2VO = new TrainingVO(accountVO,courseVO,"I","10302015");
		boolean isEnrolled = trainingService.enrolTraining(training2VO);
		Assert.assertTrue("Save Failed",isEnrolled);
		training2VO.setGrade("C");
		training2VO.setEndDate("14032015");
		training2VO.setTrainingState("F");
		
		trainingService.updateTrainingInfo(training2VO);
		
		int idAccount=1;
		List<TrainingVO> trainings = trainingService.getTrainingsByIdAccount(idAccount);
		Assert.assertTrue(trainings.size()>0);
		
		
		
	}
	
	
}
