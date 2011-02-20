package mock.inject;

import static mock.inject.MockInject.into;
import static org.junit.Assert.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Inject;
import javax.inject.Qualifier;

import org.junit.Test;

/**
 * Test {@link MockInject}.
 * 
 * @author frode
 * 
 */
public class MockInjectTest {

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER,
	    ElementType.TYPE })
    @Qualifier
    static @interface KindA {

    }

    static class Subject {

	@Inject
	private Dep dep;

	private Dep methodDep;

	@Inject
	public void setMethodDep(@KindA Dep dep) {
	    this.methodDep = dep;
	}

    }

    static class Dep {
    }

    @Test
    public void testInto() {

	assertNotNull("Injector null", MockInject.into(new Subject()));

    }

    @Test
    public void testInjectFieldNotMethod() {

	Dep dep = new Dep();
	Subject subject = new Subject();

	MockInject.into(subject).inject(dep);

	assertSame("Field not injected", dep, subject.dep);
	assertNull("Method injected", subject.methodDep);

    }

    @Test
    public void testInjectWithQualifier() {

	Dep dep = new Dep();
	Subject subject = new Subject();

	into(subject).inject(dep).injectWith(KindA.class, dep);

	assertSame("Field not injected", dep, subject.dep);
	assertSame("Method not injected", dep, subject.methodDep);

    }
}
