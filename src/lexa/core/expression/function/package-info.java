/*
 * ================================================================================
 * Lexa - Property of William Norman-Walker
 * --------------------------------------------------------------------------------
 * package-info.java
 *--------------------------------------------------------------------------------
 * Author:  William Norman-Walker
 * Created: September 2013
 *--------------------------------------------------------------------------------
 * Change Log
 * Date:        By: Ref:        Description:
 * ---------    --- ----------  --------------------------------------------------
 * -			-	-			-
 *================================================================================
 */
/**
 * Provides functional support for {@link lexa.core.expression.Expression} parsing.
 * <p>A function call in an expression is defined within square brackets with the name 
 * of the function and then any parameters.  The final parameter can be the wildcard
 * character, {@code ~}, to indicate an unknown number of arguments.  When calling a function
 * the wildcard can be used for all unknown arguments in the context data.  Within a function
 * it can be used to define all the data.  When the wildcard is used, the arguments can be 
 * individually addressed as {@code ~0}, {@code ~1}, etc.
 * <p>A simple function might be defined as:
 * <pre>
 * maxTwo {
 *   arguments a b
 *   expression "
 *     a > b ? a : b
 *   "
 * }
 * </pre>
 * This function would be called using:
 * <pre>
 * result = [maxTwo 10 15]
 * # result will equal 15
 * </pre>
 * 
 * <p>Functions can call themselves recursively, for example the extended maximum calculation used
 * in the tests for this package.  First there is a function that finds the maximum value of all
 * items in a set then there is a wrapper function that accepts limitless arguments to use the data:
 * <pre>
 * maxInSet {
 *   arguments - set
 *   expression "
 *     v0 = [data.value set 0];
 *	   ([data.size set] == 1) ?
 *	     v0 :
 *	     (
 *         data = [data.clone set];
 *         [data.remove data [data.key data 0]];
 *         max = [maxInSet data];
 *         (v0 > max) ?
 *           v0 :
 *           max
 *       )
 *   "
 * }
 * max {
 *   arguments - ~
 *   expression "
 *     [maxInSet ~]
 *   "
 * }</pre>
 * This function would be called using:
 * <pre>result = [max 10 15 30 20 45]</pre>
 * <p>The package provides several {@link lexa.core.expression.function.standard inbuilt functions}
 * @see lexa.core.expression
 */
package lexa.core.expression.function;
