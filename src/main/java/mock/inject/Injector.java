package mock.inject;

import java.lang.annotation.Annotation;

/**
 * 
 * Inject {@code @Inject} annotated elements.
 * 
 * @author frode
 * 
 * @param <T>
 *            the type that contains the @Inject annotations.
 */
public interface Injector<T> {

    /**
     * Inject fields and methods.
     * 
     * @param stuff
     *            the stuff to inject.
     * @return the Injector..
     */
    Injector<T> inject(Object... stuff);

    /**
     * Inject fields and methods matching a qualifier.
     * 
     * @param qualifier
     *            the qualifier to inject the stuff to.
     * @param stuff
     *            the stuff to inject.
     * @return the Injector.
     */
    Injector<T> injectWith(Class<? extends Annotation> qualifier, Object stuff);

}
