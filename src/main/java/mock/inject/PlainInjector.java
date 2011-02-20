package mock.inject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static mock.inject.Reflect.on;
import javax.inject.Inject;

/**
 * Inject objects on @Inject annotated fields and single parameter methods.
 * 
 * @author frode
 * 
 * @param <T>
 *            the type to apply injection on.
 */
public class PlainInjector<T> implements Injector<T> {

    private final T subject;
    private final Map<Method, Object> methodValue;

    PlainInjector(T subject) {
	this.subject = subject;
	methodValue = new HashMap<Method, Object>();
    }

    /**
     * {@inheritDoc}
     */
    public Injector<T> inject(Object... stuff) {
	for (Object obj : stuff) {
	    injectWith(null, obj);
	}

	return this;
    }

    private boolean injectFields(Class<? extends Annotation> qualifier,
	    Object stuff) {

	for (Field field : subject.getClass().getDeclaredFields()) {
	    if (isInjectable(field) && isMatchType(field, stuff)
		    && isQualifierMatch(qualifier, field)) {

		fieldAssignedScreem(field);

		on(subject).set(field, stuff);
		return true;
	    }
	}
	return false;
    }

    private boolean isQualifierMatch(Class<? extends Annotation> qualifier,
	    Field field) {
	return qualifier == null || field.getAnnotation(qualifier) != null;
    }

    private void fieldAssignedScreem(Field field) {
	Object currentValue = on(subject).get(field);

	if (currentValue != null) {
	    throw new IllegalStateException("Field " + field.getName()
		    + " in object " + subject + " already has a value: "
		    + currentValue);
	}
    }

    private boolean isInjectable(Field field) {
	return field.getAnnotation(Inject.class) != null;
    }

    private boolean isMatchType(Field field, Object type) {
	return field.getType().isAssignableFrom(type.getClass());
    }

    /**
     * {@inheritDoc}
     */
    public Injector<T> injectWith(Class<? extends Annotation> qualifier,
	    Object stuff) {
	if (!injectFields(qualifier, stuff) && !injectMethods(qualifier, stuff)) {
	    throw new IllegalStateException("Cannot inject " + stuff
		    + ": no sutiable field/method found in " + subject);
	}
	return this;
    }

    private boolean injectMethods(Class<? extends Annotation> qualifier,
	    Object stuff) {
	for (Method method : subject.getClass().getDeclaredMethods()) {
	    if (isInjectable(method) && isMatchType(method, stuff)
		    && isQualifierMatch(qualifier, method)) {

		methodCalledScreem(method);

		on(subject).call(method, stuff);
		methodValue.put(method, stuff);

		return true;
	    }
	}
	return false;
    }

    private void methodCalledScreem(Method method) {
	if (methodValue.containsKey(method)) {
	    throw new IllegalStateException("Method " + method.getName()
		    + " in object " + subject + " already called with value: "
		    + methodValue.get(method));

	}
    }

    private boolean isQualifierMatch(Class<? extends Annotation> qualifier,
	    Method method) {
	return qualifier == null
		|| method.getParameterTypes().length == 1
		&& isQualifierMatch(qualifier,
			method.getParameterAnnotations()[0]);
    }

    private boolean isQualifierMatch(Class<? extends Annotation> qualifier,
	    Annotation[] annotations) {
	for (Annotation annotation : annotations) {
	    if (annotation.annotationType().equals(qualifier)) {
		return true;
	    }
	}
	return false;
    }

    private boolean isMatchType(Method method, Object stuff) {
	return method.getParameterTypes().length == 1
		&& method.getParameterTypes()[0].isAssignableFrom(stuff
			.getClass());
    }

    private boolean isInjectable(Method method) {
	return method.getAnnotation(Inject.class) != null;
    }

}
