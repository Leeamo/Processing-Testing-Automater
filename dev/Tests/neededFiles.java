package Tests;

public class neededFiles extends Test {

    private String[] files;


    public neededFiles(String markType, int mark, String[] files) {

        this.markType = markType;
        this.mark = mark;
        this.files = files;
    }

    public boolean runTest() {
        return false;
    }

    @Override
    public boolean runTest(Object sketchObject) {
        return false;
    }

}
