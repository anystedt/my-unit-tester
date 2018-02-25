/**
 * Created on 08/11/17
 * File: TestResultItem.java
 *
 * @author Anna Nystedt, id14ant
 */

/**
 * Class helping the model and controller to keep track of
 * information about the executed test methods. Stores the name of
 * the test, if it has succeeded or failed and if the method
 * threw an exception during execution.
 */

public class TestResultItem {

    private String methodName;
    private Boolean hasSucceeded;
    private String thrownException;

    public TestResultItem(){}

    /**
     * Sets the name of the method.
     * @param methodName the name of the method
     */
    public void setMethodName(String methodName){
        this.methodName = methodName;
    }

    /**
     * Sets the value for the parameter hasSucceeded to true or false.
     * @param hasSucceeded the status of the test. True if the test
     *                     succeeded and false otherwise.
     */
    public void setHasSucceeded(Boolean hasSucceeded){
        this.hasSucceeded = hasSucceeded;
    }

    /**
     * Sets the name of the thrown exception.
     * @param thrownException
     */
    public void setThrownException(String thrownException){
        this.thrownException = thrownException;
    }

    /**
     * Returns true if the test has succeeded false otherwise
     * @return true if the test has succeeded false otherwise
     */
    public Boolean getHasSucceeded(){ return hasSucceeded;}

    /**
     * Returns the exception that has been thrown during the test.
     * @return the exception that has been thrown during the test.
     */
    public String getThrownException(){ return thrownException;}

    /**
     * Returns a string representation of the object that is readable
     * for the user.
     * @return a string representation of the object.
     */
    public String toString(){

        if(methodName != null){
            String info = methodName + ": " + getTestResult();
            if(thrownException != null){
                info = info + " Generated a " + thrownException;
            }

            return info;
        } else{
            return "Generated a " + thrownException;
        }

    }

    /**
     * Return a converted version of the boolean hasSucceeded.
     * Converts true to SUCCESS and false to FAIL.
     * @return a string describing if the test succeeded or failed.
     */
    public String getTestResult(){
        if(hasSucceeded){
            return "SUCCESS";
        }
        else{
            return "FAIL";
        }
    }
}