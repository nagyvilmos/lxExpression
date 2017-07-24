/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * MathsFunctions.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: September 2013
 *================================================================================
 */
package lexa.core.expression.function.standard;

import lexa.core.data.DataSet;
import lexa.core.expression.function.FunctionDefinition;

/**
 * Internal mathematical functions.
 * @author william
 * @since 2013-09
 */
public class MathsFunctions
{
	private MathsFunctions() { }

    /**
     * Get the internal mathematical functions
     * @return an array of functions.
     */
    public static FunctionDefinition[] getFunctions()
	{
		FunctionDefinition[] functions =
		{
			cos(),
			pi(),
			sin(),
			tan()
		};

		return functions;
	}

	private static FunctionDefinition cos()
	{
		return new InternalFunction("cos", "degrees")
		{
			@Override
			public String describe()
			{
				return "[math.cos degrees]\nget the cosine for degrees";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return Math.cos(arguments.getDouble("degrees"));
			}
		};

	}

	private static FunctionDefinition pi()
	{
		return new InternalFunction("pi")
		{
			@Override
			public String describe()
			{
				return "[math.pi]\nget the value of PI";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return Math.PI;
			}
		};
	}

	private static FunctionDefinition sin()
	{
		return new InternalFunction("sin", "degrees")
		{
			@Override
			public String describe()
			{
				return "[math.sin degrees]\nget the sine for degrees";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return Math.sin(arguments.getDouble("degrees"));
			}
		};

	}

	private static FunctionDefinition tan()
	{
		return new InternalFunction("tan", "degrees")
		{
			@Override
			public String describe()
			{
				return "[math.tan degrees]\nget the tangent for degrees";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return Math.tan(arguments.getDouble("degrees"));
			}
		};
	}
}
