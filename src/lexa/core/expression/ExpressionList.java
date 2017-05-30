/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * ExpressionList.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: August 2013
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * DD-MON-YY    ??
 *================================================================================
 */
package lexa.core.expression;

/**
 *
 * @author William
 * @since YYYY-MM
 */
public class ExpressionList
        extends MultipleExpression {

    /**
     * Create an expression list.
     * <p>the result is the final expression in the list; after all the others have been parsed
     * @param expressions a list of expressions to parse
     */
    public ExpressionList(Expression ... expressions) {
        super(ExpressionList.class, expressions);
    }

    @Override
    protected Object combine(Object overall, Object result) throws ExpressionException {
        return result;
    }

	/**
	 * The result is always valid.
	 * @param result the result to validated
	 * @return {@code true}
	 */
	@Override
	protected boolean validate(Object result)
	{
		return true;
	}

}
