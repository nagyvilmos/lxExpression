/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * Element.java
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
import lexa.core.data.DataValue;

/**
 * Represent an expression as a single element<br>
 * An element can either be a literal value or an ID to a {see DataItem} to
 * look up in the {@link DataSet} during evaluation.
 * @author William
 * @since 2013-03
 */
public class Element
        extends Expression {

	static void element(ExpressionTokens tokens) throws ExpressionException
	{
		int start = tokens.getPointer();
		switch (tokens.getType())
		{
			case LITERAL:
			{
				tokens.setExpression(new Element(tokens.getWord()));
				tokens.next();
				break;
			}
			case OPEN_BRACKET:
			{
				Expression.expression(tokens.next());
				if (tokens.getType() != WordType.CLOSE_BRACKET)
				{
					throw new ExpressionException("Expected close bracket at " + tokens.getPhrase(start,tokens.getPointer()));
				}
				tokens.next();
				break;
			}
			case SUBTRACT:
			{
				Negative.expression(tokens.next());
				break;
			}
			case START_FUNCTION:
			{
				FunctionCall.function(tokens);
				break;
			}
			default:
			{
				throw new ExpressionException("Invalid element at " + tokens.getPhrase(start -1,tokens.getPointer() +1));
			}
		}
	}

	private final InternalElement element;
	private final boolean literal;

    /**
     * Create an element.
     * @param word the string representation of the element.
     */
    public Element(String word) {

        Object object;
		InternalElement el;
		boolean lit = true;
		if ("~".equals(word))
		{
			el = new DataSetElement();
		}
		else if ((object = parseToString(word)) != null)
		{
            el = new LiteralElement(object);
        }
		else if ((object = parseToDate(word)) != null)
		{
            el = new LiteralElement(object);
        }
		else if ((object = parseToInteger(word)) != null)
		{
            el = new LiteralElement(object);
        }
		else if ((object = parseToDouble(word)) != null)
		{
            el = new LiteralElement(object);
        }
		else if ((object = parseToBoolean(word)) != null)
		{
            el = new LiteralElement(object);
        }
		else
		{
            el = new IdElement(word);
			lit = false;
        }
		this.literal = lit;
        this.element = el;
    }

    /**
     * Evaluation the element.
     * <br>
     * Either returns the literal value or reads the item from the {@code data}
     * @param data  The input to feed the expression.
     * @return The result of the evaluation
     * @throws ExpressionException when an error occurs in evaluation the expression
     */
    @Override
    public Object evaluate(DataSet data)
            throws ExpressionException{
		return this.element.evaluate(data);
    }

//    /**
//     * Is the element a literal or id.
//     * @return {@code true} if the element is a literal otherwise {@code false}.
//     */
//    public boolean isLiteral() {
//        return this.literal;
//    }

    /**
     * The string representation of the element.
     * @return a string representing the element.
     */
    @Override
    public String toString() {
		return this.element.toString();
    }

	boolean isLiteral()
	{
		return this.literal;
	}

	private interface InternalElement
	{

		Object evaluate(DataSet data)
				throws ExpressionException;
	}

	private static class DataSetElement
			implements InternalElement
	{

		@Override
		public Object evaluate(DataSet data)
				throws ExpressionException
		{
			return data;
		}
		/**
		 * The string representation of the element.
		 * @return a string representing the element.
		 */
		@Override
		public String toString() {
			return "(data)";
		}
	}

	private static class LiteralElement
			implements InternalElement
	{

		private final Object literal;
		private LiteralElement(Object literal)
		{
			this.literal = literal;
		}
		@Override
		public Object evaluate(DataSet data)
				throws ExpressionException
		{
			return literal;
		}
		/**
		 * The string representation of the element.
		 * @return a string representing the element.
		 */
		@Override
		public String toString() {
			return '(' + literal.toString() + ')';
		}

	}
	private static class IdElement
			implements InternalElement
	{
		private final String id;

		public IdElement(String id)
		{
			this.id = id;
		}
		@Override
		public Object evaluate(DataSet data)
				throws ExpressionException
		{
			//return data.getValue(id);
            DataValue value = data.item(this.id);
			return value != null ?
                    value.getObject() :
                    null;
		}
		/**
		 * The string representation of the element.
		 * @return a string representing the element.
		 */
		@Override
		public String toString() {
			return this.id;
		}

	}


//         return Element.dataSetValue((String)this.value, data)
}
