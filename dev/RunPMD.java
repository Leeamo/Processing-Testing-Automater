import java.io.*;
import java.util.concurrent.TimeUnit;

public class RunPMD {
    private static boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");

    public static void runCommand(String[] command) throws Exception {

        Process runCmd = Runtime.getRuntime().exec(command);

        runCmd.waitFor();
        System.out.print("System: ");
        for (String item : command) {
            System.out.print(item);
        }
        System.out.print(" " + runCmd.exitValue());
        System.out.println();
    }
}
