package mock.inject;

import java.lang.annotation.Annotation;

/**
 * Inject objects on @Inject annotated fields and single parameter methods.
 * 
 * @author frode
 * 
 * @param <T>
 *            the type to apply injection on.
 */
public class PlainInjector<T> implements Injector<T> {

    public Injector<T> inject(Object... stuff) {
	return null;
    }

    public Injector<T> injectWith(Class<? extends Annotation> qualifier,
	    Object stuff) {
	return null;
    }

}
