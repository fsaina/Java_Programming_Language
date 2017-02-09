package hr.fer.zemris.java.tecaj.hw5.db.FieldValueGetters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Interface is part of the strategy methodology used for returning
 * the required value from the StudentRecord object
 */
public interface IFieldValueGetter {

    /**
     * Method return the required String from the StudentRecord object
     * @param record StudentRecord used to return
     * @return String representing the field value
     */
    public String get(StudentRecord record);
}
