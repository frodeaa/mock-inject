package mock.inject;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Inject;
import javax.inject.Qualifier;

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

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER,
	    ElementType.TYPE })
    @Qualifier
    static @interface KindA {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER,
	    ElementType.TYPE })
    @Qualifier
    static @interface KindB {

    }

    static class InjectTwoKinds {

	@Inject
	@KindA
	private Dep depKindA;

	@Inject
	@KindB
	private Dep depKindB;

    }

    @Test
    public void testInjectOnlyQualifierMatch() {

	Dep dep = new Dep();
	InjectTwoKinds subject = new InjectTwoKinds();

	new PlainInjector<InjectTwoKinds>(subject).injectWith(KindA.class, dep);

	assertSame("Field not injected into ", dep, subject.depKindA);
	assertNull("Field injected into ", subject.depKindB);

    }

    @Test
    public void testInjectTwoQualifierMatch() {

	Dep depKindA = new Dep();
	Dep depKindB = new Dep();
	InjectTwoKinds subject = new InjectTwoKinds();

	new PlainInjector<InjectTwoKinds>(subject).injectWith(KindA.class,
		depKindA).injectWith(KindB.class, depKindB);

	assertSame("Field not injected into ", depKindA, subject.depKindA);
	assertSame("Field not injected into ", depKindB, subject.depKindB);

    }

    static class InjectTwoSameKind {

	@Inject
	@KindA
	private Dep depKindA1;

	@Inject
	@KindA
	private Dep depKindA2;

    }

    @Test
    public void testInjectTwoSameKind() {

	Dep depKindA = new Dep();
	InjectTwoSameKind subject = new InjectTwoSameKind();

	new PlainInjector<InjectTwoSameKind>(subject).injectWith(KindA.class,
		depKindA);

	assertSame("Field not injected into ", depKindA, subject.depKindA1);
	assertSame("Field not injected into ", depKindA, subject.depKindA2);

    }

    static class SubDep extends Dep {

    }

    @Test
    public void testInjectSubClass() {

	SubDep subDep = new SubDep();
	InjectSubject subject = new InjectSubject();

	new PlainInjector<InjectSubject>(subject).inject(subDep);

	assertSame("Field not injected", subDep, subject.dep);

    }

    @Test(expected = IllegalStateException.class)
    public void testFailInjectSameFieldTwice() {

	Dep dep = new Dep();
	InjectSubject subject = new InjectSubject();

	new PlainInjector<InjectSubject>(subject).inject(dep, dep);

    }

    @Test(expected = IllegalStateException.class)
    public void testFailInjectSameFieldTwiceChain() {

	Dep dep = new Dep();
	InjectSubject subject = new InjectSubject();

	new PlainInjector<InjectSubject>(subject).inject(dep).inject(dep);

    }

    @Test(expected = IllegalStateException.class)
    public void testFailInjectSameFieldTwiceDifferentDep() {

	Dep dep1 = new Dep();
	Dep dep2 = new Dep();
	InjectSubject subject = new InjectSubject();

	new PlainInjector<InjectSubject>(subject).inject(dep1, dep2);
    }

    @Test(expected = IllegalStateException.class)
    public void testFailInjectSameFieldTwiceDifferentDepChain() {

	Dep dep1 = new Dep();
	Dep dep2 = new Dep();
	InjectSubject subject = new InjectSubject();

	new PlainInjector<InjectSubject>(subject).inject(dep1).inject(dep2);
    }

    @Test(expected = IllegalStateException.class)
    public void testFailInjectFieldsNoMatch() {

	InjectSubject subject = new InjectSubject();

	new PlainInjector<InjectSubject>(subject).inject(new Integer(0));

    }

    @Test(expected = IllegalStateException.class)
    public void testFailInjectSameFieldWithQualifierTwice() {

	Dep depKindA1 = new Dep();
	Dep depKindA2 = new Dep();
	InjectTwoKinds subject = new InjectTwoKinds();

	new PlainInjector<InjectTwoKinds>(subject).injectWith(KindA.class,
		depKindA1).injectWith(KindA.class, depKindA2);
    }

}
