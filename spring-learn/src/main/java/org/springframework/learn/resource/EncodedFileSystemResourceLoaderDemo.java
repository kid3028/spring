package org.springframework.learn.resource;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.io.Reader;

public class EncodedFileSystemResourceLoaderDemo {

	public static void main(String[] args) throws IOException {
		String path = System.getProperty("user.dir") + "/spring-learn/src/main/java/org/springframework/learn/resource/EncodedFileSystemResourceDemo.java";
		FileSystemResourceLoader resourceLoader = new FileSystemResourceLoader();
		Resource resource = resourceLoader.getResource(path);
		EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");
		try (Reader reader = encodedResource.getReader();){
			System.out.println(IOUtils.toString(reader));
		}
	}
}
