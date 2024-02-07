import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

public class PdeClassLoader extends ClassLoader{

    public static void main( String[] args ) throws Exception {

        JarFile jarFile = new JarFile("C:\\Users\\neilp\\IdeaProjects\\example_assignment\\Submissions\\s0007_Dave_Raven\\lib\\MarchPenguin.jar");

        URL[] urls = { new URL("jar:file:" + "C:\\Users\\neilp\\IdeaProjects\\example_assignment\\Submissions\\s0007_Dave_Raven\\lib\\MarchPenguin.jar"+"!/") };

        URLClassLoader cl = URLClassLoader.newInstance(urls);

        Class c = cl.loadClass("MarchPenguin");

        c.getSimpleName();

        c.getMethod("setup");


        //Class penguin = getClassFromFile("MarchPenguin","C:\\Users\\neilp\\IdeaProjects\\example_assignment\\Submissions\\s0007_Dave_Raven\\source\\MarchPenguin.class" );

    }

    public static Class getClassFromFile(String fullClassName, String filePath) throws Exception {

        JarFile jarFile = new JarFile(filePath);

        URL[] urls = { new URL("jar:file:" + filePath +"!/") };

        URLClassLoader cl = URLClassLoader.newInstance(urls);

        return cl.loadClass("MarchPenguin");
    }


}
