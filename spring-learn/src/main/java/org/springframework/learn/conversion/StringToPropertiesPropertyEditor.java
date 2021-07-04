package org.springframework.learn.conversion;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

public class StringToPropertiesPropertyEditor extends PropertyEditorSupport {

	// 1、实现setAsText(String)方法
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
//		super.setAsText(text);
		// 2、将String类型转换为Properties类型
		Properties properties = new Properties();
		try {
			properties.load(new StringReader(text));
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		// 3、临时存储Properties对象
		this.setValue(properties);
	}
}
