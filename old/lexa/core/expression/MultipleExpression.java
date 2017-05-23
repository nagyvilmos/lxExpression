/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * ExpressionParser.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: March 2013
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * DD-MON-YY    ??
 *================================================================================
 */
package lexa.core.expression;

import java.util.*;
import lexa.core.data.DataSet;

/**
 * An expression that combines two or more expressions.
 * <br>
 * This class must be implemented by all classes that will support multiple expression evaluation.
 * After each expression is evaluated, it is validated and then processing can continue if required.
 * @author William
 * @since 2013-03
 */
public abstract class MultipleExpression
    extends Expression {
    /** a list of expressions to parse */
    private final List<Expression> expressions;

    /**
     * Create a multiple expression consisting of a list of expressions.
     * @param expressions a list of expressions to parse
     */
    protected MultipleExpression(Expression ... expressions) {
        this(null, expressions);
    }
    /**
     * Create a multiple expression consisting of a list of expressions.
     * <br>
     * If the class types of the expression is the same as the {@code classType} and
     * it's an implementation of {@code MultipleExpression} then the
     * the contained expressions are added rather than the container.
     * @param classType a class type to allow for concatenation of expressions.
     * @param expressions a list of expressions to parse
     */
    protected MultipleExpression(Class<?> classType, Expression ... expressions) {
        this.expressions = new ArrayList<Expression>();
        boolean isMultipleExpression = classType != null &&
                MultipleExpression.class.isAssignableFrom(classType);
        for (int e= 0;
                e < expressions.length;
                e++) {
            if ( isMultipleExpression &&
                    expressions[e].getClass().equals(classType)) {
                this.expressions.addAll(((MultipleExpression)expressions[e]).expressions);
            } else {
                this.expressions.add(expressions[e]);
            }
        }
    }

    /**
     * Evaluate a {@code MultipleExpression}.
     * Each expression is evaluated in turn until completion.
     * The processes can be interrupted via the methods:
     * <ul>
     * <li>{@link #validate(Object)} checks if the result is valid.</li>
     * <li>{@link #combine(Object, Object)} combines the existing overall result with the new result.</li>
     * <li>{@link #complete(Object)} stops processing.</li>
     * </ul>
     * @param data  The input to feed the expression.
     * @return The result of the evaluation
     * @throws ExpressionException when an error occurs in evaluation the expression
     */
    @Override
    public Object evaluate(DataSet data)
            throws ExpressionException {
        Object overall = null;
        for (Expression expression : this.expressions) {
            Object result = expression.evaluate(data);
            if (!this.validate(result)) {
                overall = null;
                break;
            }
            if (overall != null) {
                overall = this.combine(overall, result);
            } else {
                overall = result;
            }
            if (this.complete(overall)) {
                break;
            }
        }
        return overall;
    }

    /**
     * Checks if the result of an evaluation is valid.
     * <br>
     * The default behaviour is to check the result is not null.
     * @param result the result from an evaluated expression.
     * @return {@code true} if the result is valid.
     * @throws ExpressionException when an error occurs in validating the result.
     */
    protected boolean validate(Object result)
            throws ExpressionException {
        return (result != null);
    }

    /**
     * Combines the existing overall result with the new result.
     * @param overall the existing result from the previously evaluated expressions.
     * @param result the result from an evaluated expression.
     * @return The combined result.
     * @throws ExpressionException when an error occurs in combining the result.
     */
    protected abstract Object combine(Object overall, Object result)
             throws ExpressionException;

    /**
     * Check if processing is completed.
     * @param overall the existing result from the previously evaluated expressions.
     * @return {@code false} as the process should continue to the end.
     * @throws ExpressionException when an error occurs in checking the result.
     */
    protected boolean complete(Object overall)
            throws ExpressionException {
        return false;
    }

    /**
     * Returns a string containing the expressions.
     * @return a string of expressions
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean next = false;
        sb.append('(');
        for (Expression e : this.expressions) {
            if (next) {
                sb.append(", ");
            } else {
                next = true;
            }
            sb.append(e);
        }
        sb.append(')');

        return sb.toString();
    }
}
