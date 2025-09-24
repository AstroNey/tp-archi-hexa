package usecases;

import lni.archi.hexa.core.domain.PersonDN;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    public static void testPerson(PersonDN expected, PersonDN result) {
        assertEquals(expected, result);
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getFirstName(), result.getFirstName());
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getAge(), result.getAge());
    }
}
