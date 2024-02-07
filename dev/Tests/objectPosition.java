package Tests;

public class objectPosition extends Test {
    
    private String classType;
    private float x;
    private float y;
    private boolean result;

    public objectPosition(String markType, int mark, String classType, float x, float y) {
        this.markType = markType;
        this.mark = mark;
        this.classType = classType;
        this.x = x;
        this.y = y;
    }

    public boolean runTest() {
        return false;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public String getClassType(){
        return this.classType;
    }

    public boolean setResult(boolean result) {
        this.result = result;
        return this.result;
    }

    @Override
    public String toString() {
        return "Object Position: " + x + ", " + y + ", " + this.result;
    }

    @Override
    public boolean runTest(Object sketchObject) {
        return false;
    }
}
