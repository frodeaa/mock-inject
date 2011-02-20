package mock.inject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 
 * @author frode
 * 
 * @param <T>
 *            the type of the instance being reflected on.
 */
public class Reflect<T> {

    public static <T> Reflect<T> on(T subject) {
	return new Reflect<T>(subject);
    }

    private T subject;

    private Reflect(T subject) {
	this.subject = subject;
    }

    public Reflect<T> set(Field field, Object value) {
	try {
	    field.setAccessible(true);
	    field.set(subject, value);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	} finally {
	    field.setAccessible(false);
	}
	return this;
    }

    public Reflect<T> call(Method method, Object param) {
	try {
	    method.setAccessible(true);
	    method.invoke(subject, param);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	} finally {
	    method.setAccessible(false);
	}

	return this;
    }

    public Object get(Field field) {
	try {
	    field.setAccessible(true);
	    return field.get(subject);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
    }

    public T reflected() {
	return subject;
    }

}
