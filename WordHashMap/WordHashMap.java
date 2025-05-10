package WordHashMap;
import java.util.LinkedList;
import java.util.HashSet;

class WordEntry {
    private String key;
    private HashSet<String> value;
    WordEntry next;

    public WordEntry(String key, HashSet<String> value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }

    public String getKey() {
        return key;
    }

    public HashSet<String> getValue() {
        return value;
    }

    public void setValue(HashSet<String> value) {
        this.value = value;
    }
}

public class WordHashMap {
    //An array of linked lists serves as the hash table
    private LinkedList<WordEntry>[] table;
    private int mapCapacity;
    private int size;

    public WordHashMap(int initialCapacity) {
        this.mapCapacity = initialCapacity;
        this.table = new LinkedList[initialCapacity];
        this.size = 0;
    }

    private int hash(String key) {
        if (key == null) {
            return 0;
        }
        return Math.abs(key.hashCode()) % mapCapacity;
    }

    public void put(String key, HashSet<String> value) {
        int bucketIndex = hash(key);
        //Create new linked list if a bucket is empty
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
        bucket.add(new WordEntry(key, value));
        size++;
    }

    public HashSet<String> get(String key) {
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

    public boolean containsKey(String key) {
        if (key == null) {
            return false;
        }
        else {
            int bucketIndex = hash(key);
            LinkedList<WordEntry> bucket = table[bucketIndex];
            for (WordEntry entry : bucket) {
                if (entry.getKey().equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }
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
