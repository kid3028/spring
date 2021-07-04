package org.springframework.learn.conversion;

import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.PropertyEditorRegistry;
import org.springframework.learn.ioc.domain.User;

public class CustomPropertyEditorRegistrar implements PropertyEditorRegistrar {
	@Override
	public void registerCustomEditors(PropertyEditorRegistry registry) {
		registry.registerCustomEditor(User.class, new StringToPropertiesPropertyEditor());
	}
}
