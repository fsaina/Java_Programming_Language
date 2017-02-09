package cstring.hr.fer.zemris.java.cstr;

import hr.fer.zemris.java.cstr.CString;
import org.junit.Assert;
import org.junit.Test;

/**
 * Junit testing class for the CString class implementation
 */
public class CStringTest {

    char[] field = {'t', 'e', 's', 't'};
    CString string = new CString(field);


    @Test(expected = IllegalArgumentException.class)
    public void charAtUnexistingElement() throws Exception {
        string.charAt(4);
    }

    @Test
    public void charAtExistingElement() throws Exception {
        Assert.assertEquals('t', string.charAt(3));
    }

    @Test
    public void indexOfAExistingCharacter() throws Exception {
        Assert.assertEquals(1, string.indexOf('e'));
    }

    @Test
    public void indexOfUnexistingCharacter() throws Exception {
        Assert.assertEquals(-1, string.indexOf('m'));
    }

    @Test
    public void testDataStructureLength() throws Exception {
        Assert.assertEquals(4, string.length());
    }

    @Test
    public void startsWithCorrectSubstring() throws Exception {
        char[] testField = {'t', 'e', 's'};
        CString testString = new CString(testField);

        Assert.assertTrue(string.startsWith(testString));
    }

    @Test
    public void startsWithIncorrectSubstring() throws Exception {
        char[] testField = {'t', 'e', 'x'};
        CString testString = new CString(testField);

        Assert.assertFalse(string.startsWith(testString));
    }

    @Test(expected = IllegalArgumentException.class)
    public void startsWithNullPointerGiven() throws Exception {
        string.startsWith(null);
    }

    @Test
    public void endsWithCorrectSubstring() throws Exception {
        char[] testField = {'s', 't'};
        CString testString = new CString(testField);

        Assert.assertTrue(string.endsWith(testString));
    }

    @Test
    public void endsWithIncorrectSubstring() throws Exception {
        char[] testField = {'t', 'e', 'x'};
        CString testString = new CString(testField);

        Assert.assertFalse(string.endsWith(testString));
    }

    @Test(expected = IllegalArgumentException.class)
    public void endsWithNullPointerGiven() throws Exception {
        string.endsWith(null);
    }

    @Test
    public void endsWithTooBigComparisonString() throws Exception {
        char[] testField = {'t', 'e', 's', 't', 't', 'e', 's', 't'};
        CString testString = new CString(testField);

        Assert.assertFalse(string.endsWith(testString));
    }

    @Test
    public void containsSubstringTrueSubstring() throws Exception {
        char[] testField = {'e', 's'};
        CString testString = new CString(testField);

        Assert.assertTrue(string.contains(testString));
    }

    @Test
    public void containsSubstringFalseSubstring() throws Exception {
        char[] testField = {'x', 'y'};
        CString testString = new CString(testField);

        Assert.assertFalse(string.contains(testString));
    }

    @Test(expected = IllegalArgumentException.class)
    public void containsSubstringNullGiven() throws Exception {
        string.contains(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void substringTestEndIndexOutOfBounds() throws Exception {
        string.substring(2, 10);
    }

    @Test
    public void substringValid() throws Exception {
        char[] testField = {'e', 's', 't'};
        CString testString = new CString(testField);

        //substring should contain 'est'
        Assert.assertEquals(testString, string.substring(1, 4));
    }

    @Test
    public void indexOfSubstringElement() throws Exception {
        //get substring 'te'
        CString testString = string.substring(0, 2);

        Assert.assertEquals('e', testString.charAt(1));
    }

    @Test
    public void leftTestNewSubstring() throws Exception {
        char[] testField = {'t', 'e', 's'};
        CString testString = new CString(testField);

        Assert.assertEquals(testString, string.left(3));
    }

    @Test
    public void leftTestNewSubstringFromSubstring() throws Exception {
        //substring expected is 'tes'
        CString testString = string.substring(0, 3);

        Assert.assertEquals(testString, string.left(3));
    }

    @Test
    public void rightTestNewSubstringFromSubstring() throws Exception {
        //substring expected is 'st'
        CString testString = string.substring(2,4);

        Assert.assertEquals(testString, string.right(2));
    }

    @Test
    public void addTest() throws Exception {
        char[] endField = {'t', 'e', 's', 't','r', 'a', 'n', 'd', 'o', 'm'};
        char[] testField = {'r', 'a', 'n', 'd', 'o', 'm'};

        CString endString = new CString(endField);
        CString testString = new CString(testField);

        Assert.assertEquals(endString, string.add(testString));
    }

    @Test
    public void toStringTestForSubstringConversion() throws Exception {
        //substring is 'est'
        CString testString = string.substring(1, 4);

        Assert.assertEquals("est", testString.toString());
    }

    @Test
    public void substringFromASubstringTest() throws Exception {

        //substring is 'est'
        CString testString = string.substring(1, 4);
        CString subTestString = testString.right(2);
        String subTestStringS = subTestString.toString();

        Assert.assertEquals( "st" , subTestStringS);
    }

    @Test
    public void replaceAllCharacterTest() throws Exception {
        CString replaced = string.replaceAll('t', 'm');
        Assert.assertEquals("mesm", replaced.toString());
    }

    @Test
    public void replaceAllStringTest() throws Exception {
        char[] endField = {'t', 'e', 's', 't','r', 'a', 'n', 's', 't', 'm','t', 'e', 's', 't'};
        char[] testField = {'t', 'e' , 's', 't'};
        char[] testFieldEnd = {'A'};

        CString endString = new CString(endField);
        CString testString = new CString(testField);
        CString testStringEnd = new CString(testFieldEnd);

        Assert.assertEquals("AranstmA", endString.replaceAll(testString, testStringEnd).toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void replaceAllStringTestForNull() throws Exception {
        string.replaceAll(null, null);
    }

    @Test
    public void replaceAllStringTestFromAssignment() throws Exception {
        CString result = CString.fromString("ababab").replaceAll(CString.fromString("ab"), CString.fromString("abab"));
        Assert.assertEquals(CString.fromString("abababababab"), result);
    }
}
