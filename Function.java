package Script;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Function {
    public Function(){

    }
    public File fileCreation(String fileName){
        File newFile = null;
        String finalPath = "";
        if(fileName.equals(null)){
            throw new IllegalArgumentException("filename cannot be null");
        }
        else {
            newFile = new File(fileName);
        }
        Path newPath = Paths.get(fileName);
        if(!Files.exists(newPath)){
            int last = fileName.lastIndexOf("/");
            finalPath = fileName.substring(0,last + 1);
            pathCreation(finalPath);
        }
        try {
            if (newFile.createNewFile()) {
                System.out.println("new file created " + newFile.getName() + " at " + finalPath);
            }
            else{
                System.out.println("file already exists. applying any changes");
            }
        }
        catch(IOException e){
            System.out.println("an error occurred");
            e.printStackTrace();
        }
        return newFile;
    }
    public boolean pathCreation(String path){
        if(path == null){
            throw new IllegalArgumentException("path cannot be null");
        }
        Path newPath = Paths.get(path);
        if(!Files.exists(newPath)) {
            File pathFile = new File(path); //creates a file named pathFile
            pathFile.mkdirs(); //creates a directory or directories given by the file name
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        Function T = new Function();
        File output = T.fileCreation("/etc/modprobe.d/hid_apple.conf");
        String command = "options hid_Function fnmode=2";

        FileOutputStream fileOut = new FileOutputStream(output);
        fileOut.write(command.getBytes());

    }
}