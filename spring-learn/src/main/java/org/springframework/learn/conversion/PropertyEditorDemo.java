package org.springframework.learn.conversion;

public class PropertyEditorDemo {


	public static void main(String[] args) {
		try {
			String text = "name = qullkui";
			StringToPropertiesPropertyEditor editor = new StringToPropertiesPropertyEditor();
			editor.setAsText(text);
			System.out.println(editor.getValue());
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
