package org.springframework.learn.environment;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestPropertySourceTest.class)  // spring注解驱动测试注解
@TestPropertySource(properties = "user.name=阿华哥")
public class TestPropertySourceTest {

	@Value("${user.name}")
	private String username;

	@Autowired
	private ConfigurableEnvironment environment;

	@Test
	public void testUsername() {
		Assert.assertEquals("阿华哥", username);
		for (PropertySource<?> propertySource : environment.getPropertySources()) {
			System.out.println("propertyName : " + propertySource + ", user.name : " + propertySource.getProperty("user.name"));
		}
	}
}
