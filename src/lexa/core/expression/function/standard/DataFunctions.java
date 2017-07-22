/*==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * DataFunctions.java
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: September 2013
 *==============================================================================
 */
package lexa.core.expression.function.standard;

import lexa.core.data.ArrayDataArray;
import lexa.core.data.DataSet;
import lexa.core.data.ArrayDataSet;
import lexa.core.data.DataArray;
import lexa.core.data.DataItem;
import lexa.core.data.DataValue;
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
			valueFunction(),
			remove(),
			size(),
			map(),
            arrayFunction(),
            addFunction()
		};
		return functions;
	}

    static DataArray add(DataArray array, Object ... values)
    {
        if (values == null)
        {
            return array;
        }
        if (array == null)
        {
            return new ArrayDataArray(values);
        }
        return array.addAll(values);
    }

    private static Function addFunction()
    {
		return new InternalFunction("add", "array", "~")
		{
			@Override
			public String describe()
			{
				return "add values to an array";
			}
			@Override
			public Object execute(DataSet arguments)
			{
                DataArray array = arguments.getArray("array");
				Object value = arguments.getString("key");
				return array.add(value);
			}
		};
    }

    public static DataArray array(Object[] values)
    {
        return new ArrayDataArray(values);
    }

    private static Function arrayFunction()
    {
		return new InternalFunction("array", "~")
		{
			@Override
			public String describe()
			{
				return "create an array";
			}
			@Override
			public DataArray execute(DataSet arguments)
			{

                Object[] values = new Object[arguments.size()];
				for (int i = 0;
                     i < arguments.size();
                        i++)
                {
                    values[i] = arguments.get(i).getObject();
                }
				return DataFunctions.array(values);
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

    /**
     * Get a value from a data set
     * @param   data
     *          data to search
     * @param   value
     *          the value required, either a string for the key
     *          or integer for index.
     * @return  the requested value or {@code null} if not present.
     */
    static Object value(DataSet data, DataValue value)
    {
        if (data == null)
        {
            return null;
        }

        DataItem ret = null;
        switch (value.getType())
        {
            case STRING     : {ret = data.get(value.getString());     break;}
            case INTEGER    : {ret = data.get(value.getInteger());    break;}
        }

        if (ret == null)
        {
            return null;
        }

        return ret.getObject();
    }

    private static Function valueFunction()
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
                return DataFunctions.value(
                    arguments.getDataSet("data"),
                    arguments.getValue("value")
                );
			}
		};
	}
}
