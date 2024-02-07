import Tests.Test;
import Tests.objectCountTest;
import Tests.neededObject;
//import org.apache.tools.ant.taskdefs.PathConvert;
import java.awt.event.MouseEvent;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;

//import processing.app.Sketch;
//import MarchPenguin.Penguin;
import processing.core.*;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class sketchTest {

    private static Class<?> marchPenguinClass;

    public sketchTest(Class<?> myClass) {
        this.marchPenguinClass = myClass;
    }

    public sketchTest(File file) {
        try {
            this.marchPenguinClass = PdeClassLoader.getClassFromFile("MarchPenguin", file.getAbsolutePath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            // Object obj = marchPenguinClass.getDeclaredConstructor().newInstance();

            // Method setupMethod = marchPenguinClass.getMethod("draw");

            // setupMethod.invoke(obj);

            Class penguin = PdeClassLoader.getClassFromFile("MarchPenguin",
                    "\\Users\\neilp\\IdeaProjects\\TestingAutomater\\dev\\MarchPenguin.java");

            Object obj = penguin.getDeclaredConstructor().newInstance();

            Method setupMethod = penguin.getMethod("setup");

            // Method drawMethod = penguin.getMethod("draw");

            setupMethod.invoke(obj);

            System.out.println(countObject(obj, "Penguin"));

            // drawMethod.invoke(obj);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Class.getFromString(String input)

        // PApplet.runSketch(new String[]{"--location=0,0", ""}, sketch);

    }

    public static boolean runTest(Test test) {

        boolean result = false;

        try {
            if (test.getClass().equals(objectCountTest.class))
                result = countObject((objectCountTest) test);
            else if (test.getClass().equals(neededObject.class))
                result = neededObject((neededObject) test);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static Object getObject() {
        try {
            return marchPenguinClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public static void runMethod(Object obj, String method) {

        Method setupMethod = null;
        try {
            setupMethod = obj.getClass().getDeclaredMethod("setup");
            setupMethod.setAccessible(true);
            setupMethod.invoke(obj);

            // System.out.println(countObject(obj, "Penguin"));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        // Method drawMethod = penguin.getMethod("draw");

    }

    public static boolean countObject(objectCountTest test) throws IllegalAccessException {

        Object sketch = getObject();

        runMethod(sketch, "setup");

        Field[] fields = sketch.getClass().getDeclaredFields();
        // fields is a list of all the fields of this class

        int objCount = 0;

        for (Field f : fields) {
            f.setAccessible(true);

            Object value = f.get(sketch);

            if ((value.getClass().getSimpleName().equals(test.getClassType())))
                objCount++;

            else if (value.getClass().isArray()) {

                Object[] values = (Object[]) value;
                for (Object o : values) {

                    // System.out.println(o.getClass().getSimpleName());

                    if ((o.getClass().getSimpleName().equals(test.getClassType())))
                        objCount++;

                    // System.out.println(o); // i don't know why these are null
                }
            }

        }

        return objCount == test.getCount();
    }

    public static int countObject(Object sketch, String classType) throws IllegalAccessException {

        Field[] fields = sketch.getClass().getDeclaredFields();
        // fields is a list of all the fields of this class

        int objCount = 0;

        for (Field f : fields) {

            Object value = f.get(sketch);

            if (value.getClass().getSimpleName().equals(classType)) {
                objCount++;
            } else if (value.getClass().isArray()) {

                Object[] values = (Object[]) value;
                for (Object o : values) {

                    // System.out.println(o.getClass().getSimpleName());

                    if (o.getClass().getSimpleName().equals(classType))
                        objCount++;

                    // System.out.println(o); // i don't know why these are null
                }
            }

        }

        return objCount;
    }

    // returns first object instance of this class
    public static Object returnObject() {
        return null;
    }

    // todo
    public static boolean objectPos(String classType, float x, float y) throws IllegalAccessException {
        return false;
    }

    // for specific syntax neededObject: [Object[Object]]
    public static boolean neededObject(neededObject test) throws IllegalAccessException {

        Object sketch = getObject();

        runMethod(sketch, "setup");

        Field[] fields = sketch.getClass().getDeclaredFields();
        // fields is a list of all the fields of this class

        for (Field f : fields) {

            f.setAccessible(true);
            Object value = f.get(sketch);

            if ((value.getClass().getSimpleName().equals(test.getClassType()))) {
                Field[] innerFields = value.getClass().getDeclaredFields();
                for (Field g : innerFields) {
                    g.setAccessible(true);
                    Object innerObject = g.get(f);
                    if ((innerObject.getClass().getSimpleName().equals(test.getObjectType()))) {
                        return true;
                    }
                }
            } else if (value.getClass().isArray()) {
                Object[] values = (Object[]) value;
                boolean isTrue = false;
                for (Object o : values) {
                    if ((o.getClass().getSimpleName().equals(test.getClassType()))) {
                        Field[] innerFields = o.getClass().getDeclaredFields();
                        for (Field g : innerFields) {
                            g.setAccessible(true);
                            Object innerObject = g.get(o);
                            if ((innerObject.getClass().getSimpleName().equals(test.getObjectType()))) {
                                isTrue = true;
                            }
                        }
                        if (!isTrue) {
                            return test.setResult(false);
                        }
                    }
                }
                return test.setResult(true);
            }
        }
        return test.setResult(false);
    }

    // public static boolean mousePressedTest(int x, int y) throws AWTException {
    // MarchPenguin sketch = new MarchPenguin();
    // sketch.setup();
    // String[] appletArgs = new String[] { "MarchPenguin" };
    // PApplet.runSketch(appletArgs, sketch);
    // PointerInfo inf = MouseInfo.getPointerInfo();
    // Point p = inf.getLocation();
    //
    // Robot fakePress = new Robot();
    // fakePress.mouseMove(x, y);
    // for (int i = 0; i < sketch.colony.length; i++) {
    //
    // int textx = Math.round(sketch.colony[i].x);
    // int testy = Math.round(sketch.colony[i].y);
    // sketch.mouseX = textx;
    // sketch.mouseY = testy;
    //
    // // fakePress.mouseMove(textx, testy);
    // fakePress.mousePress(InputEvent.BUTTON1_DOWN_MASK);
    // fakePress.delay(100);
    // if (sketch.colony[i].isSelected == true) {
    //
    // System.out.println("Its been pressed");
    // System.out.println("this is sx" + sketch.mouseX);
    // System.out.println("this is sy" + sketch.mouseY);
    // return true;
    //
    // } else {
    // System.out.println("not pressing");
    // System.out.println("this is sx" + sketch.mouseX);
    // System.out.println("this is sy" + sketch.mouseY);
    //
    // }
    //
    // }
    // sketch.exit();
    // return false;
    //
    // // MouseEvent fakeClick = new MouseEvent(t, MouseEvent.MOUSE_CLICKED,
    // // System.currentTimeMillis(), 0, x, y, 1, false);
    // // EventQueue eventQueue = Toolkit.getDefaultToolkit().getSystemEventQueue();
    // // eventQueue.postEvent(fakeClick);
    //
    // // return false;
    // }

    public static void runSetup(Object penguin) {

        Method setupMethod = null;
        try {
            setupMethod = penguin.getClass().getMethod("setup");

            setupMethod.invoke(penguin);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        // Method drawMethod = penguin.getMethod("draw");

    }
}