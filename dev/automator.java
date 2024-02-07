import java.io.File;

public class automator {

    static void RecursivePrint(File[] arr, int index, int level) {
        // terminate condition
        if (index == arr.length)
            return;

        // tabs for internal levels
        for (int i = 0; i < level; i++)
            System.out.print("\t");

        // for files
        if (arr[index].isFile())
            System.out.println(arr[index].getName());

        // for sub-directories
        else if (arr[index].isDirectory()) {
            System.out.println("[" + arr[index].getName() + "]");
            // recursion for sub-directories
            RecursivePrint(arr[index].listFiles(), 0, level + 1);
        }
        // recursion for main directory
        RecursivePrint(arr, ++index, level);
    }

    // Driver Method
    public static void main(String[] args)
    {
        // Provide full path for directory(change accordingly)
        String mainDirPath = "/Users/tonmoy/Desktop/Uni/COMP4050/TestingAutomater/testFolder/testCode";

        // File object
        File mainDir = new File(mainDirPath);

        if (mainDir.exists() && mainDir.isDirectory()) {

            // array for files and sub-directory of directory pointed by mainDir
            File arr[] = mainDir.listFiles();

            System.out.println("**********************************************");
            System.out.println("Files from main directory : " + mainDir);
            System.out.println("**********************************************");

            // Calling recursive method
            RecursivePrint(arr, 0, 0);
        }
    }
}