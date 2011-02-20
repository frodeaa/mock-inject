package mock.inject;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Test {@link Reflect}.
 * 
 * @author frode
 * 
 */
public class ReflectTest {

    @Test
    public void testOn() {

	assertNotNull("New Reflect null", Reflect.on(null));

    }

}
