/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * Expression.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: March 2013
 *================================================================================
 */
package lexa.core.expression;

import java.util.Date;
import lexa.core.data.DataSet;
import lexa.core.data.formatting.CombinedFormat;
import lexa.core.expression.function.FunctionLibrary;

/**
 * Base class for expression evaluation.
 * <br>
 * This class must be implemented by all classes that will support expression evaluation.
 * @author William
 * @since 2013-03
 */
public abstract class Expression {
	/** Internal formatter used in parsing to and from strings */
	private static final CombinedFormat formatter = new CombinedFormat();

    /**
     * During evaluation this method is called to determine the value.
     * <br>
     * A {@link DataSet} provides values for determining the result which is then returned.
     * @param data  The input to feed the expression.
     * @return The result of the evaluation
     * @throws ExpressionException when an error occurs in evaluation the expression
     */
    public abstract Object evaluate(DataSet data)
            throws ExpressionException;

    /**
     * String representing an expression
     * @return the string {@code "Expression"}
     */
    @Override
    public String toString() {
        return "expression";
    }

    /**
     * Helper function for parsing a {@link String} to a {@link Boolean}
     * @param   string
     *          a String to convert to Boolean
     * @return  {@code true} if the string is {@code "true"},
     *          {@code false} if the string is {@code "false"},
     *          otherwise {@code null}.
     */
    protected static Boolean parseToBoolean(String string) {
        return Expression.formatter.booleanFormat.fromString(string);
    }

    /**
     * Helper function for parsing a {@link String} to a {@link Date}
     * @param string a String to convert to Date
     * @return the date if the string contains a date otherwise {@code null}.
     */
    protected static Date parseToDate(String string) {
        if (string.charAt(0)=='\'' && string.charAt(string.length()-1)=='\'') {
            return Expression.formatter.dateFormat.fromString(string.substring(1,string.length()-1));
        }
        return null;
    }

    /**
     * Helper function for parsing a {@link String} to a {@link Double}
     * @param string a String to convert to Double
     * @return the number if the string contains a double otherwise {@code null}.
     */
    protected static Double parseToDouble(String string) {
        if (string.indexOf('.') == -1) {
            return null;
        }
        return Expression.formatter.doubleFormat.fromString(string);
    }

    /**
     * Helper function for parsing a {@link String} to an {@link Integer}
     * @param string a String to convert to integer
     * @return the number if the string contains an integer otherwise {@code null}.
     */
    protected static Integer parseToInteger(String string) {
        if (string.indexOf('.') != -1) {
            return null;
        }
        return Expression.formatter.integerFormat.fromString(string);
    }

    /**
     * Helper function for parsing a {@link String} to a {@link Long}
     * @param string a String to convert to long
     * @return the number if the string contains a long otherwise {@code null}.
     */
    protected static Long parseToLong(String string) {
        if (string.indexOf('.') != -1) {
            return null;
        }
        return Expression.formatter.longFormat.fromString(string);
    }

    /**
     * Helper function for parsing a {@link String} from an expression to a {@link String}
     * @param string a String to convert
     * @return the string contains if it is delimited by {@code "} otherwise {@code null}.
     */
    protected static String parseToString(String string) {
        if (string.charAt(0)=='"' && string.charAt(string.length()-1)=='"') {
            return Expression.formatter.stringFormat.fromString(string.substring(1,string.length()-1));
        }
        return null;
    }

	/**
	Parse a string to form an expression
	<br>
	This parses the expression using the base function library containing
	the in built functions.
	@param expressionText a string representing an expression.
	@return an expression object.
	@throws ExpressionException when the text cannot be parsed
	@see lexa.core.expression.function
	*/
	public static Expression parse(String expressionText)
			throws ExpressionException
	{
		return parse(expressionText, FunctionLibrary.base());
	}

    /**
	Parse a string to form an expression
	<br>
	This parses the expression using the supplied function library.
	@param expressionText a string representing an expression.
	@param library a function library to support the expression string
	@return an expression object.
	@throws ExpressionException when the text cannot be parsed
	@see lexa.core.expression.function
	*/
public static Expression parse(String expressionText, FunctionLibrary library)
			throws ExpressionException
	{
		ExpressionTokens tokens = new ExpressionTokens(expressionText, library);
		expression(tokens);
		if (!tokens.done())
		{
			int p = tokens.getPointer();
			throw new ExpressionException("Invalid end of expression\n" + tokens.getPhrase(p-1, p+1));
		}
		return tokens.expression();
	}

	/**
	 * Parse the text for an expression
	 * <pre>
	 * EXPRESSION ::= ID "=" ELEMENT
	 *              | LOGICAL_OR
	 * </pre>
     *
	 * @param   tokens
	 *          the token list pointing to the start of the expression.
     * @throws  ExpressionException when the expression cannot be parsed.
	 */
	static void expression(ExpressionTokens tokens)
			throws ExpressionException
	{
		if (tokens.getType(tokens.getPointer() + 1) == WordType.ASSIGN)
		{
			Assign.assign(tokens);
		} else {
			LogicalOr.logicalOr(tokens);
		}
		if (!tokens.done() &&
				tokens.getType() == WordType.END_OF_EXPRESSION)
		{
			Expression ex = tokens.expression();
			Expression.expression(tokens.next());
			tokens.setExpression(new ExpressionList(ex, tokens.expression()));
		}
	}
}
