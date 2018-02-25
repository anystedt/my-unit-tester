/**
 * Created on 07/11/17
 * File: TestModel.java
 *
 * @author Anna Nystedt, id14ant
 */

/**
 * Class representing the model. Handles all the calculations that
 * is performed by the program. Meaning that the program checks that
 * the given class is a test class, and in that case gets all the
 * essential method and runs them. When the calculations are done
 * the model forwards the result to the controller which in turn
 * communicates with the view.
 */

import java.lang.reflect.*;
import java.util.*;

public class TestModel {

    private Class<?> testClass;
    private Object obj;
    private Method setUp;
    private Method tearDown;
    private List<Method> testMethods;
    private List<TestResultItem> testResultItems;
    private boolean hasRunTests;

    public TestModel(){}

    /**
     * Tests so the given class is a valid test class, if so gets all
     * the methods and runs them
     * @param input the name of the class
     * @return a list containing the results from the tests.
     */
    public List<TestResultItem> startTests(String input){

        testResultItems = new ArrayList<>();
        hasRunTests = false;

        //If the given class is a valid test class, get the methods
        //and run them.
        if(isValidTestClass(input)){
            getMethods();
            runTests();
            hasRunTests = true;
        }

        return testResultItems;
    }

    /**
     * Returns true if the given class is a test class. Does this by
     * creating a new instance of the class and see if it implements
     * the interface TestClass
     * @param className the name of the given class
     * @return true if the given class  is a valid test class false
     * otherwise
     */
    public boolean isValidTestClass(String className){

        try {
            testClass = Class.forName(className);
            obj = testClass.newInstance();

            if(TestClass.class.isAssignableFrom(testClass)){
                return true;
            }
        } catch (ClassNotFoundException e) {
            testResultItems.add(createExceptionMessage(
                    "Class Not Found Exception\n"));

        } catch (IllegalAccessException e) {
            testResultItems.add(createExceptionMessage(
                    "Illegal Access Exception\n"));
        } catch (InstantiationException e) {
            testResultItems.add(createExceptionMessage(
                    "InstantiationException\n"));
        }

        return false;
    }

    /**
     * Creates and returns at result item containing the given
     * exception message.
     * @param errorMessage the message containing information about
     *                     the thrown exception
     * @return the result item
     */
    public TestResultItem createExceptionMessage(String errorMessage){
        TestResultItem item = new TestResultItem();
        item.setThrownException(errorMessage);

        return item;
    }

    /**
     * Get all the essential methods from the class by using their
     * method name.
     */
    private void getMethods(){
        //Extract the methods from the saved class.
        Method[] methods = testClass.getDeclaredMethods();
        testMethods = new ArrayList<>();

        //Goes through all the methods to find the setUp and tearDown
        //if there is one, and to get all the methods that fulfills
        //the requirements of a test method.
        for(Method m:methods){
            if(isASetUp(m)){
                setUp = m;
            }
            if(isATearDown(m)){
                tearDown = m;
            }
            if(isABoolean(m) && takesNoArguments(m) &&
                    isATestMethod(m)){
                testMethods.add(m);
            }
        }
    }

    /**
     * Returns true if the given method has the name setUp.
     * @param method the method to be checked
     * @return true if the name of the method is setUp false otherwise
     */
    public boolean isASetUp(Method method){
        return method.getName().equals("setUp");
    }

    /**
     * Returns true if the given method has the name tearDown
     * @param method the method to be checked
     * @return true if the name of the method is tearDown false otherwise
     */
    public boolean isATearDown(Method method){
        return method.getName().equals("tearDown");
    }

    /**
     * Returns true if the given method returns a boolean.
     * @param method the method from the test class.
     * @return true if the return type of the method boolean false
     *         otherwise.
     */
    public boolean isABoolean(Method method){
        return method.getReturnType().equals(Boolean.TYPE);
    }

    /**
     * Returns true if the given method takes no arguments.
     * @param method the method from the test class.
     * @return true if the method takes no arguments false otherwise.
     */
    public boolean takesNoArguments(Method method){
        return method.getParameterCount()==0;
    }

    /**
     * Returns true if the name of the given method starts with the
     * phrase "true"
     * @param method the method from the test class.
     * @return true if the name of the method starts with "test"
     *         false otherwise.
     */
    public boolean isATestMethod(Method method){
        return method.getName().startsWith("test");
    }

    /**
     * Runs the setUp method if there is one in the given class.
     */
    private void runSetUp(){
        if(setUp != null){
            try {
                setUp.invoke(obj);
            } catch (IllegalAccessException e) {
                testResultItems.add(createExceptionMessage(
                        "Illegal Access Exception when trying" +
                                " to run setUp"));
            } catch (InvocationTargetException e) {
                testResultItems.add(createExceptionMessage(
                        "Invocation Target Exception when trying" +
                                " to run setUp"));
            }
        }
    }

    /**
     * Runs the test methods in the given class and stores all the
     * results in a list. Start and end every test method by running
     * setUp and tearDown if there is any.
     */
    private void runTests(){

        //Goes through every test method and create a result item for
        //each method containing the name of the method, if it
        //has succeeded and if an exception was thrown.
        for(Method test:testMethods){
            runSetUp();
            TestResultItem method = new TestResultItem();
            method.setMethodName(test.getName());

            try {
                method.setHasSucceeded((Boolean)test.invoke(obj));
            } catch (IllegalAccessException e) {
                method.setHasSucceeded(false);
                method.setThrownException(e.getCause().toString());
            } catch (InvocationTargetException e) {
                method.setHasSucceeded(false);
                method.setThrownException(e.getCause().toString());

            }

            testResultItems.add(method);
            runTearDown();
        }
    }

    /**
     * Runs the tearDown method if there is one in the given class.
     */
    private void runTearDown(){
        if(tearDown != null){
            try {
                tearDown.invoke(obj);
            } catch (IllegalAccessException e) {
                testResultItems.add(createExceptionMessage(
                        "Illegal Access Exception when trying" +
                                " to run tearDown"));
            } catch (InvocationTargetException e) {
                testResultItems.add(createExceptionMessage(
                        "Invocation Target Exception when trying" +
                                " to run tearDown"));
            }
        }
    }

    /**
     * Returns the parameter which holds information about if the
     * model has run any test methods.
     * @return true if it has run test false otherwise
     */
    public boolean hasRunTests(){ return hasRunTests;}
}