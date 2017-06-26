/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lexa.core.expression.function.standard;

import lexa.core.data.DataSet;
import lexa.core.expression.function.Function;

/**
 *
 * @author william
 */
public class MathsFunctions
{
	private MathsFunctions()
	{
	}

    /**
     *
     * @return
     */
    public static Function[] getFunctions()
	{
		Function[] functions =
		{
			cos(),
			pi(),
			sin(),
			tan()
		};

		return functions;
	}

	private static Function cos()
	{
		return new StaticFunction("maths.cos", "degrees")
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

	private static Function pi()
	{
		return new StaticFunction("maths.pi")
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

	private static Function sin()
	{
		return new StaticFunction("maths.sin", "degrees")
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

	private static Function tan()
	{
		return new StaticFunction("maths.tan", "degrees")
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
