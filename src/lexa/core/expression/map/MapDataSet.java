/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lexa.core.expression.map;

import lexa.core.data.DataItem;
import lexa.core.data.DataSet;
import lexa.core.data.SimpleDataItem;
import lexa.core.data.SimpleDataSet;
import lexa.core.expression.ExpressionException;

/**
 *
 * @author william
 */
public class MapDataSet
		extends SimpleDataSet
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
	public DataSet clone()
	{
		return new MapDataSet(this);
	}
	
    /**
     *
     * @param key
     * @return
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
			ex.printStackTrace();
			value = ex; // rather messy
		}
		item = new SimpleDataItem(key,value);
		this.put(item);
		return item;
	}
}
