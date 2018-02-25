/**
 * Created on 07/11/17
 * File: TestController.java
 *
 * @author Anna Nystedt, id14ant
 */

/**
 * Class representing the controller. Handles all the communication
 * between the view and the model. Gets information from the view
 * about actions performed by the user, and directs the model to
 * perform calculations based to these actions. The controller then
 * forward the result from the calculations to the view, so it can
 * be presented to the user through the GUI.
 */

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class TestController{

    private TestModel model;
    private TestView view;
    private boolean hasRunTests = false;

    /**
     * Creates an object with a view and a model according to the
     * given parameters and initializes the button listeners.
     * @param view the view of the program responsible for the GUI.
     * @param model the model of the program responsible for
     *              calculations.
     */
    public TestController(TestView view, TestModel model){
        this.view = view;
        this.model = model;

        setRunListener();
        setClearListener();
    }

    /**
     * Adds a listener to the run button and listen for an action.
     */
    private void setRunListener(){

        ActionListener runListener = click -> {

            String input = view.getInput();

        view.writeOutput(input +"\n");

            /**
             * Class that handles the additional thread that does all
             * the necessary calculations.
             */
            class worker extends SwingWorker<List<TestResultItem>, Void>{

                /**
                 * Performs the actions connected to the model so
                 * they are not executed on the EDT.
                 * @return the list containing the results from the
                 * tests.
                 */
                @Override
                protected List<TestResultItem> doInBackground(){

                    List<TestResultItem> results;
                    results = model.startTests(input);
                    hasRunTests = model.hasRunTests();

                    return results;
                }

                /**
                 * Converts the list of results into readable text
                 * and presents it to the user.
                 */
                @Override
                protected void done(){
                    try {
                        List<TestResultItem> results = get();

                        getResults(results);

                        //Checks if the model has run any tests.
                        if(hasRunTests){
                            view.writeOutput(summarizeResults(results).toString());
                        }

                        view.writeOutput("**********\n");

                    } catch (InterruptedException e) {
                        view.writeOutput("Thread generated a"
                                + e.getCause().toString() + "\n");
                    } catch (ExecutionException e) {
                        view.writeOutput("Thread generated a"
                                + e.getCause().toString() + "\n");
                    }
                }
            }

            new worker().execute();

        };

        view.setRunButtonListener(runListener);
    }

    /**
     * Adds a listener to the clear button and listen for an action.
     */
    private void setClearListener(){

        ActionListener clearListener = click -> view.clearText();
        view.setClearButtonListener(clearListener);
    }

    /**
     * Sends the results to the view so they can be presented to the
     * user
     * @param results the results from the tests.
     */
    private void getResults(List<TestResultItem> results){

        //Write the result in the view for every test that has been
        //run.
        for(TestResultItem item:results){
            view.writeOutput(item.toString() +
                    "\n");
        }
    }

    /**
     * Sums up the results by going through the list of results and
     * count the number of succeeded tests, failed tests and test
     * that generated an exception.
     * @param results the list containing the results.
     * @return the summarized results of the tests.
     */
    private Summary summarizeResults(List<TestResultItem> results){

        Summary sum = new Summary();

        //Goes through all the results and checks if the current test
        //has succeeded or failed. If the test has failed it checks
        //if it was due to an exception or not.
        for(TestResultItem item: results){
            if(item.getHasSucceeded() != null){
                if(item.getHasSucceeded()){
                    sum.incrementSucceeded();
                } else{
                    if(item.getThrownException() != null){
                        sum.incrementFailedByException();
                    } else{
                        sum.incrementFailed();
                    }
                }
            }
        }

        return sum;
     }
}