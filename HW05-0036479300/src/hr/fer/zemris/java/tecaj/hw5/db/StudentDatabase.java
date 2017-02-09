package hr.fer.zemris.java.tecaj.hw5.db;

import hr.fer.zemris.java.tecaj.hw5.collections.SimpleHashtable;

import java.util.*;


/**
 * Student database model representation class
 */
public class StudentDatabase {

    /**
     * Internal storage used for fast retreaval of the given objects by JMBAG string
     */
    private SimpleHashtable<String, StudentRecord> index = new SimpleHashtable<>();

    /**
     * Internal object used for retrieval of objects by any criteria
     */
    private List<StudentRecord> records;

    /**
     * Default StudentDatabase construtor
     *
     * @param records list of read strings to be added to the database as Student elements
     */
    public StudentDatabase(List<String> records) {

        this.records = new ArrayList<>();

        //store the data to index
        for (String line : records) {
            storeDataToIndex(line);
        }

    }

    /*
     * private method used for reading one line of the data structure
     * as a String, isolating data parts and adding it to the internal
     * representation of the data structure.
     */
    private void storeDataToIndex(String line) {

        StudentRecord studentRecord = null;

        String list[] = line.split("\\s+");

        if (list.length == 4) {

            studentRecord = new StudentRecord(list[0], list[1], list[2], list[3]);

        } else if (list.length == 5) {

            String lastName = list[1] + " " + list[2];
            studentRecord = new StudentRecord(list[0], lastName, list[3], list[4]);

        } else {
            throw new DatabaseFileFormatException("Format of the database is illegal");
        }

        index.put(list[0], studentRecord);
        records.add(studentRecord);
    }

    /**
     * Method used for fast O(1) retrieval of elements by jmbag String
     *
     * @param jmbag String key to retrieve value
     * @return vaule of the given key String
     */
    public StudentRecord forJMBAG(String jmbag) {
        return index.get(jmbag);
    }

    public List<StudentRecord> filter(IFilter filter) {
        List<StudentRecord> returnList = new ArrayList<StudentRecord>();

        //check every entry in the 'database'
        for (StudentRecord entry : records) {
            if (filter.accepts(entry))
                returnList.add(entry);
        }

        return returnList;
    }

}
