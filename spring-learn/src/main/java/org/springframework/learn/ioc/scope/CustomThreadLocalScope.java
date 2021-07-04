package org.springframework.learn.ioc.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.core.NamedThreadLocal;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CustomThreadLocalScope implements Scope {

	public static final String SCOPE_NAME = "thread-local";

	private NamedThreadLocal<Map<String, Object>> threadLocal = new NamedThreadLocal<Map<String, Object>>("thread-local-scope") {
		@Override
		protected Map<String, Object> initialValue() {
			return new HashMap<>();
		}
	};

	@Override
	public Object get(String name, ObjectFactory<?> objectFactory) {
		Map<String, Object> context = getContext();
		Object o = context.get(name);
		if (Objects.isNull(o)) {
			o = objectFactory.getObject();
			context.put(name, o);
		}
		return o;
	}

	private Map<String, Object> getContext() {
		return threadLocal.get();
	}

	@Override
	public Object remove(String name) {
		return getContext().remove(name);
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback) {

	}

	@Override
	public Object resolveContextualObject(String key) {
		return getContext().get(key);
	}

	@Override
	public String getConversationId() {
		return String.valueOf(Thread.currentThread().getId());
	}
}
