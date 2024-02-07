package Tests;


import java.lang.reflect.Field;

public class neededObject extends Test {

    private String classType;
    private String objectType;
    private boolean present;

    public neededObject(String markType, int mark, String classType, String objectType) {
        this.markType = markType;
        this.mark = mark;
        this.classType = classType;
        this.objectType = objectType;
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

        return false;
    }

    @Override
    public String toString() {
        return "Object Present: " + present;
    }

    public String getClassType() {
        return classType;
    }

    public String getObjectType() {
        return objectType;
    }

    public boolean setResult(boolean result) {
        this.present = result;
        return this.present;
    }

    public boolean getResult() {
        return present;
    }
}
