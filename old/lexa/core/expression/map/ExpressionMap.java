/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lexa.core.expression.map;

import java.util.*;
import lexa.core.data.DataItem;
import lexa.core.data.DataSet;
import lexa.core.expression.Expression;
import lexa.core.expression.ExpressionException;
import lexa.core.expression.function.FunctionLibrary;

/**
 *
 * @author william
 */
public class ExpressionMap
{
	private final Map<String,Expression> expressions;
	private final Map<String,ExpressionMap> children;

    /**
     *
     * @param map
     * @param library
     * @throws ExpressionException
     */
    public ExpressionMap(DataSet map, FunctionLibrary library) throws ExpressionException
	{
		this.expressions = new HashMap<>();
		this.children = new HashMap<>();
		for (DataItem item : map)
		{
			// only accept strings or DataSets:
			switch (item.getType())
			{
				case STRING :
				{
					this.expressions.put(item.getKey(), Expression.parse(item.getString(), library));
					break;
				}
				case DATA_SET :
				{
					this.children.put(item.getKey(), new ExpressionMap(item.getDataSet(), library));
					break;
				}
				default :
				{
					throw new ExpressionException("Invalid type in ExpressionMap :" + item.toString());
				}
			}
		}
	}
	
    /**
     *
     * @param key
     * @param data
     * @return
     * @throws ExpressionException
     */
    public Object evaluate(String key, MapDataSet data)
			throws ExpressionException
	{
		if (this.expressions.containsKey(key))
		{
			Expression expression = this.expressions.get(key);
			return expression.evaluate(data);
		}
		if (key.contains("."))
		{
			// split and search
			throw new UnsupportedOperationException("lexa.core.expression.map.ExpressionMap.evalute:Object call to child not supported yet.");
		}
		return null; // nothing to do
	}
}
