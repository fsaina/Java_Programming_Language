package hr.fer.zemris.java.tecaj.hw5.db.FieldValueGetters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Class implementation of the FirstName element variable in database
 */
public class FirstNameFieldValueGetter implements IFieldValueGetter {

    /**
     * {@inheritDoc}
     */
    @Override
    public String get(StudentRecord record) {
        return record.getFirstName();
    }
}
