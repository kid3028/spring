package org.springframework.learn.resource;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.util.Arrays;

public class InjectResourceDemo {

	@Value("classpath:/META-INF/user.properties")
	private Resource resource;

	@Value("classpath*:/META-INF/*.properties")
	private Resource[] resources;

	public static void main(String[] args) throws IOException {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(InjectResourceDemo.class);
		ctx.refresh();
		InjectResourceDemo bean = ctx.getBean(InjectResourceDemo.class);
		EncodedResource encodedResource = new EncodedResource(bean.resource, "UTF-8");
		System.out.println(IOUtils.toString(encodedResource.getReader()));

		System.out.println("==========");
		Arrays.stream(bean.resources).forEach(r -> {
			EncodedResource e = new EncodedResource(r);
			try {
				System.out.println(IOUtils.toString(e.getReader()));
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		});
	}

}
