package org.springframework.learn.resource;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;

import java.io.IOException;
import java.io.Reader;

public class EncodedFileSystemResourceDemo {

	public static void main(String[] args) throws IOException {
		String path = System.getProperty("user.dir") + "/spring-learn/src/main/java/org/springframework/learn/resource/EncodedFileSystemResourceDemo.java";
		FileSystemResource fileSystemResource = new FileSystemResource(path);
		EncodedResource encodedResource = new EncodedResource(fileSystemResource, "UTF-8");
		try (Reader reader = encodedResource.getReader();){
			System.out.println(IOUtils.toString(reader));
		}
	}
}
