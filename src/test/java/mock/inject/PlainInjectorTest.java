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

    static class OtherDep {
    }

    static class InjectTwo {

	@Inject
	private Dep dep;

	@Inject
	private OtherDep otherDep;

    }

    @Test
    public void testInjectTwoFields() {

	Dep dep = new Dep();
	OtherDep otherDep = new OtherDep();
	InjectTwo subject = new InjectTwo();

	new PlainInjector<InjectTwo>(subject).inject(dep, otherDep);

	assertSame("Field not injected into ", dep, subject.dep);
	assertSame("Field not injected into ", otherDep, subject.otherDep);

    }

    @Test
    public void testInjectTwoFieldsChain() {

	Dep dep = new Dep();
	OtherDep otherDep = new OtherDep();
	InjectTwo subject = new InjectTwo();

	new PlainInjector<InjectTwo>(subject).inject(dep).inject(otherDep);

	assertSame("Field not injected into ", dep, subject.dep);
	assertSame("Field not injected into ", otherDep, subject.otherDep);

    }

}
