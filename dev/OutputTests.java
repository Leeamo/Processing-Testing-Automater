public class OutputTests {

    public int testNumber;
    private final String testName;
    private boolean occurrence;
    private int numberOfInstances;

    public OutputTests(int testNumber, String testName, boolean occurrence, int numberOfInstances) {
        this.testNumber = testNumber;
        this.testName = testName;
        this.occurrence = occurrence;
        this.numberOfInstances = numberOfInstances;
    }

    public String getTestName() {
        return testName;
    }

    public boolean getOccurrence() {
        return occurrence;
    }

    public int getNumberOfInstances() {
        return numberOfInstances;
    }

    public void setOccurrence(boolean newOccurrence) {
        this.occurrence = newOccurrence;
    }

    public void setNumberOfInstances(int newNumberOfInstances) {
        this.numberOfInstances += newNumberOfInstances;
    }

    public int getTotalMarks() {
        if (getNumberOfInstances() == 0) {
            return 5;
        } else if (getNumberOfInstances() > 0 && getNumberOfInstances() <= 3) {
            return 4;
        } else if (getNumberOfInstances() > 3 && getNumberOfInstances() <= 6) {
            return 3;
        } else if (getNumberOfInstances() > 6 && getNumberOfInstances() <= 9) {
            return 2;
        } else if (getNumberOfInstances() > 9 && getNumberOfInstances() <= 12) {
            return 1;
        } else if (getNumberOfInstances() > 12) {
            return 0;
        } else
            return -1;
    }

    public int hashCode() {
        int hashcode = 0;
        hashcode = testNumber * 20;
        hashcode += testName.hashCode();
        return hashcode;
    }

    public boolean equals(Object obj) {
        if (obj instanceof OutputTests) {
            OutputTests t = (OutputTests) obj;
            return (t.testNumber == this.testNumber) && t.testName.equals(this.testName)
                    && t.occurrence == this.occurrence && t.numberOfInstances == this.numberOfInstances;
        } else {
            return false;
        }
    }
}
