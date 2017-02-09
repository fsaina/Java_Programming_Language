package hr.fer.zemris.java.custom.scripting.demo;

import org.junit.Test;

import java.util.EmptyStackException;

import static org.junit.Assert.*;

public class MultistackTests {

    ObjectMultistack multistack = new ObjectMultistack();

    // Integer tests

    @Test
    public void multistackIncerementIntegerTest() throws Exception {
        multistack.push("int", new ValueWrapper(10));
        multistack.peek("int").increment(5);

        assertEquals(15, multistack.pop("int").getValue());
    }

    @Test
    public void multistackDecrementIntegerTest() throws Exception {
        multistack.push("int", new ValueWrapper(10));
        multistack.peek("int").decrement(5);

        assertEquals(5, multistack.pop("int").getValue());
    }

    @Test
    public void multistackMultipltyIntegerTest() throws Exception {
        multistack.push("int", new ValueWrapper(10));
        multistack.peek("int").multiply(5);

        assertEquals(50, multistack.pop("int").getValue());
    }

    @Test
    public void multistackDivideIntegerTest() throws Exception {
        multistack.push("int", new ValueWrapper(10));
        multistack.peek("int").divide(5);

        assertEquals(2, multistack.pop("int").getValue());
    }

    @Test(expected = ArithmeticException.class)
    public void multistackDivideWithZeroIntegerTest() throws Exception {
        multistack.push("int", new ValueWrapper(10));
        multistack.peek("int").divide(0);
    }

    // Double tests

    @Test
    public void multistackIncerementDoubleTest() throws Exception {
        multistack.push("double", new ValueWrapper(10.0));
        multistack.peek("double").increment(5.0);

        assertEquals(15.0, multistack.pop("double").getValue());
    }

    @Test
    public void multistackDecrementDoubleTest() throws Exception {
        multistack.push("double", new ValueWrapper(10.0));
        multistack.peek("double").decrement(5.0);

        assertEquals(5.0, multistack.pop("double").getValue());
    }

    @Test
    public void multistackMultipltyDoubleTest() throws Exception {
        multistack.push("double", new ValueWrapper(10.0));
        multistack.peek("double").multiply(5.0);

        assertEquals(50.0, multistack.pop("double").getValue());
    }

    @Test
    public void multistackDivideDoubleTest() throws Exception {
        multistack.push("double", new ValueWrapper(10.0));
        multistack.peek("double").divide(5.0);

        assertEquals(2.0, multistack.pop("double").getValue());
    }

    @Test(expected = ArithmeticException.class)
    public void multistackDivideWithZeroDoubleTest() throws Exception {
        multistack.push("double", new ValueWrapper(10.0));
        multistack.peek("double").divide(0.0);
    }

    // Integer with Double value tests

    @Test
    public void multistackIncerementIntegerDoubleTest() throws Exception {
        multistack.push("double", new ValueWrapper(10));
        multistack.peek("double").increment(5.0);

        assertEquals(15.0, multistack.pop("double").getValue());
    }

    @Test
    public void multistackDivideIntegerDoubleTest() throws Exception {
        multistack.push("double", new ValueWrapper(10.0));
        multistack.peek("double").divide(5);

        assertEquals(2.0, multistack.pop("double").getValue());
    }

    // String invalid tests

    @Test(expected = RuntimeException.class)
    public void multistackDivideInvalidStringTest() throws Exception {
        multistack.push("string", new ValueWrapper("123.123.0123"));
        multistack.peek("string").divide("5");
    }

    @Test(expected = RuntimeException.class)
    public void multistackDivideInvalidStringWithETest() throws Exception {
        multistack.push("string", new ValueWrapper("123.234EE23"));
        multistack.peek("string").divide("5");
    }

    @Test(expected = RuntimeException.class)
    public void multistackDivideInvalidStringWithCharactersTest() throws Exception {
        multistack.push("string", new ValueWrapper("asdfdsf"));
        multistack.peek("string").divide("5");
    }

    // Stack function tests
    @Test
    public void stackFunctionTest() throws Exception {
        multistack.push("stack1", new ValueWrapper("5"));
        multistack.push("stack2", new ValueWrapper("15"));
        multistack.push("stack1", new ValueWrapper("25"));
        multistack.push("stack2", new ValueWrapper("25"));

        assertEquals("25", multistack.pop("stack1").getValue());
        assertEquals("5", multistack.pop("stack1").getValue());
    }

    @Test(expected = EmptyStackException.class)
    public void stackFunctionEmptyTest() throws Exception {
        multistack.push("stack1", new ValueWrapper("5"));
        multistack.push("stack1", new ValueWrapper("25"));

        assertEquals("25", multistack.pop("stack1").getValue());
        assertEquals("5", multistack.pop("stack1").getValue());
        multistack.pop("stack1").getValue();

    }

    // Integer, Double, String tests

    @Test
    public void multistackDivideValidStringWithDoubleTest() throws Exception {

        multistack.push("stackString", new ValueWrapper("100.0"));
        multistack.peek("stackString").divide(5.0);

        assertEquals(20.0, multistack.pop("stackString").getValue());
    }

    @Test
    public void multistackDivideValidStringWithIntegerTest() throws Exception {

        multistack.push("stackString", new ValueWrapper(50));
        multistack.peek("stackString").divide("5");

        assertEquals(10, multistack.pop("stackString").getValue());
    }
    
 


}
