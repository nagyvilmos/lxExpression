/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * NullFunctions.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: September 2013
 *================================================================================
 */
package lexa.core.expression.function.standard;

import lexa.core.data.DataSet;
import lexa.core.expression.ExpressionException;
import lexa.core.expression.function.FunctionDefinition;

/**
 * Internal null reference handling functions.
 * @author william
 * @since 2013-09
 */
public class NullFunctions
{
	private NullFunctions() { }

    /**
     * Get the internal null reference handling functions
     * @return an array of functions.
     */
    public static FunctionDefinition[] getFunctions()
	{
		return new FunctionDefinition[]{
			isNull(),
			nullFunction(),
			nullValue()
		};
	}

	private static FunctionDefinition isNull()
	{
		return new InternalFunction("isNull","value")
		{
			@Override
			public String describe()
			{
				return "check if value is null";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return arguments.contains("value") ?
						arguments.getObject("value") == null :
						true;
			}
		};
	}
	private static FunctionDefinition nullFunction()
	{
		return new InternalFunction("null")
		{
			@Override
			public String describe()
			{
				return "get a null";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return null;
			}
		};
	}
	private static FunctionDefinition nullValue()
	{
		return new InternalFunction("nullValue", "value","whenNull")
		{
			@Override
			public String describe()
			{
				return "if value is null return whenNull";
			}
			@Override
			public Object execute(DataSet arguments)
					throws ExpressionException
			{
				Object value = arguments.getObject("value");
				return value != null ?
						value :
						arguments.getObject("whenNull");
			}
		};
	}
}
