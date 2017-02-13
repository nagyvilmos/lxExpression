/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lexa.core.expression.function.standard;

import lexa.core.data.DataSet;
import lexa.core.data.SimpleDataSet;
import lexa.core.expression.function.Function;

/**
 *
 * @author william
 */
public class DataFunctions
{

	/** private constructor */
	private DataFunctions()
	{
	}

	/**
	Get the data functions
	@return the data functions
	*/
	public static Function[] getFunctions()
	{
		Function[] functions =
		{
			dataClone(),
			contains(),
			key(),
			value(),
			remove(),
			size(),
			map()
		};
		return functions;
	}

	/**
	A function to clone a {@link DataSet}.
	<p>This is called using:
	<pre>[data.clone name]</pre>
	@return the clone function
	*/
	private static Function dataClone()
	{
		return new StaticFunction("data.clone", "data")
		{
			@Override
			public String describe()
			{
				return "clone the named data set";
			}

			@Override
			public Object execute(DataSet arguments)
			{
				return new SimpleDataSet(arguments.getDataSet("data"));
			}

		};
	}

	/**
	A function to find if a dataset contains a key
	<p>This is called using:
	<pre>[data.clone name key]</pre>
	@return {@code true} if the item exists.
	*/
	private static Function contains()
	{
		return new StaticFunction("data.contains", "data", "key")
		{
			@Override
			public String describe()
			{
				return "find if the data set contains the key";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				DataSet data = arguments.getDataSet("data");
				String key = arguments.getString("key");
				return data != null &&
						key != null &&
						data.contains(key);
			}
		};
	}

	private static Function value()
	{
		return new StaticFunction("data.value", "data", "index")
		{
			@Override
			public String describe()
			{
				return "find the value at index position in the data set";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return arguments.getDataSet("data")
						.get(arguments.getInteger("index"))
						.getValue();
			}
		};
	}

	private static Function key()
	{
		return new StaticFunction("data.key", "data", "index")
		{
			@Override
			public String describe()
			{
				return "find the key at index position in the data set";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return arguments.getDataSet("data")
						.get(arguments.getInteger("index"))
						.getKey();
			}
		};
	}

	private static Function remove()
	{
		return new StaticFunction("data.remove", "data", "key")
		{
			@Override
			public String describe()
			{
				return "remove the item for key in the data set";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return arguments.getDataSet("data")
						.remove(arguments.getString("key"));
			}
		};
	}

	private static Function size()
	{
		return new StaticFunction("data.size", "data")
		{
			@Override
			public String describe()
			{
				return "get the size of the data set";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return arguments.getDataSet("data")
						.size();
			}
		};
	}

	private static Function map()
	{
		return new StaticFunction("data.map", "data", "map")
		{
			@Override
			public String describe()
			{
				return "** UNDEFINED **";
			}
			@Override
			public Object execute(DataSet arguments)
			{
				return null;
			}
		};
	}

}
