import Tests.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.*;
import javax.lang.model.SourceVersion;
import javax.swing.filechooser.FileSystemView;
import javax.tools.*;

public class Punit {

    private static ArrayList<Test> statTests = new ArrayList<Test>();
    private static ArrayList<Test> dynTests = new ArrayList<Test>();
    private static ArrayList<StudentDetails> students = new ArrayList<>();

    // User Commands
    private static String compileTest = "-ct";
    private static String staticTest = "-st";
    private static String dynamicTest = "-dt";
    private static String outPut = "-o";
    private static String exit = "-e";
    private static String help = "-h";

    private static String str = "";
    private static String txt = "";
    private static String testFile = "";
    private static String xmlFile = "";
    private static String outPath = "";
    private static String newConvertDir = "";
    private static String projectName = "";

    // private static StudentDetails student = new StudentDetails();

    private static Boolean ctCheck;
    private static Boolean stCheck;
    private static Boolean dtCheck;

    private static Boolean conversionCheck;

    private static boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

    // "C:\\Users\\joshu\\OneDrive\\Documents\\Uni\\COMP4050\\Processing-Tester\\sketch_220803a.java"
    // "C:\\Users\\joshu\\OneDrive\\Documents\\Uni\\COMP4050\\Processing-Tester\\sketch_220803a.pde"
    // "C:\\Users\\joshu\\OneDrive\\Documents\\Uni\\COMP4050\\Processing-Tester\\SampleClass.java"
    // "C:\\Users\\joshu\\OneDrive\\Documents\\Uni\\COMP4050\\example_assignment-main\\example_assignment-main\\Submissions\\s0001_Alice_Penguin\\MarchPenguin\\MarchPenguin.pde"
    // "C:\\Users\\joshu\\OneDrive\\Documents\\Uni\\COMP4050\\test1\\source\\MarchPenguin.java"

    // C:\Users\joshu\OneDrive\Documents\Uni\COMP4050\example_assignment-main\example_assignment-main\Submissions\s0003_Bob_Eagle\MarchPenguin\\MarchPenguin.pde
    // C:\\Users\\joshu\\OneDrive\\Documents\\Uni\\COMP4050\\TestingAutomater\\TestFile.txt
    // C:\Users\joshu\OneDrive\Documents\Uni\COMP4050\TestingAutomater\dev\staticRules.xml

    // \w+\.java - matches any alpha numeric character + .java
    // \w+\.pde - matches any alpha numeric character + .pde
    // \w+\.pde - matches any alpha numeric character + .pde
    public static void main(String[] args) throws IOException {

        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader bf = new BufferedReader(in);

        try {
            if (args.length == 0) {
                System.out.println(
                        "ARG0: Please enter directory path to processing sketch in either -- .pde, .java or submission folder format!");
                String pdePath = bf.readLine();

                System.out.println("ARG1: Please enter directory path to test file in .txt format!");
                String tfPath = testFileCheck(bf);

                System.out.println("ARG2: Please enter directory path to static rules file in .xml format!");
                String stRulesPath = testFileCheck(bf);
                args = new String[] {
                        pdePath,
                        tfPath,
                        stRulesPath };
            }

            if (args[0].length() > 0 && args[1].length() > 0 && args[2].length() > 0) {
                File inputFile = new File(args[0]);

                testFile = args[1];
                xmlFile = args[2];

                // Call resurcive print which recurses through the directory
                ////////////////////////////////////////////////////////////

                ///////////////////////////////////////////////////////////

                // File Object
                File mainDir = new File(args[0]);
                parseTestHandler(testFile);
                if (mainDir.exists() && mainDir.isDirectory()) {

                    // array for files and sub-directory of directory pointed by mainDir
                    File arr[] = mainDir.listFiles();

                    RecursivePrint(arr, 0, 0, in, bf);

                    // directoryHandler(in, bf, inputFile);

                    ///////////////////////////////////////////////////////////
                } else {
                    javaProjectHandler(in, bf, inputFile);
                    processingProjectHandler(in, bf, inputFile);

                }
                spreadsheetGen(bf);
            }

        } catch (Exception e) {
            System.out.println("Error: invalid input parameters. Refer to user manual for help.");
            e.printStackTrace();
            logOutput(e);
        }

    }

    /**************************************************************************
     ************************************************************************** 
     * Batch processing method
     * 
     * @inputs: filePath,
     * 
     *          Takes file path
     *          Recurses thorugh file path to find pde
     *          - validate for naming convention for student folder
     *          - validate if this is a processing project
     *          Tests the pde against "StaticRules.xml"
     * 
     *          Call commandHandler which saves the result output
     * 
     *          break when no more folder
     ***************************************************************************
     ***************************************************************************/

    static void RecursivePrint(File[] arr, int index, int level, InputStreamReader in, BufferedReader bf) {
        // terminate condition
        if (index == arr.length) {
            return;
        }

        // tabs for internal levels
        for (int i = 0; i < level; i++)
            System.out.print("\t");
        // directoryHandler(in, bf, arr[i]);

        // for files
        if (arr[index].isFile() && arr[index].getName().equals("MarchPenguin.pde")) {
            // System.out.println(arr[index].getAbsolutePath());
            processingProjectHandler(in, bf, arr[index]);
            javaProjectHandler(in, bf, arr[index]);
        }

        // for sub-directories
        else if (arr[index].isDirectory()) {
            // System.out.println("[" + arr[index].getName() + "]");
            // recursion for sub-directories
            RecursivePrint(arr[index].listFiles(), 0, level + 1, in, bf);
        }
        // recursion for main directory
        RecursivePrint(arr, ++index, level, in, bf);
    }

    /***************************************************************************
     ***************************************************************************    
     ***************************************************************************/

    public static void spreadsheetGen(BufferedReader bf) {
        xlsxTest test = new xlsxTest();
        test.xlsxOut(students);
        System.out.println("System: exiting now!");
        try {
            bf.close();
        } catch (IOException e) {
            System.out.println("System: Error closing buffered reader!");
            e.printStackTrace();
        }
        System.exit(0);

    }

    public static void directoryHandler(InputStreamReader in, BufferedReader bf, File inputFile) {
        try {
            if (inputFile.isDirectory() && inputFile.exists()) {
            }

        } catch (Exception e) {
            e.printStackTrace();
            logOutput(e);
        }
    }

    public static void processingProjectHandler(InputStreamReader in, BufferedReader bf, File inputFile) {
        try {
            String[] pathSplit;
            StudentDetails student = new StudentDetails();
            if (Pattern.matches("\\w+\\.pde", inputFile.getName())) {
                System.out.println();
                System.out.println("System: pde match: " + inputFile.getName());
                String path = inputFile.getParent();
                // System.out.println(path);

                if (isWindows) {
                    pathSplit = path.split("\\\\");
                } else {
                    pathSplit = path.split("/");
                }
                projectName = pathSplit[pathSplit.length - 1];
                // System.out.println("Name: " + projectName);
                student.studentDeets = student.studentInfo(pathSplit);
                String[] studentInfoSplit = student.studentDeets.split("_");
                student.studentInfoSplit(studentInfoSplit);

                outPath = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
                newConvertDir = outPath + "/" + student.studentDeets;
                // System.out.println(newConvertDir);

                String[] command = { "processing-java", "--sketch=" + path, "--output=" + newConvertDir,
                        "--export" };
                String[] commandForce = { "processing-java", "--sketch=" + path, "--output=" + newConvertDir,
                        "--force",
                        "--export" };

                if (runProcessingCommand(command) == 0) {
                    conversionCheck = true;

                    inputFile = new File(
                            newConvertDir + "/lib/MarchPenguin.jar");
                    File javaFile = new File(newConvertDir + "/source/MarchPenguin.java");
                    System.out.println("System: Processing project converted");
                    commandHandler(in, bf, inputFile, student, javaFile);
                } else {
                    conversionCheck = false;
                    if (runProcessingCommand(commandForce) == 0) {
                        conversionCheck = true;

                        inputFile = new File(
                                newConvertDir + "/lib/MarchPenguin.jar");
                        File javaFile = new File(newConvertDir + "/source/MarchPenguin.java");
                        System.out.println("System: Processing project converted with --force");
                        commandHandler(in, bf, inputFile, student, javaFile);

                    } else {
                        conversionCheck = false;
                    }

                }
            }

        } catch (Exception e) {
            System.out.println("System: Error!");
            e.printStackTrace();
            logOutput(e);
        }

    }

    public static void javaProjectHandler(InputStreamReader in, BufferedReader bf, File inputFile) {
        try {
            StudentDetails student = new StudentDetails();
            if (inputFile.isFile() && inputFile.exists()) {
                if (Pattern.matches("\\w+\\.java", inputFile.getName())) {
                    System.out.println();
                    System.out.println("System: java match: " + inputFile.getName());
                    conversionCheck = false;
                    commandHandler(in, bf, inputFile, student, inputFile);
                }
            }

        } catch (Exception e) {
            System.out.println("System: Error!");
            e.printStackTrace();
            logOutput(e);
        }

    }

    public static String studentSplitDetails(String[] split) {
        for (int i = 0; i < split.length; i++) {
            if (Pattern.matches("(s)\\d+_([A-Za-z]+)_([A-Za-z]+)", split[i])) {

                return split[i];

            }
        }
        return null;
    }

    // checks testfile and static rules path has been entered and named correctly
    public static String testFileCheck(BufferedReader bf) {
        String tfPath = "";
        int i = 0;
        while (Pattern.matches("([A-Z]:)[\\\\\\w+\\-\\\\]*(TestFile.txt|staticRules.xml)", tfPath) == false) {
            i += 1;
            try {
                if (i == 1) {
                    tfPath = bf.readLine();
                }
                if (i > 1) {
                    System.out.println(
                            "Please enter the correct test file directory path! -- refer to user manual for more help");
                    tfPath = bf.readLine();
                }

            } catch (IOException e) {
                e.printStackTrace();
                logOutput(e);
            }
        }
        return tfPath;
    }

    /***
     * Calls the Parser Class
     *
     */
    public static void parseTestHandler(String testFile) {
        Parser.parseTests(testFile, statTests, dynTests);
    }

    // handles testing runtime
    public static void commandHandler(InputStreamReader in, BufferedReader bf, File input, StudentDetails student,
            File jFile) {
        try {

            ctCheck = false;
            stCheck = false;
            dtCheck = false;

            if (ctCheck != true && stCheck != true && dtCheck != true) {
                compile(jFile);
                String[] args = { jFile.getAbsolutePath(), xmlFile, newConvertDir };
                Output out = new Output();
                out.main(args);
                stCheck = true;
                sketchTest newTest = new sketchTest(input);
                // sketchTest newTest = new sketchTest(MarchPenguin.class);
                for (Test test : dynTests) {
                    if (test != null) {
                        test.setPassed(sketchTest.runTest(test));
                    }
                }
                dtCheck = true;
                System.out.println("System: Dynamic Testing finished!");
                resultOutput(student, out);
                students.add(student);
                System.out.println(
                        "System: Results for " + student.sID + " "
                                + "finished, check student directory in documents for results!");

            }

        } catch (Exception e) {
            System.out.println("Error!");
            e.printStackTrace();
            logOutput(e);
        }

    }

    // outputting a system log for errors
    public static void logOutput(Exception e) {
        // check if file exists
        // if not create it
        // clear the first three lines (will have java version, os and blank line)
        // append new log to the top of the file
        // if the file exceeds x amount of lines, clear the bottom output
        // save file
        try {
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            PrintWriter out = new PrintWriter(newConvertDir + "/" + "log.txt");
            out.println("Operating System: " + System.getProperty("os.name"));
            out.println("Java Version: " + System.getProperty("java.version"));
            for (SourceVersion supportedVersion : compiler.getSourceVersions()) {
                out.println("Supported Javac: " + supportedVersion);
            }
            out.println();
            out.println("Exception: " + e.toString());
            out.println();
            for (StackTraceElement item : e.getStackTrace())
                out.println(item);
            out.close();
        } catch (FileNotFoundException f) {
            f.printStackTrace();
            System.out.println("System: File was not created!");
        }
    }

    // Calculates and prints the individual results of a student for each form of
    // testing to a .txt file
    public static void resultOutput(StudentDetails student, Output out) throws Exception {
        int counter = 0;
        int ctMark = 20;
        int dtMark = 10;
        int zeroMark = 0;
        PrintWriter result = new PrintWriter(newConvertDir + "/" + "Result.txt");

        result.println("Results for - ");

        result.println("Student ID:  " + "" + student.sID + " " + "Name: " + "" + student.name + "\n");

        if (ctCheck == null) {
            System.out.println("Error: Compile Test has not been run, please refer to user manual or use -h for help");
            result.close();

        } else {
            eolPrint(result, '#', 200);
            eolPrint(result, '#', 200);
            result.println();
            result.println("COMPILE TEST\n");

            if (ctCheck) {
                counter += ctMark;
                result.println("Compile Test Passed: " + counter);
            } else {
                counter += zeroMark;
                result.println("Compile Test Failed: " + counter);
            }
            result.println();
            result.println("STATIC TEST\n");
            Set<OutputTests> keys = out.staticTestMarks().keySet();
            for (OutputTests item : keys) {
                result.println(item.getTestName() + ":" + " " + "ID-" + item.testNumber + ":" + " "
                        + out.staticTestMarks().get(item));
                counter += (int) out.staticTestMarks().get(item);
            }

            result.println();
            result.println("DYNAMIC TEST\n");

            for (Test test : dynTests) {
                if (test.getPassed() == true) {
                    counter += test.getMark();
                    result.print(test);
                    result.print(":" + " " + test.getMark());
                    result.println();
                }
            }
            eolPrint(result, '#', 200);
            eolPrint(result, '#', 200);
            result.println();
            result.println("Result Total = " + counter);
            result.close();
            student.mark = counter;

        }

    }

    // read the converted pde file add in the new functions for testing check
    public static void txtOutput(File input) throws IOException {
        PrintWriter out = new PrintWriter("output.txt");
        Scanner fReader = new Scanner(new FileReader(input));
        while (fReader.hasNextLine()) {
            txt = fReader.nextLine();
            out.println(txt);
            if (txt == null) {
                break;
            }
        }
        out.close();
        fReader.close();
    }

    // returns the arguments for compiling - needs to be fixed so that its adaptable
    // for each student
    public static ArrayList<String> compilerArgs() {
        ArrayList<String> args = new ArrayList<>();
        args.add("-cp");
        // switch delimeter based on os
        if (isWindows) {
            args.add(
                    "\"" + newConvertDir + "/lib/jogl-all.jar;" + newConvertDir + "/lib/core.jar;" + newConvertDir
                            + "/lib/gluegen-rt.jar;" + newConvertDir + "/lib/" + projectName + ".jar\"");
        } else {
            args.add(
                    "\"" + newConvertDir + "/lib/jogl-all.jar:" + newConvertDir + "/lib/core.jar:" + newConvertDir
                            + "/lib/gluegen-rt.jar:" + newConvertDir + "/lib/" + projectName + ".jar\"");
        }
        return args;

    }

    // Compiler test - uses systems java compiler to test basic functionality of
    // whether the program compiles
    public static void compile(File Input) throws ExecutionException, InterruptedException, IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        JavaCompiler.CompilationTask task;

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        StandardJavaFileManager sfm = compiler.getStandardFileManager(diagnostics, null, null);
        Iterable<? extends JavaFileObject> jfo = sfm.getJavaFileObjects(Input);
        for (JavaFileObject hold : jfo) {
            System.out.println(hold.getClass());
        }

        if (conversionCheck) {
            Iterable<String> args = compilerArgs();
            task = compiler.getTask(null, sfm, diagnostics, args, null, jfo);

        } else {
            task = compiler.getTask(null, sfm, diagnostics, null, null, jfo);

        }

        Future<Boolean> future = Executors.newFixedThreadPool(1).submit(task);
        Boolean result = future.get();
        if (result != null && result == true) {
            System.out.println();
            System.out.println("System: Compile Test finished!");
            System.out.println();
            ctCheck = true;
        } else {
            System.out.println();
            System.out.println("System: Compile Test failed!");
            System.out.println();
            diagnostics.getDiagnostics().forEach(System.out::println);
            ctCheck = false;
        }

        sfm.close();
    }

    public static int runProcessingCommand(String[] command) throws InterruptedException, IOException {
        Process runCmd = Runtime.getRuntime().exec(command);
        runCmd.waitFor();
        System.out.print("System: ");
        for (String item : command) {
            System.out.print(item);
        }
        System.out.print(" " + runCmd.exitValue());
        System.out.println();
        return runCmd.exitValue();

    }

    public static void eolPrint(PrintWriter inputFile, char inputCharacter, int i) {
        if (i == 0) {
            i = 100;
        }
        while (i-- != 0) {
            inputFile.print(inputCharacter);
        }
        inputFile.println();

    }
}
