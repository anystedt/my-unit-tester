/**
 * Created on 08/11/17
 * File: Summary.java
 *
 * @author Anna Nystedt, id14ant
 */

/**
 * Class that handle the summation of the test results.
 */

public class Summary {

    int succeeded;
    int failed;
    int failedByException;

    /**
     * Creates an object containing three integers that is used to
     * count the number of succeeded tests, failed tests, and tests
     * failed due to an exception.
     */
    public Summary(){
        succeeded = 0;
        failed = 0;
        failedByException = 0;
    }

    /**
     * Increments the number of succeeded tests with one.
     */
    public void incrementSucceeded(){ succeeded++; }

    /**
     * Increments the number of failed tests with one
     */
    public void incrementFailed(){ failed++; }

    /**
     * Increments the number of failed tests due to an exception
     * with one.
     */
    public void incrementFailedByException(){ failedByException++; }

    /**
     * Returns a readable string of the summarized test results.
     * @return the string of the summarized test results.
     */
    public String toString(){
        return "\n" + succeeded + " tests succeeded \n" +
                failed + " tests failed\n" + failedByException +
                " tests failed because of an exception\n\n";
    }
}
