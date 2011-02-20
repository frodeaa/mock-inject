package mock.inject;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.lang.reflect.Field;

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

	Integer one = new Integer(1);

	Reflect<Integer> reflect = Reflect.on(one);

	assertSame("Reflected unknown", one, reflect.reflected());

    }

    @Test
    public void testGetFieldValue() throws SecurityException,
	    NoSuchFieldException {

	FieldObject object = new FieldObject();
	object.field = "field-value";
	Field field = object.getClass().getDeclaredField("field");

	Object fieldValue = Reflect.on(object).get(field);

	assertSame("Field value", "field-value", fieldValue);

    }

    @Test(expected = RuntimeException.class)
    public void testFailGetFieldValue() throws SecurityException,
	    NoSuchFieldException {

	FieldObject object = new FieldObject();
	Field otherField = Other.class.getDeclaredField("field");

	Reflect.on(object).get(otherField);

    }

    static class FieldObject {
	private String field;
    }

    static class Other {
	private String field;
    }

}
