/*==============================================================================
 * Lexa - Property of William Norman-Walker
 *------------------------------------------------------------------------------
 * FunctionLibrary.java (lxExpression)
 *------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: September 2013
 *==============================================================================
 */
package lexa.core.expression.function;

import lexa.core.expression.function.standard.NullFunctions;
import lexa.core.expression.function.standard.DataFunctions;
import lexa.core.expression.function.standard.StringFunctions;
import lexa.core.expression.function.standard.MathsFunctions;
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
	private final Map<String, FunctionDefinition> functions;

	private FunctionLibrary()
			throws ExpressionException
	{
		this(null,null);
	}

    /**
     * Create a function library based on the defined functions in the data set
     *
     * <p>This creates a new library and uses the {@link FunctionLibrary#base() base}
     * as its parent.
     *
     * @param   functions
     *          a data set containing function definitions.
     * @throws  ExpressionException
     *          when an exception occurs parsing the functions
     */
    public FunctionLibrary(DataSet functions)
			throws ExpressionException
	{
		this(FunctionLibrary.base(), functions);
	}

    /**
     * Create an empty function library with the given library as a parent
     *
     * @param   parent
     *          the parent library.
     * @throws  ExpressionException
     *          when an error occurs adding the function
     */
    public FunctionLibrary(FunctionLibrary parent)
			throws ExpressionException
	{
		this(parent, null);
	}

    /**
     * Create a function library based on the defined functions in the data set
     * with the supplied parent
     *
     * @param   parent
     *          the parent library.
     * @param   functions
     *          a data set containing function definitions.
     * @throws  ExpressionException
     *          when an exception occurs parsing the functions
     */
    public FunctionLibrary(FunctionLibrary parent, DataSet functions)
			throws ExpressionException
	{
		this.parent = parent;
		this.functions = new HashMap<>();
		boolean bl = (parent == null);
		if (bl)
		{
			// only load annnoymous at the top of the stack:
			loadInternalFunctions();
		}
		this.baseLibrary = bl; // set AFTER loading
		this.addFunctions(functions);
	}

    /**
     * Add a function to the library
     * @param   function
     *          function definition to add
     * @throws  ExpressionException
     *          when an error occurs adding the function
     */
	public final void addFunction(FunctionDefinition function)
			throws ExpressionException
	{
		if (this.baseLibrary)
		{
			throw new ExpressionException("Cannot extend the base library");
		}
		this.functions.put(function.getName(), function);
		function.parse(this);
	}

    /**
     * Add a function to the library
     *
     * @param   name
     *          the name if the function
     * @param   data
     *          the definition, as a data set, for the function.
     * @throws ExpressionException
     *          when an error occurs adding the function
     */
    public final void addFunction(String name, DataSet data)
			throws ExpressionException
	{
		this.addFunction(new FunctionFromExpression(name, data));
	}

    /**
     * Add functions from a data set to the library
     * @param   data
     *          the definitions for the functions
     * @throws  ExpressionException
     *          when an error occurs adding the function
     */
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

    /**
     * Get a {@link FunctionDefinition} by its name
     *
     * <p>If the function is defined in this library then it is returned
     * otherwise the function is requested from the parent
     * @param   name
     *          the name of the required function
     * @return  the defined function.
     * @throws  ExpressionException
     *          when the function is not defined, or a {@code null} or empty
     *          name is supplied.
     */
    public final FunctionDefinition getFunction(String name)
			throws ExpressionException
	{
		if (name == null || "".equals(name)) {
			throw new ExpressionException("Function name missing");
		}
		FunctionDefinition f = this.functions.get(name);
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
		this.loadInternalFunctions(MathsFunctions.getFunctions());
		this.loadInternalFunctions(DataFunctions.getFunctions());
		this.loadInternalFunctions(StringFunctions.getFunctions());
	}

	private void loadInternalFunctions(FunctionDefinition[] functions)
			throws ExpressionException
	{
        for (FunctionDefinition function : functions)
        {
            this.addFunction(function);
        }
	}

    /**
     * Get the names of all the functions defined in this library
     *
     * @return  a list of function names
     */
    public Set<String> functions()
	{
		return new TreeSet<>(this.functions.keySet());
	}

    /**
     * Get the names of all the functions available from this library
     *
     * <p>This includes all the functions defined in this library and in and in
     * any parents up to, and including, the base library.
     *
     * @return  a list of function names
     */
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
