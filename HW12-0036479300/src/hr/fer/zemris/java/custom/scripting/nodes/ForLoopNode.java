package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.*;

/**
 * ForLoopNode Class that is used to represent For loop elements in Lexer and Parser objects
 */
public class ForLoopNode extends Node {

    /**
     * Read-only ElementVariable - node Name
     */
    private final ElementVariable variable;

    /**
     * Read-only Element - start expression for loop
     */
    private final Element startExpression;

    /**
     * Read-only Element - end expression for the loop
     */
    private final Element endExpression;

    /**
     * Read-only Element - step expression of the loop
     */
    private final Element stepExpression;

    /**
     * For loop node constructor
     *
     * @param variable        ElementVariable represents the name
     * @param startExpression - ElementString, ElementNumber, ElementVariable element
     * @param endExpression   - ElementString, ElementNumber, ElementVariable element
     * @param stepExpression  - ElementString, ElementNumber, ElementVariable element
     */
    public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression, Element stepExpression) {
        if (variable == null ||
                startExpression == null ||
                endExpression == null)
            throw new IllegalArgumentException("Variable, startExpression and endExpression arguments are not allowed to be null references");

        if (!(startExpression instanceof ElementString ||
                startExpression instanceof ElementConstantDouble ||
                startExpression instanceof ElementConstantInteger ||
                startExpression instanceof ElementVariable))
            throw new IllegalArgumentException("StartExpression argument is not an allowed type");

        if (!(endExpression instanceof ElementString ||
                endExpression instanceof ElementConstantDouble ||
                endExpression instanceof ElementConstantInteger ||
                endExpression instanceof ElementVariable))
            throw new IllegalArgumentException("EndExpression argument is not an allowed type");

        if (!(stepExpression instanceof ElementString ||
                stepExpression instanceof ElementConstantDouble ||
                stepExpression instanceof ElementConstantInteger ||
                stepExpression instanceof ElementVariable))
            throw new IllegalArgumentException("StepExpression argument is not an allowed type");

        this.variable = variable;
        this.startExpression = startExpression;
        this.endExpression = endExpression;
        this.stepExpression = stepExpression;
    }

    /**
     * ForLoopNode constructor
     *
     * @param variable        ElementVariable represents the name
     * @param startExpression - ElementString, ElementNumber, ElementVariable element
     * @param endExpression   - ElementString, ElementNumber, ElementVariable element
     */
    public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression) {
        this(variable, startExpression, endExpression, null);
    }

    /**
     * Getter for the variable element
     *
     * @return ElementVariable, repesenting the name
     */
    public ElementVariable getVariable() {
        return variable;
    }

    /**
     * Getter for the startExpresson element
     *
     * @return Element representation
     */
    public Element getStartExpression() {
        return startExpression;
    }

    /**
     * Getter for the endExpression element
     *
     * @return Element representation
     */
    public Element getEndExpression() {
        return endExpression;
    }

    /**
     * Getter for the stepExpression element
     *
     * @return Element representation
     */
    public Element getStepExpression() {
        return stepExpression;
    }

    @Override
    public void accept(INodeVisitor visitor) {
        visitor.visitForLoopNode(this);
    }
}
