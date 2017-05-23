/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * Numeric.java
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

/**
 * Base class for numeric expressions.
 * <br>
 * This class must be implemented by all classes that will support numeric expression.
 * @author William
 * @since 2013-03
 */
public abstract class Numeric
        extends MultipleExpression {

    /**
     * Create a numeric expression consisting of a list of expressions.
     * @param expressions a list of expressions to parse
     */
    protected Numeric(Expression ... expressions) {
        super(expressions);
    }

    /**
     * Create a multiple expression consisting of a list of expressions.
     * <br>
     * If the class types of the expression is the same as the {@code classType} and
     * it's an implementation of {@code Numeric} then the
     * the contained expressions are added rather than the container.
     * @param classType a class type to allow for concatenation of expressions.
     * @param expressions a list of expressions to parse
     */
    protected Numeric(Class<?> classType, Expression ... expressions) {
        super(classType, expressions);
    }

    /**
     * Checks if the result of an evaluation is valid.
     * <br>
     * Check the result is numeric.
     * @param result the result from an evaluated expression.
     * @return {@code true} if the result is valid.
     * @throws ExpressionException when an error occurs in validating the result.
     */
    @Override
    protected boolean validate(Object result) throws ExpressionException {
        return (super.validate(result) &&
                (Integer.class.isAssignableFrom(result.getClass()) ||
                Double.class.isAssignableFrom(result.getClass())));
    }

    /**
     * Combines the existing overall result with the new result.
     * <p> Uses the methods {@link #calcInteger calcInteger} and {@link #calcDouble calcDouble}
     * to evaluate the results.
     * @param overall the existing result from the previously evaluated expressions.
     * @param result the result from an evaluated expression.
     * @return The combined result.
     * @throws ExpressionException when an error occurs in combining the result.
     */
    @Override
    protected Object combine(Object overall, Object result) throws ExpressionException {
       if (Integer.class.isAssignableFrom(overall.getClass()) ||
                Integer.class.isAssignableFrom(result.getClass())) {
           return calcInteger((Integer)overall, (Integer)result);
       }
       if (Double.class.isAssignableFrom(overall.getClass()) ||
                Double.class.isAssignableFrom(result.getClass())) {
           return calcDouble((Double)overall, (Double)result);
       }
       throw new ExpressionException("Values not numeric");
    }

    /**
     * Calculate the result of two {@link Double} numbers.
     * <p><b>NB</b> This method must be synonymous with {@link #calcInteger calcInteger}
     * @param lhs the left hand side of the expression.
     * @param rhs the right hand side of the expression.
     * @return the result of the calculation.
     */
    protected abstract Double calcDouble (Double lhs, Double rhs);

    /**
     * Calculate the result of two {@link Integer} numbers.
     * <p><b>NB</b> This method must be synonymous with {@link #calcDouble calcDouble}
     * @param lhs the left hand side of the expression.
     * @param rhs the right hand side of the expression.
     * @return the result of the calculation.
     */
    protected abstract Integer calcInteger (Integer lhs, Integer rhs);
}
