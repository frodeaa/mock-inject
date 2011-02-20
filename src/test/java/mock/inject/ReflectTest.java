package mock.inject;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;

/**
 * Test {@link Reflect}.
 * 
 * @author frode
 * 
 */
public class ReflectTest {

    @Test
    public void testOnNeverNull() {

	assertNotNull("New Reflect null", Reflect.on(null));

    }

    @Test
    public void testReflected() {

	Integer subject = new Integer(1);

	Reflect<Integer> reflect = Reflect.on(subject);

	assertSame("Reflected unknown", subject, reflect.reflected());

    }

    @Test
    public void testGetFieldValue() throws SecurityException,
	    NoSuchFieldException {

	FieldObject subject = new FieldObject();
	subject.field = "field-value";
	Field field = subject.getClass().getDeclaredField("field");

	Object fieldValue = Reflect.on(subject).get(field);

	assertSame("Field value", "field-value", fieldValue);

    }

    @Test(expected = RuntimeException.class)
    public void testFailGetFieldValue() throws SecurityException,
	    NoSuchFieldException {

	FieldObject subject = new FieldObject();
	Field otherField = Other.class.getDeclaredField("field");

	Reflect.on(subject).get(otherField);

    }

    static class FieldObject {
	private String field;
    }

    static class Other {
	@SuppressWarnings("unused")
	private String field;
    }

    @Test
    public void testSet() throws SecurityException, NoSuchFieldException {

	FieldObject subject = new FieldObject();
	Field field = subject.getClass().getDeclaredField("field");

	Reflect.on(subject).set(field, "new-value");

	assertSame("Field value", "new-value", subject.field);
    }

    @Test(expected = RuntimeException.class)
    public void testFailSet() throws SecurityException, NoSuchFieldException {

	FieldObject subject = new FieldObject();
	Field otherField = Other.class.getDeclaredField("field");

	Reflect.on(subject).set(otherField, "new-value");

    }

    @Test
    public void testCall() throws SecurityException, NoSuchMethodException {

	MethodObject subject = new MethodObject();
	Method method = subject.getClass().getDeclaredMethod("method",
		String.class);
	String param = "a-param";

	Reflect.on(subject).call(method, param);

	assertSame("Param not updated", param, subject.param);
    }

    @Test(expected = RuntimeException.class)
    public void testCallFail() throws SecurityException, NoSuchMethodException {
	MethodObject subject = new MethodObject();
	Method method = OtherMethod.class.getDeclaredMethod("method",
		String.class);
	String param = "a-param";

	Reflect.on(subject).call(method, param);
    }

    static class MethodObject {
	private String param;

	@SuppressWarnings("unused")
	private void method(String param) {
	    this.param = param;
	}

    }

    static class OtherMethod {

	@SuppressWarnings("unused")
	private void method(String param) {
	}

    }

}
