package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MyHashTableTest {
    private MyHashTable hashTable;

    @BeforeEach
    public void setUp() {
        hashTable = new MyHashTable(11);
    }

    @Test
    public void testUpdateElementExistingKey() {
        hashTable.put(1, "value1");
        Object oldValue = hashTable.updateElement(1, "newValue");

        assertEquals("value1", oldValue);
        assertEquals("newValue", hashTable.get(1));
    }

    @Test
    public void testUpdateElementNewKey() {
        Object result = hashTable.updateElement(2, "value2");

        assertNull(result);
        assertEquals("value2", hashTable.get(2));
    }

    @Test
    public void testUpdateKeyExistingKey() {
        hashTable.put(3, "value3");
        Object updatedElement = hashTable.updateKey(3, 33);

        assertEquals("value3", updatedElement);
        assertNull(hashTable.get(3));
        assertEquals("value3", hashTable.get(33));
    }

    @Test
    public void testUpdateKeyNonExistingKey() {
        Object result = hashTable.updateKey(4, 44);

        assertNull(result);
        assertNull(hashTable.get(4));
        assertNull(hashTable.get(44));
    }

    @Test
    public void testDeleteExistingKey() {
        hashTable.put(5, "value5");
        assertEquals("value5", hashTable.get(5));

        hashTable.delete(5);

        assertNull(hashTable.get(5));
    }

    @Test
    public void testDeleteNonExistingKey() {
        hashTable.delete(6);
        assertNull(hashTable.get(6));
    }

    @Test
    public void testSizeAfterOperations() {
        assertEquals(0, hashTable.size());

        hashTable.put(7, "value7");
        assertEquals(1, hashTable.size());

        hashTable.updateElement(7, "newValue7");
        assertEquals(1, hashTable.size());

        hashTable.updateKey(7, 77);
        assertEquals(1, hashTable.size());

        hashTable.delete(77);
        assertEquals(0, hashTable.size());
    }
}