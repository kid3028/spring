package org.springframework.learn.resource;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.PathMatcher;

import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

public class CustomResourcePatternResolveDemo {
	public static void main(String[] args) throws IOException {
		try {

		String path = System.getProperty("user.dir") + "/spring-learn/src/main/java/org/springframework/learn/resource/*.java";
		FileSystemResourceLoader resourceLoader = new FileSystemResourceLoader();
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(resourceLoader);
		resolver.setPathMatcher(new JavaFilePathMatcher());
		Resource[] resources = resolver.getResources(path);
		if (resources.length == 0) {
			return;
		}
		Arrays.stream(resources).forEach(resource -> {
			try {
				EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");
				Reader reader = encodedResource.getReader();
				System.out.println(IOUtils.toString(reader));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	static class JavaFilePathMatcher implements PathMatcher {

		@Override
		public boolean isPattern(String path) {
			return path.endsWith(".java");
		}

		@Override
		public boolean match(String pattern, String path) {
			return path.endsWith(".java");
		}

		@Override
		public boolean matchStart(String pattern, String path) {
			return false;
		}

		@Override
		public String extractPathWithinPattern(String pattern, String path) {
			return null;
		}

		@Override
		public Map<String, String> extractUriTemplateVariables(String pattern, String path) {
			return null;
		}

		@Override
		public Comparator<String> getPatternComparator(String path) {
			return null;
		}

		@Override
		public String combine(String pattern1, String pattern2) {
			return null;
		}
	}
}
