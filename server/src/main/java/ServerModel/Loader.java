package ServerModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

/**
 * Created by tyler on 12/7/2017.
 */

public class Loader {

    public ArrayList<String> readFile(String fileName) {
        // This will reference one line at a time
        String line = null;
        fileName = "server/lib/" + fileName;
        ArrayList<String> arrayList = new ArrayList<String>();
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                arrayList.add(line);
            }

            // Always close files.
            bufferedReader.close();
            return arrayList;
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
        }
        return null;
    }

    public Object loadClass(String fileName,String className) {
        String jarPath = null;
         File myjar = null;
        if(fileName.equals("sql.txt")){
            myjar = new File("server\\lib\\sqlplugin.jar");
            //jarPath = "./plugins/src/main/SQL.jar";."+className);
//
        }
        else if(fileName.equals("file.txt")){
            myjar = new File("server\\lib\\fileplugin.jar");
            //jarPath = "./plugins/src/main/File.jar";
        }

        // Getting the jar URL which contains target class
        try {
           //URL u =  myjar.toURL();
            URL[] classLoaderUrls = new URL[]{myjar.toURL()};

            // Create a new URLClassLoader
            URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);

            // Load the target class
            Class<?> beanClass = urlClassLoader.loadClass(className);
//
//            Class<?> beanClass = Class.forName(className)
            //Class<?> beanClass = urlClassLoader.loadClass(className);

            // Create a new instance from the loaded class
            //return beanClass.getConstructor().newInstance();
            return beanClass.newInstance();
        }catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
