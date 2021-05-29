package org.springframework.beans.example;


import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class BeanFactoryTest {

	@Test
	public void testXmlReader() {
		XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource("beanFactoryTest.xml"));
		factory.getBean("beanTest");
	}

}
