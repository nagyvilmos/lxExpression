/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * DataFunctions.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: September 2013
 *================================================================================
 */
package lexa.core.expression.function.standard;

import lexa.core.data.DataSet;
import lexa.core.data.ArrayDataSet;
import lexa.core.data.DataItem;
import lexa.core.expression.function.Function;

/**
 * Internal null reference handling functions.
 * @author william
 * @since 2013-09
 */
public class DataFunctions
{

	private DataFunctions() { }

	/**
     * Get the data functions
	 * @return the data functions
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
		return new InternalFunction("clone", "data")
		{
			@Override
			public String describe()
			{
				return "clone the named data set";
			}

			@Override
			public Object execute(DataSet arguments)
			{
				return new ArrayDataSet(arguments.getDataSet("data"));
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
		return new InternalFunction("contains", "data", "key")
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
		return new InternalFunction("value", "data", "value")
		{
			@Override
			public String describe()
			{
				return "find the value in the data set";
			}
			@Override
			public Object execute(DataSet arguments)
			{
                DataItem val = arguments.get("value");
                DataSet data = arguments.getDataSet("data");
                if (data == null)
                {
                    return null;
                }

                DataItem ret = null;
                switch (val.getType())
                {
                    case STRING     : {ret = data.get(val.getString());     break;}
                    case INTEGER    : {ret = data.get(val.getInteger());    break;}
                }

                if (ret == null)
                {
    				return null;
                }

                return ret.getObject();
			}
		};
	}

	private static Function key()
	{
		return new InternalFunction("key", "data", "index")
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
		return new InternalFunction("remove", "data", "key")
		{
			@Override
			public String describe()
			{
				return "remove the item for key in the data set, ";
			}
			@Override
			public Object execute(DataSet arguments)
			{
                DataItem removed = arguments.getDataSet("data")
						.remove(arguments.getString("key"));
                // return the value's object not the item.
                // expressions NEVER handle DataValue objects.
                return removed != null ?
                        removed.getObject() :
                        null;
			}
		};
	}

	private static Function size()
	{
		return new InternalFunction("size", "data")
        {
            @Override
			public String describe()
			{
				return "Get the size of a data set, array or string";
			}
			@Override
			public Object execute(DataSet arguments)
			{
                DataItem data = arguments.get("data");
                switch (data.getType())
                {
                    case ARRAY      : return data.getArray().size();
                    case DATA_SET   : return data.getDataSet().size();
                    case STRING     : return data.getString().length();
                }
				return null;
			}
		};
	}

	private static Function map()
	{
		return new InternalFunction("map", "data", "map")
		{
			@Override
			public String describe()
			{
				return "** Not Implemented **";
			}
			@Override
			public Object execute(DataSet arguments)
			{
                return null;
			}
		};
	}

}
