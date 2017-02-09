package hr.fer.zemris.java.tecaj.hw5.db.FieldValueGetters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Created by filipsaina on 11/04/16.
 */
public class LastNameFieldValueGetter implements IFieldValueGetter {

    /**
     * {@inheritDoc}
     */
    @Override
    public String get(StudentRecord record) {
        return record.getLastName();
    }
}
