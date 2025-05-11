package WordHashMap;
import java.util.LinkedList;
import java.util.ArrayList;

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
public class WordHashMap {
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
