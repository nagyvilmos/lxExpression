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
import lexa.core.data.DataArray;
import lexa.core.data.DataItem;
import lexa.core.data.DataValue;
import lexa.core.expression.function.FunctionDefinition;

/**
 * Internal data handling functions.
 * @author william
 * @since 2013-09
 */
public class DataFunctions
{

    /** private constructor for static class */
	private DataFunctions() { }

    /**
     * Implementation of the add function.
     * <p>
     * Add a series of values to a {@link DataArray}.
     * If the array does not exist then a new array is created with the values
     * and if no values are provided then the original array is returned.
     *
     * @param   array
     *          an array to add values to.
     * @param   values
     *          the values to add to the array
     * @return  the array with the values added.
     */
    public static DataArray add(DataArray array, Object ... values)
    {
        if (values == null)
        {
            return array;
        }
        if (array == null)
        {
            return DataFunctions.array(values);
        }
        return array.addAll(values);
    }

    private static FunctionDefinition addFunction()
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
                Object[] values = new Object[arguments.size()-1];
				for (int i = 1;
                     i < arguments.size();
                        i++)
                {
                    values[i - 1] = arguments.get(i).getObject();
                }
				return DataFunctions.add(array, values);
			}
		};
    }

    /**
     * Implementation of the array function.
     * <p>
     * Create a {@link DataArray} from a set of values
     *
     * @param   values
     *          the values to make into an array
     * @return  the array with all the values.
     */
    public static DataArray array(Object ... values)
    {
        return new ArrayDataArray(values);
    }

    private static FunctionDefinition arrayFunction()
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
     * Implementation of the clone function for an array.
     * <p>
     * Create a clone of a {@link DataArray}
     *
     * @param   array
     *          the array to clone
     * @return  a clone of the original array
     */
    public static DataArray cloneArray(DataArray array)
    {
        return array.factory().clone(array);
    }

    /**
     * Implementation of the clone function for a data set.
     * <p>
     * Create a clone of a {@link DataSet}
     *
     * @param   dataSet
     *          the data set to clone
     * @return  a clone of the original data set
     */
    public static Object cloneDataSet(DataSet dataSet)
    {
        return dataSet.factory().clone(dataSet);
    }

	/**
	A function to clone a {@link DataSet}.
	<p>This is called using:
	<pre>[data.clone name]</pre>
	@return the clone function
	*/
	private static FunctionDefinition cloneFunction()
	{
		return new InternalFunction("clone", "data")
		{
			@Override
			public String describe()
			{
				return "clone the named data set or array";
			}

			@Override
			public Object execute(DataSet arguments)
			{
                DataItem item = arguments.get("data");
                switch (item.getType()){
                    case DATA_SET :
                    {
                        return DataFunctions.cloneDataSet(item.getDataSet());
                    }
                    case ARRAY :
                    {
                        return DataFunctions.cloneArray(item.getArray());
                    }
                }
				return null;
			}
		};
	}

    /**
     * Implementation of the contains function.
     * <p>
     * Find if a data set contains a value for the given key
     *
     * @param   data
     *          the data set to check
     * @param   key
     *          the key to the item to be found
     * @return  {@code true} if an item exists for the key,
     *          otherwise {@code false}.
     */
    public static Boolean contains(DataSet data, String key)
    {
        return data != null &&
                key != null &&
                data.contains(key);
    }

	/**
	A function to find if a dataset contains a key
	<p>This is called using:
	<pre>[data.clone name key]</pre>
	@return {@code true} if the item exists.
	*/
	private static FunctionDefinition containsFunction()
	{
		return new InternalFunction("contains", "data", "key")
		{
			@Override
			public String describe()
			{
				return "find if the data set contains the key";
			}
			@Override
			public Boolean execute(DataSet arguments)
			{
				DataSet data = arguments.getDataSet("data");
				String key = arguments.getString("key");
                return DataFunctions.contains(data, key);
			}
		};
	}

	/**
     * Get the data functions
	 * @return the data functions
	 */
	public static FunctionDefinition[] getFunctions()
	{
		FunctionDefinition[] functions =
		{
	        addFunction(),
	        arrayFunction(),
    		cloneFunction(),
			containsFunction(),
            joinFunction(),
			keyFunction(),
			mapFunction(),
			removeFunction(),
			sizeFunction(),
			valueFunction()
    	};
		return functions;
	}

    /**
     * Implementation of the join function for arrays.
     * <p>
     * Create a {@link DataArray} by joining two arrays together
     *
     * @param   first
     *          the first array
     * @param   second
     *          the second array
     * @return  a new array containing both input arrays.
     */
    public static DataArray joinArray(DataArray first, DataArray second)
    {
        DataArray joined = first.factory().clone(first);
        if (second != null)
        {
            joined.addAll(second);
        }
        return joined;
    }

    /**
     * Implementation of the join function for data sets.
     * <p>
     * Create a {@link DataSet} by joining two data sets together.
     * If a key is repeated then the item from {@code second} will replace the
     * item from {@code first}.
     *
     * @param   first
     *          the first data set
     * @param   second
     *          the second data set
     * @return  a new data set containing both input data sets.
     */
    public static DataSet joinDataSet(DataSet first, DataSet second)
    {
        DataSet joined = first.factory().clone(first);
        if (second != null)
        {
            joined.put(second);
        }
        return joined;
    }

    private static FunctionDefinition joinFunction()
	{
		return new InternalFunction("join", "first", "second")
		{
			@Override
			public String describe()
			{
				return "join two data sets or arrays";
			}

			@Override
			public Object execute(DataSet arguments)
			{
                DataItem first = arguments.get("first");
                DataItem second = arguments.get("second");
                switch (first.getType()){
                    case DATA_SET :
                    {
                        return DataFunctions.joinDataSet(
                                first.getDataSet(),
                                second.getDataSet()
                        );
                    }
                    case ARRAY :
                    {
                        return DataFunctions.joinArray(
                                first.getArray(),
                                second.getArray()
                        );
                    }
                }
				return null;
			}
		};
	}

    /**
     * Implementation of the key function.
     * <p>
     * Find the key for the item at {@code index} position.
     * If the index is out of range then returns null
     * @param   data
     *          the data set
     * @param   index
     *          the position in the data set.
     * @return  If the index is valid then {@link DataItem#getKey()} for the item,
     *          otherwise returns {@code null}.
     */
    public static String key(DataSet data, Integer index)
    {
        return data != null &&
                    index != null &&
                    index >= 0 &&
                    index < data.size() ?
                data.get(index).getKey() :
                null;
    }
	private static FunctionDefinition keyFunction()
	{
		return new InternalFunction("key", "data", "index")
		{
			@Override
			public String describe()
			{
				return "find the key at index position in the data set";
			}
			@Override
			public String execute(DataSet arguments)
			{
				return DataFunctions.key(
                        arguments.getDataSet("data"),
						arguments.getInteger("index")
                );
			}
		};
	}

	private static FunctionDefinition mapFunction()
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

    /**
     * Implementation of the remove function.
     * <p>
     * Remove an item from the data set either by key or index. If the input is
     * a string then it is removed by key and if it is an integer then it is
     * removed by index.
     *
     * @param   data
     *          the data set
     * @param   item
     *          the item in the data set, either a key or index
     * @return  If the item is removed then {@link DataItem#getObject()} for the
     *          item, otherwise returns {@code null}.
     */
    public static Object remove(DataSet data, DataItem item)
    {
        if (data == null || item == null)
        {
            return null;
        }

        DataItem removed = null;
        switch (item.getType())
        {
            case STRING :
            {
                removed = data.remove(item.getString());
                break;
            }
            case INTEGER :
            {
                removed = data.remove(item.getInteger());
                break;
            }
        }
        // return the value's object not the item.
        // expressions NEVER handle DataValue objects.
        return removed != null ?
                removed.getObject() :
                null;
    }

	private static FunctionDefinition removeFunction()
	{
		return new InternalFunction("remove", "data", "key_index")
		{
			@Override
			public String describe()
			{
				return "remove the item for a key or index from the data set";
			}
			@Override
			public Object execute(DataSet arguments)
			{
                return DataFunctions.remove(
                        arguments.getDataSet("data"),
						arguments.get("key_index")
                );
			}
		};
	}

    private static Integer size(DataItem item)
    {
        if (item != null)
        {
            switch (item.getType())
            {
                case ARRAY      : return item.getArray().size();
                case DATA_SET   : return item.getDataSet().size();
                case STRING     : return item.getString().length();
            }
        }
        return null;
    }

	private static FunctionDefinition sizeFunction()
	{
		return new InternalFunction("size", "item")
        {
            @Override
			public String describe()
			{
				return "Get the size of a data set, array or string";
			}
			@Override
			public Integer execute(DataSet arguments)
			{
                return DataFunctions.size(arguments.get("item"));
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
            case STRING     : return data.getObject(value.getString());
            case INTEGER    : return data.get(value.getInteger()).getObject();
        }
        return null;
    }

    private static FunctionDefinition valueFunction()
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
