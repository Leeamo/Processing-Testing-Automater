package Tests;

public class unchangedFiles extends Test {

    private String[] files;


    public unchangedFiles(String markType, int mark, String[] files) {

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
