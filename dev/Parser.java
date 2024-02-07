import Tests.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Parser {

    // add variables for tests, so user can run specific ones

    public static void main(String[] args) {
        String testFile = "./TestFile.txt";

        ArrayList<Test> staticTests = new ArrayList<Test>();

        ArrayList<Test> dynamicTests = new ArrayList<Test>();

        parseTests(testFile, staticTests, dynamicTests);

        // System.out.println(parseTest(null));

    }

    // runs all tests
    // if given array of ints, only runs those tests
    public static void runTests(int[] testNumber) {
    }

    // returns an array of
    public static void parseTests(String fileName, ArrayList<Test> staticTests, ArrayList<Test> dynamicTests) {

        // read the file

        // append static tests to that array list and dynamic to the other array list

        boolean staticRead = false;

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {

                i++;

                line.trim();

                if (line.equals("#EOF")) {
                    return;
                }

                // Has read all the static tests
                if (line.equals("#DYNAMIC")) {
                    staticRead = true;
                }

                Test newTest = parseTest(line);

                if (newTest == null) {
                    // System.out.println("Test on line " + i + " is not correct");
                    continue;
                }

                else if (!staticRead) {
                    staticTests.add(newTest);
                } else {
                    dynamicTests.add(newTest);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static Test parseTest(String input) {

        // input = "-s 5 neededFiles: [Penguin.pde, Dog.pde]";

        // input = "-a 5 unchangedFiles: [Landscape.pde]";
        // System.out.println(input);

        String[] inputArray = input.split(" ");

        // check if markType is entered correctly;

        if (!(inputArray[0].equals("-s") || inputArray[0].equals("-a")))
            return null;

        String markType = inputArray[0];

        int mark = Integer.parseInt(inputArray[1]);

        // System.out.println(inputArray[2].equals("unchangedFiles:"));

        Test newTest = null;
        switch (inputArray[2]) {

            case "neededFiles:":
                newTest = new neededFiles(markType, mark,
                        parseFiles(Arrays.copyOfRange(inputArray, 3, inputArray.length)));
                break;

            case "unchangedFiles:":
                newTest = new unchangedFiles(markType, mark,
                        parseFiles(Arrays.copyOfRange(inputArray, 3, inputArray.length)));
                break;

            case "objectCount:":
                newTest = new objectCountTest(markType, mark, inputArray[3].substring(1),
                        Integer.parseInt(inputArray[inputArray.length - 1].substring(0,
                                inputArray[inputArray.length - 1].length() - 1)));
                break;
            case "neededObject:":
                newTest = new neededObject(markType, mark, inputArray[3].substring(1, inputArray[3].indexOf('[', inputArray[3].indexOf('[') + 1)), 
                    inputArray[3].substring(inputArray[3].indexOf('[', inputArray[3].indexOf('[') + 1) + 1, inputArray[3].length() - 2));
                break;
            case "CyclomaticComplexity":
                newTest = new StaticTest(markType, mark, "CyclomaticComplexity");
                break;

            case "ExcessiveMethodLength":
                newTest = new StaticTest(markType, mark, "ExcessiveMethodLength");
                break;

            case "TooManyFields":
                newTest = new StaticTest(markType, mark, "TooManyFields");
                break;
            case "TooManyMethods":
                newTest = new StaticTest(markType, mark, "TooManyMethods");
                break;
            case "OneDeclarationPerLine":
                newTest = new StaticTest(markType, mark, "OneDeclarationPerLine");
                break;
            case "FieldNamingConventions":
                newTest = new StaticTest(markType, mark, "FormalParameterNamingConventions");
                break;
            case "FormalParameterNamingConventions":
                newTest = new StaticTest(markType, mark, "FormalParameterNamingConventions");
                break;
            case "MethodNamingConventions":
                newTest = new StaticTest(markType, mark, "MethodNamingConventions");
                break;
            case "ShortClassName":
                newTest = new StaticTest(markType, mark, "ShortClassName");
                break;
            case "ShortMethodName":
                newTest = new StaticTest(markType, mark, "ShortMethodName");
                break;
            case "ShortVariable":
                newTest = new StaticTest(markType, mark, "ShortVariable");
                break;


        }

        return newTest;
    }

    private static String[] parseFiles(String[] array) {

        array[0] = array[0].substring(1);
        array[array.length - 1] = array[array.length - 1].substring(0, array[array.length - 1].length() - 1);

        return array;
    }

}
