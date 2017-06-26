/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lexa.core.expression.function.standard;

import lexa.core.data.DataSet;
import lexa.core.expression.ExpressionException;
import lexa.core.expression.function.Function;

/**
 *
 * @author william
 */
public class NullFunctions
{
	private NullFunctions()
	{
	}

    /**
     *
     * @return
     */
    public static Function[] getFunctions()
	{
		return new Function[]{
			isNull(),
			nullFunction(),
			nullValue()
		};
	}

	private static Function isNull()
	{
		return new StaticFunction("isNull","value")
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
	private static Function nullFunction()
	{
		return new StaticFunction("null")
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
	private static Function nullValue()
	{
		return new StaticFunction("nullValue", "value","whenNull")
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
