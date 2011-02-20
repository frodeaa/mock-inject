package mock.inject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Use reflection on an object.
 * 
 * @author frode
 * 
 * @param <T>
 *            the type of the instance being reflected on.
 */
public class Reflect<T> {

    /**
     * Create a new Reflect
     * 
     * @param <T>
     *            the type to reflect on.
     * @param subject
     *            the subject to call reflections on.
     * @return
     */
    public static <T> Reflect<T> on(T subject) {
	return new Reflect<T>(subject);
    }

    private T subject;

    private Reflect(T subject) {
	this.subject = subject;
    }

    /**
     * @return the instance that is reflected on.
     */
    public T reflected() {
	return subject;
    }

    /**
     * Get the field value.
     * 
     * @param field
     *            the field to get the value of.
     * @return the value of the field.
     */
    public Object get(Field field) {
	
	try {
	    field.setAccessible(true);
	    return field.get(subject);
	} catch (Exception e) {
	    throw new RuntimeException(e);
	}
	
    }

    /**
     * Set field value.
     * 
     * @param field
     *            the field to change.
     * @param value
     *            the value to assign to the field.
     * @return self.
     */
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

    /**
     * Call method with parameter.
     * 
     * @param method
     *            the method to call.
     * @param param
     *            the parameter to call the method with.
     * @return self.
     */
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

}
