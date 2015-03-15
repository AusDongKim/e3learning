package e3learning.framework.validator;

import java.lang.reflect.Field;
import java.util.Set;

import javax.persistence.Column;
import javax.validation.ConstraintViolation;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


public class CustomLocalValidatorFactoryBean extends LocalValidatorFactoryBean {

	
	@Override
	public void processConstraintViolations(Set<ConstraintViolation<Object>> violations, Errors errors) {

		// super.processConstraintViolations(violations, errors);
		for (ConstraintViolation<Object> violation : violations) {
			String field = violation.getPropertyPath().toString();
			FieldError fieldError = errors.getFieldError(field);
			if (fieldError == null || !fieldError.isBindingFailure()) {
				try {
					String errorCode = violation.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName();
					Object[] errorArgs = getArgumentsForConstraint(errors.getObjectName(), field, violation.getConstraintDescriptor());
					if (errors instanceof BindingResult) {
						BindingResult bindingResult = (BindingResult) errors;
						String nestedField = bindingResult.getNestedPath() + field;
						if ("".equals(nestedField)) {
							String[] errorCodes = bindingResult.resolveMessageCodes(errorCode);
							bindingResult.addError(new ObjectError(errors.getObjectName(), errorCodes, errorArgs, 
									hookViolationGetMessage(violation)/*violation.getMessage()*/)); 
						} else {
							Object invalidValue = violation.getInvalidValue();
							if (!"".equals(field) && invalidValue == violation.getLeafBean()) {
								invalidValue = bindingResult.getRawFieldValue(field);
							}
							String[] errorCodes = bindingResult.resolveMessageCodes(errorCode, field);
							bindingResult.addError(new FieldError(errors.getObjectName(), nestedField, invalidValue, false, errorCodes, errorArgs, 
									hookViolationGetMessage(violation)/*violation.getMessage()*/)); 
						}
					} else {
						errors.rejectValue(field, errorCode, errorArgs, violation.getMessage());
					}
				} catch (NotReadablePropertyException ex) {
					throw new IllegalStateException("JSR-303 validated property '" + field + "' does not have a corresponding accessor for Spring data binding - " + "check your DataBinder's configuration (bean property versus direct field access)", ex);
				}
			}
		}
	}
	
	protected String hookViolationGetMessage(ConstraintViolation<Object> violation)
	{
		String message = violation.getMessage();
		if(message.contains("__propertyName__")) {
			Object leafBean = violation.getLeafBean();
			String propPath = violation.getPropertyPath().toString();
			Column annotation = null;
			String propertyName = propPath;
			if ("".equals(propPath)) {
				annotation = leafBean.getClass().getAnnotation(Column.class);
			} else {
				try {
					/**
					 * Link: http://tutorials.jenkov.com/java-reflection/private-fields-and-methods.html
					 * 
					 * PrivateObject privateObject = new PrivateObject("The Private Value");
					   Field privateStringField = PrivateObject.class.getDeclaredField("privateString");							
					   privateStringField.setAccessible(true);							
					  String fieldValue = (String) privateStringField.get(privateObject);
					 */
					Field field = leafBean.getClass().getDeclaredField(propPath);
					field.setAccessible(true);
					annotation = field.getAnnotation(Column.class);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(annotation != null) {
				propertyName = annotation.name();
			}
			message = message.replace("__propertyName__", propertyName).trim();
		}
		return message;
	}
}
