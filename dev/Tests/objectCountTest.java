package Tests;


import java.lang.reflect.Field;

public class objectCountTest extends Test {

    private String classType;
    private int count;

    public objectCountTest(String markType, int mark, String classType, int count) {
        this.markType = markType;
        this.mark = mark;
        this.classType = classType;
        this.count = count;
    }

    public boolean runTest(Object sketchObject) {


        Field[] fields = sketchObject.getClass().getDeclaredFields();

        int objCount = 0;

        for (Field f: fields) {

            Object value = null;
            try {
                value = f.get(sketchObject);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

            if ((value.getClass().equals(classType))) objCount++;

            else if (value.getClass().isArray()) {
                Object[] values = (Object[]) value;
                for (Object o: values) {
                    if ((o.getClass().equals(classType))) objCount++;
                    //System.out.println(o); // i don't know why these are null
                }
            }

        }

        return objCount == this.count;
    }


    public String getClassType() {
        return classType;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "Object Count: " + classType + " " + count + " = " + testPassed;
    }
}
