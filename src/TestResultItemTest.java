/**
 * Created on 08/11/17
 * File: TestResultItemTest.java
 *
 * @author Anna Nystedt, id14ant
 */

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests that all the methods in TestResultItem returns the expected
 * value.
 */
public class TestResultItemTest {

    private TestResultItem item;

    @Before
    public void setUp(){
        item = new TestResultItem();
    }

    /**
     * Tests that the item contains and return true when the test
     * has succeeded.
     */
    @Test
    public void hasSucceededShouldReturnTrue() {
        item.setHasSucceeded(true);
        assertTrue(item.getHasSucceeded());
    }

    /**
     * Tests that the item contains and return false when the test
     * has failed.
     */
    @Test
    public void hasSucceededShouldReturnFalse(){
        item.setHasSucceeded(false);
        assertFalse(item.getHasSucceeded());
    }

    /**
     * Tests that the item contains and returns the right exception.
     */
    @Test
    public void hasThrownExceptionShouldReturnTrue(){
        item.setThrownException(ClassNotFoundException.class.getName());
        assertEquals("java.lang.ClassNotFoundException", item.getThrownException());
    }

    /**
     * Tests that the item returns the right string when the method
     * toString() is used when a test has succeeeded.
     */
    @Test
    public void toStringSucceededShouldReturnTrue(){
        item.setMethodName("Name");
        item.setHasSucceeded(true);

        assertEquals("Name: SUCCESS", item.toString());
    }

    /**
     * Tests that the item returns the right string when the method
     * toString() is used when a test has failed because of an
     * exception
     */
    @Test
    public void toStringExceptionShouldReturnTrue(){
        item.setMethodName("Name");
        item.setHasSucceeded(false);
        item.setThrownException("java.lang.ClassNotFoundException");

        assertEquals("Name: FAIL Generated a java.lang.ClassNot" +
                "FoundException", item.toString());
    }

    /**
     * Tests that the item returns SUCCESS when the test has succeeded.
     */
    @Test
    public void getTestResultShouldReturnSuccess(){
        item.setHasSucceeded(true);
        assertEquals(item.getTestResult(), "SUCCESS");
    }

    /**
     * Tests that the item returns FAIL when the test has failed.
     */
    @Test
    public void getTestResultShouldReturnFailed(){
        item.setHasSucceeded(false);
        assertEquals(item.getTestResult(), "FAIL");
    }
}