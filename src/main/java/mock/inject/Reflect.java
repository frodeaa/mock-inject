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

}
