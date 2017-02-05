/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * ExpressionParser.java
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

import lexa.core.data.DataSet;

/**
 * An expression that performs a conditional branch.
 *
 * <p>A condition has three elements - {@code condition}, {@code onTrue} and {@code onFalse}.
 * The {@code condition} is evaluated
 * and if true, the {@code onTrue} is evaluated, otherwise {@code onFalse} is evaluated.
 *
 * @author William
 * @since 2013-08
 */
public class Conditional
        extends Expression {

	static void condition(ExpressionTokens tokens)
			throws ExpressionException
	{
		int start = tokens.getPointer();
		Value.primary(tokens);
		Expression left = tokens.expression();
		if (tokens.getType() == WordType.IF)
		{
			Value.primary(tokens.next());
			Expression trueEx = tokens.expression();
			if (tokens.getType() != WordType.ELSE)
			{
				throw new ExpressionException("Expected colon\n" + tokens.getPhrase(start, tokens.getPointer()+2));
			}
			Value.primary(tokens.next());
			Expression falseEx = tokens.expression();
			tokens.setExpression(new Conditional(left, trueEx, falseEx));
		}
	}
    private final Expression condition;
    private final Expression onTrue;
    private final Expression onFalse;

	/**
	 * Create a new conditional expression.
	 * <p>If {@code condition} evaluates as {@code true} then {@code onTrue} is evaluated,
	 * otherwise then {@code onFalse} is evaluated.
	 * @param condition condition to evaluate
	 * @param onTrue
	 * @param onFalse 
	 */
    public Conditional(Expression condition, Expression onTrue, Expression onFalse) {
        this.condition=condition;
        this.onTrue=onTrue;
        this.onFalse=onFalse;
    }

    @Override
    public Object evaluate(DataSet data) throws ExpressionException {
        Object conditionResult = this.condition.evaluate(data);
        if (conditionResult == null ||
                !Boolean.class.isAssignableFrom(conditionResult.getClass())) {
            return null;
        }
        if ((Boolean)conditionResult) {
            return this.onTrue.evaluate(data);
        }
        return this.onFalse.evaluate(data);
    }

    @Override
    public String toString() {
        return "if(" + condition + ") then (" + onTrue + ") else (" + onFalse + ')';
    }

}
