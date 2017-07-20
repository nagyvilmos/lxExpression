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
     *
     * @param expressionMap
     * @param data
     */
    public MapDataSet(ExpressionMap expressionMap, DataSet data)
	{
		super(data);
		this.expressionMap = expressionMap;
	}

    /**
     *
     * @param clone
     */
    public MapDataSet(MapDataSet clone)
	{
		super(clone);
		this.expressionMap = clone.expressionMap;
	}

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
     * <br>
     * Go through all the defined fields in the map and return their evaluated
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
