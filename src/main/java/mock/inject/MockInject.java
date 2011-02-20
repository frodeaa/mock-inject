package mock.inject;

import java.lang.annotation.Annotation;

/**
 * Simple "CDI" injection of {@code @Inject} annotated fields and single
 * parameter methods, should probably just be used when testing.
 * 
 * <pre>
 * import static mock.inject.MockInject.into;
 * 
 *   &#64;Test
 *   public void test() {
 *  
 *    into( bean )
 *     .inject( mockA )
 *     .injectWith( KindB.class, mockB );
 * 
 *    // test the bean
 *  }
 *  
 * class Bean {
 *      &#64;Inject 
 *      private A a;
 *      
 *      private A b;
 *   
 *      &#64;Inject
 *      public setB( &#64;KindB A b ){...}
 * }
 * </pre>
 * 
 * @author frode
 * 
 */
public class MockInject<T> implements Injector<T> {

    private final Injector<T> injector;

    /**
     * Inject fields and single parameter method
     * 
     * @param <T>
     *            the type to inject into.
     * @param subject
     *            the instance to inject stuff into.
     * @return new Injector.
     */
    public static <T> Injector<T> into(T subject) {
	return new MockInject<T>(new PlainInjector<T>(subject));
    }

    private MockInject(Injector<T> injector) {
	this.injector = injector;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Injector<T> inject(Object... stuff) {
	return injector.inject(stuff);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Injector<T> injectWith(Class<? extends Annotation> qualifier,
	    Object stuff) {
	return injector.injectWith(qualifier, stuff);
    }

}
