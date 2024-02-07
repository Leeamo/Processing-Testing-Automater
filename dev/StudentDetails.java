import java.util.regex.Pattern;

public class StudentDetails {
    public String studentDeets;
    public String sID;
    public String name;
    public String assignmentName;
    public Integer mark;

    public StudentDetails() {
        studentDeets = "";
        sID = "";
        name = "";
        assignmentName = "";
        mark = 0;
    }

    public String studentInfo(String[] split) {
        for (int i = 0; i < split.length; i++) {
            if (Pattern.matches("(s)\\d+_([A-Za-z]+)_([A-Za-z]+)", split[i])) {
                studentDeets = split[i];
                return split[i];

            }
        }
        return null;
    }

    public void studentInfoSplit(String[] split) {
        this.sID = split[0];
        this.name = split[1];
        this.assignmentName = split[2];

    }

}
