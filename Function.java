
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Function {
    public Function() {

    }

    public File fileCreation(String fileName) {
        File newFile = null;
        String finalPath = "";
        if (fileName.equals(null)) {
            throw new IllegalArgumentException("filename cannot be null");
        } else {
            newFile = new File(fileName);
        }
        Path newPath = Paths.get(fileName);
        if (!Files.exists(newPath)) {
            int last = fileName.lastIndexOf("/");
            finalPath = fileName.substring(0, last + 1);
            pathCreation(finalPath);
        }
        String actualName = fileName.substring(fileName.lastIndexOf('/') + 1);
        try {
            if (newFile.createNewFile()) {
                System.out.println("new file created " + newFile.getName() + " at " + finalPath);
            } else {
                System.out.println("\"" + actualName + "\"" + " already exists. applying any changes");
            }
        } catch (IOException e) {
            System.out.println("an error occurred");
            e.printStackTrace();
        }
        return newFile;
    }

    public boolean pathCreation(String path) {
        if (path == null) {
            throw new IllegalArgumentException("path cannot be null");
        }
        Path newPath = Paths.get(path);
        if (!Files.exists(newPath)) {
            System.out.println("directory does not exist......");
            System.out.println(".....creating new directory");
            File pathFile = new File(path); //creates a file named pathFile
            pathFile.mkdirs(); //creates a directory or directories given by the file name
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        boolean start = true;
        while (start) {

            System.out.println("What would you like to set the function mode to? ");
            System.out.println(" '0' Disables the function keys. ");
            System.out.println(" '1' Sets modifiers to be priority. ");
            System.out.println(" '2' Sets function keys to be priority");
            System.out.print("Input: ");
            int userDecision = input.nextInt();
            while ((userDecision > 2 || userDecision < 0)) {
                System.out.println("Error, mode can either be '0', '1', or '2'. retry.....");
                System.out.print("Input: ");
                userDecision = input.nextInt();
            }
            System.out.println();
            Function T = new Function();
            File temporary = T.fileCreation("/sys/module/hid_apple/parameters/fnmode");
            File permanent = T.fileCreation("/etc/modprobe.d/hid_apple.conf");

            String permanentComand = "options hid_apple fnmode=" + userDecision + '\n';
            String temporaryCommand = String.valueOf(userDecision);
            FileOutputStream permanentOut = new FileOutputStream(permanent);
            FileOutputStream temporaryOut = new FileOutputStream(temporary);

            temporaryOut.write(temporaryCommand.getBytes());
            permanentOut.write(permanentComand.getBytes());

            System.out.println("\nWould you like to change the modifier again? (yes/no)");
            System.out.print("Input: ");
            Scanner newScanner = new Scanner(System.in);
            String finalUserDecision = newScanner.nextLine();
            if(finalUserDecision.equalsIgnoreCase("y")) {
                finalUserDecision = "yes";
            }
            if(finalUserDecision.equalsIgnoreCase("n")){
                finalUserDecision = "no";
            }
            while (!(finalUserDecision.equalsIgnoreCase("yes") || finalUserDecision.equalsIgnoreCase("no"))){
                System.out.print("\nError, Input can either be 'yes' or 'no' (caps ignored)\nInput: ");
                finalUserDecision = newScanner.nextLine();
            }
            if (finalUserDecision.equalsIgnoreCase("yes") || finalUserDecision.equalsIgnoreCase("y")){
                start = true;
            }
            if (finalUserDecision.equalsIgnoreCase("no") || finalUserDecision.equalsIgnoreCase("n")){
                start = false;
            }
            System.out.println();
        }
        System.out.println();
    }
}
