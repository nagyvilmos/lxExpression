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

import java.util.*;
import lexa.core.data.DataItem;
import lexa.core.data.DataSet;
import lexa.core.expression.Expression;
import lexa.core.expression.ExpressionException;
import lexa.core.expression.function.FunctionLibrary;

/**
 * A data set created using expressions.
 * @author  william
 * @since   2017-07
 */
public class ExpressionMap
{
	private final Map<String,Expression> expressions;
	private final Map<String,ExpressionMap> children;

    /**
     * Create a new expression map.
     * <br>
     * The map is built from the contents of the {@link DataSet}. The content
     * may be either strings or embeded maps.
     * @param   map
     *          the definitions for the expression map
     * @throws  ExpressionException
     *          when an error occurs passing an expression.
     */
    public ExpressionMap(DataSet map) throws ExpressionException
	{
        this(map, FunctionLibrary.base());
    }

    /**
     * Create a new expression map.
     * <br>
     * The map is built from the contents of the {@link DataSet}. The content
     * may be either strings or embeded maps.
     * @param   map
     *          the definitions for the expression map
     * @param   library
     *          the library for evaluation the expressions
     * @throws  ExpressionException
     *          when an error occurs passing an expression.
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
     * Evaluate an expression from the map
     * @param   key
     *          the name of the expression
     * @param   data
     *          the input data for the expression
     * @return  the result of the expression.
     * @throws  ExpressionException
     *          when an error occurs adding the function
     */
    public Object evaluate(String key, MapDataSet data)
			throws ExpressionException
	{
		if (this.expressions.containsKey(key))
		{
			Expression expression = this.expressions.get(key);
			return expression.evaluate(data);
		}
        int split = key.indexOf(",");
		if (split > 1)
		{
            ExpressionMap child = this.children.get(key.substring(0, split));
            if (child != null)
            {
                return child.evaluate(key.substring(split+1), data);
            }
		}
		return null; // nothing to do
	}

    Set<String> children()
    {
        return this.children.keySet();
    }

    Set<String> fields()
    {
        return this.expressions.keySet();
    }
}
