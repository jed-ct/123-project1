import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner; // Import the Scanner class to read text files


public class main {
    public static String[] convertToWordArray(String sentence) {
        return sentence.replaceAll("[^a-zA-Z ]", "").split(" ");
    }

    //Function to print out the union of hashsets of docs
    public static void printUnionOfSets(ArrayList<HashSet<String>> setOfDocuments) {
        HashSet<String> result = new HashSet<>();
        //Since hashsets ignore duplicate values, you can just add it
        for (HashSet<String> hashset : setOfDocuments) {
            for (String documents : hashset) {
                result.add(documents);
            }
        }
        printSet(result);
    }
    //Function to print out the union of hashsets of docs
    public static void printIntersectionOfSets(ArrayList<HashSet<String>> setOfDocuments) {
        //Create hashset of all documents
        HashSet<String> neededDocuments = new HashSet<>(Arrays.asList("Doc1", "Doc2", "Doc3"));
        for (HashSet<String> hashset : setOfDocuments) {
            //Intersect this hashset of all documents to all sets
            neededDocuments.retainAll(hashset);
        }
        if (neededDocuments.isEmpty()) {
            System.out.println("No common documents found.");
        }
        printSet(neededDocuments);
    }

    public static void printSet(HashSet<String> set) {
        if (set == null) {
            System.out.println("Cannot find word in document.");
        }
        else {
            for (String elements : set) {
                System.out.println(elements);
            }
        }
    }
    public static void retrieveWordLocations(String userQuery, HashMap<String,HashSet<String>> wordMap) {
        ArrayList<HashSet<String>> arrayOfDocumentSets = new ArrayList<>();
        if (userQuery.contains("OR")) {
            //Normalize the array by removing space and punctuations
            String[] words = userQuery.split(" OR ");
            for (String word : words) {
                if (wordMap.get(word) == null) {
                    System.out.println("Word " + word + " not found in documents.");
                    return;
                }
                else {
                    arrayOfDocumentSets.add(wordMap.get(word));
                }
            }
            printUnionOfSets(arrayOfDocumentSets);
        }
        else if (userQuery.contains("AND")) {
            String[] words = userQuery.split(" AND ");
            for (String word : words) {
                if (wordMap.get(word) == null) {
                    System.out.println("Word " + word + " not found in documents.");
                    return;
                }
                else {
                    arrayOfDocumentSets.add(wordMap.get(word));
                }
            }
            printIntersectionOfSets(arrayOfDocumentSets);
        }
        else {
            printSet(wordMap.get(userQuery));
        }
    }

    public static void addToWordMap(String[] wordArray, HashMap<String, HashSet<String>> wordMap,String documentName) {
        for (String word : wordArray) {
            if (wordMap.get(word) != null) {
                wordMap.get(word).add(documentName);
            }
            else {
                wordMap.put(word, new HashSet<>(Arrays.asList(documentName)));
            }
        }
    }



    public static void main(String args[]) {
        HashMap<String, HashSet<String>> wordMap = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Jacob Las");
        System.out.println("Ken Chedrey Duculan");
        System.out.println("Lyda Samuelle Ballesteros");
        //Sample hashmap
        String docOneText = "jabol drop sybau bonkers frisbee";
        String docTwoText = "wagna jabol bonjers";
        String docThreeText = "jabol bonjers elyu bonkers";
        String[] docOneWords = convertToWordArray(docOneText);
        String[] docTwoWords = convertToWordArray(docTwoText);
        String[] docThreeWords = convertToWordArray(docThreeText);

        addToWordMap(docOneWords, wordMap, "Doc1");
        addToWordMap(docTwoWords, wordMap, "Doc2");
        addToWordMap(docThreeWords, wordMap, "Doc3");

        //Get search query from user
        System.out.print("Enter search query: ");
        String userQuery = scanner.nextLine();

        //Process the search
        retrieveWordLocations(userQuery, wordMap);
    }
}
