import Tests.Test;

public class StaticTest extends Test {



    private String testType;

    public StaticTest(String markType, int mark, String testType) {
        this.markType = markType;
        this.mark = mark;
        this.testType = testType;
    }

    public String getTestType() {
        return testType;
    }

    @Override
    public boolean runTest(Object sketchObject) {
        return false;
    }
}
