package org.openmrs.module.operationtheater;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.openmrs.validator.ValidateUtil;
import org.powermock.api.mockito.PowerMockito;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * common methods that are used to mock objects
 */
public class MockUtil {

	/**
	 * @param validationShouldPass
	 * @param validatedClass
	 * @param errorField
	 * @param errorCode
	 * @return
	 * @throws Exception
	 */
	public static void mockValidateUtil(final boolean validationShouldPass, Class<?> validatedClass,
	                                    final String errorField, final String errorCode) throws Exception {
		PowerMockito.spy(ValidateUtil.class);

		//do not execute the validate method
		PowerMockito.doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
				Errors errors = (Errors) invocationOnMock.getArguments()[1];
				if (!validationShouldPass) {
					errors.rejectValue(errorField, errorCode);
				}
				return null;
			}
		}).when(ValidateUtil.class, "validate", Mockito.any(validatedClass), Mockito.any(Errors.class));
	}

	public static Validator mockValidator(final boolean validationShouldPass, Class<? extends Validator> validatorClass,
	                                      Class<?> validatedClass, final String errorField, final String errorCode)
			throws Exception {

		Validator validator = Mockito.mock(validatorClass);

		Mockito.doAnswer(new Answer<Void>() {

			@Override
			public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
				Errors errors = (Errors) invocationOnMock.getArguments()[1];
				if (!validationShouldPass) {
					errors.rejectValue(errorField, errorCode);
				}
				return null;
			}

		}).when(validator).validate(Mockito.any(validatedClass), Mockito.any(Errors.class));

		return validator;
	}
}
