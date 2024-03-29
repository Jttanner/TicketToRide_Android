package ServerModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modeling.User;
import modeling.UserInfo;

/**
 * Created by korea on 12/7/2017.
 */

public class FileUserDao implements IUserDao {

    public FileUserDao () {
        File dir = new File("User");
        boolean successful = dir.mkdir();
        if (successful) {
            System.out.println("User Directory is created!");
        }
        else {
            System.out.println("User Directory already exists");
        }
    }
    @Override
    public boolean registerUser(String userName, String password) {
        FileWriter fileWriter;
        File gameFile;
        boolean result = false;
        //Create new txt file in User
        gameFile = new File("User/" + userName + ".txt");
        try {
            if (gameFile.createNewFile()){
                System.out.println("File " + userName + " is created!");
            }else{
                //If the user name already exists don't update the file
                System.out.println("File " + userName +  " already exists.");
                return result;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Update file in User Directory
        try {
            fileWriter = new FileWriter(gameFile);
            fileWriter.write(password);
            fileWriter.close();
            result = true;
            System.out.println("File " + userName + " successfully updated!");
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
            System.out.println("File " + userName + " not updated!");
        }
        return result;
    }

    //Will verifyUser return a user or a boolean?
    @Override
    public User verifyUser(String name, String password) {
        String userFile;
        File directory;
        boolean result = false;
        userFile = "User";
        directory = new File(userFile);
        String[]entries = directory.list();
        String line;
        if(entries != null) {
            //System.out.println("Files inside User ");
            for(String s: entries) {
                //System.out.println(s);
                if(s.equals((name + ".txt"))) {
                    try {
                        File file = new File("User/" + s);
                        // FileReader reads text files in the default encoding.
                        FileReader fileReader = new FileReader(file);

                        // Always wrap FileReader in BufferedReader.
                        BufferedReader bufferedReader = new BufferedReader(fileReader);

                        line = bufferedReader.readLine();
                        if(line.equals(password)) {
                            result = true;

                        }
                        // Always close files.
                        bufferedReader.close();
                    }
                    catch(FileNotFoundException ex) {
                        System.out.println("VerifyUser: Unable to open file '" + name + "'");
                    }
                    catch(IOException ex) {
                        System.out.println("Error reading file '" + name + "'");
                        // Or we could just do this:
                        // ex.printStackTrace();
                    }
                }
            }
        }

        //return result;
        if(result) {
            UserInfo info = new UserInfo(name, password);
            User temp = new User(info);
            return temp;
        }
        return null;
    }


    @Override
    public boolean clear() {
        String userFile;
        File directory;
        boolean result = false;
        //fileName = new String(Files.readAllBytes(Paths.get("Game")));
        userFile = "User";
        directory = new File(userFile);
        String[]entries = directory.list();
        Boolean temp = true;
        if(entries != null) {
            for(String s: entries) {
                File currentFile = new File(directory.getPath(), s);
                result = currentFile.delete();
                if(!result) {
                    temp = false;
                }
            }
        }
        if(temp) {
            System.out.println("Clear successful");
        }
        else {
            System.out.println("Clear unsuccessful");
        }
        return result;
    }

    @Override
    public List<User> getAllUsers() {
        String userFile;
        File directory;
        boolean result = false;
        userFile = "User";
        directory = new File(userFile);
        String[]entries = directory.list();
        String line;
        List allUsers = new ArrayList<>();
        if(entries != null) {
            //System.out.println("Files inside User ");
            for(String s: entries) {
                //System.out.println(s);
                //if(s.equals((name + ".txt"))) {
                    try {
                        //s = s.substring(0, s.length() - 4);
                        File file = new File("User/" + s);
                        // FileReader reads text files in the default encoding.
                        FileReader fileReader = new FileReader(file);

                        // Always wrap FileReader in BufferedReader.
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        //Gets the name of the User

                        //Gets the password
                        line = bufferedReader.readLine();
                        // Always close files.
                        bufferedReader.close();

                        UserInfo userInfo = new UserInfo(s, line);
                        User user = new User(userInfo);
                        allUsers.add(user);
                    }
                    catch(FileNotFoundException ex) {
                        System.out.println("GetAllUsers: Unable to open file " + s);
                    }
                    catch(IOException ex) {
                        System.out.println("Error reading file '" + "'");
                        // Or we could just do this:
                        // ex.printStackTrace();
                    }
                //}
            }
        }


        return allUsers;
    }


}
