//Import necessary libraries
import java.io.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.LinkedList;

/*This is a custom implementation of a hash map that stores String keys and associated sets of document names
 as values. It uses an array of linked lists to handle collisions through separate chaining. It supports basic
 operations like put, get, containsKey, size, and print. */

//This is the object that would be stored in the hashmap
class WordEntry {
    private final String key;
    private ArrayList<String> value;

    public WordEntry(String key, ArrayList<String> value) {
        this.key = key;
        this.value = value;
    }
    public String getKey() {
        return key;
    }
    //No setter for key because it will not be modified

    public ArrayList<String> getValue() {
        return value;
    }

    public void setValue(ArrayList<String> value) {
        this.value = value;
    }
}

//Main hashmap implementation
class WordHashMap {
    //An array of linked lists will serve as the hashmap in order for separate chaining to be utilized
    private LinkedList<WordEntry>[] table;
    private int mapCapacity;
    private int size;

    //Constructs hashmap with a specified capacity
    public WordHashMap(int initialCapacity) {
        this.mapCapacity = initialCapacity;
        this.table = new LinkedList[initialCapacity];
        this.size = 0;
    }
    //The Hash function that generates the index used to navigate the array of linked lists
    private int hash(String key) {
        if (key == null) {
            return 0;
        }
        return Math.abs(key.hashCode()) % mapCapacity;
    }
    //Adds an entry to the hashmap
    public void put(String key, ArrayList<String> value) {
        //We call a slot in the linked list array a "bucket"
        int bucketIndex = hash(key);
        //Initialize new linked list if a bucket is empty
        if (table[bucketIndex] == null) {
            table[bucketIndex] = new LinkedList<>();
        }
        LinkedList<WordEntry> bucket = table[bucketIndex];
        for (WordEntry entry : bucket) {
            //Update value if the key is already present
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
        }
        //If the key wasn’t found, adds a new node (key-value pair) to the end of the list and resolve collision
        bucket.add(new WordEntry(key, value));
        size++;
    }

    //Returns value of a key in the hashmap
    public ArrayList<String> get(String key) {
        if (key == null) {
            return null;
        }
        int bucketIndex = hash(key);
        LinkedList<WordEntry> bucket = table[bucketIndex];

        if (bucket != null) {
            //Loop through the linked list and if the key is present, return its value
            for (WordEntry entry : bucket) {
                if (entry.getKey().equals(key)) {
                    return entry.getValue();
                }
            }
        }
        //Return null if key is not present
        return null;
    }
    //Returns true if key is present in the hashmap
    public boolean containsKey(String key) {
        int bucketIndex = hash(key);
        LinkedList<WordEntry> bucket = table[bucketIndex];
        if (key == null || bucket == null) {
            return false;
        }
        else {
            for (WordEntry entry : bucket) {
                if (entry.getKey().equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }
    //For debugging purposes
    public int size() {
        return size;
    }

    //Prints out the entire hash table, for debugging purposes
    public void print() {
        for (LinkedList<WordEntry> linkedListEntry : table) {
            if (linkedListEntry == null) {
                continue;
            }
            for (WordEntry entry : linkedListEntry) {
                System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
            }
        }
    }


}

public class MiniSearchEngine {
    //List of documents you want to read. Make sure they are present in the same folder where Main.java is located
    private static String[] docFiles = {"Doc1.txt", "Doc2.txt", "Doc3.txt"};
    //Label of documents that will be outputted in the search engine. Make sure it is the same length as docFiles
    private static String[] docLabels = {"Document 1", "Document 2", "Document 3"};

    //Reads the document file with the corresponding filename
    private static String readDocument(String filename) {
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

    //Converts a string into an array of words
    private static String[] convertToWordArray(String sentence) {
        //remove punctuation and use space as separator
        return sentence.toLowerCase().split("[^a-zA-Z0-9']+");
    }

    //Function to print out the union of lists
    private static void printUnionOfArrays(ArrayList<ArrayList<String>> listOfDocuments) {
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
    private static void printIntersectionOfArrays(ArrayList<ArrayList<String>> listOfDocuments) {
        //Create list containing all documents neededDocuments
        ArrayList<String> neededDocuments = new ArrayList<>(Arrays.asList(docLabels));
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
    private static void printList(ArrayList<String> list) {
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
    private static void retrieveWordLocations(String userQuery, WordHashMap wordMap) {
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
                //Checks if one word in the query does not exist in any document
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
    private static void addToWordMap(String[] wordArray, WordHashMap wordMap,String documentName) {
        for (String word : wordArray) {
            if (wordMap.get(word) != null && !wordMap.get(word).contains(documentName)) {
                wordMap.get(word).add(documentName);
            }
            else if (wordMap.get(word) == null) {
                wordMap.put(word, new ArrayList<String>(Arrays.asList(documentName)));
            }
        }
    }

    public static void main(String args[]) {
        WordHashMap wordMap = new WordHashMap(50);
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < docFiles.length; i++) {
            String content = readDocument(docFiles[i]);
            String[] words = convertToWordArray(content);
            addToWordMap(words, wordMap, docLabels[i]);
        }
        //Get search query from user
        System.out.println("DOCUMENT SEARCH ENGINE");
        System.out.println("By: Duncan Red Benedict De Guzman, Frian Karl Nabo, Jedric Tuquero \n");
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
