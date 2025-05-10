//Import necessary libraries
import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import WordHashMap.WordHashMap;

public class Main {
    //Converts a string into an array of words
    public static String[] convertToWordArray(String sentence) {
        //remove punctuation and use space as separator
        return sentence.replaceAll("[^a-zA-Z ]", "").split(" ");
    }

    //Function to print out the union of hashsets
    public static void printUnionOfSets(ArrayList<HashSet<String>> setOfDocuments) {
        HashSet<String> result = new HashSet<>();
        //Since hashsets ignore duplicate values, we can just add individual elements to it
        for (HashSet<String> hashset : setOfDocuments) {
            for (String documents : hashset) {
                result.add(documents);
            }
        }
        printSet(result);
    }
    //Function to print out the intersection of hashsets of docs
    public static void printIntersectionOfSets(ArrayList<HashSet<String>> setOfDocuments) {
        //Create hashset of all documents
        HashSet<String> neededDocuments = new HashSet<>(Arrays.asList("Doc1", "Doc2", "Doc3"));
        for (HashSet<String> hashset : setOfDocuments) {
            //Intersect this hashset of document collection to all sets using .retainAll
            neededDocuments.retainAll(hashset);
        }
        //Print error if no common documents
        if (neededDocuments.isEmpty()) {
            System.out.println("No common documents found.");
        }
        printSet(neededDocuments);
    }
    //Prints hashset
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
    //Retrieves the location of selected word/s across all documents
    public static void retrieveWordLocations(String userQuery, WordHashMap wordMap) {
        ArrayList<HashSet<String>> arrayOfDocumentSets = new ArrayList<>();
        if (userQuery.contains("OR")) {
            //Normalize the array by removing space and punctuations
            String[] words = userQuery.split(" OR ");
            for (String word : words) {
                if (!wordMap.containsKey(word)) {
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
                if (!wordMap.containsKey(word)) {
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
    //Adds individual words to hashmap along with their corresponding locations
    public static void addToWordMap(String[] wordArray, WordHashMap wordMap,String documentName) {
        for (String word : wordArray) {
            if (wordMap.get(word) != null) {
                wordMap.get(word).add(documentName);
            }
            else {
                wordMap.put(word, new HashSet<>(Arrays.asList(documentName)));
            }
        }
    }

    public static String readDocument(String filename) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(" ");
            }
        } catch (IOException e) {
            System.out.println("Cannot find document: " + filename);
        }
        return content.toString().trim();
    }



    public static void main(String args[]) {
        WordHashMap wordMap = new WordHashMap(50);
        Scanner scanner = new Scanner(System.in);
        //Sample hashmap
        String[] docFiles = {"Doc1.txt", "Doc2.txt", "Doc3.txt"};
        String[] docLabels = {"Document 1", "Document 2", "Document 3"};

        for (int i = 0; i < docFiles.length; i++) {
            String content = readDocument(docFiles[i]);
            String[] words = convertToWordArray(content);
            addToWordMap(words, wordMap, docLabels[i]);
        }

        //Get search query from user
        System.out.print("Enter search query: ");
        String userQuery = scanner.nextLine();

        //Process the search
        retrieveWordLocations(userQuery, wordMap);
    }
}
