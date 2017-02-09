package hr.fer.zemris.java.cstr;

import java.util.Arrays;

/**
 * CString class representing the String data model. Each element of the string is stored in a character
 * field.
 */
public class CString {

    /**
     * Internal data structure to store the string information
     */
    private final char[] data;

    /**
     * Offset of the stored String element
     */
    private final int offset;

    /**
     * String length to be used when referencing a common object in memory
     */
    private final int length;

    /**
     * Constructor for the CString object model.
     *
     * @param data   non null reference of the data field
     * @param offset offset for the CString object in memory
     * @param length size of the CString object
     */
    public CString(char[] data, int offset, int length) {
        if (data == null)
            throw new IllegalArgumentException("Char field reference can't be null");
        if (offset < 0 || offset > data.length - 1)
            throw new IllegalArgumentException("Offset cant be outside of the scope of data");
        if (length < 0 || length > data.length)
            throw new IllegalArgumentException("Length can't be negative or outside the scope of the data");
        if (offset + length > data.length)
            throw new IllegalArgumentException("Offset added with length value can't be greater than the length of the data field");

        this.data = data;
        this.offset = offset;
        this.length = length;

    }

    /**
     * Constructor that makes a CString object from a character array
     *
     * @param data character array to store data
     */
    public CString(char[] data) {
        this(data, 0, data.length);
    }

    /**
     * Constructor to the new String object in memory. If the original string differs in length from its
     * internal data structure (e.g. a substring) then copy a new instance of ONLY the elements required and
     * returns them as a new CString object.
     *
     * @param original CString object to be passed
     */
    public CString(CString original) {
        if (original == null)
            throw new IllegalArgumentException("Given reference cannot be null pointer");

        if (original.data.length != length()) {
            //allocate new

            this.data = original.toCharArray();
            this.offset = 0;
            this.length = this.data.length;

        } else {
            //keep the common reference

            this.data = original.data;
            this.offset = original.offset;
            this.length = original.length;

        }
    }

    /**
     * Static method for converting the given string to a new CString object reference
     *
     * @param s String object to be copyed
     * @return new CString object representation of the original String
     */
    public static CString fromString(String s) {
        return new CString(s.toCharArray(), 0, s.length());
    }

    /**
     * Size of the current CString object
     *
     * @return size of object
     */
    public int length() {
        return length;
    }

    /**
     * Method returns the char element at a index point in the inrenal data structure
     *
     * @param index position of the char to be returned
     * @return char to return
     */
    public char charAt(int index) {
        if (index < 0 || index >= length)
            throw new IllegalArgumentException("Requested position of the char is out of bounds");
        return data[offset + index];
    }

    /**
     * Convert current CString object in a char array
     *
     * @return char array of the CString
     */
    public char[] toCharArray() {
        return Arrays.copyOfRange(data, offset, offset + length);
    }

    /**
     * Convert the object to a String representation
     *
     * @return String of the data stored
     */
    public String toString() {
        return new String(toCharArray());
    }

    /**
     * Method returns the first ocurance of a given character. Non-destructive read.
     *
     * @param c char to be searched
     * @return index of the element inside field
     */
    public int indexOf(char c) {

        for (int i = 0; i < length; i++) {
            char currentChar = charAt(i);
            if (currentChar == c)
                return i;
        }

        return -1;
    }

    /**
     * Method tests if the current String starts with a given string
     *
     * @param string CString object to be compared with
     * @return true if start, false otherwise
     */
    public boolean startsWith(CString string) {
        if (string == null)
            throw new IllegalArgumentException("Argument CString object can't be a null reference");
        if (string.length > length)
            return false;

        for (int i = 0, size = string.length; i < size; i++) {
            if ((string.charAt(i) ^ charAt(i)) != 0)
                return false;
        }

        return true;
    }

    /**
     * Method tests if the current CString object starts with a given CString object instance
     *
     * @param string CString to be tested
     * @return true if ends, false otherwise
     */
    public boolean endsWith(CString string) {
        if (string == null)
            throw new IllegalArgumentException("Argument CString object can't be a null reference");
        if (string.length > length)
            return false;

        for (int endOffset = length - string.length, i = 0; endOffset + i != length; i++) {
            if ((string.charAt(i) ^ charAt(endOffset + i)) != 0)
                return false;
        }

        return true;
    }

    /**
     * Method contains tests the current string if a occurance of the given is found within.
     *
     * @param string String to be tested with.
     * @return boolean, true if contains, false otherwise
     */
    public boolean contains(CString string) {
        if (string == null)
            throw new IllegalArgumentException("Argument provided can't be null");

        for (int i = 0; i < length(); i++) {
            int len = 0;
            while (charAt(i + len) == string.charAt(len)) {
                len++;
                if (len == string.length)
                    return true;
            }

        }

        return false;
    }

    /**
     * Method references the new elements form with the elements in the original string data
     *
     * @param startIndex initial index placement
     * @param endIndex   end index placement, exclusive
     * @return CString representation of the initial object
     */
    public CString substring(int startIndex, int endIndex) {
        if (startIndex < 0)
            throw new IllegalArgumentException("StartIndex argument cant be negative value");
        if (endIndex <= startIndex)
            throw new IllegalArgumentException("End index can't be smaller or equal than startIndex");
        if (endIndex > length + 1)
            throw new IllegalArgumentException("EndIndex cannot be greater than the length of the string");

        return new CString(data, startIndex, endIndex - startIndex);
    }

    /**
     * Method returns new CString object containing the required substring from start. No new memory is
     * allocated.
     *
     * @param n number of element to obtain from the start of the string
     * @return CString object representign the required string
     */
    public CString left(int n) {
        return substring(offset, offset + n);
    }

    /**
     * Method returns new CString object containing the required substring from end. No new memory is
     * allocated.
     *
     * @param n number of element to obtain from the end of the string
     * @return CString object representation the required string
     */
    public CString right(int n) {
        return substring(offset + length - n, offset + length);
    }

    /**
     * Method concatinates the given string object to the existing one. New memory is allocated for keeping
     * them both.
     *
     * @param string String to be concatinated
     * @return CString object representation
     */
    public CString add(CString string) {
        if (string == null)
            throw new IllegalArgumentException("Given argument can't be null reference");

        int index = 0;
        char[] newArray = new char[length + string.length()];

        //copy current string
        for (index = 0; index < length; index++) {
            newArray[index] = charAt(index);
        }

        //copy the given string
        for (index = 0; index < string.length(); index++) {
            newArray[length + index] = string.charAt(index);
        }

        return new CString(newArray);
    }

    /**
     * Method creates a new CString in memory with all of its information. If the oldChar is not found, method
     * copies the original string.
     *
     * @param oldChar Character to be replaced
     * @param newChar Character to be replaced with
     * @return CString object representing the String object
     */
    public CString replaceAll(char oldChar, char newChar) {
        char[] newCharFiled = toCharArray();

        for (int i = 0; i < length; i++) {
            if (newCharFiled[i] == oldChar)
                newCharFiled[i] = newChar;
        }
        return new CString(newCharFiled);
    }

    /**
     * Method replaces all substrings withing the string equaling the oldString with the newString characters.
     * If no oldString are found the string is copied.
     *
     * @param oldString characters to be replaced
     * @param newString characters to replace with
     * @return CString object of the new Object representation
     */
    public CString replaceAll(CString oldString, CString newString) {
        if (oldString == null || newString == null)
            throw new IllegalArgumentException("Arguments provided can't be null");

        char[] newArrayField = toCharArray();

        StringBuilder sb = new StringBuilder();

        //replace the new char array
        for (int i = 0; i < newArrayField.length; i++) {
            //for every element in the array
            int len = 0;
            boolean append = false;

            //if not the required string, copy it to string builder
            if (newArrayField[i + len] != oldString.charAt(len)) {
                sb.append(newArrayField[i]);
                continue;
            }

            //else, start checking if the string is oldString
            while (newArrayField[i + len] == oldString.charAt(len)) {
                len++;
                if (len == oldString.length) {
                    //copy the new String
                    sb.append(newString);
                    append = true;
                    //it was the required string, update i accordingly
                    i += oldString.length() - 1;
                    break;
                }

            }

            if (!append)
                sb.append(newArrayField[i]);

        }
        return new CString(sb.toString().toCharArray());
    }

    /**
     * CString object definition of the equals method. Two Strings are equal if they contain the same amount
     * of information regarding the characters they contain.
     *
     * @param obj object to be tested
     * @return true if the are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof CString))
            return false;
        CString object = (CString) obj;

        for (int i = 0; i < length; i++) {
            if (charAt(i) != object.charAt(i))
                return false;
        }

        return true;
    }
}
