/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * Compare.java
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
 * Compare the results of two {@see Expression} evaluations.
 * <p>Both expressions must return results of the same type and the class must
 * implement {@see Comparable}.
 * <p>If either results is null, the return will be null, otherwise it will be
 * {@code true} or {@code false} depending on the result.
 * <p>If the first expression returns null, then the second is not evaluated.
 * @author William
 * @since 2013-03
 */
class Compare
        extends MultipleExpression {

	static void compare(ExpressionTokens tokens)
			throws ExpressionException
	{
		Sum.addition(tokens);
		WordType op = tokens.getType();

		if (op == WordType.LESS_THAN ||
				op == WordType.LESS_THAN_OR_EQUALS ||
				op == WordType.NOT_EQUALS ||
				op == WordType.EQUALS ||
				op == WordType.GREATER_THAN_OR_EQUALS ||
				op == WordType.GREATER_THAN)
		{
			Expression left = tokens.expression();
			Sum.addition(tokens.next());
			tokens.setExpression(new Compare(left, op, tokens.expression()));
		}
	}
    /** sub-class with the desired comparison */
    private final Calc calc;
    /** the type of comparison */
    private final WordType comparison;
    /**
     * Create a comparison object.
     * @param lhs the left hand side of the expression
     * @param comparison the type of comparison
     * @param rhs the right hand side of the expression
     * @throws ExpressionException when an error occurs in creating the comparison
     */
    Compare(Expression lhs, WordType comparison, Expression rhs) throws ExpressionException {
        super(lhs, rhs);
        this.comparison = comparison;
        switch (comparison) {
        case LESS_THAN : {
            //this.calc = new LessThan();
            this.calc = new Calc() {
                @Override
                boolean compare(Comparable<Object> lhs, Comparable<Object> rhs) {
                    return lhs.compareTo(rhs) < 0;
                }
            };
            break;
        }
        case LESS_THAN_OR_EQUALS : {
            this.calc = new Calc() {
                @Override
                boolean compare(Comparable<Object> lhs, Comparable<Object> rhs) {
                    return lhs.compareTo(rhs) <= 0;
                }
            };
            break;
        }
        case EQUALS : {
            this.calc = new Calc() {
                @Override
                boolean compare(Comparable<Object> lhs, Comparable<Object> rhs) {
                    return lhs.compareTo(rhs) == 0;
                }
            };
            break;
        }
        case NOT_EQUALS : {
            this.calc = new Calc() {
                @Override
                boolean compare(Comparable<Object> lhs, Comparable<Object> rhs) {
                    return lhs.compareTo(rhs) != 0;
                }
            };
            break;
        }
        case GREATER_THAN_OR_EQUALS : {
            this.calc = new Calc() {
                @Override
                boolean compare(Comparable<Object> lhs, Comparable<Object> rhs) {
                    return lhs.compareTo(rhs) >= 0;
                }
            };
            break;
        }
        case GREATER_THAN : {
            this.calc = new Calc() {
                @Override
                boolean compare(Comparable<Object> lhs, Comparable<Object> rhs) {
                    return lhs.compareTo(rhs) > 0;
                }
            };
            break;
        }
        default : {
            throw new ExpressionException("Expected comparison :" + comparison.getValue());
        }
        }
    }

    /**
     * Combines the two results if both are valid.
     * @param overall The first expression.
     * @param result The second result.
     * @return {@code true} or {@code false} based on the comparison.
     * @throws ExpressionException when an error occurs in combining the result.
     */
    @Override
    protected Object combine(Object overall, Object result)
            throws ExpressionException {
        try {
            return this.calc.compare((Comparable<Object>)overall, (Comparable<Object>)result);
        } catch (ClassCastException ex) {
            throw new ExpressionException("Result not comparable", ex);
        }
    }

    /**
     * Returns a string containing the comparison.
     * <br>
     * The return is of the format {@code EQUALS(lhs, rhs)}
     * @return a string of the comparison
     */
    @Override
    public String toString() {
        return this.comparison + super.toString();
    }


    /**
     * Definition for the calculation to compare to expressions.
     * <br>
     * This is implemented with anonymous classes in the constructor.
     */
    private static abstract class Calc {
        abstract boolean compare(Comparable<Object> lhs, Comparable<Object> rhs);
    }
}
