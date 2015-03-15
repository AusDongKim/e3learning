package e3learning.framework.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class BaseController {

	
	@ExceptionHandler({Exception.class})
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	public void simpleExceptionError(Exception e) {
		e.printStackTrace();
	}
}
