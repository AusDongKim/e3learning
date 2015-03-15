package e3learning.framework.mvc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class CommResponseBody {

	
	private boolean trasnactionStatus;
	private List<String> errorMsg;
	private Object body;

	public CommResponseBody(boolean trasnactionStatus, List<String> errorMsg,
			Object body) {
		super();
		this.trasnactionStatus = trasnactionStatus;
		this.errorMsg = errorMsg;
		this.body = body;
	}
	
	
	public CommResponseBody() {
		// TODO Auto-generated constructor stub
	}


	public void setErrorMsgFromBindResult(BindingResult result) {
		setTransactionStatus(result.hasErrors()?false:true);
		List<String> errList = new ArrayList<String>();
		for(ObjectError error: result.getAllErrors()) {
			errList.add(error.getDefaultMessage());
		}
		setErrorMsg(errList);
	}
	
	
	public boolean isTransactionStatus() {
		return trasnactionStatus;
	}
	public void setTransactionStatus(boolean transactionStatus) {
		this.trasnactionStatus = transactionStatus;
	}
	public List<String> getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(List<String> errorMsg) {
		this.errorMsg = errorMsg;
	}
	public Object getBody() {
		return body;
	}
	public void setBody(Object body) {
		this.body = body;
	}
	
	
	
}
