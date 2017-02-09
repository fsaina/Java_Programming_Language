package hr.fer.zemris.java.custom.scripting.demo;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Object multi-stack data structure implementation class. Data structure supports adding multiple values for
 * a given unique key String value.
 */
public class ObjectMultistack {

    /**
     * Internal implementation of the map stack object
     */
    private Map<String, LinkedList<MultistackEntry>> stackMap = new HashMap<>();

    /**
     * Method pushes the given valueWrapper value to a stack by the String name
     *
     * @param name         Key String value of the stack to be pushed
     * @param valueWrapper ValueWrapper class to be stored on stack
     */
    public void push(String name, ValueWrapper valueWrapper) {
        testVariableIfNull(name);
        testVariableIfNull(valueWrapper);

        LinkedList<MultistackEntry> stack = stackMap.get(name);

        if (stack == null) {
            //create a new entry
            LinkedList<MultistackEntry> entryNew = new LinkedList<>();
            entryNew.add(new MultistackEntry(valueWrapper, null));


            stackMap.put(name, entryNew);
        } else {
            stack.add(new MultistackEntry(valueWrapper, stack.get(stack.size() - 1)));
        }
    }

    /**
     * Method pops the next element from the object stack with the key 'name'
     *
     * @param name non-null key value of the stack to be poped
     * @return ValueWrapper from the stack
     *
     * @throws EmptyStackException if the requested stacks is empty
     */
    public ValueWrapper pop(String name) {
        testVariableIfNull(name);

        if (isEmpty(name))
            throw new EmptyStackException();

        LinkedList<MultistackEntry> entry = stackMap.get(name);

        ValueWrapper value = entry.get(entry.size() - 1).getValue();
        entry.remove(entry.size() - 1);

        return value;
    }

    /**
     * Method peeks the next value to be pooped from the stack with the Key 'name'
     *
     * @param name key of the stack
     * @return value from the stack
     *
     * @throws EmptyStackException if the requested stacks is empty
     */
    public ValueWrapper peek(String name) {
        testVariableIfNull(name);

        if (isEmpty(name))
            throw new EmptyStackException();

        LinkedList<MultistackEntry> entry = stackMap.get(name);

        return entry.get(entry.size() - 1).getValue();
    }

    /*
     * Private method used for testing the input argument references.
     * Throws IllegalArgumentException if the passed value is null.
     */
    private void testVariableIfNull(Object var) {
        if (var == null)
            throw new IllegalArgumentException("Argument: " + var + " should not be a null reference");
    }


    /**
     * Method for testing if the Stack object with the key 'name' is empty or not.
     *
     * @param name key String of the stack
     * @return true if the stack is empty, false otherwise
     */
    public boolean isEmpty(String name) {
        testVariableIfNull(name);

        LinkedList<MultistackEntry> entry = stackMap.get(name);

        if (entry == null)
            return true;

        if (entry.isEmpty())
            return true;

        return false;
    }

    /**
     * Class implementation for representing a single element in the linked list of a stack implemented as a
     * linked list.
     */
    private static class MultistackEntry {

        /**
         * Variable containing the value stored in the node
         */
        private final ValueWrapper value;

        /**
         * Reference to the next multistack element in the stack
         */
        private final MultistackEntry nextMultistackEntry;

        /**
         * Default constructor of the Multidstack element
         *
         * @param value ValueWrapper object representing the value
         * @param next  reference to the next MultistackEntry
         */
        public MultistackEntry(ValueWrapper value, MultistackEntry next) {
            this.value = value;
            this.nextMultistackEntry = next;
        }

        /**
         * Getter for the value stored
         *
         * @return value of the entry
         */
        public ValueWrapper getValue() {
            return value;
        }

        /**
         * Getter of the next stack element
         *
         * @return reference to the next stack element
         */
        public MultistackEntry getNextMultistackEntry() {
            return nextMultistackEntry;
        }
    }
}