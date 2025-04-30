import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files


public class main {
    public static void main(String args[]) {
        System.out.println("Jacob Las");
        System.out.println("Ken Chedrey Duculan");
        try {
            File documentOne = new File("Doc1.txt");
            Scanner myReader = new Scanner(documentOne);
            while (myReader.hasNextLine()) {
                String documentOneText = myReader.nextLine();
                System.out.println(documentOneText);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find document.");
        }
    }
}
