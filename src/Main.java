//Galiev Ilyas
//from lab 3 materials

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //reading the input from compiler
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        String line = sc.nextLine();

        //Creating map
        Map<String, Integer> map = new HashMap<>(n);

        //Filling the map with keys
        String[] words = line.split(" ");
        for (String word : words) {
            if (map.get(word) == null) {
                map.put(word, 1);
            } else {
                int k = map.get(word) + 1;
                map.put(word, k);
            }
        }

        //Creating set of entries
        List<Entry<String, Integer>> entries = map.entrySet();

        //Applying selection sort to the map members
        // to sort by frequency and lexicography
        n = entries.size();
        for (int i = 0; i < n; i++) {
            Entry<String, Integer> mx = new Entry<>("a", 0);
            for (int j = 0; j < entries.size(); j++){
                if (entries.get(j).value > mx.value){
                    mx = entries.get(j);
                } else if (entries.get(j).value == mx.value){
                    if (entries.get(j).key.compareTo(mx.key) < 0){
                        mx = entries.get(j);
                    }
                }
            }
            System.out.println(mx.key + " " + mx.value);
            entries.remove(entries.indexOf(mx));
        }
    }
}

//Interface for map class
interface Map<K, V> {
    int size();

    boolean isEmpty();

    V get(K key);

    void put(K key, V value);

    void remove(K key);

    List<Entry<K, V>> entrySet();
}
//Class for objects with entry type for mep and set in main.
//Object of this type have key and value.
class Entry<K, V> {
    K key;
    V value;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

//Hashmap data type realisation
class HashMap<K, V> implements Map<K, V> {
    int mapSize;
    int capacity;
    List<Entry<K, V>>[] hashTable;

    public HashMap(int capacity) {
        this.capacity = capacity;
        this.mapSize = 0;
        this.hashTable = new List[capacity];
        for (int i = 0; i < this.capacity; i++) {
            this.hashTable[i] = new LinkedList<>();
        }
    }

    public Entry<K, V> getEntry(K key) {
        int hashcode = Math.abs(key.hashCode()) % capacity;
        for (Entry<K, V> entry : hashTable[hashcode]) {
            if (entry.key.equals(key)) {
                return entry;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return mapSize;
    }

    @Override
    public boolean isEmpty() {
        return size() <= 0;
    }

    @Override
    public V get(K key) {
        int hashcode = Math.abs(key.hashCode()) % capacity;
        for (Entry<K, V> entry : hashTable[hashcode]) {
            if (entry.key.equals(key)) {
                return entry.value;
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        try {
            if (get(key) != null) {
                Entry<K, V> temp = getEntry(key);
                temp.value = value;
            } else {
                int hash = Math.abs(key.hashCode()) % this.capacity;
                hashTable[hash].add(new Entry<>(key, value));
                this.mapSize++;
                if (mapSize > capacity) {
                    throw new Exception();
                }
            }
        } catch (Exception e) {
            System.out.println("Hashtable is full");
            System.exit(1);
        }
    }

    @Override
    public void remove(K key) {
        int hashcode = Math.abs(key.hashCode()) % capacity;
        for (Entry<K, V> entry : hashTable[hashcode]) {
            if (entry.key.equals(key)) {
                hashTable[hashcode].remove(entry);
            }
        }
    }

    @Override
    public List<Entry<K, V>> entrySet() {
        List<Entry<K, V>> entries = new ArrayList<>();
        for (List<Entry<K, V>> list : this.hashTable) {
            for (Entry<K, V> entry : list) {
                entries.add(entry);
            }
        }
        return entries;
    }
}