import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

class Output {
    public void main(String[] args) {
        // C:\Users\joshu\OneDrive\Documents\Uni\COMP4050\TestingAutomater\dev\pmd-bin-6.49.0
        File PMDLocation = new File(System.getProperty("user.dir") +
                "/dev/pmd-bin-6.49.0/bin");
        // C:\Users\joshu\OneDrive\Documents\Uni\COMP4050\TestingAutomater\dev\MarchPenguin.java
        // "C:\\Users\\joshu\\OneDrive\\Documents\\Uni\\COMP4050\\TestingAutomater\\dev\\MarchPenguin.java"
        String processingFile = args[0];
        // "C:\\Users\\joshu\\OneDrive\\Documents\\Uni\\COMP4050\\TestingAutomater\\dev\\staticRules.xml"
        String rulesFile = args[1];
        // "C:\\Users\\joshu\\OneDrive\\Documents\\Uni\\COMP4050\\TestingAutomater\\ST_Log.txt"
        String outputFile = args[2] + "/ST_Log.txt";

        try {
            boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

            if (isWindows) {
                String[] command = { PMDLocation.getAbsolutePath() + "/pmd.bat", "-d", processingFile, "-R", rulesFile,
                        "-r", outputFile };
                RunPMD.runCommand(command);
                // RunPMD.runCommand(location, "command"); // for Mac(Linux based OS) users list
                // files
            } else {
                String[] command = { PMDLocation.getAbsolutePath() + "/run.sh", "pmd", "-d", processingFile, "-R",
                        rulesFile, "-r", outputFile };
                RunPMD.runCommand(command);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Incorrect File path or command");
        }
        marks(outputFile);
        System.out.println();
        System.out.println("System: Static Testing finished!");
    }

    public List<OutputTests> tests = new ArrayList<>(11);

    public void marks(String string) {
        tests.add(new OutputTests(1, "CyclomaticComplexity", false, 0));
        tests.add(new OutputTests(2, "ExcessiveMethodLength", false, 0));
        tests.add(new OutputTests(3, "TooManyFields", false, 0));
        tests.add(new OutputTests(4, "TooManyMethods", false, 0));
        tests.add(new OutputTests(5, "OneDeclarationPerLine", false, 0));
        tests.add(new OutputTests(6, "FieldNamingConventions", false, 0));
        tests.add(new OutputTests(7, "FormalParameterNamingConventions", false, 0));
        tests.add(new OutputTests(8, "MethodNamingConventions", false, 0));
        tests.add(new OutputTests(9, "ShortClassName", false, 0));
        tests.add(new OutputTests(10, "ShortMethodName", false, 0));
        tests.add(new OutputTests(11, "ShortVariable", false, 0));

        Scanner scanner;
        try {
            scanner = new Scanner(new File(string));
            while (scanner.hasNext()) {
                scanner.skip(Pattern.compile(".+?\\t"));
                String s = scanner.nextLine();
                int i = s.indexOf(":");
                s = s.substring(0, i);

                switch (s) {
                    case "CyclomaticComplexity":
                        tests.get(0).setOccurrence(true);
                        tests.get(0).setNumberOfInstances(1);
                        break;
                    case "ExcessiveMethodLength":
                        tests.get(1).setOccurrence(true);
                        tests.get(1).setNumberOfInstances(1);
                        break;
                    case "TooManyFields":
                        tests.get(2).setOccurrence(true);
                        tests.get(2).setNumberOfInstances(1);
                        break;
                    case "TooManyMethods":
                        tests.get(3).setOccurrence(true);
                        tests.get(3).setNumberOfInstances(1);
                        break;
                    case "OneDeclarationPerLine":
                        tests.get(4).setOccurrence(true);
                        tests.get(4).setNumberOfInstances(1);
                        break;
                    case "FieldNamingConventions":
                        tests.get(5).setOccurrence(true);
                        tests.get(5).setNumberOfInstances(1);
                        break;
                    case "FormalParameterNamingConventions":
                        tests.get(6).setOccurrence(true);
                        tests.get(6).setNumberOfInstances(1);
                        break;
                    case "MethodNamingConventions":
                        tests.get(7).setOccurrence(true);
                        tests.get(7).setNumberOfInstances(1);
                        break;
                    case "ShortClassName":
                        tests.get(8).setOccurrence(true);
                        tests.get(8).setNumberOfInstances(1);
                        break;
                    case "ShortMethodName":
                        tests.get(9).setOccurrence(true);
                        tests.get(9).setNumberOfInstances(1);
                        break;
                    case "ShortVariable":
                        tests.get(10).setOccurrence(true);
                        tests.get(10).setNumberOfInstances(1);
                        break;
                    default:
                        break;
                }
            }

            for (int i = 0; i < 11; i++) {
                System.out.printf("%-35s %-10s %-10s %2d %12s %1d%n", tests.get(i).getTestName(),
                        tests.get(i).getOccurrence(), " Instances: ", tests.get(i).getNumberOfInstances(),
                        "Marks", tests.get(i).getTotalMarks());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public LinkedHashMap<OutputTests, Integer> staticTestMarks() {
        LinkedHashMap<OutputTests, Integer> marks = new LinkedHashMap<>();
        for (int i = 0; i < 11; i++) {
            marks.put(tests.get(i), tests.get(i).getTotalMarks());
        }
        return marks;
    }
}