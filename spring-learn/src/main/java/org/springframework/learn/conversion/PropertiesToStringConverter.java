package org.springframework.learn.conversion;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.util.Collections;
import java.util.Properties;
import java.util.Set;

public class PropertiesToStringConverter implements ConditionalGenericConverter {
	@Override
	public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
		return Properties.class.equals(sourceType.getObjectType()) && String.class.equals(targetType.getObjectType());
	}

	@Override
	public Set<ConvertiblePair> getConvertibleTypes() {
		return Collections.singleton(new ConvertiblePair(Properties.class, String.class));
	}

	@Override
	public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
		Properties properties = (Properties) source;
		StringBuilder builder = new StringBuilder();
		properties.forEach((k,v) -> builder.append(k).append("=").append(v).append(System.getProperty("line.separator")));
		return builder.toString();
	}
}
