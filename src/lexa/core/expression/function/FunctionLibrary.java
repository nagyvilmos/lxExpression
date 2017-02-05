/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * FunctionLibrary.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: September 2013
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 2014.11.17	WNW				Ratioanlise import statements.
 * 2014.11.17	WNW				Make sure all javadoc is upto date.
 *================================================================================
 */
package lexa.core.expression.function;

import lexa.core.expression.function.standard.NullFunctions;
import lexa.core.expression.function.standard.DataFunctions;
import lexa.core.expression.function.standard.StringFunctions;
import lexa.core.expression.function.standard.MathFunctions;
import java.util.*;
import lexa.core.data.DataItem;
import lexa.core.data.DataSet;
import lexa.core.expression.ExpressionException;

/**
Provides a library of functions to be used for expression evaluation.
<p>Each library, except the common base, has a parent.  When evaluating a function, the library
is checked and if the function does not exist, the parent is checked.
<p>The base library contains {@link lexa.core.expression.function.standard in built functions}.
@author William
@since 2013-09
 */
public final class FunctionLibrary
{
	/** base library for inbuilt functions */
	private static FunctionLibrary base;
	/**
	Set up the static data.
	*/
	static {
		FunctionLibrary fl = null;
		try
		{
			fl = new FunctionLibrary();
		}
		catch (ExpressionException ex)
		{
			throw new InstantiationError(ex.getMessage());
		}
		FunctionLibrary.base = fl;
	}

	/**
	Get the base library of inbuilt functions
	@return the base library 
	*/
	public static FunctionLibrary base()
	{
		return FunctionLibrary.base;
	}

	/** parent library */
	private final FunctionLibrary parent;
	/** indicates if this is the base library */
	private final boolean baseLibrary;
	/** Functions supported by this library */
	private final Map<String, Function> functions;

	private FunctionLibrary()
			throws ExpressionException
	{
		this(null,null);
	}

	public FunctionLibrary(DataSet functions)
			throws ExpressionException
	{
		this(FunctionLibrary.base(), functions);
	}

	public FunctionLibrary(FunctionLibrary parent)
			throws ExpressionException
	{
		this(parent, null);
	}

	public FunctionLibrary(FunctionLibrary parent, DataSet functions)
			throws ExpressionException
	{
		this.parent = parent;
		this.functions = new HashMap();
		boolean bl = (parent == null);
		if (bl)
		{
			// only load annnoymous at the top of the stack:
			loadInternalFunctions();
		}
		this.baseLibrary = bl; // set AFTER loading
		this.addFunctions(functions);
	}

	/* TODO Make maps work:
	public final void addExpressionMap(ExpressionMap map)
			throws ExpressionException
	{
		this.expressionMaps.put(map.getName(), map);
		map.parse(this.parser);
	}

	public final void addExpressionMap(String name, DataSet data)
			throws ExpressionException
	{
		this.addExpressionMap(new ExpressionMap( name, data));
	}
	public final void addExpressionMaps(DataSet data)
			throws ExpressionException
	{
		if (data == null)
		{
			return; // elegant exit
		}
		for (DataItem item
				: data)
		{
			this.addExpressionMap(item.getKey(), item.getDataSet());
		}
	}
	*/
	
	public final void addFunction(Function function)
			throws ExpressionException
	{
		if (this.baseLibrary)
		{
			throw new ExpressionException("Cannot extend the base library");
		}
		this.functions.put(function.getName(), function);
		function.parse(this);
	}

	public final void addFunction(String name, DataSet data)
			throws ExpressionException
	{
		this.addFunction(new FunctionFromExpression( name, data));
	}
	public final void addFunctions(DataSet data)
			throws ExpressionException
	{
		if (data == null)
		{
			return; // elegant exit
		}
		for (DataItem item
				: data)
		{
			this.addFunction(item.getKey(), item.getDataSet());
		}
	}

	public final Function getFunction(String name)
			throws ExpressionException
	{
		if (name == null || "".equals(name)) {
			throw new ExpressionException("Function name missing");
		}
		Function f = this.functions.get(name);
		if (f != null)
		{
			return f;
		}
		if (!this.baseLibrary)
		{
			return this.parent.getFunction(name);
		}
		
		throw new ExpressionException("Function not defined : " + name + '\n' + 
				this.allFunctions().toString());
	}

	private void loadInternalFunctions()
			throws ExpressionException
	{
		this.loadInternalFunctions(NullFunctions.getFunctions());
		this.loadInternalFunctions(MathFunctions.getFunctions());
		this.loadInternalFunctions(DataFunctions.getFunctions());
		this.loadInternalFunctions(StringFunctions.getFunctions());
	}

	private void loadInternalFunctions(Function[] functions)
			throws ExpressionException
	{
		for (int f = 0;
				f < functions.length;
				f++) {
			this.addFunction(functions[f]);
		}
	}

	public Set<String> functions()
	{
		return new TreeSet(this.functions.keySet());
	}
	public Set<String> allFunctions()
	{
		Set<String> fl =  this.functions();
		if (this.parent != null) 
		{
			fl.addAll(this.parent.allFunctions());
		}
		return fl;
	}
}
