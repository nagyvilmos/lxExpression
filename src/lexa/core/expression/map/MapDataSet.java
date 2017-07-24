/*==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * ExpressionMap.java (lxExpression)
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: July 2017
 *==============================================================================
 */
package lexa.core.expression.map;

import lexa.core.data.DataItem;
import lexa.core.data.DataSet;
import lexa.core.data.ArrayDataItem;
import lexa.core.data.ArrayDataSet;
import lexa.core.expression.ExpressionException;

/**
 * A {@link DataSet} backed by an {@link ExpressionMap}.
 * <br>
 * If a field does not exist in the map then it will be evaluate from the
 * provided expression map.
 * Each field is evaluated once and only once.
 *
 * @author  williamnw
 * @since   2017-07
 */
public class MapDataSet
		extends ArrayDataSet
{
	private final ExpressionMap expressionMap;

    /**
     * Create a map data set.
     * @param   expressionMap
     *          the map of expressions for the values
     * @param   data
     *          the data behind the expressions
     */
    public MapDataSet(ExpressionMap expressionMap, DataSet data)
	{
		super(data.factory().clone(data));
		this.expressionMap = expressionMap;
	}

    /**
     * Create a mapped data set as a clone of another
     * @param   clone
     *          the data to clone.
     */
    public MapDataSet(MapDataSet clone)
	{
		super(clone);
		this.expressionMap = clone.expressionMap;
	}

    /**
     * Get an item from the data set
     *
     * <p>If the item already exists then it's current value is used otherwise
     * if an expression exists, then that is used instead.
     * @param   key
     *          the key for the required item.
     * @return  the item corresponding to the key.
     */
    @Override
	public synchronized DataItem get(String key)
	{
		DataItem item = super.get(key);
		// the VALUE may be null but the ITEM exists.
		if (item != null)
		{
			return item;
		}
		// look in the expression map for value:
		Object value;
		try
		{
			value = this.expressionMap.evaluate(key,this);
		}
		catch (ExpressionException ex)
		{
			value = null;
		}
		item = new ArrayDataItem(key,value);
		this.put(item);
		return item;
	}

    /**
     * Evaluate all the expressions in the map.
     *
     * <p>Go through all the defined fields in the map and return their evaluated
     * values.
     * @return the result of all the evaluations
     */
    public DataSet evaluate()
    {
        DataSet data = new ArrayDataSet();

        for (String field : this.expressionMap.fields())
        {
            data.put(this.get(field));
        }
        for (String child : this.expressionMap.children())
        {
            MapDataSet childData = (MapDataSet)this.getDataSet(child);
            data.put(child, childData.evaluate());
        }
        return data;
    }
}
