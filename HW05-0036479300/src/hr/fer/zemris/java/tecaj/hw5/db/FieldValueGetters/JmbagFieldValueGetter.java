package hr.fer.zemris.java.tecaj.hw5.db.FieldValueGetters;

import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;

/**
 * Jmbag object class value getter class
 */
public class JmbagFieldValueGetter implements IFieldValueGetter {

    /**
     * {@inheritDoc}
     */
    @Override
    public String get(StudentRecord record) {
        return record.getJmbag();
    }
}
