package main;

import dataStructures.HashTable;

public class MyHashTable extends HashTable {

    public MyHashTable(int theDivisor) {
        super(theDivisor);
    }

    public Object updateElement(Object theKey, Object theElement) {
        Object oldElement = get(theKey);
        put(theKey, theElement);
        return oldElement;
    }

    public Object updateKey(Object theKey, Object theNewKey) {
        Object elementToUpdate = get(theKey);

        if (elementToUpdate != null) {
            deleteUsingRehash(theKey);
            put(theNewKey, elementToUpdate);
            return elementToUpdate;
        }

        return null;
    }

    public void delete(Object theKey) {
        deleteUsingRehash(theKey);
    }

    private void deleteUsingRehash(Object theKey) {
        if (get(theKey) == null) return;

        java.util.ArrayList<Object> keys = new java.util.ArrayList<>();
        java.util.ArrayList<Object> values = new java.util.ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Object testKey = i;
            Object value = get(testKey);
            if (value != null && !testKey.equals(theKey)) {
                keys.add(testKey);
                values.add(value);
            }
        }

        for (int i = 0; i < divisor; i++) {
            table[i] = null;
        }
        size = 0;

        for (int i = 0; i < keys.size(); i++) {
            put(keys.get(i), values.get(i));
        }
    }
}