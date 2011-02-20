package mock.inject;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

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

}
