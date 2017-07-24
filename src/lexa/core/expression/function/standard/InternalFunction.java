/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * InternalFunction.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: September 2013
 *================================================================================
 */
package lexa.core.expression.function.standard;

import lexa.core.expression.ExpressionException;
import lexa.core.expression.function.FunctionDefinition;
import lexa.core.expression.function.FunctionLibrary;

/**
 * An internal function definition.
 * @author william
 * @since 2013-09
 */
public abstract class InternalFunction
		extends FunctionDefinition
{

    /**
     * Create an internal function
     * @param name the name of the function
     * @param arguments any arguments for the function
     */
    InternalFunction(String name, String ... arguments)
	{
		super(name, arguments);
	}

	@Override
	public void parse(FunctionLibrary library)
			throws ExpressionException
	{
		// does not need to do anything;
	}


}
