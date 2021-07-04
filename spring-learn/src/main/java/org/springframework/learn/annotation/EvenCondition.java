package org.springframework.learn.annotation;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class EvenCondition implements Condition {

	@Override
	@SuppressWarnings("deprecation")
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		// 条件上下文
		Environment environment = context.getEnvironment();
		return environment.acceptsProfiles("even");
	}
}
