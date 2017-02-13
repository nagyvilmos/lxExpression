/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * Function.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: September 2013
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * 2014.11.17	WNW				Add  describe() method.
 *================================================================================
 */
package lexa.core.expression.function;

import lexa.core.data.DataSet;
import lexa.core.data.SimpleDataSet;
import lexa.core.expression.Expression;
import lexa.core.expression.ExpressionException;

/**
 * Base class for a function to be used by the expressions engine.
 * <p> Link {@link lexa.core.expression.function.standard}
 * @author William
 * @since 2013-09
 */
public abstract class Function {

	/** the name of the function */
    private final String name;
	/** the names for the arguments */
    private final String[] arguments;
	private final String format;

	/**
	 * Create a new function.
	 * <p>This initiates the base settings for a function.
	 * <p>The arguments are a list of names; with the last optionally set to {@code *}
	 * for a variable number of arguments.
	 * @param name the name of the function.
	 * @param arguments space separated list of arguments for the function.
	 */
    public Function(String name, String arguments) {
        this(name,
                arguments == null ?
                        null :
                        arguments.split(" "));
    }

	/**
	 * Create a new function.
	 * <p>This initiates the base settings for a function.
	 * <p>The arguments are a list of names; with the last optionally set to {@code *}
	 * for a variable number of arguments.
	 * @param name the name of the function.
	 * @param arguments a list of arguments for the function.
	 */
	public Function(String name, String ... arguments) {
        this.name = name;
        this.arguments = arguments;
		String f = '[' + name;
		for (String a : arguments)
		{
			f = f + ' ' + a;
		}
		this.format = f + ']';
    }

	/**
	Check that all the arguments are defined.
	@param arguments argument data for the function 
	@throws ExpressionException when an argument is missing.
	*/
    private void checkArguments(DataSet arguments)
            throws ExpressionException {
        int count = this.getArgumentCount();
        for (int a = 0;
                a < count;
                a++) {
            if(!arguments.contains(this.arguments[a]) &&
					!"~".equals(this.arguments[a])) {
                throw new ExpressionException("Missing function argument " + this.arguments[a]);
            }
        }
    }

	/**
	Gets the number of arguments defined for the function. 
	@return the number of arguments required.
	*/
    public int getArgumentCount() {
        return this.arguments == null ? 0 :
                this.arguments.length;
    }

	/**
	 * Evaluate the function.
	 * <p>The arguments are checked and then the function is executed.
	 * @param data source data for function
	 * @param argumentExpressions the arguments as listed by the function definition
	 * @return the result of evaluating the function.
	 * 
	 * @throws ExpressionException when the function fails.
	 */
    public Object evaluate(DataSet data, Expression[] argumentExpressions)
            throws ExpressionException {
		DataSet argData = new SimpleDataSet();
		int max = this.getArgumentCount()-1;
		for (int a =0;
				a < this.getArgumentCount();
				a++) 
		{
			if (a < max || !"~".equals(this.arguments[a])) {
				String k = this.arguments[a];
				Object v = argumentExpressions[a].evaluate(data);
				argData.put(k,v);
			} else {
				for (int i = 0;
						a+i < argumentExpressions.length;
						i++)
				{
					Object v = argumentExpressions[a+i].evaluate(data);
					argData.put("~" + i,v);
				}
			}
		}
        return this.evaluate(argData);
	}
	
	/**
	Evaluate the function.
	<p>The arguments are checked and then the function is executed.
	@param arguments argument data for the function 
	@return the result of the function
	@throws ExpressionException when the evaluation fails
	@see Function#execute(lexa.core.data.DataSet) 
	*/
    public Object evaluate(DataSet arguments)
            throws ExpressionException {
		this.checkArguments(arguments);
        return this.execute(arguments);
    }

	/**
	Execute the function
	@param arguments argument data for the function 
	@return the result of the function
	@throws ExpressionException when the evaluation fails
	*/
    public abstract Object execute(DataSet arguments)
            throws ExpressionException;

	/**
	Parse the function for valid syntax for the current library.
	@param library
	@throws ExpressionException 
	*/
	public abstract void parse(FunctionLibrary library) 
			throws ExpressionException;
	
	/**
	Get the name of the function
	@return the name of the function
	*/
    public String getName() {
        return this.name;
    }

	/**
	Get the function's string representation.
	@return the function's string representation
	*/
    @Override
    public String toString() {
        return "function." + this.name;
    }
	/**
	Describe what a function does
	@return a description of the function
	*/
	public abstract String describe();
	/**
	Describe what a function does
	@return a description of the function
	*/
	public String format()
	{
		return this.format;
	}

    /**
     *
     * @return
     */
    public String help()
	{
		return this.format() + '\n' + this.describe();
	}
}