/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.web.filter;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 解析 PUT、PATCH、DELETE 请求、响应中的form data数据，并将它们以请求参数形式暴露
 * {@code Filter} that parses form data for HTTP PUT, PATCH, and DELETE requests
 * and exposes it as Servlet request parameters. By default the Servlet spec
 * only requires this for HTTP POST.
 *
 * @author Rossen Stoyanchev
 * @since 5.1
 */
public class FormContentFilter extends OncePerRequestFilter {

	private static final List<String> HTTP_METHODS = Arrays.asList("PUT", "PATCH", "DELETE");

	private FormHttpMessageConverter formConverter = new AllEncompassingFormHttpMessageConverter();


	/**
	 * Set the converter to use for parsing form content.
	 * <p>By default this is an instance of {@link AllEncompassingFormHttpMessageConverter}.
	 */
	public void setFormConverter(FormHttpMessageConverter converter) {
		Assert.notNull(converter, "FormHttpMessageConverter is required");
		this.formConverter = converter;
	}

	/**
	 * The default character set to use for reading form data.
	 * This is a shortcut for:<br>
	 * {@code getFormConverter.setCharset(charset)}.
	 */
	public void setCharset(Charset charset) {
		this.formConverter.setCharset(charset);
	}


	@Override
	protected void doFilterInternal(
			HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// 将请求参数拆分为键值对
		MultiValueMap<String, String> params = parseIfNecessary(request);
		if (!CollectionUtils.isEmpty(params)) {
			filterChain.doFilter(new FormContentRequestWrapper(request, params), response);
		}
		else {
			filterChain.doFilter(request, response);
		}
	}

	/**
	 * 解析request
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@Nullable
	private MultiValueMap<String, String> parseIfNecessary(HttpServletRequest request) throws IOException {
		if (!shouldParse(request)) {
			return null;
		}

		HttpInputMessage inputMessage = new ServletServerHttpRequest(request) {
			@Override
			public InputStream getBody() throws IOException {
				return request.getInputStream();
			}
		};
		// 将请求参数拆分为键值对
		return this.formConverter.read(null, inputMessage);
	}

	/**
	 * 1、是否是特定的方法{@link #HTTP_METHODS}
	 * 2、是否是特定的请求类型{@link MediaType#APPLICATION_FORM_URLENCODED}
	 * @param request
	 * @return
	 */
	private boolean shouldParse(HttpServletRequest request) {
		if (!HTTP_METHODS.contains(request.getMethod())) {
			return false;
		}
		try {
			MediaType mediaType = MediaType.parseMediaType(request.getContentType());
			return MediaType.APPLICATION_FORM_URLENCODED.includes(mediaType);
		}
		catch (IllegalArgumentException ex) {
			return false;
		}
	}


	private static class FormContentRequestWrapper extends HttpServletRequestWrapper {

		private MultiValueMap<String, String> formParams;

		public FormContentRequestWrapper(HttpServletRequest request, MultiValueMap<String, String> params) {
			super(request);
			this.formParams = params;
		}

		@Override
		@Nullable
		public String getParameter(String name) {
			String queryStringValue = super.getParameter(name);
			String formValue = this.formParams.getFirst(name);
			return (queryStringValue != null ? queryStringValue : formValue);
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			Map<String, String[]> result = new LinkedHashMap<>();
			Enumeration<String> names = getParameterNames();
			while (names.hasMoreElements()) {
				String name = names.nextElement();
				result.put(name, getParameterValues(name));
			}
			return result;
		}

		@Override
		public Enumeration<String> getParameterNames() {
			Set<String> names = new LinkedHashSet<>();
			names.addAll(Collections.list(super.getParameterNames()));
			names.addAll(this.formParams.keySet());
			return Collections.enumeration(names);
		}

		@Override
		@Nullable
		public String[] getParameterValues(String name) {
			String[] parameterValues = super.getParameterValues(name);
			List<String> formParam = this.formParams.get(name);
			if (formParam == null) {
				return parameterValues;
			}
			if (parameterValues == null || getQueryString() == null) {
				return StringUtils.toStringArray(formParam);
			}
			else {
				List<String> result = new ArrayList<>(parameterValues.length + formParam.size());
				result.addAll(Arrays.asList(parameterValues));
				result.addAll(formParam);
				return StringUtils.toStringArray(result);
			}
		}
	}

}
