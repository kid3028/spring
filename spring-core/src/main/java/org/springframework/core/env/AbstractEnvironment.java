/*
 * Copyright 2002-2019 the original author or authors.
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

package org.springframework.core.env;

import java.security.AccessControlException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.core.SpringProperties;
import org.springframework.core.convert.support.ConfigurableConversionService;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * {@link Environment}的抽象实现。
 * 可以通过{@link #ACTIVE_PROFILES_PROPERTY_NAME} {@link #DEFAULT_PROFILES_PROPERTY_NAME}来设置激活profile 或者 默认profile
 *
 *
 * Abstract base class for {@link Environment} implementations. Supports the notion of
 * reserved default profile names and enables specifying active and default profiles
 * through the {@link #ACTIVE_PROFILES_PROPERTY_NAME} and
 * {@link #DEFAULT_PROFILES_PROPERTY_NAME} properties.
 *
 * 具体的子类间主要的不同在于{@link PropertySource}对象
 * 子类应该通过{@link #customizePropertySources(MutablePropertySources)}来自定义属性源，
 * 客户端通过{@link ConfigurableEnvironment#getPropertySources()} {@link MutablePropertySources}来使用属性源
 *
 * <p>Concrete subclasses differ primarily on which {@link PropertySource} objects they
 * add by default. {@code AbstractEnvironment} adds none. Subclasses should contribute
 * property sources through the protected {@link #customizePropertySources(MutablePropertySources)}
 * hook, while clients should customize using {@link ConfigurableEnvironment#getPropertySources()}
 * and working against the {@link MutablePropertySources} API.
 * See {@link ConfigurableEnvironment} javadoc for usage examples.
 *
 * @author Chris Beams
 * @author Juergen Hoeller
 * @since 3.1
 * @see ConfigurableEnvironment
 * @see StandardEnvironment
 */
public abstract class AbstractEnvironment implements ConfigurableEnvironment {

	/**
	 * 忽略系统环境变量中的系统属性，即不能通过{@link System#getenv()}检索变量
	 * 默认是false，如果spring环境属性无法解析将降级到系统属性。
	 * System property that instructs Spring to ignore system environment variables,
	 * i.e. to never attempt to retrieve such a variable via {@link System#getenv()}.
	 * <p>The default is "false", falling back to system environment variable checks if a
	 * Spring environment property (e.g. a placeholder in a configuration String) isn't
	 * resolvable otherwise. Consider switching this flag to "true" if you experience
	 * log warnings from {@code getenv} calls coming from Spring, e.g. on WebSphere
	 * with strict SecurityManager settings and AccessControlExceptions warnings.
	 * @see #suppressGetenvAccess()
	 */
	public static final String IGNORE_GETENV_PROPERTY_NAME = "spring.getenv.ignore";

	/**
	 * 指定激活的profiles。多个profiles可以使用逗号分隔
	 * 【注意】在某些shell环境中(如bash)不允许在变量中使用标点符号
	 * 如果spring {@link SystemEnvironmentPropertySource}是可用的，
	 * {@link #ACTIVE_PROFILES_PROPERTY_NAME}这个属性通常通过环境变量来指定
	 * Name of property to set to specify active profiles: {@value}. Value may be comma
	 * delimited.
	 * <p>Note that certain shell environments such as Bash disallow the use of the period
	 * character in variable names. Assuming that Spring's {@link SystemEnvironmentPropertySource}
	 * is in use, this property may be specified as an environment variable as
	 * {@code SPRING_PROFILES_ACTIVE}.
	 * @see ConfigurableEnvironment#setActiveProfiles
	 */
	public static final String ACTIVE_PROFILES_PROPERTY_NAME = "spring.profiles.active";

	/**
	 * 指定默认的profiles
	 * Name of property to set to specify profiles active by default: {@value}. Value may
	 * be comma delimited.
	 * <p>Note that certain shell environments such as Bash disallow the use of the period
	 * character in variable names. Assuming that Spring's {@link SystemEnvironmentPropertySource}
	 * is in use, this property may be specified as an environment variable as
	 * {@code SPRING_PROFILES_DEFAULT}.
	 * @see ConfigurableEnvironment#setDefaultProfiles
	 */
	public static final String DEFAULT_PROFILES_PROPERTY_NAME = "spring.profiles.default";

	/**
	 * 确认的profiles
	 * 如果没有指定active profiles、default profiles，那么将使用缺省的  default profiles
	 * Name of reserved default profile name: {@value}. If no default profile names are
	 * explicitly and no active profile names are explicitly set, this profile will
	 * automatically be activated by default.
	 * @see #getReservedDefaultProfiles
	 * @see ConfigurableEnvironment#setDefaultProfiles
	 * @see ConfigurableEnvironment#setActiveProfiles
	 * @see AbstractEnvironment#DEFAULT_PROFILES_PROPERTY_NAME
	 * @see AbstractEnvironment#ACTIVE_PROFILES_PROPERTY_NAME
	 */
	protected static final String RESERVED_DEFAULT_PROFILE_NAME = "default";


	protected final Log logger = LogFactory.getLog(getClass());

	private final Set<String> activeProfiles = new LinkedHashSet<>();

	private final Set<String> defaultProfiles = new LinkedHashSet<>(getReservedDefaultProfiles());

	private final MutablePropertySources propertySources = new MutablePropertySources();

	private final ConfigurablePropertyResolver propertyResolver =
			new PropertySourcesPropertyResolver(this.propertySources);


	/**
	 * 创建Environment实例，底层将会调用{@link #customizePropertySources(MutablePropertySources)}
	 * 子类可以覆盖{@link #customizePropertySources(MutablePropertySources)}来操作{@link PropertySource}
	 * Create a new {@code Environment} instance, calling back to
	 * {@link #customizePropertySources(MutablePropertySources)} during construction to
	 * allow subclasses to contribute or manipulate {@link PropertySource} instances as
	 * appropriate.
	 * @see #customizePropertySources(MutablePropertySources)
	 */
	public AbstractEnvironment() {
		customizePropertySources(this.propertySources);
	}


	/**
	 * 自定义{@link PropertySource}的处理
	 * Customize the set of {@link PropertySource} objects to be searched by this
	 * {@code Environment} during calls to {@link #getProperty(String)} and related
	 * methods.
	 *
	 * 推荐子类来覆盖该方法来增加自定义的PropertySource
	 *
	 * <p>Subclasses that override this method are encouraged to add property
	 * sources using {@link MutablePropertySources#addLast(PropertySource)} such that
	 * further subclasses may call {@code super.customizePropertySources()} with
	 * predictable results. For example:
	 * <pre class="code">
	 * public class Level1Environment extends AbstractEnvironment {
	 *     &#064;Override
	 *     protected void customizePropertySources(MutablePropertySources propertySources) {
	 *         super.customizePropertySources(propertySources); // no-op from base class
	 *         propertySources.addLast(new PropertySourceA(...));
	 *         propertySources.addLast(new PropertySourceB(...));
	 *     }
	 * }
	 *
	 * public class Level2Environment extends Level1Environment {
	 *     &#064;Override
	 *     protected void customizePropertySources(MutablePropertySources propertySources) {
	 *         super.customizePropertySources(propertySources); // add all from superclass
	 *         propertySources.addLast(new PropertySourceC(...));
	 *         propertySources.addLast(new PropertySourceD(...));
	 *     }
	 * }
	 * </pre>
	 * In this arrangement, properties will be resolved against sources A, B, C, D in that
	 * order. That is to say that property source "A" has precedence over property source
	 * "D". If the {@code Level2Environment} subclass wished to give property sources C
	 * and D higher precedence than A and B, it could simply call
	 * {@code super.customizePropertySources} after, rather than before adding its own:
	 * <pre class="code">
	 * public class Level2Environment extends Level1Environment {
	 *     &#064;Override
	 *     protected void customizePropertySources(MutablePropertySources propertySources) {
	 *         propertySources.addLast(new PropertySourceC(...));
	 *         propertySources.addLast(new PropertySourceD(...));
	 *         super.customizePropertySources(propertySources); // add all from superclass
	 *     }
	 * }
	 * </pre>
	 * The search order is now C, D, A, B as desired.
	 *
	 * <p>Beyond these recommendations, subclasses may use any of the {@code add&#42;},
	 * {@code remove}, or {@code replace} methods exposed by {@link MutablePropertySources}
	 * in order to create the exact arrangement of property sources desired.
	 *
	 * <p>The base implementation registers no property sources.
	 *
	 * <p>Note that clients of any {@link ConfigurableEnvironment} may further customize
	 * property sources via the {@link #getPropertySources()} accessor, typically within
	 * an {@link org.springframework.context.ApplicationContextInitializer
	 * ApplicationContextInitializer}. For example:
	 * <pre class="code">
	 * ConfigurableEnvironment env = new StandardEnvironment();
	 * env.getPropertySources().addLast(new PropertySourceX(...));
	 * </pre>
	 *
	 * 实例变量的访问
	 * 该方法禁止访问子类中声明的有初始化值的对象属性。
	 * 由于Java对象创建机制的限制，AbstractEnvironment构造器中调用该方法时，子类的中的对象属性还没有完成初始化，
	 * 所以访问对象属性将会造成NPE或者其他问题。
	 * 如果确实需要访问实例变量，那么该方法应该做空实现，并且在子类的构造器中完成对属性源的操作，和实例变量的访问。
	 *
	 * 执行父类的静态代码块，并初始化父类静态成员变量
	 * 执行子类的静态代码块，并初始化子类静态成员变量
	 * 执行父类的构造代码块，执行父类的构造函数，并初始化父类普通成员变量
	 * 执行子类的构造代码块， 执行子类的构造函数，并初始化子类普通成员变量
	 *
	 * <h2>A warning about instance variable access</h2>
	 * Instance variables declared in subclasses and having default initial values should
	 * <em>not</em> be accessed from within this method. Due to Java object creation
	 * lifecycle constraints, any initial value will not yet be assigned when this
	 * callback is invoked by the {@link #AbstractEnvironment()} constructor, which may
	 * lead to a {@code NullPointerException} or other problems. If you need to access
	 * default values of instance variables, leave this method as a no-op and perform
	 * property source manipulation and instance variable access directly within the
	 * subclass constructor. Note that <em>assigning</em> values to instance variables is
	 * not problematic; it is only attempting to read default values that must be avoided.
	 *
	 * @see MutablePropertySources
	 * @see PropertySourcesPropertyResolver
	 * @see org.springframework.context.ApplicationContextInitializer
	 */
	protected void customizePropertySources(MutablePropertySources propertySources) {
	}

	/**
	 * 返回缺省profile
	 * 子类可以覆盖该方法，自定义缺省的profile
	 * Return the set of reserved default profile names. This implementation returns
	 * {@value #RESERVED_DEFAULT_PROFILE_NAME}. Subclasses may override in order to
	 * customize the set of reserved names.
	 * @see #RESERVED_DEFAULT_PROFILE_NAME
	 * @see #doGetDefaultProfiles()
	 */
	protected Set<String> getReservedDefaultProfiles() {
		return Collections.singleton(RESERVED_DEFAULT_PROFILE_NAME);
	}


	//---------------------------------------------------------------------
	// Implementation of ConfigurableEnvironment interface
	//---------------------------------------------------------------------

	@Override
	public String[] getActiveProfiles() {
		return StringUtils.toStringArray(doGetActiveProfiles());
	}

	/**
	 * 返回active profiles
	 * Return the set of active profiles as explicitly set through
	 * {@link #setActiveProfiles} or if the current set of active profiles
	 * is empty, check for the presence of the {@value #ACTIVE_PROFILES_PROPERTY_NAME}
	 * property and assign its value to the set of active profiles.
	 * @see #getActiveProfiles()
	 * @see #ACTIVE_PROFILES_PROPERTY_NAME
	 */
	protected Set<String> doGetActiveProfiles() {
		synchronized (this.activeProfiles) {
			if (this.activeProfiles.isEmpty()) {
				String profiles = getProperty(ACTIVE_PROFILES_PROPERTY_NAME);
				if (StringUtils.hasText(profiles)) {
					setActiveProfiles(StringUtils.commaDelimitedListToStringArray(
							StringUtils.trimAllWhitespace(profiles)));
				}
			}
			return this.activeProfiles;
		}
	}

	@Override
	public void setActiveProfiles(String... profiles) {
		Assert.notNull(profiles, "Profile array must not be null");
		if (logger.isDebugEnabled()) {
			logger.debug("Activating profiles " + Arrays.asList(profiles));
		}
		synchronized (this.activeProfiles) {
			this.activeProfiles.clear();
			for (String profile : profiles) {
				validateProfile(profile);
				this.activeProfiles.add(profile);
			}
		}
	}

	@Override
	public void addActiveProfile(String profile) {
		if (logger.isDebugEnabled()) {
			logger.debug("Activating profile '" + profile + "'");
		}
		validateProfile(profile);
		doGetActiveProfiles();
		synchronized (this.activeProfiles) {
			this.activeProfiles.add(profile);
		}
	}


	@Override
	public String[] getDefaultProfiles() {
		return StringUtils.toStringArray(doGetDefaultProfiles());
	}

	/**
	 * 默认的profiles
	 * Return the set of default profiles explicitly set via
	 * {@link #setDefaultProfiles(String...)} or if the current set of default profiles
	 * consists only of {@linkplain #getReservedDefaultProfiles() reserved default
	 * profiles}, then check for the presence of the
	 * {@value #DEFAULT_PROFILES_PROPERTY_NAME} property and assign its value (if any)
	 * to the set of default profiles.
	 * @see #AbstractEnvironment()
	 * @see #getDefaultProfiles()
	 * @see #DEFAULT_PROFILES_PROPERTY_NAME
	 * @see #getReservedDefaultProfiles()
	 */
	protected Set<String> doGetDefaultProfiles() {
		synchronized (this.defaultProfiles) {
			if (this.defaultProfiles.equals(getReservedDefaultProfiles())) {
				String profiles = getProperty(DEFAULT_PROFILES_PROPERTY_NAME);
				if (StringUtils.hasText(profiles)) {
					setDefaultProfiles(StringUtils.commaDelimitedListToStringArray(
							StringUtils.trimAllWhitespace(profiles)));
				}
			}
			return this.defaultProfiles;
		}
	}

	/**
	 * Specify the set of profiles to be made active by default if no other profiles
	 * are explicitly made active through {@link #setActiveProfiles}.
	 * <p>Calling this method removes overrides any reserved default profiles
	 * that may have been added during construction of the environment.
	 * @see #AbstractEnvironment()
	 * @see #getReservedDefaultProfiles()
	 */
	@Override
	public void setDefaultProfiles(String... profiles) {
		Assert.notNull(profiles, "Profile array must not be null");
		synchronized (this.defaultProfiles) {
			this.defaultProfiles.clear();
			for (String profile : profiles) {
				validateProfile(profile);
				this.defaultProfiles.add(profile);
			}
		}
	}

	@Override
	@Deprecated
	public boolean acceptsProfiles(String... profiles) {
		Assert.notEmpty(profiles, "Must specify at least one profile");
		for (String profile : profiles) {
			if (StringUtils.hasLength(profile) && profile.charAt(0) == '!') {
				if (!isProfileActive(profile.substring(1))) {
					return true;
				}
			}
			else if (isProfileActive(profile)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean acceptsProfiles(Profiles profiles) {
		Assert.notNull(profiles, "Profiles must not be null");
		return profiles.matches(this::isProfileActive);
	}

	/**
	 * Return whether the given profile is active, or if active profiles are empty
	 * whether the profile should be active by default.
	 * @throws IllegalArgumentException per {@link #validateProfile(String)}
	 */
	protected boolean isProfileActive(String profile) {
		validateProfile(profile);
		Set<String> currentActiveProfiles = doGetActiveProfiles();
		return (currentActiveProfiles.contains(profile) ||
				(currentActiveProfiles.isEmpty() && doGetDefaultProfiles().contains(profile)));
	}

	/**
	 * Validate the given profile, called internally prior to adding to the set of
	 * active or default profiles.
	 * <p>Subclasses may override to impose further restrictions on profile syntax.
	 * @throws IllegalArgumentException if the profile is null, empty, whitespace-only or
	 * begins with the profile NOT operator (!).
	 * @see #acceptsProfiles
	 * @see #addActiveProfile
	 * @see #setDefaultProfiles
	 */
	protected void validateProfile(String profile) {
		if (!StringUtils.hasText(profile)) {
			throw new IllegalArgumentException("Invalid profile [" + profile + "]: must contain text");
		}
		if (profile.charAt(0) == '!') {
			throw new IllegalArgumentException("Invalid profile [" + profile + "]: must not begin with ! operator");
		}
	}

	@Override
	public MutablePropertySources getPropertySources() {
		return this.propertySources;
	}

	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Map<String, Object> getSystemProperties() {
		try {
			return (Map) System.getProperties();
		}
		catch (AccessControlException ex) {
			return (Map) new ReadOnlySystemAttributesMap() {
				@Override
				@Nullable
				protected String getSystemAttribute(String attributeName) {
					try {
						return System.getProperty(attributeName);
					}
					catch (AccessControlException ex) {
						if (logger.isInfoEnabled()) {
							logger.info("Caught AccessControlException when accessing system property '" +
									attributeName + "'; its value will be returned [null]. Reason: " + ex.getMessage());
						}
						return null;
					}
				}
			};
		}
	}

	@Override
	@SuppressWarnings({"rawtypes", "unchecked"})
	public Map<String, Object> getSystemEnvironment() {
		if (suppressGetenvAccess()) {
			return Collections.emptyMap();
		}
		try {
			return (Map) System.getenv();
		}
		catch (AccessControlException ex) {
			return (Map) new ReadOnlySystemAttributesMap() {
				@Override
				@Nullable
				protected String getSystemAttribute(String attributeName) {
					try {
						return System.getenv(attributeName);
					}
					catch (AccessControlException ex) {
						if (logger.isInfoEnabled()) {
							logger.info("Caught AccessControlException when accessing system environment variable '" +
									attributeName + "'; its value will be returned [null]. Reason: " + ex.getMessage());
						}
						return null;
					}
				}
			};
		}
	}

	/**
	 * Determine whether to suppress {@link System#getenv()}/{@link System#getenv(String)}
	 * access for the purposes of {@link #getSystemEnvironment()}.
	 * <p>If this method returns {@code true}, an empty dummy Map will be used instead
	 * of the regular system environment Map, never even trying to call {@code getenv}
	 * and therefore avoiding security manager warnings (if any).
	 * <p>The default implementation checks for the "spring.getenv.ignore" system property,
	 * returning {@code true} if its value equals "true" in any case.
	 * @see #IGNORE_GETENV_PROPERTY_NAME
	 * @see SpringProperties#getFlag
	 */
	protected boolean suppressGetenvAccess() {
		return SpringProperties.getFlag(IGNORE_GETENV_PROPERTY_NAME);
	}

	@Override
	public void merge(ConfigurableEnvironment parent) {
		for (PropertySource<?> ps : parent.getPropertySources()) {
			if (!this.propertySources.contains(ps.getName())) {
				this.propertySources.addLast(ps);
			}
		}
		String[] parentActiveProfiles = parent.getActiveProfiles();
		if (!ObjectUtils.isEmpty(parentActiveProfiles)) {
			synchronized (this.activeProfiles) {
				for (String profile : parentActiveProfiles) {
					this.activeProfiles.add(profile);
				}
			}
		}
		String[] parentDefaultProfiles = parent.getDefaultProfiles();
		if (!ObjectUtils.isEmpty(parentDefaultProfiles)) {
			synchronized (this.defaultProfiles) {
				this.defaultProfiles.remove(RESERVED_DEFAULT_PROFILE_NAME);
				for (String profile : parentDefaultProfiles) {
					this.defaultProfiles.add(profile);
				}
			}
		}
	}


	//---------------------------------------------------------------------
	// Implementation of ConfigurablePropertyResolver interface
	//---------------------------------------------------------------------

	@Override
	public ConfigurableConversionService getConversionService() {
		return this.propertyResolver.getConversionService();
	}

	@Override
	public void setConversionService(ConfigurableConversionService conversionService) {
		this.propertyResolver.setConversionService(conversionService);
	}

	@Override
	public void setPlaceholderPrefix(String placeholderPrefix) {
		this.propertyResolver.setPlaceholderPrefix(placeholderPrefix);
	}

	@Override
	public void setPlaceholderSuffix(String placeholderSuffix) {
		this.propertyResolver.setPlaceholderSuffix(placeholderSuffix);
	}

	@Override
	public void setValueSeparator(@Nullable String valueSeparator) {
		this.propertyResolver.setValueSeparator(valueSeparator);
	}

	@Override
	public void setIgnoreUnresolvableNestedPlaceholders(boolean ignoreUnresolvableNestedPlaceholders) {
		this.propertyResolver.setIgnoreUnresolvableNestedPlaceholders(ignoreUnresolvableNestedPlaceholders);
	}

	@Override
	public void setRequiredProperties(String... requiredProperties) {
		this.propertyResolver.setRequiredProperties(requiredProperties);
	}

	@Override
	public void validateRequiredProperties() throws MissingRequiredPropertiesException {
		this.propertyResolver.validateRequiredProperties();
	}


	//---------------------------------------------------------------------
	// Implementation of PropertyResolver interface
	//---------------------------------------------------------------------

	@Override
	public boolean containsProperty(String key) {
		return this.propertyResolver.containsProperty(key);
	}

	@Override
	@Nullable
	public String getProperty(String key) {
		return this.propertyResolver.getProperty(key);
	}

	@Override
	public String getProperty(String key, String defaultValue) {
		return this.propertyResolver.getProperty(key, defaultValue);
	}

	@Override
	@Nullable
	public <T> T getProperty(String key, Class<T> targetType) {
		return this.propertyResolver.getProperty(key, targetType);
	}

	@Override
	public <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
		return this.propertyResolver.getProperty(key, targetType, defaultValue);
	}

	@Override
	public String getRequiredProperty(String key) throws IllegalStateException {
		return this.propertyResolver.getRequiredProperty(key);
	}

	@Override
	public <T> T getRequiredProperty(String key, Class<T> targetType) throws IllegalStateException {
		return this.propertyResolver.getRequiredProperty(key, targetType);
	}

	@Override
	public String resolvePlaceholders(String text) {
		return this.propertyResolver.resolvePlaceholders(text);
	}

	@Override
	public String resolveRequiredPlaceholders(String text) throws IllegalArgumentException {
		return this.propertyResolver.resolveRequiredPlaceholders(text);
	}


	@Override
	public String toString() {
		return getClass().getSimpleName() + " {activeProfiles=" + this.activeProfiles +
				", defaultProfiles=" + this.defaultProfiles + ", propertySources=" + this.propertySources + "}";
	}

}
