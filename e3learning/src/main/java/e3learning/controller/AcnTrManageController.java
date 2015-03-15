package e3learning.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import e3learning.domain.AccountVO;
import e3learning.domain.CourseVO;
import e3learning.domain.TrainingVO;
import e3learning.framework.mvc.BaseController;
import e3learning.framework.mvc.CommResponseBody;
import e3learning.service.AccountService;
import e3learning.service.CourseService;
import e3learning.service.TrainingService;

@Controller
public class AcnTrManageController extends BaseController {

	@Autowired
	AccountService accountService;
	@Autowired
	TrainingService trainingService;
	@Autowired
	CourseService courseService;
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String getIndex(HttpServletRequest request) {
		return "index";
	}
	
	@RequestMapping(value="/account",method={RequestMethod.POST},consumes={MediaType.APPLICATION_JSON_VALUE},produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> addAccount(@Valid @RequestBody AccountVO accountVO, BindingResult bindResult) throws Exception {
		CommResponseBody responseBody = new CommResponseBody();
		responseBody.setErrorMsgFromBindResult(bindResult);
		if(responseBody.isTransactionStatus()==false) {
			return new ResponseEntity<CommResponseBody>(responseBody, HttpStatus.BAD_REQUEST);
		} else {
			accountService.addAcount(accountVO.addInit());			
			responseBody.setBody(accountVO);
			return new ResponseEntity<CommResponseBody>(responseBody, HttpStatus.ACCEPTED);
		}
	}
	
	@RequestMapping(value="/account/{id}",method={RequestMethod.GET},produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> getAccount(@PathVariable("id") int idAccount) throws Exception {
		AccountVO accountVO = new AccountVO();
		accountVO.setIdAccount(idAccount);
		accountVO = this.accountService.getAccountDetail(accountVO);
		CommResponseBody responseBody = new CommResponseBody();
		responseBody.setTransactionStatus(true);
		responseBody.setBody(accountVO);
		return new ResponseEntity<CommResponseBody>(responseBody, HttpStatus.ACCEPTED);
	}

	@RequestMapping(value="/account/{id}",method={RequestMethod.POST},produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> modifytAccount(@PathVariable("id") int idAccount, @Valid @RequestBody AccountVO accountVO, BindingResult bindResult) throws Exception {
		CommResponseBody responseBody = new CommResponseBody();
		responseBody.setErrorMsgFromBindResult(bindResult);
		if(responseBody.isTransactionStatus()==false) {
			return new ResponseEntity<CommResponseBody>(responseBody, HttpStatus.BAD_REQUEST);
		} else {
			accountVO = this.accountService.updateUserDetail(accountVO.addUpdate());
			responseBody.setTransactionStatus(true);
			responseBody.setBody(accountVO);
			return new ResponseEntity<CommResponseBody>(responseBody, HttpStatus.ACCEPTED);
		}
	}
	@RequestMapping(value="/accounts",method={RequestMethod.GET},produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> getAccounts(HttpServletRequest request) throws Exception {
		CommResponseBody responseBody = new CommResponseBody();	
		responseBody.setTransactionStatus(true);
		List<AccountVO> listAccount = this.accountService.getAccountList();
		responseBody.setBody(listAccount);
		return new ResponseEntity<CommResponseBody>(responseBody, HttpStatus.ACCEPTED);
	
	}
	
	
	@RequestMapping(value="/training/{idAccount}",method={RequestMethod.GET},produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> getTrainingByAccountId(@PathVariable("idAccount") int idAccount) {
		CommResponseBody responseBody = new CommResponseBody();	
		responseBody.setTransactionStatus(true);
		List<TrainingVO> listTrainings = this.trainingService.getTrainingsByIdAccount(idAccount);
		responseBody.setBody(listTrainings);
		return new ResponseEntity<CommResponseBody>(responseBody, HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(value="/courses",method={RequestMethod.GET},produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> getCourses(HttpServletRequest request) throws Exception {
		CommResponseBody responseBody = new CommResponseBody();	
		responseBody.setTransactionStatus(true);
		List<CourseVO> listAccount = this.courseService.getCourseList();
		responseBody.setBody(listAccount);
		return new ResponseEntity<CommResponseBody>(responseBody, HttpStatus.ACCEPTED);
	
	}
	
	@RequestMapping(value="/course",method={RequestMethod.POST},consumes={MediaType.APPLICATION_JSON_VALUE},produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> addCourse(@Valid @RequestBody CourseVO courseVO, BindingResult bindResult) throws Exception {
		CommResponseBody responseBody = new CommResponseBody();
		responseBody.setErrorMsgFromBindResult(bindResult);
		if(responseBody.isTransactionStatus()==false) {
			return new ResponseEntity<CommResponseBody>(responseBody, HttpStatus.BAD_REQUEST);
		} else {
			courseService.addCourse(courseVO.addInit());			
			responseBody.setBody(courseVO);
			return new ResponseEntity<CommResponseBody>(responseBody, HttpStatus.ACCEPTED);
		}
	}
	
	@RequestMapping(value="/training",method={RequestMethod.POST},consumes={MediaType.APPLICATION_JSON_VALUE},produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<?> addTraing(@Valid @RequestBody TrainingVO trainingVO, BindingResult bindResult) throws Exception {
		CommResponseBody responseBody = new CommResponseBody();
		responseBody.setErrorMsgFromBindResult(bindResult);
		if(responseBody.isTransactionStatus()==false) {
			return new ResponseEntity<CommResponseBody>(responseBody, HttpStatus.BAD_REQUEST);
		} else {
			trainingService.enrolTraining(trainingVO.addInit());			
			responseBody.setBody(trainingVO);
			return new ResponseEntity<CommResponseBody>(responseBody, HttpStatus.ACCEPTED);
		}
	}
	
}
