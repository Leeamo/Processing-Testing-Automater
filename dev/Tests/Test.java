package Tests;

import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

public abstract class Test {

    protected String markType;
    protected int mark;

    protected Boolean testPassed;

    abstract public boolean runTest(Object sketchObject);

    @Override
    public String toString() {
        return "If you are seeing this you need to overwrite a toString method in this Test class";
    }

    public void setPassed(boolean passed) {

        testPassed = passed;
    }

    public boolean getPassed() {
        return testPassed;
    }

    public int getMark() {
        return mark;
    }
}
