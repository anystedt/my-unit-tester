/**
 * Created on 10/11/17
 * File: Test2.java
 *
 * @author Anna Nystedt, id14ant
 */

/**
 * Class that can be used to show that the program works as intended.
 */

import java.util.ArrayList;
import java.util.List;

public class Test2 implements TestClass {

    public Test2() {
    }

    private List<Integer> list;

    public void setUp(){ list = new ArrayList<>(); }

    public void tearDown(){ list = null; }

    //Test that should succeed
    public boolean testAdd(){
        list.add(1);
        return list.size()==1;
    }

    //Test that should succeed
    public boolean testClear(){
        list.add(1);
        list.clear();
        return list.size()==0;
    }

    //Test that should throw an exception
    public boolean testIndexOutOfBoundException(){
        list.get(1);
        return true;
    }

    //Test that should throw an exception
    public boolean testNullPointerException(){
        list =null;
        list.remove(0);
        return true;

    }

    //Test that should fail
    public boolean testFailing(){
        return false;
    }
}