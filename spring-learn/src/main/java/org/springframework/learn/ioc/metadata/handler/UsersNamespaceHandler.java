package org.springframework.learn.ioc.metadata.handler;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.learn.ioc.domain.User;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

public class UsersNamespaceHandler extends NamespaceHandlerSupport {
	@Override
	public void init() {
		// 将 user 元素注册对应的BeanDefinitionParser实现
		registerBeanDefinitionParser("user", new UserBeanDefinitionParser());
	}

	public static class UserBeanDefinitionParser  extends AbstractSingleBeanDefinitionParser {
		@Override
		protected Class<?> getBeanClass(Element element) {
			return User.class;
		}

		@Override
		protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
			getAndSetProperty("id", element, builder);
			getAndSetProperty("name", element, builder);
			getAndSetProperty("city", element, builder);
		}

		private void getAndSetProperty(String attributeName, Element element, BeanDefinitionBuilder builder) {
			String attribute = element.getAttribute(attributeName);
			if (StringUtils.hasText(attribute)) {
				builder.addPropertyValue(attributeName, attribute);
			}
		}
	}
}
