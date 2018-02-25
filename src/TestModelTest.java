/**
 * Created on 07/11/17
 * File: TestModelTest.java
 *
 * @author Anna Nystedt, id14ant
 */

import org.junit.Before;
import org.junit.Test;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Tests that all the testable methods in TestModel returns the
 * expected value. When needed the class Test1 and TestResultItem
 * is used for evaluating the methods.
 */
public class TestModelTest{

    private TestModel model;
    private Class<?> test;
    private List<TestResultItem> results;

    @Before
    public void setUp() throws ClassNotFoundException {
        model = new TestModel();
        test = Class.forName("Test1");
        results = new ArrayList<>();
    }

    /**
     * Tests that the model returns five test results when Test1 is
     * written in the input field.
     */
    @Test
    public void startTestShouldReturnFiveResults(){
        assertEquals(model.startTests("Test1").size(), 5);
    }

    /**
     * Tests that the correct exception is caught and returned in a list.
     */
    @Test
    public void isValidTestClassShouldReturnClassException(){
        results = model.startTests("Hej");
        assertEquals(results.get(0).getThrownException(),
                "Class Not Found Exception\n");
    }

    /**
     * Tests that the correct exception is caught and returned in a list.
     */
    @Test
    public void isValidTestClassShouldReturnInstantiationException(){
        results = model.startTests("TestClass");
        assertEquals(results.get(0).getThrownException(),
                "InstantiationException\n");
    }

    /**
     * Tests that the method isASetUp returns true when the method
     * name is setUp.
     * @throws NoSuchMethodException if the method do not exist
     */
    @Test
    public void isASetUpShouldReturnTrue() throws
            NoSuchMethodException {
        Method setUp = test.getDeclaredMethod("setUp");
        assertTrue(model.isASetUp(setUp));
    }

    /**
     * Tests thar the method isASetUp returns false when the method
     * name is not setUp.
     * @throws NoSuchMethodException if the method do not exist
     */
    @Test
    public void isASetUpShouldReturnFalse() throws
            NoSuchMethodException {
        Method setUp = test.getDeclaredMethod("tearDown");
        assertFalse(model.isASetUp(setUp));
    }

    /**
     * Tests that the method isATearDown returns true when the method
     * name is tearDown.
     * @throws NoSuchMethodException if the method do not exist
     */
    @Test
    public void isATearDownShouldReturnTrue() throws
            NoSuchMethodException {
        Method tearDown = test.getDeclaredMethod("tearDown");
        assertTrue(model.isATearDown(tearDown));
    }

    /**
     * Tests that the method isATearDown returns false if the method
     * name is not tearDown.
     * @throws NoSuchMethodException if the method do not exist
     */
    @Test
    public void isATearDownShouldReturnFalse() throws
            NoSuchMethodException {
        Method tearDown = test.getDeclaredMethod("setUp");
        assertFalse(model.isATearDown(tearDown));
    }

    /**
     * Tests that the method isABoolean returns true if the given
     * method return a boolean.
     * @throws NoSuchMethodException if the method do not exist
     */
    @Test
    public void isABooleanShouldReturnTrue() throws
            NoSuchMethodException {
        Method increment = test.getDeclaredMethod("testIncrement");
        assertTrue(model.isABoolean(increment));
    }

    /**
     * Tests that the method isABoolean returns false when the given
     * method do not return a boolean.
     * @throws NoSuchMethodException if the method do not exist
     */
    @Test
    public void isABooleanShouldReturnFalse() throws
            NoSuchMethodException {
        Method setUp = test.getDeclaredMethod("setUp");
        assertFalse(model.isABoolean(setUp));
    }

    /**
     * Tests that the method takesNoArguments returns true when the
     * given method do not take any arguments.
     * @throws NoSuchMethodException if the method do not exist
     */
    @Test
    public void takesNoArgumentShouldReturnTrue() throws
            NoSuchMethodException {
        Method setUp = test.getDeclaredMethod("setUp");
        assertTrue(model.takesNoArguments(setUp));
    }

    /**
     * Tests that the method takesNoArguments returns false when the
     * given method takes one or several arguments.
     * @throws ClassNotFoundException if the class cannot be found
     * @throws NoSuchMethodException if the method do not exist
     */
    @Test
    public void takesNoArgumentShouldReturnFalse() throws
            ClassNotFoundException, NoSuchMethodException {
        Class<?> item = Class.forName("TestResultItem");
        Method method = item.getDeclaredMethod("setMethodName", String.class);
        assertFalse(model.takesNoArguments(method));
    }

    /**
     * Tests that the method isATestMethod returns true when the
     * name of the given method starts with "test".
     * @throws NoSuchMethodException if the method do not exist
     */
    @Test
    public void isATestMethodShouldReturnTrue() throws
            NoSuchMethodException {
        Method method = test.getDeclaredMethod("testDecrement");
        assertTrue(model.isATestMethod(method));
    }

    /**
     * Tests that the method isATestMethod returns false when the
     * name of the given method do not start with "test".
     * @throws NoSuchMethodException if the method do not exist
     */
    @Test
    public void isATestMethodShouldReturnFalse() throws
            NoSuchMethodException {
        Method method = test.getDeclaredMethod("setUp");
        assertFalse(model.isATestMethod(method));
    }

    /**
     * Tests that the method createExceptionMessage returns an item
     * containing the given exception message.
     */
    @Test
    public void createExceptionMessageShouldReturnMessage(){
        TestResultItem item = model.createExceptionMessage("Message");
        assertEquals(item.getThrownException(), "Message");
    }
}