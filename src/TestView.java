/**
 * Created on 07/11/17
 * File: TestView.java
 *
 * @author Anna Nystedt, id14ant
 */

/**
 * Class representing the view. Creates the applications GUI that
 * handles the communication between the user and the rest of the
 * program. Receives actions performed by the user and pass these to
 * the controller. When the given action has been performed by
 * other parts of the program, the view gives the user appropriate
 * feedback through the GUI.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TestView {

    private JTextField inputField;
    private JButton runButton;
    private JButton clearButton;
    private JTextArea outputArea;

    /**
     * Creates and initiates all the necessary parts of the GUI.
     */
    public TestView(){

        // Create the frame and initializes it.
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //Add a top and a mid panel with necessary components to the frame.
        frame.add(createTopPanel(), BorderLayout.PAGE_START);
        frame.add(createMidPanel(), BorderLayout.CENTER);

        //Add a clear button to the frame.
        clearButton = new JButton("Clear");
        frame.add(clearButton, BorderLayout.PAGE_END);

        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Creates the top panel of the GUI that contains a input field
     * where the user can enter a class name and a button to start
     * the tests.
     * @return The panel that is placed at the top of the frame.
     */
    private JPanel createTopPanel(){

        // Create the top panel and add text field and button to it.
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,2));

        inputField= new JTextField();
        runButton = new JButton("Run tests");

        panel.add(inputField);
        panel.add(runButton);

        return panel;
    }

    /**
     * Creates and returns the mid panel of the GUI that contains
     * a scrollable  text area where the output will be printed.
     * @return the scrollable area
     */
    private JScrollPane createMidPanel(){

        outputArea = new JTextArea(20, 50);
        outputArea.setEditable(false);

        JScrollPane scrollArea = new JScrollPane(outputArea);

        return scrollArea;
    }

    /**
     * Sets a listener to the run button to be able to know when the
     * button is pressed.
     * @param actionListener the ActionListener attached to the button.
     */
    public void setRunButtonListener(ActionListener actionListener){
        runButton.addActionListener(actionListener);
    }

    /**
     * Sets a listener to the clear button to be able to know when
     * the button is pressed.
     * @param actionListener the ActionListener attached to the button.
     */
    public void setClearButtonListener(ActionListener actionListener){
        clearButton.addActionListener(actionListener);
    }

    /**
     * Returns the input from the input field in the GUI.
     * @return the string that is currently typed in the input field.
     */
    public String getInput(){return inputField.getText();}

    /**
     * Gives the user feedback by presenting the given input through
     * the text area in the programs GUI.
     * @param output the input to be presented to the user.
     */
    public void writeOutput(String output){outputArea.append(output);}

    /**
     * Clear the input field and the text area from text.
     */
    public void clearText(){
        outputArea.setText(null);
        inputField.setText(null);
    }
}