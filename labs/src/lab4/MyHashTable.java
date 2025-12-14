package lab4;

import dataStructures.HashTable;

import java.util.ArrayList;

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
            delete(theKey);
            put(theNewKey, elementToUpdate);
            return elementToUpdate;
        }

        return null;
    }

    public void delete(Object theKey) {
        if(get(theKey)== null) return;

        ArrayList<Object> keys = new ArrayList<>();
        ArrayList<Object> values = new ArrayList<>();

        for (int i = 1; i <= divisor; i++) {
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