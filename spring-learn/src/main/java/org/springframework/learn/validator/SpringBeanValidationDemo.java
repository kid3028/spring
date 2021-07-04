package org.springframework.learn.validator;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * spring Bean Validation整合示例
 *
 * @see org.springframework.validation.Validator
 * @see org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
 */
public class SpringBeanValidationDemo {

	public static void main(String[] args) {
		try {
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:/META-INF/validation-context.xml");
			Validator bean = ctx.getBean(Validator.class);
			System.out.println("validator instanceof LocalValidatorFactoryBean ? " + (bean instanceof LocalValidatorFactoryBean));
			StudentService studentService = ctx.getBean(StudentService.class);
			studentService.testValidate(new Student());
			ctx.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Component
	@Validated
	static class StudentService {
		public void testValidate(@Valid Student student) {

		}
	}

	static class Student {
		@NotBlank(message = "name不能为空")
		private String name;
	}
}
