/**
 * Created on 07/11/17.
 * File: UnitTester.java
 *
 * @author Anna Nystedt, id14ant
 */


/**
 * Main for MyUnitTester that makes the settings for the program.
 * Creates the view, the model and the controller that is used when
 * the program is running.
 */

import javax.swing.SwingUtilities;

public class MyUnitTester {
    public static void main(String []argv) {

        TestModel model = new TestModel();

        SwingUtilities.invokeLater(() -> {
            TestView view = new TestView();
            new TestController(view, model);
        });
    }
}