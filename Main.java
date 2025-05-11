//Import necessary libraries
import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;
import WordHashMap.WordHashMap;

public class Main {
    //Converts a string into an array of words
    public static String[] convertToWordArray(String sentence) {
        //remove punctuation and use space as separator
        return sentence.toLowerCase().split("[^a-zA-Z0-9']+");
    }

    //Function to print out the union of lists
    public static void printUnionOfArrays(ArrayList<ArrayList<String>> listOfDocuments) {
        ArrayList<String> result = new ArrayList<>();
        for (ArrayList<String> arraylist : listOfDocuments) {
            for (String documents : arraylist) {
                    if (!result.contains(documents)) {
                        result.add(documents);
                }
            }
        }
        printList(result);
    }
    //Function to print out the intersection of list of docs
    public static void printIntersectionOfArrays(ArrayList<ArrayList<String>> listOfDocuments) {
        //Create list containing all documents neededDocuments
        ArrayList<String> neededDocuments = new ArrayList<>(Arrays.asList("Document 1", "Document 2", "Document 3"));
        for (ArrayList<String> list : listOfDocuments) {
            //Intersect every list of document location of a word to neededDocuments using .retainAll
            neededDocuments.retainAll(list);
            //Stop operation if resulting list is already empty
            if (neededDocuments.isEmpty()) {
                System.out.println("No common documents found.");
                return;
            }
        }
        printList(neededDocuments);
    }
    //Prints all documents in the list
    public static void printList(ArrayList<String> list) {
        if (list == null) {
            System.out.println("Cannot find word in document.");
        }
        else {
            for (String elements : list) {
                System.out.println(elements);
            }
        }
    }
    //Retrieves the location of selected word/s across all documents
    public static void retrieveWordLocations(String userQuery, WordHashMap wordMap) {
        ArrayList<ArrayList<String>> arrayOfDocumentLists = new ArrayList<>();
        if (userQuery.contains("OR") && userQuery.contains("AND")) {
            System.out.println("You can only use one type of logical operator.");
            return;
        }
        if (userQuery.contains(" OR ")) {
            boolean oneWordFound = false;
            //Normalize the array by removing space and punctuations
            String[] words = userQuery.replaceAll("\\s", "").split("OR");
            for (String word : words) {
                word = word.toLowerCase();
                if (wordMap.get(word) != null) {
                    arrayOfDocumentLists.add(wordMap.get(word));
                    oneWordFound = true;
                }
                else {
                    System.out.println("Warning: Word " + word + " not found in documents.");
                }
            }
            if (oneWordFound) {
                printUnionOfArrays(arrayOfDocumentLists);
            }
        }
        else if (userQuery.contains(" AND ")) {
            String[] words = userQuery.replaceAll("\\s", "").split("AND");
            for (String word : words) {
                word = word.toLowerCase();
                if (!wordMap.containsKey(word)) {
                    System.out.println("Word " + word + " not found in documents.");
                    return;
                }
                else {
                    arrayOfDocumentLists.add(wordMap.get(word));
                }
            }
            printIntersectionOfArrays(arrayOfDocumentLists);
        }
        else if (userQuery.contains("AND") || userQuery.contains("OR")) {
            System.out.println("Error with the logical operator.");
        }
        else {
            printList(wordMap.get(userQuery.toLowerCase()));
        }
    }
    //Adds individual words to hashmap along with their corresponding locations
    public static void addToWordMap(String[] wordArray, WordHashMap wordMap,String documentName) {
        for (String word : wordArray) {
            if (wordMap.get(word) != null) {
                wordMap.get(word).add(documentName);
            }
            else {
                wordMap.put(word, new ArrayList<String>(Arrays.asList(documentName)));
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
        return content.toString().trim().toLowerCase();
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
        System.out.println("DOCUMENT SEARCH ENGINE");
        System.out.println("By: Frian Karl Nabo, Red De Guzman, Jedric Tuquero \n");
        while (true) {
            System.out.print("Enter search query: ");
            String userQuery = scanner.nextLine();
            retrieveWordLocations(userQuery, wordMap);
            System.out.print("Would you like to try again? (Y/N): ");
            String choice = scanner.nextLine();
            if (!choice.toUpperCase().equals("Y")) {
                break;
            }
        }

    }
}
