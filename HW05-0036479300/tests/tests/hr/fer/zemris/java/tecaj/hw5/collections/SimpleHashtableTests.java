package tests.hr.fer.zemris.java.tecaj.hw5.collections;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable;
import org.junit.Assert;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleHashtableTests {

    SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);

    @Test(expected = IllegalStateException.class)
    public void testMultipleHashtableIteratorRemoval() throws Exception {
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        examMarks.put("Kristina", 5);
        examMarks.put("Ivana", 5);

        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
        while (iter.hasNext()) {
            SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
            if (pair.getKey().equals("Ivana")) {
                iter.remove();
                iter.remove();
            }
        }
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testOuterMethodModifiesHashtable() throws Exception {
        examMarks.put("Ivana", 2);

        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
        while (iter.hasNext()) {
            SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
            if (pair.getKey().equals("Ivana")) {
                examMarks.remove("Ivana");
            }
        }

    }

    @Test
    public void testRemovalFromHashtableValidForm() throws Exception {
        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
        while (iter.hasNext()) {
            SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
            if (pair.getKey().equals("Ivana")) {
                iter.remove(); // sam iterator kontrolirano uklanja trenutni element }
            }
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void testEmptyHashtableToReturnNext() throws Exception {

        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
        SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
    }

    @Test
    public void testEmptyHashtableToReturnHasNext() throws Exception {

        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
        boolean pair = iter.hasNext();

        Assert.assertFalse(pair);
    }

    @Test
    public void testHashtableCallingJustNextOnIterator() throws Exception {

        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);

        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();

        Assert.assertEquals("Ante", iter.next().getKey());
        Assert.assertEquals("Ivana", iter.next().getKey());
        Assert.assertEquals("Jasna", iter.next().getKey());

    }

    @Test
    public void testHashtableCallingHasnext() throws Exception {

        examMarks.put("Ante", 2);

        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();


        Assert.assertTrue(iter.hasNext());
        Assert.assertEquals("Ante", iter.next().getKey());
        Assert.assertFalse(iter.hasNext());

    }


    @Test
    public void testHashtableSize() throws Exception {
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.put("Jasna", 2);
        Assert.assertEquals(3, examMarks.size());
    }

    @Test
    public void testHashtableSizeWithRapidAddAndRemoval() throws Exception {
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.remove("Ivana");
        Assert.assertEquals(1, examMarks.size());
    }

    @Test
    public void testHashtableContinousRemovalOfElements() throws Exception {
        Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
        while (iter.hasNext()) {
            iter.remove();
        }
        Assert.assertEquals(0, examMarks.size());
    }

    @Test
    public void testHastableSizeGeneration() throws Exception {
        examMarks = new SimpleHashtable<>(30);
        Assert.assertTrue(examMarks.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHashtableGetNullReferencePassed() throws Exception {
        examMarks.get(null);
    }

    @Test
    public void testHashtableClearMethod() throws Exception {
        examMarks.put("Ivana", 2);
        examMarks.put("Ante", 2);
        examMarks.clear();
        Assert.assertTrue(examMarks.isEmpty());
    }

    @Test
    public void testHashtableIfContainsUnexistingValue() throws Exception {
        Assert.assertFalse(examMarks.containsValue(2));
    }

    @Test
    public void testHashtableIfContainsExistingValue() throws Exception {
        examMarks.put("Ivana", 2);
        Assert.assertTrue(examMarks.containsValue(2));
    }

    @Test
    public void testHashtableIfContainsUnexistingKey() throws Exception {
        Assert.assertFalse(examMarks.containsKey("Ivana"));
    }

    @Test
    public void testHashtableIfContainsExistingKey() throws Exception {
        examMarks.put("Ivana", 2);
        Assert.assertTrue(examMarks.containsKey("Ivana"));
    }

}
