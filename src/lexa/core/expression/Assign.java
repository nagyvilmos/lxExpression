/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * Assign.java
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

import lexa.core.data.DataSet;
import lexa.core.data.SimpleDataSet;

/**
 * Assign a value within an expression.
 * <br>
 * Assigns the result of an {@see Expression} to a key.
 * @author William
 * @since 2013-03
 */
public class Assign extends Expression {

	static void assign(ExpressionTokens tokens) throws ExpressionException
	{
		int p = tokens.getPointer();
		if (tokens.getType() != WordType.LITERAL)
		{
			throw new ExpressionException("expected literal for assign\n" + tokens.getPhrase(p, p+2));
		}
		String assignTo = tokens.getWord();
		tokens.next();
		if (tokens.getType() != WordType.ASSIGN)
		{
			throw new ExpressionException("expected equals for assign\n" + tokens.getPhrase(p, p+2));
		}
		Value.primary(tokens.next());
		tokens.setExpression(new Assign(assignTo, tokens.expression()));
	}
    /** The field name to be assigned */
    private final String key;
    /** The expression that will provide the value to assign */
    private final Expression expression;

    /**
     * Create a new assignment expression.
     * @param key the key value to be populated by {@code expression}
     * @param expression the {@see Expression} to evaluate.
     * @throws ExpressionException when the {@code key} is not a valid name.
     */
    public Assign(String key, Expression expression)
            throws ExpressionException {
        Element v = new Element(key);
        if (v.isLiteral()) {
            throw new ExpressionException ("Not a valid ID at " + key);
        }
        this.key = key;
        this.expression = expression;
    }

    /**
     * Evaluates the expression and assign its result to a new item.
     * @param data  the input to feed the expression.
     * @return the value assigned.
     * @throws ExpressionException when an error occurs in evaluation the expression
     */
    @Override
    public Object evaluate(DataSet data)
            throws ExpressionException {
        Object value = this.expression.evaluate(data);
        this.assign(data, key, value);
        return value;
    }

    /**
     * A string representation of an assignment.
     * <br>
     * Returns a string in the format {@code (<key> = <expression>)}
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "(" + key + " = " + expression.toString() + ")";
    }

    private void assign(DataSet data, String key, Object value)
            throws ExpressionException {
        int point = key.indexOf('.');
        if (point == -1) {
            data.put(key,value);
        } else {
            String setKey = key.substring(0,point);
            String subKey = key.substring(point+1);
            DataSet subData = data.getDataSet(setKey);
            if (subData == null) {
                subData = new SimpleDataSet();
                data.put(setKey,subData);
            }
            assign(subData, subKey, value);
        }
    }
}
