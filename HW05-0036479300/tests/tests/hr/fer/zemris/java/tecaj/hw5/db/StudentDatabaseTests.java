package tests.hr.fer.zemris.java.tecaj.hw5.db;

import hr.fer.zemris.java.tecaj.hw5.db.QueryFilter;
import hr.fer.zemris.java.tecaj.hw5.db.QueryFormatException;
import hr.fer.zemris.java.tecaj.hw5.db.StudentRecord;
import org.junit.Assert;
import org.junit.Test;

public class StudentDatabaseTests {

    QueryFilter queryFilter;
    StudentRecord studentRecord = new StudentRecord("0000000002", "Bakamović", "Petra", "3");
    StudentRecord studentRecord2 = new StudentRecord("0000000003", "Bosnić", "Andrea", "4");

    @Test
    public void testDatabaseLastNameQuery() throws Exception {
        queryFilter = new QueryFilter("lastName LIKE \"Ba*\"");
        Assert.assertTrue(queryFilter.accepts(studentRecord));
    }

    @Test
    public void testDatabaseFirstNameQuery() throws Exception {
        queryFilter = new QueryFilter("firstName LIKE \"Pet*\"");
        Assert.assertTrue(queryFilter.accepts(studentRecord));
    }

    @Test(expected = QueryFormatException.class)
    public void testDatabaseLastNameWithCase() throws Exception {
        queryFilter = new QueryFilter("lastname LIKE \"Ba*\"");
        Assert.assertTrue(queryFilter.accepts(studentRecord));
    }

    @Test
    public void testDatabaseLastNameCompressedButCorrect() throws Exception {
        queryFilter = new QueryFilter("lastName<\"B\"");
    }

    //ERROR
    @Test(expected = QueryFormatException.class)
    public void testDatabaseWrongQueryFormat() throws Exception {
        queryFilter = new QueryFilter("firstName LIKE \"Pet*\" lastName = \"Jesica\" ");
    }

    @Test
    public void testDatabaseQueryFormatWithSpaces() throws Exception {
        queryFilter = new QueryFilter("lastName      LIKE \t\t \"Ba*\"");
        Assert.assertTrue(queryFilter.accepts(studentRecord));
    }

    @Test
    public void testDatabaseQueryFormatWithUpperLowerCaseAnd() throws Exception {
        queryFilter = new QueryFilter("lastName LIKE \"B*ć\" aNd jmbag>\"0000000002\"");
        Assert.assertTrue(queryFilter.accepts(studentRecord2));
    }

    @Test(expected = QueryFormatException.class)
    public void testDatabaseInvalidOperatorProvided() throws Exception {
        queryFilter = new QueryFilter("firstName!\"Pet*\"");
    }

}
