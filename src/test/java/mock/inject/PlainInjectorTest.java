package mock.inject;

import static org.junit.Assert.assertSame;

import javax.inject.Inject;

import org.junit.Test;

/**
 * Test {@link PlainInjector}.
 * 
 * @author frode
 * 
 */
public class PlainInjectorTest {

    static class Dep {
    }

    static class InjectSubject {
	@Inject
	private Dep dep;
    }

    @Test
    public void testInjectField() {

	Dep dep = new Dep();
	InjectSubject subject = new InjectSubject();

	new PlainInjector<InjectSubject>(subject).inject(dep);

	assertSame("Dep not injected", dep, subject.dep);
    }

}
