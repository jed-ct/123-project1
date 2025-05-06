import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner; // Import the Scanner class to read text files


public class main {
    //Function to print out the union of hashsets of docs
    public static void printUnionOfSets(ArrayList<HashSet<String>> setOfDocuments) {
        HashSet<String> result = new HashSet<>();
        //Since hashsets ignore duplicate values, you can just add it
        for (HashSet<String> hashset : setOfDocuments) {
            for (String documents : hashset) {
                result.add(documents);
            }
        }
        for (String docs : result) {
            System.out.println(docs);
        }
    }
    //Function to print out the union of hashsets of docs
    public static void printIntersectionOfSets(ArrayList<HashSet<String>> setOfDocuments) {
        //Create hashset of all documents
        HashSet<String> neededDocuments = new HashSet<>(Arrays.asList("Doc1", "Doc2", "Doc3"));
        for (HashSet<String> hashset : setOfDocuments) {
            //Intersect this hashset of all documents to all sets
            neededDocuments.retainAll(hashset);
        }
        for (String docs : neededDocuments) {
            System.out.println(docs);
        }
    }



    public static void main(String args[]) {
        HashMap <String, HashSet<String>> wordMap = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Jacob Las");
        System.out.println("Ken Chedrey Duculan");
        System.out.println("Lyda Samuelle Ballesteros");

        try {
            File documentOne = new File("Doc1.txt");
            Scanner myReader = new Scanner(documentOne);
            while (myReader.hasNextLine()) {
                String documentOneText = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find document.");
        }
        System.out.print("Enter search query: ");
        String userQuery = scanner.nextLine();
        System.out.println(userQuery);

        //Sample hashmap
        wordMap.put("jabol", new HashSet<>(Arrays.asList("Doc1", "Doc2", "Doc3")));
        wordMap.put("elyu", new HashSet<>(Arrays.asList("Doc1", "Doc3")));
        wordMap.put("bonkers", new HashSet<>(Arrays.asList("Doc2")));

        ArrayList<HashSet<String>> lmao = new ArrayList<>();
        lmao.add(new HashSet<>(Arrays.asList("Doc1", "Doc2")));
        lmao.add(new HashSet<>(Arrays.asList("Doc1", "Doc3")));
        printIntersectionOfSets(lmao);
    }
}
